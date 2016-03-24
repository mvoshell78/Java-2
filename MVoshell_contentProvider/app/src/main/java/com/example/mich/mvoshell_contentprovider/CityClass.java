package com.example.mich.mvoshell_contentprovider;

import java.io.Serializable;

/**
 * Created by Mich on 3/15/16.
 */
public class CityClass implements Serializable {

    // Member variables
    private String mCity;
    private String mPopulation;
    private String mClimate;


    //creates the object
    public CityClass(String _name, String _favoriteColor, String _favoriteFood) {

        mCity = _name;
        mPopulation = _favoriteColor;
        mClimate = _favoriteFood;


    }




    // Getter methods.
    public String getmCity() { return mCity;}
    public String getmPopulation() { return mPopulation;}
    public String getmClimate() { return mClimate;}

    // Setter Methods
    public void setmCity(String _name) { mCity = _name; }
    public void setmPopulation(String _favoriteColor) { mPopulation = _favoriteColor; }
    public void setmClimate(String _favoriteFood) { mClimate = _favoriteFood; }

}
