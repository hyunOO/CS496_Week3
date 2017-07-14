package com.cs496.cs496_week3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab1RoomList extends Fragment {
    View view;
    static FirstPageFragmentListener firstPageListener;

    public Tab1RoomList() {
    }

    public Tab1RoomList(FirstPageFragmentListener listener) {
        firstPageListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_roomlist, null);

        ArrayList<Room> roomList = new ArrayList<>();
        Room room1 = new Room();
        room1.setTitle("전산과 모여라!");
        room1.setMakerId("rongrong");
        room1.setMealType("상관없음");
        room1.setCurrent(4, 3);
        roomList.add(room1);
        roomList.add(room1);
        roomList.add(room1);
        roomList.add(room1);
        roomList.add(room1);
        roomList.add(room1);


        ListView listview = (ListView) view.findViewById(R.id.roomlistListView);
        RoomListAdapter adapter = new RoomListAdapter(roomList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("listview click", "clicked " + i + "th item in listview");
                firstPageListener.onSwitchToNextFragment();
            }
        });

        return view;
    }

}
