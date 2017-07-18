package com.cs496.cs496_week3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String department, circle, hobby;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        department = intent.getStringExtra("department");
        circle = intent.getStringExtra("circle");
        hobby = intent.getStringExtra("hobby");
        String tag = intent.getStringExtra("tag");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPagertab with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView real_id = header.findViewById(R.id.real_id_show);
        real_id.setText(Login.id+"님 안녕하세요.");

        Menu menu = navigationView.getMenu();
        MenuItem department_show = menu.findItem(R.id.department_show);
        department_show.setTitle("학과: " + department);
        MenuItem circle_show = menu.findItem(R.id.circle_show);
        circle_show.setTitle("동아리: " + circle);
        MenuItem hobby_show = menu.findItem(R.id.hobby_show);
        hobby_show.setTitle("취미: " + hobby);

    }

    @Override
    public void onBackPressed() {

        if (mViewPager.getCurrentItem() == 0) {
            if (mSectionsPagerAdapter.getItem(0) instanceof RoomInfo) {
                ((RoomInfo) mSectionsPagerAdapter.getItem(0)).backPressed();
            } else if (mSectionsPagerAdapter.getItem(0) instanceof Tab1RoomList) {
                finish();
            }
        } else if (mViewPager.getCurrentItem() == 1) {
            if (mSectionsPagerAdapter.getItem(1) instanceof RoomInfo2) {
                ((RoomInfo2) mSectionsPagerAdapter.getItem(1)).backPressed();
            } else if (mSectionsPagerAdapter.getItem(1) instanceof Tab2MyRoom) {
                finish();
            }
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit) {
            final View dialogView = getLayoutInflater().inflate(R.layout.sign_modify, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("개인정보 수정");
            alert.setView(dialogView);

            final TextView txt1 = dialogView.findViewById(R.id.id_modify);
            txt1.setText(Login.id);
            final EditText txt3 = dialogView.findViewById(R.id.circle_modify);
            txt3.setText(circle);
            final EditText txt4 = dialogView.findViewById(R.id.hobbyselect_modify);
            txt4.setText(hobby);
            final TextView hiden3 = dialogView.findViewById(R.id.hiden3);
            hiden3.setVisibility(View.GONE);

            final Spinner spinner = dialogView.findViewById(R.id.departmentselect_modify);
            final ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this, R.array.department, R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(sAdapter);
            String[] arr = getResources().getStringArray(R.array.department);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals(department)) {
                    spinner.setSelection(i);
                }
            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    hiden3.setText("" + parent.getItemAtPosition(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    final TextView txt_hiden4 = (TextView) dialogView.findViewById(R.id.hiden4);
                    txt_hiden4.setText("false");

                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://13.124.143.15:10001/user/" + Login.id).newBuilder();
                    String url_new = urlBuilder.build().toString();
                    String department_modify = hiden3.getText().toString();
                    String circle_modify = txt3.getText().toString();
                    String hobby_modify = txt4.getText().toString();

                    department = department_modify;
                    circle = circle_modify;
                    hobby = hobby_modify;
                    PutHandler handler = new PutHandler(Login.id, "1", department_modify, circle_modify, hobby_modify);
                    String result = null;
                    try {
                        result = handler.execute(url_new).toString();
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu menu = navigationView.getMenu();
                        MenuItem department_show = menu.findItem(R.id.department_show);
                        department_show.setTitle("학과: " + department);
                        MenuItem circle_show = menu.findItem(R.id.circle_show);
                        circle_show.setTitle("동아리: " + circle);
                        MenuItem hobby_show = menu.findItem(R.id.hobby_show);
                        hobby_show.setTitle("취미: " + hobby);
                    } catch (Exception e) {

                    }
                }
            });

            alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            alert.show();

        } else if (id == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

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

    public class PutHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        String id, pw, department, circle, hobby;

        public PutHandler(String id, String pw, String department, String circle, String hobby) {
            this.id = id;
            this.pw = pw;
            this.department = department;
            this.circle = circle;
            this.hobby = hobby;
        }

        @Override
        protected String doInBackground(String... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("pw", pw)
                    .add("department", department)
                    .add("circle", circle)
                    .add("hobby", hobby)
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

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final class FirstPageListener implements FirstPageFragmentListener {
            public void onSwitchToNextFragment() {
                mFragmentManager.beginTransaction().remove(tab1)
                        .commit();
                if (tab1 instanceof Tab1RoomList) {
                    tab1 = new RoomInfo(listener1);
                } else { // Instance of NextFragment
                    tab1 = new Tab1RoomList(listener1);
                }
                notifyDataSetChanged();
            }
        }

        private final class SecondPageListener implements SecondPageFragmentListener {
            public void onSwitchToNextFragment() {
                mFragmentManager.beginTransaction().remove(tab2)
                        .commit();
                if (tab2 instanceof Tab2MyRoom) {
                    tab2 = new RoomInfo2(listener2);
                } else { // Instance of NextFragment
                    tab2 = new Tab2MyRoom(listener2);
                }
                notifyDataSetChanged();
            }
        }

        private final FragmentManager mFragmentManager;
        public Fragment tab1;
        public Fragment tab2;
        private Context context;
        FirstPageListener listener1 = new FirstPageListener();
        SecondPageListener listener2 = new SecondPageListener();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    if (tab1 == null) {
                        tab1 = new Tab1RoomList(listener1);
                    }
                    return tab1;
                case 1:
                    if (tab2 ==null){
                        tab2 = new Tab2MyRoom(listener2);
                    }
                    return tab2;
                case 2:
                    Tab3Message tab3 = new Tab3Message();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "List";
                case 1:
                    return "My Room";
                case 2:
                    return "Message";
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof Tab1RoomList && tab1 instanceof RoomInfo) {
                return POSITION_NONE;
            }
            if (object instanceof RoomInfo && tab1 instanceof Tab1RoomList) {
                return POSITION_NONE;
            }
            if (object instanceof Tab2MyRoom && tab2 instanceof RoomInfo2) {
                return POSITION_NONE;
            }
            if (object instanceof RoomInfo2 && tab2 instanceof Tab2MyRoom) {
                return POSITION_NONE;
            }
            return POSITION_UNCHANGED;
        }
    }

}
