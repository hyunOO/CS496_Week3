package com.cs496.cs496_week3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.annotation.ColorRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by rongrong on 2017-07-14.
 */

public class MessageAdapter extends BaseAdapter{
    private ArrayList<Message> messageList = new ArrayList<>();

    public MessageAdapter(){
    }

    @Override
    public int getCount(){
        return messageList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Context context = parent.getContext();
        final Message message = messageList.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 방장이 자신이 만든 방에 가입하고 싶다고 요청을 보낸 메시지를 보여준다.
            if(message.getBanjangRead() == false && Login.id.equals(message.getBangjang()) && checkHide(message) == true){
                convertView = inflater.inflate(R.layout.tab0_message_layout, parent, false);

                final TextView checkNumber = convertView.findViewById(R.id.checkNumber);
                checkNumber.setText("0");
                final LinearLayout linearLayout1 = convertView.findViewById(R.id.whole_layout);
                final LinearLayout linearLayout_whole = convertView.findViewById(R.id.real_whole_layout);

                //linearLayout_whole.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
                linearLayout1.setVisibility(View.GONE);

                String roomId = message.getRoomId();
                HttpUrl.Builder urlBuilder_room = HttpUrl.parse("http://13.124.143.15:10001/room/roomId/"+roomId).newBuilder();
                String url_room = urlBuilder_room.build().toString();
                GetHandler handler_room = new GetHandler();
                String res_room = null;
                try{
                    res_room = handler_room.execute(url_room).get();
                    JSONObject room_res = new JSONObject(res_room);
                    String title = room_res.getString("title");
                    TextView txt = convertView.findViewById(R.id.message_title);
                    txt.setText("방제: "+title);
                }
                catch (Exception e){

                }
                TextView txt = convertView.findViewById(R.id.message_from);
                txt.setText(message.getRequester());

                HttpUrl.Builder urlBuilder_requester = HttpUrl.parse("http://13.124.143.15:10001/user/id/"+message.getRequester()).newBuilder();
                String url_requester = urlBuilder_requester.build().toString();
                GetHandler handler_requester = new GetHandler();
                String res_requester = null;
                try{
                    res_requester = handler_requester.execute(url_requester).get();
                    JSONObject requester_res = new JSONObject(res_requester);
                    String dep_req = requester_res.getString("department");
                    String circle_req = requester_res.getString("circle");
                    String hobby_req = requester_res.getString("hobby");
                    TextView req_dep = convertView.findViewById(R.id.userDepartment);
                    req_dep.setText(dep_req);
                    TextView req_circle = convertView.findViewById(R.id.userCircle);
                    req_circle.setText(circle_req);
                    TextView req_hobby = convertView.findViewById(R.id.userHobby);
                    req_hobby.setText(hobby_req);
                }
                catch(Exception e){
                    Log.d("Hello", ""+e);
                }

                final ImageButton btn_accept = convertView.findViewById(R.id.accept);
                final ImageButton btn_reject = convertView.findViewById(R.id.reject);
                final ImageButton btn_look = convertView.findViewById(R.id.userInfo);


                btn_look.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkNumber.getText().equals("0")){
                            linearLayout1.setVisibility(View.VISIBLE);
                            //linearLayout_whole.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                            checkNumber.setText("1");
                        }
                        else{
                            linearLayout1.setVisibility(View.GONE);
                            //linearLayout_whole.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
                            checkNumber.setText("0");
                        }
                    }
                }));

                btn_accept.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://13.124.143.15:10001/message").newBuilder();
                        String url = urlBuilder.build().toString();
                        PostHandler handler = new PostHandler(message.getRoomId(), message.getRequester(), message.getBangjang(), true, true);
                        String res = null;
                        try{
                            res = handler.execute(url).get();
                            HttpUrl.Builder urlBuilder_add = HttpUrl.parse("http://13.124.143.15:10001/room/addUser/"+message.getRoomId()).newBuilder();
                            String url_hello = urlBuilder_add.build().toString();
                            AddUserHanler addUserHanler = new AddUserHanler(message.getRoomId(), message.getRequester());
                            String add_res = null;
                            try{
                                add_res = addUserHanler.execute(url_hello).get();
                                btn_reject.setVisibility(View.GONE);
                                btn_accept.setVisibility(View.GONE);
                                btn_look.setVisibility(View.GONE);
                            }
                            catch (Exception e){

                            }
                            Log.d("Hello", "Hi");
                        }
                        catch(Exception e){

                        }
                    }
                }));

                btn_reject.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://13.124.143.15:10001/message").newBuilder();
                        String url = urlBuilder.build().toString();
                        PostHandler handler = new PostHandler(message.getRoomId(), message.getRequester(), message.getBangjang(), true, false);
                        String res = null;
                        try{
                            res = handler.execute(url).get();
                            Log.d("Hello", "Hi");
                            btn_reject.setVisibility(View.GONE);
                            btn_accept.setVisibility(View.GONE);
                            btn_look.setVisibility(View.GONE);
                        }
                        catch(Exception e){

                        }
                    }
                }));
            }

            // 방장이 자신이 만든 방에 가입하고 싶다고 요청을 보낸 메시지에 대해 답장을 했을 때의 경우를 보여준다.
            else if(message.getBanjangRead() == false && Login.id.equals(message.getBangjang()) && checkHide(message) == false){
                convertView = inflater.inflate(R.layout.tab0_message_after_response, parent, false);
                String roomId = message.getRoomId();
                HttpUrl.Builder urlBuilder_room = HttpUrl.parse("http://13.124.143.15:10001/room/roomId/"+roomId).newBuilder();
                String url_room = urlBuilder_room.build().toString();
                GetHandler handler_room = new GetHandler();
                String res_room = null;
                try{
                    res_room = handler_room.execute(url_room).get();
                    JSONObject room_res = new JSONObject(res_room);
                    String title = room_res.getString("title");
                    TextView txt = convertView.findViewById(R.id.message_title);
                    txt.setText("방제: "+title);
                }
                catch (Exception e){

                }
                TextView txt = convertView.findViewById(R.id.message_from);
                txt.setText(message.getRequester());
            }

            // 가입 요청자 자신이 가입하고 싶다고 요청을 보낸 메시지를 보여준다.
            else if(message.getBanjangRead() == false && Login.id.equals(message.getRequester())){
                convertView = inflater.inflate(R.layout.tab0_message_send_layout, parent, false);
                String roomId = message.getRoomId();
                HttpUrl.Builder urlBuilder_room = HttpUrl.parse("http://13.124.143.15:10001/room/roomId/"+roomId).newBuilder();
                String url_room = urlBuilder_room.build().toString();
                GetHandler handler_room = new GetHandler();
                String res_room = null;
                try{
                    res_room = handler_room.execute(url_room).get();
                    JSONObject room_res = new JSONObject(res_room);
                    String title = room_res.getString("title");
                    TextView txt = convertView.findViewById(R.id.message_title);
                    txt.setText("방제: "+title);
                }
                catch (Exception e){

                }
                TextView txt = convertView.findViewById(R.id.message_from);
                txt.setText(message.getBangjang());
            }

            // 가입 요청자가 가입 요청을 보낸 메시지에 대해 방장이 수락을 했을때의 메시지를 보여준다.
            else if(message.getBanjangRead() == true && message.getAccept() == true && Login.id.equals(message.getRequester())){
                convertView = inflater.inflate(R.layout.tab0_message_accept_layout, parent, false);
                String roomId = message.getRoomId();
                HttpUrl.Builder urlBuilder_room = HttpUrl.parse("http://13.124.143.15:10001/room/roomId/"+roomId).newBuilder();
                String url_room = urlBuilder_room.build().toString();
                GetHandler handler_room = new GetHandler();
                String res_room = null;
                try{
                    res_room = handler_room.execute(url_room).get();
                    JSONObject room_res = new JSONObject(res_room);
                    String title = room_res.getString("title");
                    TextView txt = convertView.findViewById(R.id.message_title);
                    txt.setText("방제: "+title);
                }
                catch (Exception e){

                }
                TextView txt = convertView.findViewById(R.id.message_from);
                txt.setText(message.getBangjang());
            }

            // 가입 요청자가 가입 요청을 보낸 메시지에 대해 방장이 거절 했을때의 메시지를 보여준다.
            else if(message.getBanjangRead() == true && message.getAccept() == false && Login.id.equals(message.getRequester())){
                convertView = inflater.inflate(R.layout.tab0_message_reject_layout, parent, false);
                String roomId = message.getRoomId();
                HttpUrl.Builder urlBuilder_room = HttpUrl.parse("http://13.124.143.15:10001/room/roomId/"+roomId).newBuilder();
                String url_room = urlBuilder_room.build().toString();
                GetHandler handler_room = new GetHandler();
                String res_room = null;
                try{
                    res_room = handler_room.execute(url_room).get();
                    JSONObject room_res = new JSONObject(res_room);
                    String title = room_res.getString("title");
                    TextView txt = convertView.findViewById(R.id.message_title);
                    txt.setText("방제: "+title);
                }
                catch (Exception e){

                }
                TextView txt = convertView.findViewById(R.id.message_from);
                txt.setText(message.getBangjang());
            }

            // 방장이 가입 요청자를 승인했을때의 메시지를 보여준다.
            else if(message.getBanjangRead() == true && message.getAccept() == true && Login.id.equals(message.getBangjang())){
                convertView = inflater.inflate(R.layout.tab0_message_okay_layout, parent, false);
                String roomId = message.getRoomId();
                HttpUrl.Builder urlBuilder_room = HttpUrl.parse("http://13.124.143.15:10001/room/roomId/"+roomId).newBuilder();
                String url_room = urlBuilder_room.build().toString();
                GetHandler handler_room = new GetHandler();
                String res_room = null;
                try{
                    res_room = handler_room.execute(url_room).get();
                    JSONObject room_res = new JSONObject(res_room);
                    String title = room_res.getString("title");
                    TextView txt = convertView.findViewById(R.id.message_title);
                    txt.setText("방제: "+title);
                }
                catch (Exception e){

                }
                TextView txt = convertView.findViewById(R.id.message_from);
                txt.setText(message.getRequester());
            }

            // 방장이 가입 요청자를 거절했을때의 메시지를 보여준다.
            else if(message.getBanjangRead() == true && message.getAccept() == false && Login.id.equals(message.getBangjang())){
                convertView = inflater.inflate(R.layout.tab0_message_no_layout, parent, false);
                String roomId = message.getRoomId();
                HttpUrl.Builder urlBuilder_room = HttpUrl.parse("http://13.124.143.15:10001/room/roomId/"+roomId).newBuilder();
                String url_room = urlBuilder_room.build().toString();
                GetHandler handler_room = new GetHandler();
                String res_room = null;
                try{
                    res_room = handler_room.execute(url_room).get();
                    JSONObject room_res = new JSONObject(res_room);
                    String title = room_res.getString("title");
                    TextView txt = convertView.findViewById(R.id.message_title);
                    txt.setText("방제: "+title);
                }
                catch (Exception e){

                }
                TextView txt = convertView.findViewById(R.id.message_from);
                txt.setText(message.getRequester());
            }

        /*
        if(message != null){
            //TextView message_title = (TextView) convertView.findViewById(R.id.message_title);
            TextView message_from = (TextView) convertView.findViewById(R.id.message_from);
            TextView message_content = (TextView) convertView.findViewById(R.id.message_content);

            Button accept = (Button) convertView.findViewById(R.id.accept);
            Button reject = (Button) convertView.findViewById(R.id.reject);
//            Button confirm = (Button) convertView.findViewById(R.id.confirm);


            //message_title.setText(message.getTitle());
            message_from.setText(message.getBangjang());;
//            message_content.setText(message.getContent());

        }
        */
        return convertView;
    }


    public boolean checkHide (Message message){
        HttpUrl.Builder urlBuilder_new = HttpUrl.parse("http://13.124.143.15:10001/message/specific/" + message.getRoomId()+"/"+message.getRequester()+"/"+true).newBuilder();
        String url_new = urlBuilder_new.build().toString();
        GetHandler handler_new = new GetHandler();
        String res = null;
        try{
            res = handler_new.execute(url_new).get();
        }
        catch(Exception e){

        }
        if(res == null) return true;
        else return false;
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

    public class AddUserHanler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        String roomId, userId;

        public AddUserHanler(String roomId, String userId) {
            this.roomId = roomId;
            this.userId = userId;
        }

        @Override
        protected String doInBackground(String... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("roomId", roomId)
                    .add("userId", userId)
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
    }


    public class PostHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        String roomId, requester, bangjang;
        boolean bangjangRead, accept;

        public PostHandler(String roomId, String requester, String bangjang, boolean bangjangRead, boolean accept) {
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
                    .addEncoded("bangjangRead", ""+bangjangRead)
                    .addEncoded("accept", ""+accept)
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
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Message getItem(int position) {
        return messageList.get(position) ;
    }

    public void add(Message message){
        messageList.add(message);
    }

    public void sortArray(){
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message message, Message t1) {
                if(Integer.parseInt(message.getMessageId().substring(0, 7), 16) < Integer.parseInt(t1.getMessageId().substring(0, 7), 16)){
                    return 1;
                }
                else if(Integer.parseInt(message.getMessageId().substring(0, 7), 16) > Integer.parseInt(t1.getMessageId().substring(0, 7), 16)){
                    return -1;
                }
                return 0;
            }
        });
    }

    public void remove(Message message){
        messageList.remove(message);
        notifyDataSetChanged();
    }

    public void remove(int index){
        messageList.remove(index);
        notifyDataSetChanged();
    }

    public void clearAll(){
        messageList.clear();
        notifyDataSetChanged();
    }
}
