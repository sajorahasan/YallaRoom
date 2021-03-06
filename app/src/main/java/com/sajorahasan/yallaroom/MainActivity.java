package com.sajorahasan.yallaroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibPostAd;
    private Button btnSearch, btnSearchBed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Views
        ibPostAd = (ImageButton) findViewById(R.id.ibPostAd);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearchBed = (Button) findViewById(R.id.btnBedSpace);


        ibPostAd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnSearchBed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ibPostAd) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        if (view == btnSearch) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        if (view == btnSearchBed) {
            Intent intent = new Intent(MainActivity.this, SearchBedspace.class);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
    }
}
