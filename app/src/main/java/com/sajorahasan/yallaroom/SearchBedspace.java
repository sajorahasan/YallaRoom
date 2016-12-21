package com.sajorahasan.yallaroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sajorahasan.yallaroom.Adapter.CustomAdapter;
import com.sajorahasan.yallaroom.Model.Pojo;
import com.sajorahasan.yallaroom.Service.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sajorahasan.yallaroom.R.id.etAdd;

public class SearchBedspace extends AppCompatActivity {
    private static final String TAG = "SearchBedspace";
    SearchActivity searchActivity;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private EditText etAddress1;
    private String address1;
    private Button btnSearchBs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bedspace);

        //Initializing Views
        etAddress1 = (EditText) findViewById(etAdd);
        btnSearchBs = (Button) findViewById(R.id.btnSearchBedSpace);
        recyclerView = (RecyclerView) findViewById(R.id.SearchRecyclerView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SearchBedspace.this, PostAdActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnSearchBs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //fetching value from address editText
                address1 = etAddress1.getText().toString().trim();

                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());


                Search search = new Search();
                search.execute();

            }
        });
    }


    private class Search extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SearchBedspace.this);
            dialog.setMessage("Searching, Please wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<Pojo> pojoArrayList = new ArrayList<>();

            try {
                HttpRequest req = new HttpRequest(Config.BASE_URL + Config.SEARCH);

                HashMap<String, String> params = new HashMap<>();
                params.put("address", address1);

                String result = req.prepare(HttpRequest.Method.POST).withData(params).sendAndReadString();

                JSONObject object = new JSONObject(result);
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

            dialog.dismiss();
        }
    }
}
