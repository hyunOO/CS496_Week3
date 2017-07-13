package com.cs496.cs496_week3;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab2MyRoom extends Fragment{
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_message, null);
        return view;
    }
}
