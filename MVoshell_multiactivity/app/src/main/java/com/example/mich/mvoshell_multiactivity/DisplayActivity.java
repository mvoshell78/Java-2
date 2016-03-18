package com.example.mich.mvoshell_multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {




    ArrayList<FavoritesClass> mFavorites;
    FavoritesClass fav;

    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mFavorites = new ArrayList<FavoritesClass>();
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent mainIntent = getIntent();
        mFavorites = (ArrayList<FavoritesClass>) mainIntent.getSerializableExtra("Fav");
        position = mainIntent.getIntExtra("position", 0);


        fav = mFavorites.get(position);

        getFragmentManager().beginTransaction().replace(R.id.dispaly_container,new DisplayFragment()).commit();

       DisplayFragment fragment = (DisplayFragment) getFragmentManager().findFragmentByTag("DisplayFragment");

        if (fragment == null){
            fragment = DisplayFragment.newInstanceOf(fav);
            getFragmentManager().beginTransaction().replace(R.id.dispaly_container,fragment,DisplayFragment.TAG).commit();
        } else {
            fragment.loadText(fav);
        }

      Button  share  = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String favTxt = "From my favorites list : "
                        + "\nName : " + mFavorites.get(position).getmName()
                        + "\nFavorite Color : " + mFavorites.get(position).getmFavoriteColor()
                        + "\nFavorite Food : "+ mFavorites.get(position).getmFavoriteFood();

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, favTxt);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share data with..."));

//                Snackbar.make(v, "button pressed", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFavorites.remove(position);
                Snackbar.make(view, "Item Was Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                sendBack();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void sendBack(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("return", mFavorites);
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
