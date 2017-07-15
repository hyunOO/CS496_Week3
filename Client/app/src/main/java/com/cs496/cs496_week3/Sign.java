package com.cs496.cs496_week3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by q on 2017-07-15.
 */

public class Sign extends AppCompatActivity {
    public static String id = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_layout);
        final TextView txt = (TextView) findViewById(R.id.hiden);
        final TextView txthiden = (TextView) findViewById(R.id.hiden2);
        txthiden.setText("true");
        txt.setVisibility(View.GONE);
        txthiden.setVisibility(View.GONE);
        final Context mContext = this;

        final Spinner spinner = (Spinner) findViewById(R.id.departmentselect);
        final ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this, R.array.department, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                txt.setText(""+parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button dup_btn = (Button) findViewById(R.id.duplicate);
        dup_btn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText txt1 = (EditText) findViewById(R.id.id);
                String id = txt1.getText().toString();
                if(id.equals("")){
                    Toast.makeText(mContext, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else{
                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://13.124.143.15:10001/user/id/"+id).newBuilder();
                    String url_new = urlBuilder.build().toString();
                    GetHandler handler = new GetHandler();
                    String result = null;
                    try{
                        result = handler.execute(url_new).get();
                        if(result == null && !id.equals("")){
                            Toast.makeText(mContext, "사용할 수 있는 아이디입니다.", Toast.LENGTH_LONG).show();
                            txthiden.setText("false");
                        }
                        else{
                            Toast.makeText(mContext, "이미 있는 아이디입니다.", Toast.LENGTH_LONG).show();
                            txthiden.setText("true");
                        }
                    }
                    catch (Exception e){

                    }
                }
            }
        }));

        Button btn = (Button) findViewById(R.id.press);
        btn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(txthiden.getText().toString().equals("true")){
                    Toast.makeText(mContext, "아이디 중복확인을 받으세요.", Toast.LENGTH_LONG).show();
                }
                else{
                    EditText txt1 = (EditText) findViewById(R.id.id);
                    EditText txt2 = (EditText) findViewById(R.id.pw);
                    EditText txt3 = (EditText) findViewById(R.id.circleselect);
                    EditText txt4 = (EditText) findViewById(R.id.hobbyselect);

                    String id = txt1.getText().toString();
                    String pw = txt2.getText().toString();
                    String department = txt.getText().toString();
                    String circle = txt3.getText().toString();
                    String hobby = txt4.getText().toString();
                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://13.124.143.15:10001/user/").newBuilder();
                    String url_new = urlBuilder.build().toString();
                    PostHandler handler = new PostHandler(id, pw, department, circle, hobby);
                    String result = null;
                    try{
                        result = handler.execute(url_new).toString();
                        Toast.makeText(mContext, "회원 가입 완료되었습니다.", Toast.LENGTH_LONG).show();
                        String[] tag = {};
                        Intent intent = new Intent(Sign.this, MainActivity.class);
                        Login.id = id;
                        intent.putExtra("id", id);
                        intent.putExtra("department", department);
                        intent.putExtra("circle", circle);
                        intent.putExtra("hobby", hobby);
                        intent.putExtra("tag", tag);
                        startActivity(intent);
                        finish();
                    }
                    catch (Exception e){

                    }
                }
            }
        }));
    }

    public class GetHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        public GetHandler() {
        }
        @Override
        protected String doInBackground(String... params) {
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.toString());
                return response.body().string();
            } catch (Exception e) {
            }
            return null;
        }
    }

    public class PostHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        String id, pw, department, circle, hobby;
        public PostHandler(String id, String pw, String department, String circle, String hobby) {
            this.id = id;
            this.pw = pw;
            this.department = department;
            this.circle = circle;
            this.hobby = hobby;
        }
        @Override
        protected String doInBackground(String... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("id", id)
                    .add("pw", pw)
                    .add("department", department)
                    .add("circle", circle)
                    .add("hobby", hobby)
                    .build();
            Request request = new Request.Builder()
                    .url(params[0]).post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.toString());
                return response.body().string();
            } catch (Exception e) {}
            return null;
        }
    }
}
