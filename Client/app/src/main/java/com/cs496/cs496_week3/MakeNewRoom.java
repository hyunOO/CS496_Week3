package com.cs496.cs496_week3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by rongrong on 2017-07-17.
 */

public class MakeNewRoom extends AppCompatActivity {

    String mealType_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_new_room);

        Spinner makeNewRoomSpinner = (Spinner) findViewById(R.id.makeNewRoomMealType);
        final ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this, R.array.mealType, R.layout.support_simple_spinner_dropdown_item);
        makeNewRoomSpinner.setAdapter(sAdapter);
        makeNewRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mealType_content = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button makeNewRoomConfirm = (Button) findViewById(R.id.makeNewRoomConfirm);
        Button makeNewRoomCancel = (Button) findViewById(R.id.makeNewRoomCancel);

        makeNewRoomConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText makeNewRoomTitle = (EditText) findViewById(R.id.makeNewRoomTitle);
                EditText makeNewRoomMaxUser = (EditText) findViewById(R.id.makeNewRoomMaxUser);

                String title_content = makeNewRoomTitle.getText().toString();
                String maxUser_content = makeNewRoomMaxUser.getText().toString();

                if (title_content.equals("")) {
                    Toast.makeText(MakeNewRoom.this, "방 제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (maxUser_content.equals("")) {
                    Toast.makeText(MakeNewRoom.this, "최대 인원을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(maxUser_content) < 3) {
                    Toast.makeText(MakeNewRoom.this, "최대 인원은 3인 이상이어야 합니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject bodyContent = new JSONObject();
                try {
                    bodyContent.put("makerId", Login.id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    bodyContent.put("title", title_content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    bodyContent.put("mealType", mealType_content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    bodyContent.put("maxUser", Integer.parseInt(maxUser_content));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CustomThread thread = new CustomThread(bodyContent);
                thread.start();
                try {
                    thread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JSONObject makeNewRoomResult = null;
                try {
                    makeNewRoomResult = new JSONObject(thread.getResult());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Integer HTTPresult = null;
                try {
                    HTTPresult = makeNewRoomResult.getInt("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (HTTPresult == 1) {
                    Toast.makeText(MakeNewRoom.this, "새로운 방을 만들었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MakeNewRoom.this, "새로운 방을 만드는데 실패하였습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

        makeNewRoomCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public class PostNewRoom {
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    public class CustomThread extends Thread {
        JSONObject bodyContent;
        String response;
        final PostNewRoom example = new PostNewRoom();

        public CustomThread(JSONObject bodyContent) {
            this.bodyContent = bodyContent;
        }

        @Override
        public void run() {
            try {
                response = example.post("http://13.124.143.15:10001/room", bodyContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getResult() {
            return response;
        }
    }

}
