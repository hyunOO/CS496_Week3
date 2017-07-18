package com.cs496.cs496_week3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by rongrong on 2017-07-14.
 */

public class RoomListAdapter extends BaseAdapter {

    private ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<Room> displayList = new ArrayList<>();

    public RoomListAdapter() {
    }

    @Override
    public int getCount() {
        return displayList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tab0_roomlist_layout, parent, false);
        }
        Room room = displayList.get(position);

        if (room != null) {
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView mealType = (TextView) convertView.findViewById(R.id.mealType);
            TextView makerId = (TextView) convertView.findViewById(R.id.makerId);
            TextView current = (TextView) convertView.findViewById(R.id.current);
            TextView roomId = (TextView) convertView.findViewById(R.id.roomId);
            title.setText(room.getTitle());
            mealType.setText(room.getMealType());
            makerId.setText(room.getMakerId());
            current.setText(room.getCurrent());
            roomId.setText(room.getRoomId());
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Room getItem(int position) {
        return displayList.get(position);
    }

    public void add(Room room) {
        roomList.add(0, room);
        displayList.add(0, room);
        notifyDataSetChanged();
    }

    public void remove(Room room) {
        roomList.remove(room);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        roomList.remove(index);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        roomList.clear();
        displayList.clear();
        notifyDataSetChanged();
    }

    public void filter(String search_text) {
        search_text = search_text.toLowerCase(Locale.getDefault());
        displayList.clear();
        if (search_text.length() == 0) {
            displayList.addAll(roomList);
        } else {
            for (Room room : roomList) {
                if (room.getTitle().contains(search_text)
                        || (room.getMealType().contains(search_text))
                        || (room.getMakerId().contains(search_text))) {
                    displayList.add(room);
                }
            }
        }
        notifyDataSetChanged();
    }

}
