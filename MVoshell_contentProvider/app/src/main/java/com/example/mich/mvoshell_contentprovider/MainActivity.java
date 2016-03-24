package com.example.mich.mvoshell_contentprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements passBackFromFrag {


    private static final int MAIN_REQUEST_CODE = 1234;
    public ArrayList<CityClass> mCities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mCities = new ArrayList<CityClass>();
        getFragmentManager().beginTransaction().replace(R.id.mainActivity, new MainActivityFragment()).commit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



    @Override
    public void passBack(CityClass cityItem) {
        // makes a new instance of the mFav arrayList


        // adds the object to the array

        mCities.add(cityItem);

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("Fav", mCities);
        //startActivity(intent);
        startActivityForResult(intent, MAIN_REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAIN_REQUEST_CODE || resultCode == RESULT_OK){

            mCities = (ArrayList<CityClass>) data.getSerializableExtra("return");


        }

    }
}



