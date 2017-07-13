package com.cs496.cs496_week3;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button search = (Button) findViewById(R.id.searchFragment);
        Button room = (Button) findViewById(R.id.myRoomFragment);
        Button message = (Button) findViewById(R.id.messageFragment);

        search.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        room.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        message.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
    }
}
