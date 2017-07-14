package com.cs496.cs496_week3;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab2MyRoom extends Fragment{
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_myroom, null);

        ArrayList<Room> roomList = new ArrayList<>();
        Room room1 = new Room();
        room1.setTitle("전산과 모여라!");
        room1.setMakerId("rongrong");
        room1.setMealType("상관없음");
        room1.setCurrent(4, 3);


        ListView listview = (ListView) view.findViewById(R.id.roomlistListView);
        RoomListAdapter adapter = new RoomListAdapter();
        listview.setAdapter(adapter);

        adapter.add(room1);
        adapter.add(room1);
        adapter.add(room1);
        adapter.add(room1);
        adapter.add(room1);


        return view;
    }
}
