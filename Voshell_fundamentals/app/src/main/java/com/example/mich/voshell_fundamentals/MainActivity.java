package com.example.mich.voshell_fundamentals;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity implements onDataItemClickListener,getApiDataPasser {

    locationObject latLong;
    String city;
    EditText searchText;
    Context context;
    Boolean trueFalse;


    String[] data = {"Dover", "Smyrna", "Camden", "Newark", "Laurel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // displays initial fragment
        getFragmentManager().beginTransaction().replace(R.id.mainContainer,new DisplayListFragment()).commit();
        getFragmentManager().beginTransaction().replace(R.id.displayView, new DisplayDataFragment()).commit();

        // calls the function to check if were connected
        isOnline();
       trueFalse = true;



    }

    public void onDeleteClick(View view){
        deleteData();
    }

    public void onPrefClick(View view){



        if (trueFalse == true){
            trueFalse = false;
            SettingsFragment fragment = (SettingsFragment)getFragmentManager().findFragmentByTag(SettingsFragment.TAG);
            // checking for null
            if (fragment == null) {
                fragment = new SettingsFragment();
                // if no frag exists create on


                // Toast.makeText(this,lat,Toast.LENGTH_SHORT).show();
                // Toast.makeText(this,lon,Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().addToBackStack(SettingsFragment.TAG).replace(R.id.displayView, fragment, SettingsFragment.TAG).commit();

            } else {
                // if a frag exists update it with the following txt items
                getFragmentManager().beginTransaction().addToBackStack(SettingsFragment.TAG).replace(R.id.displayView, fragment, SettingsFragment.TAG).commit();
            }
        } else if (trueFalse == false){
            trueFalse = true;
            DisplayDataFragment fragment = (DisplayDataFragment)getFragmentManager().findFragmentByTag(DisplayDataFragment.TAG);
            fragment = new DisplayDataFragment();
            getFragmentManager().beginTransaction().addToBackStack(DisplayDataFragment.TAG).replace(R.id.displayView, fragment,DisplayDataFragment.TAG ).commit();
        }



    }

// Method for search function
    public void onButtonClick(View view){



        //sets a var for the edit text field
        searchText = (EditText) findViewById(R.id.searchTextField);

        // sets the city value to the serch text
        city = String.valueOf(searchText.getText());
        // user feed back of typed city


        // checks to see if online and proceed accordingly
        isOnline();
    }

    protected boolean isOnline() {

        // in order for this to work you must add permissions to the android mainfest file or it will crash
        // func to check for network connectivity
        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();



        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            //Toast.makeText(this, "We're Connected", Toast.LENGTH_LONG).show();


            GetAPIData getData  = new GetAPIData(this);

            getData.runBackTask();
            getData.passedCity = city;
            // if were connected function executes backtask method to pull from the API
            //backTask task = new backTask();
            //task.execute();

            return true;
        } else{

            // Toast to alert the user that the info is coming from storage
            Toast.makeText(this,"No internet connection, \nReading from storage", Toast.LENGTH_SHORT).show();

            // if not connected func executes the read from storage method
            readFromStorage();

            return false;
        }
    }



    @Override
    public void dataItemSelected(String item) {
        // checks for which item was selected from the list

        // varible is set by the name of the item clicked
        // used in the API call and the storage and read functions to get to the data
        city = item;

        //System.out.println(city);

        // checks again for connection when selecting the item to see which path it should take
        // read from storage or pull the API
        isOnline();


    }


public  void updateFrag(){
    // new instance of fragment
    DisplayDataFragment fragment = (DisplayDataFragment)getFragmentManager().findFragmentByTag(DisplayDataFragment.TAG);
    // when finnished update the frag

        // var to hold the pieces of the object to display the text in the fragment
        String lat = latLong.getLatitude();
        String lon = latLong.getLongitude();

        // checking for null
        if (fragment == null) {

            // if no frag exists create on
            fragment = DisplayDataFragment.newInstanceOf(lat, lon);

            // Toast.makeText(this,lat,Toast.LENGTH_SHORT).show();
            // Toast.makeText(this,lon,Toast.LENGTH_SHORT).show();

            getFragmentManager().beginTransaction().replace(R.id.displayView, fragment, DisplayDataFragment.TAG).commit();

        } else {
            // if a frag exists update it with the following txt items
            fragment.loadText(lat, lon);
        }

}

    // save the dat to storage method
    public void saveData(locationObject latLong) {

        // creates a var from the item selected to write the data to
        String FILENAME = city +".txt";


        //Toast.makeText(this, FILENAME, Toast.LENGTH_SHORT).show();
        try {
            // writeing the data
            FileOutputStream fileOutputStream = openFileOutput(FILENAME, MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(latLong);
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
    public locationObject readFromStorage(){

        // creates a var from the item selected to read the data from
        String FILENAME = city +".txt";

        //Toast.makeText(this, FILENAME, Toast.LENGTH_SHORT).show();

        // creates a place to write the data to
        latLong = new locationObject("", "");
        try{
            // read that data
            FileInputStream fileInputStream = openFileInput(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            latLong = (locationObject) objectInputStream.readObject();
            //Toast.makeText(this,"Read Succesfull", Toast.LENGTH_SHORT).show();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
            if (latLong.getLatitude().isEmpty()){
                updateFrag();
                Toast.makeText(this,"No Saved Data", Toast.LENGTH_SHORT).show();
            } else {
                updateFrag();
            }



        return latLong;
    }


    @Override
    public void ApiDataToPass(locationObject lattLong) {
        latLong = lattLong;
        updateFrag();
       saveData(lattLong);

    }


    public void deleteData(){


        String[] data = {"Dover", "Smyrna", "Camden", "Newark", "Laurel"};

        for (int i = 0; i < data.length; i++) {

            String FILENAME = data[i] + ".txt";


            deleteFile(FILENAME);

        }

Toast.makeText(this,"data deleted", Toast.LENGTH_SHORT).show();
        System.out.println("test");
    }
}
