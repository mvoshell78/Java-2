package com.example.mich.voshell_fundamentals;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mich on 3/9/16.
 */
public class GetAPIData {


    locationObject latLong;
    String passedCity;

    Context mContext;

    getApiDataPasser listener;

    public GetAPIData(Context mContext){

        if (mContext instanceof getApiDataPasser){
            listener = (getApiDataPasser) mContext;
            this.mContext = mContext;
        }

    }

    public void runBackTask(){
        backTask task = new backTask();
        task.execute();
    }
    // method to pull the data from the API
    public class backTask extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            // Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_LONG).show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            // The URL string that points to our web resource.

            // var to create the urlString
            String state = "Delaware";
            String address = "";
            String urlString = "http://www.yaddress.net/api/address?AddressLine1=" + address + "&AddressLine2=" + passedCity + "+" + state;




            // Creating the URL object that points to our web resource.
            URL url = null;

            // Establish a connection to the resource at the URL.
            HttpURLConnection connection = null;

            String resourceData = "No Data";
            try {
                url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                // Setting connection properties.
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000); // 10 seconds
                connection.setReadTimeout(10000); // 10 seconds
                // Refreshing the connection.
                connection.connect();

                // Optionally check the status code. Status 200 means everything went OK.
                int statusCode = connection.getResponseCode();

                // Getting the InputStream with the data from our resource.
                InputStream stream = connection.getInputStream();


                // Reading data from the InputStream using the Apache library.
                resourceData = IOUtils.toString(stream);


                // Cleaning up our connection resources.
                stream.close();
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject apiData;

            try{
                apiData = new JSONObject(resourceData);


            } catch(Exception e){
                System.out.println("Cannot convert API resource to JSON");
                apiData = null;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (apiData != null) {

                return apiData;
            }else{
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject s) {
            //System.out.println(s);
            try {

                // var to hold the data that comes back
                String longitude = s.getString("Longitude");
                String latitude = s.getString("Latitude");

                // creating the custom object from the returned data
                latLong = new locationObject(latitude, longitude);

                //System.out.println(latLong.getLatitude());
                //System.out.println(latLong.getLongitude());

                // saving the data in the event their is no connection
                //saveData(latLong);

                listener.ApiDataToPass(latLong);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // updating the frag
            //updateFrag();
        }
    }

}
