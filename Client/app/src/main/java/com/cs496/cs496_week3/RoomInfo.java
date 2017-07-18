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

public class RoomInfo extends Fragment {
    UserListAdapter adapter = new UserListAdapter();
    static View view;
    static String roomId = null;
    static String makerId = null;
    static FirstPageFragmentListener firstPageListener;

    public RoomInfo() {
    }

    public RoomInfo(FirstPageFragmentListener listener) {
        firstPageListener = listener;
    }

    public void backPressed() {
        firstPageListener.onSwitchToNextFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.room_info, null);

        final ListView listview = (ListView) view.findViewById(R.id.userlistListView);
        listview.setAdapter(adapter);

        LoadUserList();

        final Button sendRequest = (Button) view.findViewById(R.id.sendRequest);

        String status = checkStatus();

        switch (status) {
            case "BANGJANG":
                sendRequest.setText("이 방의 방장입니다");
                sendRequest.setEnabled(false);
                sendRequest.setBackgroundColor(getResources().getColor(R.color.nonClickable));
                break;
            case "MEMBER":
                sendRequest.setText("이미 가입된 방입니다");
                sendRequest.setEnabled(false);
                sendRequest.setBackgroundColor(getResources().getColor(R.color.nonClickable));
                break;
            case "REJECT":
                sendRequest.setText("가입이 거부되었습니다");
                sendRequest.setEnabled(false);
                sendRequest.setBackgroundColor(getResources().getColor(R.color.nonClickable));
                break;
            case "WAITING":
                sendRequest.setText("승인 대기중");
                sendRequest.setEnabled(false);
                sendRequest.setBackgroundColor(getResources().getColor(R.color.nonClickable));
                break;
            case "NOTHING":
                sendRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject bodyContent = new JSONObject();
                        try {
                            bodyContent.put("roomId", roomId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            bodyContent.put("requester", Login.id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            bodyContent.put("bangjang", makerId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            bodyContent.put("bangjangRead", false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            bodyContent.put("accept", false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PostNewMessageThread post_thread = new PostNewMessageThread(bodyContent);
                        post_thread.start();
                        try {
                            post_thread.join();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        JSONObject makeNewMessageResult = null;
                        try {
                            makeNewMessageResult = new JSONObject(post_thread.getResult());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Integer postHTTPresult = null;
                        try {
                            postHTTPresult = makeNewMessageResult.getInt("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (postHTTPresult == 1) {
                            Toast.makeText(getContext(), "가입요청을 보냈습니다", Toast.LENGTH_SHORT).show();
                            sendRequest.setText("승인 대기중");
                            sendRequest.setEnabled(false);
                            sendRequest.setBackgroundColor(getResources().getColor(R.color.nonClickable));
                        } else {
                            Toast.makeText(getContext(), "가입요청을 보내는데 실패하였습니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                break;
            default:
                break;
        }

        Button btnReturn = (Button) view.findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstPageListener.onSwitchToNextFragment();
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

    public String checkStatus() {
        // 방장 체크
        if (makerId.equals(Login.id)) return "BANGJANG";
        // 가입되있는지 체크
        if (adapter.checkUser(Login.id)) return "MEMBER";
        // 거부되었는지 체크
        GetMessageThread get_thread = new GetMessageThread(true);
        get_thread.start();
        try {
            get_thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject trueCheckResult = get_thread.getResult();
        try {
            trueCheckResult.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
            return "REJECT";
        }

        // 승인 대기중 체크
        GetMessageThread get_thread2 = new GetMessageThread(false);
        get_thread2.start();
        try {
            get_thread2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject falseCheckResult = get_thread2.getResult();
        try {
            falseCheckResult.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
            return "WAITING";
        }

        // 나머지에 가입요청
        return "NOTHING";
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

    public class PostNewMessage {
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    public class PostNewMessageThread extends Thread {
        JSONObject bodyContent;
        String response;
        final PostNewMessage example = new PostNewMessage();

        public PostNewMessageThread(JSONObject bodyContent) {
            this.bodyContent = bodyContent;
        }

        @Override
        public void run() {
            try {
                response = example.post("http://13.124.143.15:10001/message", bodyContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getResult() {
            return response;
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

    public class GetMessageThread extends Thread {
        Boolean bangjangRead = null;
        JSONObject jsonobject = new JSONObject();
        final GetMessage example = new GetMessage();

        public GetMessageThread(Boolean bangjangRead) {
            this.bangjangRead = bangjangRead;
        }

        @Override
        public void run() {
            String response = null;
            try {
                response = example.get("http://13.124.143.15:10001/message/specific/" + roomId + "/" + Login.id + "/" + String.valueOf(bangjangRead));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jsonobject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public JSONObject getResult() {
            return jsonobject;
        }
    }
}
