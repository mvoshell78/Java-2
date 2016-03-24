package com.example.mich.mvoshell_contentprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {




    ArrayList<CityClass> mCities;
    CityClass city;

    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mCities = new ArrayList<CityClass>();
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent mainIntent = getIntent();
        mCities = (ArrayList<CityClass>) mainIntent.getSerializableExtra("cities");
        position = mainIntent.getIntExtra("position", 0);


        city = mCities.get(position);

        getFragmentManager().beginTransaction().replace(R.id.dispaly_container,new DisplayFragment()).commit();

        DisplayFragment fragment = (DisplayFragment) getFragmentManager().findFragmentByTag("DisplayFragment");

        if (fragment == null){
            fragment = DisplayFragment.newInstanceOf(city);
            getFragmentManager().beginTransaction().replace(R.id.dispaly_container,fragment,DisplayFragment.TAG).commit();
        } else {
            fragment.loadText(city);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String delName = "";

              delName = mCities.get(position).getmCity();
                //getContentResolver().delete(CitiesProvider.CONTENT_URI,null,null);
                getContentResolver().delete(CitiesProvider.CONTENT_URI,"cityNameText=?", new String[]{delName});


                mCities.remove(position);
                Snackbar.make(view, "Item Was Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                sendBack();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void sendBack(){

            Intent returnIntent = new Intent();
            returnIntent.putExtra("return", mCities);
            setResult(RESULT_OK, returnIntent);
            finish();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // Quit if back is pressed
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            sendBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

