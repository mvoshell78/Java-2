package com.example.mich.mvoshell_multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements passBackFromFrag {


    private static final int MAIN_REQUEST_CODE = 1234;
    public ArrayList<FavoritesClass> mFavorites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mFavorites = new ArrayList<FavoritesClass>();
        getFragmentManager().beginTransaction().replace(R.id.mainActivity, new MainActivityFragment()).commit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void passBack(FavoritesClass favorite) {
        // makes a new instance of the mFav arrayList


        // adds the object to the array

        mFavorites.add(favorite);

         Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("Fav", mFavorites);
        //startActivity(intent);
            startActivityForResult(intent, MAIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAIN_REQUEST_CODE || resultCode == RESULT_OK){

            mFavorites = (ArrayList<FavoritesClass>) data.getSerializableExtra("return");


        }

    }
}



