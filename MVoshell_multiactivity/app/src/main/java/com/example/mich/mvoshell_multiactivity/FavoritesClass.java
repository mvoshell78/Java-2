package com.example.mich.mvoshell_multiactivity;

import java.io.Serializable;

/**
 * Created by Mich on 3/15/16.
 */
public class FavoritesClass implements Serializable {

    // Member variables
    private String mName;
    private String mFavoriteColor;
    private String mFavoriteFood;


    //creates the object
    public FavoritesClass(String _name, String _favoriteColor, String _favoriteFood ) {

        mName = _name;
        mFavoriteColor = _favoriteColor;
        mFavoriteFood = _favoriteFood;


    }




    // Getter methods.
    public String getmName() { return mName;}
    public String getmFavoriteColor() { return mFavoriteColor;}
    public String getmFavoriteFood() { return mFavoriteFood;}

    // Setter Methods
    public void setmName(String _name) { mName = _name; }
    public void setmFavoriteColor(String _favoriteColor) { mFavoriteColor = _favoriteColor; }
    public void setmFavoriteFood(String _favoriteFood) { mFavoriteFood = _favoriteFood; }

}
