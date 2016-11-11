package com.sajorahasan.yallaroom;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sajorahasan.yallaroom.Service.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class PostAdActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PostAdActivity";

    private EditText etName, etAdd, etPhone, etDesc;
    private String name, add, phone, desc;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        //Initializing Views
        etName = (EditText) findViewById(R.id.etName);
        etAdd = (EditText) findViewById(R.id.etAddress);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etDesc = (EditText) findViewById(R.id.etDesc);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        //Toast.makeText(this, "" + name, Toast.LENGTH_LONG).show();


        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSubmit) {

            name = etName.getText().toString().trim();
            add = etAdd.getText().toString().trim();
            phone = etPhone.getText().toString().trim();
            desc = etDesc.getText().toString().trim();

            Log.d(TAG, "onClick: " + name);


            SubmitData submitData = new SubmitData();
            submitData.execute();
        }
    }

    private class SubmitData extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PostAdActivity.this);
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String data = null;

            try {
                HttpRequest req = new HttpRequest(Config.BASE_URL + Config.POST_AD);

                HashMap<String, String> params = new HashMap<>();
                params.put("uname", name);
                params.put("uadd", add);
                params.put("uphone", phone);
                params.put("udesc", desc);
                params.put("uimage", "img");

                String result = req.prepare(HttpRequest.Method.POST).withData(params).sendAndReadString();

                JSONObject jsonObject = new JSONObject(result);

                data = jsonObject.getString("status");

                Log.d(TAG, "doInBackground: " + result);
                Log.d(TAG, "doInBackground: " + data);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equals("success")) {

                etName.setText("");
                etAdd.setText("");
                etPhone.setText("");
                etDesc.setText("");

                Toast.makeText(PostAdActivity.this, "Ad posted successfully", Toast.LENGTH_SHORT).show();
            }
            if (s.equals("error")) {

                Toast.makeText(PostAdActivity.this, "Error, please try again", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }
}
