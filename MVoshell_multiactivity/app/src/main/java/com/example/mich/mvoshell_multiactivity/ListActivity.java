package com.example.mich.mvoshell_multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements getListPosition {


    private static final int MAIN_REQUEST_CODE = 1234;
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


            saveData(mFavorites);
            readFromStorage();
            createFrag();

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

    @Override
    public void passBackPosition(int position) {

// passbackPosition also transisitions to displayActivity
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("Fav", mFavorites);
        intent.putExtra("position",position);
        startActivityForResult(intent, MAIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAIN_REQUEST_CODE || resultCode == RESULT_OK){
            mFavorites = (ArrayList<FavoritesClass>) data.getSerializableExtra("return");

            saveData(mFavorites);
            readFromStorage();


            ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);

            fragment.setUpList(mFavorites);
        }
    }

    // save the dat to storage method
    public void saveData(ArrayList<FavoritesClass> FavoriteList) {

        // creates a var from the item selected to write the data to
        String FILENAME = "Fav.txt";


        //Toast.makeText(this, FILENAME, Toast.LENGTH_SHORT).show();
        try {
            // writeing the data
            FileOutputStream fileOutputStream = openFileOutput(FILENAME, MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(FavoriteList);
            objectOutputStream.close();

            // user feedback to let them know the save was successful
            Toast.makeText(this, "Saved Succesful", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Item Not Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Item Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    // read the data from storage
    public ArrayList<FavoritesClass> readFromStorage(){

        // creates a var from the item selected to read the data from
        String FILENAME = "Fav.txt";

        //Toast.makeText(this, FILENAME, Toast.LENGTH_SHORT).show();

        // creates a place to write the data to
        mFavorites = new ArrayList<FavoritesClass>();
        try{
            // read that data
            FileInputStream fileInputStream = openFileInput(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            mFavorites = (ArrayList<FavoritesClass>) objectInputStream.readObject();
            Toast.makeText(this,"Read Succesfull", Toast.LENGTH_SHORT).show();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }


        return mFavorites;
    }


    public void createFrag(){

        getFragmentManager().beginTransaction().replace(R.id.main_container,new ListFragment()).commit();

        ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);
        if (fragment == null){
            fragment = ListFragment.newInstanceOf(mFavorites);
            getFragmentManager().beginTransaction().replace(R.id.main_container,fragment,ListFragment.TAG).commit();

        } else {
            fragment.setUpList(mFavorites);
        }
    }
}
