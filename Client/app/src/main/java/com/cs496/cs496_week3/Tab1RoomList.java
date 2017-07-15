package com.cs496.cs496_week3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rongrong on 2017-07-14.
 */

public class Tab1RoomList extends Fragment {
    RoomListAdapter adapter = new RoomListAdapter();
    View view, view2;
    static FirstPageFragmentListener firstPageListener;
    EditText searchRoom;

    public Tab1RoomList() {
    }

    public Tab1RoomList(FirstPageFragmentListener listener) {
        firstPageListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_roomlist, null);

        final ListView listview = (ListView) view.findViewById(R.id.roomlistListView);
        listview.setAdapter(adapter);

        LoadRoomList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("listview click", "clicked " + i + "th item in listview");

                TextView clicked_title = (TextView) view.findViewById(R.id.title);
                TextView clicked_mealType = (TextView) view.findViewById(R.id.mealType);
                TextView clicked_makerId = (TextView) view.findViewById(R.id.makerId);
                TextView clicked_current = (TextView) view.findViewById(R.id.current);
                TextView clicked_roomId = (TextView) view.findViewById(R.id.roomId);
                RoomInfo.roomId = clicked_roomId.getText().toString();

                firstPageListener.onSwitchToNextFragment();

                TextView roominfo_title = (TextView) RoomInfo.view.findViewById(R.id.roominfo_title);
                TextView roominfo_mealType = (TextView) RoomInfo.view.findViewById(R.id.roominfo_mealType);
                TextView roominfo_makerId = (TextView) RoomInfo.view.findViewById(R.id.roominfo_makerId);
                TextView roominfo_current = (TextView) RoomInfo.view.findViewById(R.id.roominfo_current);

                roominfo_title.setText(clicked_title.getText().toString());
                roominfo_mealType.setText(clicked_mealType.getText().toString());
                roominfo_makerId.setText(clicked_makerId.getText().toString());
                roominfo_current.setText(clicked_current.getText().toString());

            }
        });

        searchRoom = (EditText) view.findViewById(R.id.searchRoom);

        searchRoom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if(motionEvent.getRawX() >= searchRoom.getCompoundDrawables()[2].getBounds().width()) {
                    if (motionEvent.getRawX() >= searchRoom.getRight() - searchRoom.getCompoundDrawables()[2].getBounds().width()) {
                        searchRoom.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        searchRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search_text = editable.toString();
                adapter.filter(search_text);
            }
        });

        Button departmentSearch = (Button) view.findViewById(R.id.departmentSearch);
        Button mealTypeSearch = (Button) view.findViewById(R.id.mealTypeSearch);

        departmentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] departmentList = new String[]{"물리학과", "수리과학과", "화학과", "나노과학기술대학원", "생명과학과", "의과학대학원", "전산학부", "정보통신공학과", "조천식녹색교통대학원", "전기및전자공학부", "건설및환경공학과", "바이오및뇌공학과", "산업디자인학과", "산업및시스템공학과", "생명화학공학과", "원자력및양자공학과", "EEWS 대학원", "신소재공학과", "기계공학과", "항공우주공학과", "인문사회과학부", "문화기술대학원", "문술미래전략대학원", "과학기술정책대학원", "경영공학부", "기술경영학부", "테크노경영대학원", "금융전문대학원", "정보미디어경영대학원", "녹색성장대학원"};
                Arrays.sort(departmentList);
                FastSearchDialog("학과", departmentList);
            }
        });

        mealTypeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] mealTypeList = new String[]{"상관없음", "식사", "카페", "술"};
                FastSearchDialog("유형으", mealTypeList);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) getView().findViewById(R.id.showRoom);
        swipeView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark), getResources().getColor(android.R.color.holo_blue_light), getResources().getColor(android.R.color.holo_green_dark));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadRoomList();
                        adapter.filter(searchRoom.getText().toString());
                        swipeView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void LoadRoomList() {
        adapter.clearAdapter();

        JSONArray JSONroomList = null;
        CustomThread thread = new CustomThread();
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONroomList = thread.getResult();

        for (int i = 0; i < JSONroomList.length(); i++) {
            JSONObject room = null;
            try {
                room = JSONroomList.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Boolean openState = null;
            try {
                openState = !room.getBoolean("closed");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (openState) {
                Room newroom = new Room();
                try {
                    newroom.setTitle(room.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    newroom.setMakerId(room.getString("makerId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    newroom.setMealType(room.getString("mealType"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    newroom.setCurrent(room.getInt("maxUser"), room.getInt("currentUser"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    newroom.setRoomId(room.getString("_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.add(newroom);
            }
        }
    }

    public class GetRoomList {
        OkHttpClient client = new OkHttpClient();

        String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        }
    }

    public class CustomThread extends Thread {
        JSONArray jsonarray = null;
        final GetRoomList example = new GetRoomList();

        public CustomThread() {
            jsonarray = null;
        }

        @Override
        public void run() {
            String response = null;
            try {
                response = example.get("http://13.124.143.15:10001/room");
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

    String search_content;

    public void FastSearchDialog(String searchType, final String[] versionArray) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
        dlg.setTitle(searchType + "로 검색");
        search_content = versionArray[0];
        dlg.setSingleChoiceItems(versionArray, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        search_content = versionArray[i];
                    }
                });
        dlg.setNegativeButton("취소", null);
        dlg.setPositiveButton("검색", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                searchRoom.setText(search_content);
            }
        });
        dlg.show();
    }

}
