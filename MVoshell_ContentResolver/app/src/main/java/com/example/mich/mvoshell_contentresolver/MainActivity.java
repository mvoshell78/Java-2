package com.example.mich.mvoshell_contentresolver;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements getListPosition{
    public static  String AUTHORITY;
    public static  String BASE_PATH;
    public static  Uri CONTENT_URI;
    ContentResolver resolver;
    public Cursor cursor;

    private ArrayList<CityClass> mCities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mCities = new ArrayList<CityClass>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AUTHORITY = "com.example.plainolnotes.notesprovider";
        BASE_PATH = "notes";
        CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

        resolver = getContentResolver();

            readFromDb();

       createFrag();

    }

    public void createFrag(){

        getFragmentManager().beginTransaction().replace(R.id.mainContainer,new ListFragment()).commit();
        getFragmentManager().beginTransaction().replace(R.id.displayView,new DisplayFragment()).commit();

        ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);
        if (fragment == null){
            fragment = ListFragment.newInstanceOf(mCities);
            getFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment,ListFragment.TAG).commit();

        } else {
            fragment.setUpList(mCities);
        }
    }

    private void readFromDb() {

        String TABLE_NOTES = "cities";
        String NOTE_ID = "_id";
        String CITY_Text = "cityNameText";
        String POPULATION_Text = "populationText";
        String CLIMATE_Text = "climateText";


        String[] ALL_COLUMNS = {NOTE_ID, CITY_Text, CLIMATE_Text, POPULATION_Text};

        Cursor cursor = resolver.query(CONTENT_URI, ALL_COLUMNS,null,null,null);


        String reCity = "";
        String rePopulation = "";
        String reClimate = "";
        CityClass fromDbFav;




        try {

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


    @Override
    public void passBackPosition(int position) {

        String dataString =
                "City Name : " + mCities.get(position).getmCity() +
                "\nPopulation : " + mCities.get(position).getmPopulation() +
                "\nClimate : " + mCities.get(position).getmClimate();


        DisplayFragment fragment = (DisplayFragment) getFragmentManager().findFragmentByTag(DisplayFragment.TAG);

        if (fragment == null ){
            fragment = DisplayFragment.newInstanceOf(dataString);
            getFragmentManager().beginTransaction().replace(R.id.displayView, fragment, DisplayFragment.TAG).commit();
        } else {
            fragment.loadText(dataString);
        }
    }
}
