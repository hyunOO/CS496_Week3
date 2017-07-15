package com.cs496.cs496_week3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab1RoomList extends Fragment {
    RoomListAdapter adapter = new RoomListAdapter();
    View view, view2;
    static FirstPageFragmentListener firstPageListener;

    public Tab1RoomList() {
    }

    public Tab1RoomList(FirstPageFragmentListener listener) {
        firstPageListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_roomlist, null);

        final ListView listview = (ListView) view.findViewById(R.id.roomlistListView);
        listview.setAdapter(adapter);

        LoadRoomList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("listview click", "clicked " + i + "th item in listview");

                TextView clicked_title = (TextView) view.findViewById(R.id.title);
                TextView clicked_mealType = (TextView) view.findViewById(R.id.mealType);
                TextView clicked_makerId = (TextView) view.findViewById(R.id.makerId);
                TextView clicked_current = (TextView) view.findViewById(R.id.current);
                TextView clicked_roomId = (TextView) view.findViewById(R.id.roomId);
                RoomInfo.roomId = clicked_roomId.getText().toString();

                firstPageListener.onSwitchToNextFragment();

                TextView roominfo_title = (TextView) RoomInfo.view.findViewById(R.id.roominfo_title);
                TextView roominfo_mealType = (TextView) RoomInfo.view.findViewById(R.id.roominfo_mealType);
                TextView roominfo_makerId = (TextView) RoomInfo.view.findViewById(R.id.roominfo_makerId);
                TextView roominfo_current = (TextView) RoomInfo.view.findViewById(R.id.roominfo_current);

                roominfo_title.setText(clicked_title.getText().toString());
                roominfo_mealType.setText(clicked_mealType.getText().toString());
                roominfo_makerId.setText(clicked_makerId.getText().toString());
                roominfo_current.setText(clicked_current.getText().toString());

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) getView().findViewById(R.id.showRoom);
        swipeView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark), getResources().getColor(android.R.color.holo_blue_light), getResources().getColor(android.R.color.holo_green_dark));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadRoomList();
                        swipeView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void LoadRoomList() {
        adapter.clearAdapter();

        JSONArray JSONroomList = null;
        CustomThread thread = new CustomThread();
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONroomList = thread.getResult();

        for (int i = 0; i < JSONroomList.length(); i++) {
            JSONObject room = null;
            try {
                room = JSONroomList.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Room newroom = new Room();
            try {
                newroom.setTitle(room.getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                newroom.setMakerId(room.getString("makerId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                newroom.setMealType(room.getString("mealType"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                newroom.setCurrent(room.getInt("maxUser"), room.getInt("currentUser"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                newroom.setRoomId(room.getString("_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.add(newroom);
        }
    }

    public class GetRoomList {
        OkHttpClient client = new OkHttpClient();

        String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        }
    }

    public class CustomThread extends Thread {
        JSONArray jsonarray = null;
        final GetRoomList example = new GetRoomList();

        public CustomThread() {
            jsonarray = null;
        }

        @Override
        public void run() {
            String response = null;
            try {
                response = example.get("http://13.124.143.15:10001/room");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jsonarray = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public JSONArray getResult() {
            return jsonarray;
        }
    }

}
