package com.cs496.cs496_week3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by q on 2017-07-15.
 */

public class Login extends AppCompatActivity {
    public static String id = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        final Context mContext = this;

        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.main_sign_layout);
        linearLayout1.setVisibility(View.GONE);

        Button login_btn = (Button) findViewById(R.id.login);
        login_btn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText txt1 = (EditText) findViewById(R.id.enterId);
                String str = txt1.getText().toString();
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://13.124.143.15:10001/user/id/"+str).newBuilder();
                String url = urlBuilder.build().toString();
                GetHandler handler = new GetHandler();
                try{
                    String res = handler.execute(url).get();
                    if(res == null){
                        Toast.makeText(mContext, "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        JSONObject json = new JSONObject(res);
                        id = json.getString("id");
                        String department = json.getString("department");
                        String circle = json.getString("circle");
                        String hobby = json.getString("hobby");
                        String tag = json.getString("tag");
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("department", department);
                        intent.putExtra("circle", circle);
                        intent.putExtra("hobby", hobby);
                        intent.putExtra("tag", tag);
                        startActivity(intent);
                    }
                }
                catch (Exception e){
                }
            }
        }));

        Button sign_btn = (Button) findViewById(R.id.sign);
        sign_btn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Login.this, Sign.class);
                startActivity(intent);
                /*
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_login_layout);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
                params.topMargin = 50;
                params.bottomMargin = 0;
                linearLayout.setLayoutParams(params);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_login_layout);
                linearLayout.setVisibility(View.GONE);
                LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.main_sign_layout);
                linearLayout1.setVisibility(View.VISIBLE);
                Spinner spinner = (Spinner) findViewById(R.id.departmentselect);
                ArrayAdapter sAdapter = ArrayAdapter.createFromResource(mContext, R.array.department, R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(sAdapter);
                */
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
}
