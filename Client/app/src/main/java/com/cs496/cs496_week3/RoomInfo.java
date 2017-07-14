package com.cs496.cs496_week3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by rongrong on 2017-07-14.
 */

public class RoomInfo extends Fragment {
    View view;
    static FirstPageFragmentListener firstPageListener;

    public RoomInfo(){
    }

    public RoomInfo(FirstPageFragmentListener listener){
        firstPageListener = listener;
    }

    public void backPressed() {
        firstPageListener.onSwitchToNextFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.room_info, null);

        Button btnReturn = (Button) view.findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstPageListener.onSwitchToNextFragment();
            }
        });

        return view;
    }
}
