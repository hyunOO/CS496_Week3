package com.cs496.cs496_week3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab3Message extends Fragment {
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_message, null);

        ArrayList<Message> messageList = new ArrayList<>();
        Message message1 = new Message();
        message1.setTitle("전산과 모여라~~");
        message1.setRequester("강현우");
        message1.setBangjang("rongrong");
        messageList.add(message1);
        messageList.add(message1);
        messageList.add(message1);
        messageList.add(message1);
        messageList.add(message1);

        ListView listview = (ListView) view.findViewById(R.id.messageListView);
        MessageAdapter adapter = new MessageAdapter(messageList, "rongrong");
        listview.setAdapter(adapter);


        return view;
    }
}
