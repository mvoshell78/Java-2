package com.example.mich.mvoshell_multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements passBackFromFrag {

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
            startActivity(intent);
    }
}



