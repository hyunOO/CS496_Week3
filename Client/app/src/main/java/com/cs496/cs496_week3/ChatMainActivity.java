package com.cs496.cs496_week3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rongrong on 2017-07-18.
 */

public class ChatMainActivity extends AppCompatActivity {

    static String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_main);

        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");

    }
}
