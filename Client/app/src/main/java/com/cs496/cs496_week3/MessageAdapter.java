package com.cs496.cs496_week3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tab0_message_layout, parent, false);
        }
        Message message = messageList.get(position);

        if(message != null){
            TextView status = (TextView) convertView.findViewById(R.id.status);
            TextView title = (TextView) convertView.findViewById(R.id.titleContentMessage) ;
            TextView maker = (TextView) convertView.findViewById(R.id.makerContentMessage) ;
            TextView currentState = (TextView) convertView.findViewById(R.id.currentStateMessage) ;
            status.setText(message.getStauts());
            title.setText(message.getRoom().getTitle());
            maker.setText(message.getRoom().getMaker());
            currentState.setText(message.getRoom().getCurrentState());
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
