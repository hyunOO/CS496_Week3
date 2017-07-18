package com.cs496.cs496_week3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rongrong on 2017-07-15.
 */

public class UserListAdapter extends BaseAdapter {
    private ArrayList<User> userList = new ArrayList<>();

    public UserListAdapter() {

    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tab0_userlist_layout, parent, false);
        }
        User user = userList.get(position);

        if (user != null) {
            TextView id = (TextView) convertView.findViewById(R.id.userId);
            TextView department = (TextView) convertView.findViewById(R.id.userDepartment);
            TextView circle = (TextView) convertView.findViewById(R.id.userCircle);
            TextView hobby = (TextView) convertView.findViewById(R.id.userHobby);
            id.setText(user.getId());
            department.setText(user.getDepartment());
            circle.setText(user.getCircle());
            hobby.setText(user.getHobby());
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    public void add(User user) {
        userList.add(user);
        notifyDataSetChanged();
    }

    public void remove(User user) {
        userList.remove(user);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        userList.remove(index);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        userList.clear();
        notifyDataSetChanged();
    }

    public Boolean checkUser(String userId) {
        for (int i = 0; i < userList.size(); i++) {
            if (userId.equals(userList.get(i).getId())) {
                return true;
            }
        }
        return false;
    }
}