package com.sajorahasan.yallaroom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sajorahasan.yallaroom.Adapter.CustomAdapter;
import com.sajorahasan.yallaroom.Model.Pojo;
import com.sajorahasan.yallaroom.Service.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    SwipeRefreshLayout mSwipeRefreshLayout;

    LoadRooms loadRooms;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshItems();
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SearchActivity.this, PostAdActivity.class);
                startActivity(i);
                finish();
            }
        });

        LoadRooms loadRooms = new LoadRooms();
        loadRooms.execute();
    }

    private void refreshItems() {

        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {

        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class LoadRooms extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            ArrayList<Pojo> pojoArrayList = new ArrayList<>();

            try {
                HttpRequest req = new HttpRequest(Config.BASE_URL + Config.SHOW_ROOMS);

                String response = req.prepare(HttpRequest.Method.POST).sendAndReadString();

                JSONObject object = new JSONObject(response);
                JSONArray array = object.getJSONArray("room");

                for (int i = 0; i < array.length(); i++) {

                    Pojo p = new Pojo();
                    JSONObject object1 = array.getJSONObject(i);

                    int id = object1.getInt("id");
                    String name = object1.getString("person_name");
                    String add = object1.getString("address");
                    String phone = object1.getString("phone");
                    String desc = object1.getString("room_desc");
                    String image = object1.getString("url");

                    p.setId(id);
                    p.setName(name);
                    p.setAddress(add);
                    p.setPhone(phone);
                    p.setDesc(desc);
                    p.setImage(image);

                    pojoArrayList.add(p);

                    Log.d(TAG, "onPostExecute: " + image);

                    CustomAdapter customAdapter = new CustomAdapter(pojoArrayList, getApplicationContext());
                    recyclerView.setAdapter(customAdapter);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
