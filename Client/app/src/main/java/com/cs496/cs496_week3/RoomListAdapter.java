package com.cs496.cs496_week3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rongrong on 2017-07-14.
 */

public class RoomListAdapter extends BaseAdapter {

    private ArrayList<Room> roomList = new ArrayList<>();

    public RoomListAdapter(){

    }

    @Override
    public int getCount(){
        return roomList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tab0_roomlist_layout, parent, false);
        }
        Room room = roomList.get(position);

        if(room != null){
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView mealType = (TextView) convertView.findViewById(R.id.mealType);
            TextView makerId = (TextView) convertView.findViewById(R.id.makerId);
            TextView current = (TextView) convertView.findViewById(R.id.current);
            title.setText(room.getTitle());
            mealType.setText(room.getMealType());
            makerId.setText(room.getMakerId());
            current.setText(room.getCurrent());
//
//            LinearLayout roomlist_layout = (LinearLayout) convertView.findViewById(R.id.roomlist_layout);
//            roomlist_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.e("listview click", "clicked "+position+"th item in listview");
//                }
//            });



        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Room getItem(int position) {
        return roomList.get(position) ;
    }

    public void add(Room room){
        roomList.add(0, room);
        notifyDataSetChanged();
    }

    public void remove(Room room){
        roomList.remove(room);
        notifyDataSetChanged();
    }

    public void remove(int index){
        roomList.remove(index);
        notifyDataSetChanged();
    }
}
