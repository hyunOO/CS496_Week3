package com.cs496.cs496_week3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by rongrong on 2017-07-14.
 */

public class RoomInfo2 extends Fragment {
    UserListAdapter adapter = new UserListAdapter();
    static View view;
    static String roomId = null;
    static String makerId = null;
    static SecondPageFragmentListener secondPageListener;

    public RoomInfo2() {
    }

    public RoomInfo2(SecondPageFragmentListener listener) {
        secondPageListener = listener;
    }

    public void backPressed() {
        secondPageListener.onSwitchToNextFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.room_info, null);

        final ListView listview = (ListView) view.findViewById(R.id.userlistListView);
        listview.setAdapter(adapter);

        LoadUserList();

        Button enterChat = (Button) view.findViewById(R.id.sendRequest);
        enterChat.setText("채팅하기");

        Button btnReturn = (Button) view.findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondPageListener.onSwitchToNextFragment();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) getView().findViewById(R.id.showUserList);
        swipeView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark), getResources().getColor(android.R.color.holo_blue_light), getResources().getColor(android.R.color.holo_green_dark));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadUserList();
                        swipeView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void LoadUserList() {
        adapter.clearAdapter();

        JSONArray JSONuserList = null;
        GetUserListThread thread = new GetUserListThread();
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONuserList = thread.getResult();

        for (int i = 0; i < JSONuserList.length(); i++) {
            JSONObject user = null;
            try {
                user = JSONuserList.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            User newuser = new User();
            try {
                newuser.setId(user.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                newuser.setDepartment(user.getString("department"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                newuser.setCircle(user.getString("circle"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                newuser.setHobby(user.getString("hobby"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.add(newuser);
        }
    }

    public class GetUserList {
        OkHttpClient client = new OkHttpClient();

        String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        }
    }

    public class GetUserListThread extends Thread {
        JSONArray jsonarray = null;
        final GetUserList example = new GetUserList();

        public GetUserListThread() {
            jsonarray = null;
        }

        @Override
        public void run() {
            String response = null;
            try {
                response = example.get("http://13.124.143.15:10001/room/" + roomId + "/userlist");
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

    public class GetMessage {
        OkHttpClient client = new OkHttpClient();

        String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        }
    }

}
