package com.cs496.cs496_week3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.HttpUrl;

/**
 * Created by q on 2017-07-15.
 */

public class Sign extends AppCompatActivity {
    public static String id = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_layout);

        Spinner spinner = (Spinner) findViewById(R.id.departmentselect);
        ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this, R.array.department, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);


    }
}
