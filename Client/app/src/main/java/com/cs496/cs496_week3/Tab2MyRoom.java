package com.cs496.cs496_week3;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab2MyRoom extends Fragment{
    static RoomListAdapter adapter = new RoomListAdapter();
    View view;
    EditText searchMyRoom;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_myroom, null);

        final ListView listview = (ListView) view.findViewById(R.id.myroomlistListView);
        listview.setAdapter(adapter);

        LoadMyRoomList();

        searchMyRoom = (EditText) view.findViewById(R.id.searchMyRoom);

        searchMyRoom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if(motionEvent.getRawX() >= searchRoom.getCompoundDrawables()[2].getBounds().width()) {
                    if (motionEvent.getRawX() >= searchMyRoom.getRight() - searchMyRoom.getCompoundDrawables()[2].getBounds().width()) {
                        searchMyRoom.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        searchMyRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search_text = editable.toString();
                adapter.filter(search_text);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadMyRoomList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) getView().findViewById(R.id.showMyRoom);
        swipeView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark), getResources().getColor(android.R.color.holo_blue_light), getResources().getColor(android.R.color.holo_green_dark));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadMyRoomList();
                        adapter.filter(searchMyRoom.getText().toString());
                        swipeView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void LoadMyRoomList() {
        adapter.clearAdapter();

        JSONArray JSONmyroomList = null;
        CustomThread thread = new CustomThread();
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONmyroomList = thread.getResult();

        for (int i = 0; i < JSONmyroomList.length(); i++) {
            JSONObject room = null;
            try {
                room = JSONmyroomList.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String userList = null;
            try {
                userList = room.getString("userList");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (userList.contains("\""+Login.id+"\"")) {
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
    }

    public class GetMyRoomList {
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
        final GetMyRoomList example = new GetMyRoomList();

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
