package com.example.mich.voshell_fundamentals;

import java.io.Serializable;

/**
 * Created by Mich on 3/5/16.
 */
public class locationObject implements Serializable {


    // Member variables
    private String mLatitude;
    private String mLongitude;


    //creates the object
    public locationObject(String _latitude, String _longitude ) {

        mLatitude = _latitude;
        mLongitude = _longitude;


    }


    // Getter methods.
    public String getLatitude() { return mLatitude;}
    public String getLongitude() { return mLongitude;}

    // Setter Methods
    public void setLatitude(String _latitude) { mLatitude = _latitude; }
    public void setLongitude(String _longitude) { mLongitude = _longitude; }

}
