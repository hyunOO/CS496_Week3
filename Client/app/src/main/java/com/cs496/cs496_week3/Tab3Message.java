package com.cs496.cs496_week3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab3Message extends Fragment {
    final MessageAdapter adapter = new MessageAdapter();
    View view;
    Runnable mTimerTask;
    Handler mHandler;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_message, null);
        ListView listview = (ListView) view.findViewById(R.id.messageListView);
        listview.setAdapter(adapter);

        /*
        timer = new Timer();
        timerTask = new TimerTask(){
            public void run(){
                LoadMessage(adapter);
            }
        };
        timer.schedule(timerTask, 0, 10);
        */
        LoadMessage();
        adapter.sortArray();
        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) getView().findViewById(R.id.showMessage);
        swipeView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark), getResources().getColor(android.R.color.holo_blue_light), getResources().getColor(android.R.color.holo_green_dark));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadMessage();
                        adapter.sortArray();
                        adapter.notifyDataSetChanged();
                        swipeView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void LoadMessage() {
        adapter.clearAll();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://13.124.143.15:10001/message/bangjang/" + Login.id).newBuilder();
        String url = urlBuilder.build().toString();
        GetHandler handler = new GetHandler();
        try {
            String res = handler.execute(url).get();
            JSONArray jsonArray = new JSONArray(res);
            if (res == null) {
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String roomId = jsonArray.getJSONObject(i).getString("roomId");
                    String requester = jsonArray.getJSONObject(i).getString("requester");
                    String bangjang = jsonArray.getJSONObject(i).getString("bangjang");
                    boolean banjangRead = jsonArray.getJSONObject(i).getBoolean("bangjangRead");
                    boolean accept = jsonArray.getJSONObject(i).getBoolean("accept");
                    Message message = new Message(roomId, "Hello", requester, bangjang, banjangRead, accept);

                    String messageId = jsonArray.getJSONObject(i).getString("_id");
                    message.setMessageId(messageId);
                    adapter.add(message);
                }
            }
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        HttpUrl.Builder urlBuilder_new = HttpUrl.parse("http://13.124.143.15:10001/message/requester/" + Login.id).newBuilder();
        String url_new = urlBuilder_new.build().toString();
        GetHandler handler_new = new GetHandler();
        try {
            String res = handler_new.execute(url_new).get();
            JSONArray jsonArray = new JSONArray(res);
            if (res != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String roomId = jsonArray.getJSONObject(i).getString("roomId");
                    String requester = jsonArray.getJSONObject(i).getString("requester");
                    String bangjang = jsonArray.getJSONObject(i).getString("bangjang");
                    boolean banjangRead = jsonArray.getJSONObject(i).getBoolean("bangjangRead");
                    boolean accept = jsonArray.getJSONObject(i).getBoolean("accept");
                    Message message = new Message(roomId, "Hello", requester, bangjang, banjangRead, accept);

                    String messageId = jsonArray.getJSONObject(i).getString("_id");
                    message.setMessageId(messageId);
                    adapter.add(message);
                }
            }
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }

    public class GetHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        public GetHandler() {
        }

        @Override
        protected String doInBackground(String... params) {
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.toString());
                return response.body().string();
            } catch (Exception e) {
            }
            return null;
        }
    }

    public class PostHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        String roomId, requester, bangjang, bangjangRead, accept;

        public PostHandler(String roomId, String requester, String bangjang, String bangjangRead, String accept) {
            this.roomId = roomId;
            this.requester = requester;
            this.bangjang = bangjang;
            this.bangjangRead = bangjangRead;
            this.accept = accept;
        }

        @Override
        protected String doInBackground(String... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("roomId", roomId)
                    .add("requester", requester)
                    .add("bangjang", bangjang)
                    .add("bangjangRead", bangjangRead)
                    .add("accept", accept)
                    .build();
            Request request = new Request.Builder()
                    .url(params[0]).post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.toString());
                return response.body().string();
            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(String params) {

        }
    }
}
