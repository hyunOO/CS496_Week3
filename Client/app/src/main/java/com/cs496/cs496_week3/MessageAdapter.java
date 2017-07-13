package com.cs496.cs496_week3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rongrong on 2017-07-14.
 */

public class MessageAdapter extends BaseAdapter{
    private ArrayList<Message> messageList = new ArrayList<>();
    private String userId = null;

    public MessageAdapter(ArrayList<Message> messageList, String userId){
        this.messageList = messageList;
        this.userId = userId;
    }

    @Override
    public int getCount(){
        return messageList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tab0_message_layout, parent, false);
        }
        Message message = messageList.get(position);

        if(message != null){
            TextView message_title = (TextView) convertView.findViewById(R.id.message_title);
            TextView message_from = (TextView) convertView.findViewById(R.id.message_from);
            TextView message_content = (TextView) convertView.findViewById(R.id.message_content);

            Button accept = (Button) convertView.findViewById(R.id.accept);
            Button reject = (Button) convertView.findViewById(R.id.reject);
//            Button confirm = (Button) convertView.findViewById(R.id.confirm);


            message_title.setText(message.getTitle());
            message_from.setText(message.getFrom());;
//            message_content.setText(message.getContent());

        }
        return convertView;
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
        notifyDataSetChanged();
    }

    public void remove(Message message){
        messageList.remove(message);
        notifyDataSetChanged();
    }

    public void remove(int index){
        messageList.remove(index);
        notifyDataSetChanged();
    }
}
