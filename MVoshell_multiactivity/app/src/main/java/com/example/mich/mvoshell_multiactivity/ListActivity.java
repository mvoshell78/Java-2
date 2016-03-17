package com.example.mich.mvoshell_multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {



    private ArrayList<FavoritesClass> mFavorites;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavorites = new ArrayList<FavoritesClass>();
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent mainIntent = getIntent();
        mFavorites = (ArrayList<FavoritesClass>) mainIntent.getSerializableExtra("Fav");



        getFragmentManager().beginTransaction().replace(R.id.main_container,new ListFragment()).commit();

        ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);
        if (fragment == null){
            fragment = ListFragment.newInstanceOf(mFavorites);
            getFragmentManager().beginTransaction().replace(R.id.main_container,fragment,ListFragment.TAG).commit();

        } else {
            fragment.setUpList(mFavorites);
        }



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
