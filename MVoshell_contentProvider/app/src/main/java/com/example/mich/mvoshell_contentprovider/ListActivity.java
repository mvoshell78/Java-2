package com.example.mich.mvoshell_contentprovider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements getListPosition {


    private static final int MAIN_REQUEST_CODE = 1234;
    private ArrayList<CityClass> mCities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mCities = new ArrayList<CityClass>();
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent mainIntent = getIntent();
        mCities = (ArrayList<CityClass>) mainIntent.getSerializableExtra("Fav");



        insertIntoDb();

        readFromDb();

        createFrag();

    }

    private void readFromDb() {
        String reCity = "";
        String rePopulation = "";
        String reClimate = "";
        CityClass fromDbFav;




        try {
            Cursor cursor = getContentResolver().query(CitiesProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS, null, null, null, null);
            cursor.moveToFirst();

            mCities.clear();

            do {

                reCity = (cursor.getString(1));
                rePopulation = (cursor.getString(2));
                reClimate = (cursor.getString(3));

                Log.d("ListActivity", "reCity " + reCity);
                Log.d("ListActivity", "rePopulation " + rePopulation);
                Log.d("ListActivity", "reClimate " + reClimate);

                fromDbFav = new CityClass(reCity, rePopulation, reClimate);
                mCities.add(fromDbFav);
            } while (cursor.moveToNext());


        } catch (CursorIndexOutOfBoundsException e) {

        }
    };


    private void insertIntoDb() {

        int objectSize =  mCities.size() - 1;

        String name = mCities.get(objectSize).getmCity();
        String color = mCities.get(objectSize).getmPopulation();
        String food = mCities.get(objectSize).getmClimate();

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.CITY_Text, name);
        values.put(DBOpenHelper.POPULATION_Text, color);
        values.put(DBOpenHelper.CLIMATE_Text, food);

        Uri noteUri  = getContentResolver().insert(CitiesProvider.CONTENT_URI, values);
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

    @Override
    public void passBackPosition(int position) {

// passbackPosition also transisitions to displayActivity
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("cities", mCities);
        intent.putExtra("position",position);
        startActivityForResult(intent, MAIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAIN_REQUEST_CODE || resultCode == RESULT_OK){
            mCities = (ArrayList<CityClass>) data.getSerializableExtra("return");

            //saveData(mCities);
           // readFromStorage();

            readFromDb();


            ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);

            fragment.setUpList(mCities);
        }
    }





    public void createFrag(){

        getFragmentManager().beginTransaction().replace(R.id.main_container,new ListFragment()).commit();

        ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);
        if (fragment == null){
            fragment = ListFragment.newInstanceOf(mCities);
            getFragmentManager().beginTransaction().replace(R.id.main_container,fragment,ListFragment.TAG).commit();

        } else {
            fragment.setUpList(mCities);
        }
    }
}
