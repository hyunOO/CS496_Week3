package com.cs496.cs496_week3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import android.os.Handler;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab1RoomList extends Fragment {
    RoomListAdapter adapter = new RoomListAdapter();
    View view;
    static FirstPageFragmentListener firstPageListener;

    public Tab1RoomList() {
    }

    public Tab1RoomList(FirstPageFragmentListener listener) {
        firstPageListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_roomlist, null);

        ListView listview = (ListView) view.findViewById(R.id.roomlistListView);
        listview.setAdapter(adapter);

        Room room1 = new Room();
        room1.setTitle("전산과 모여라!");
        room1.setMakerId("rongrong");
        room1.setMealType("상관없음");
        room1.setCurrent(4, 3);
        adapter.add(room1);
        adapter.add(room1);
        adapter.add(room1);
        adapter.add(room1);
        adapter.add(room1);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("listview click", "clicked " + i + "th item in listview");
                firstPageListener.onSwitchToNextFragment();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) getView().findViewById(R.id.showRoom);
        swipeView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark), getResources().getColor(android.R.color.holo_blue_light), getResources().getColor(android.R.color.holo_green_dark));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                swipeView.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        swipeView.setRefreshing(false);
                        Room room1 = new Room();
                        room1.setTitle("전산과 엠티!");
                        room1.setMakerId("mtmtmt");
                        room1.setMealType("마시고 죽자");
                        room1.setCurrent(4, 3);
                        adapter.add(room1);
                    }
                }, 1000);
            }
        });
    }
}
