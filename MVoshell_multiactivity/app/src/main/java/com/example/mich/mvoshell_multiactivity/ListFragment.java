package com.example.mich.mvoshell_multiactivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Mich on 3/15/16.
 */
public class ListFragment extends android.app.ListFragment {

    public static final String TAG = "ListFragment";
    private static String ARG = "arg";

    public ListFragment(){

    }

    public static ListFragment newInstanceOf(ArrayList<FavoritesClass> fav ){
        ListFragment fragment = new ListFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG, fav);
        fragment.setArguments(args);
        return  fragment;
    }
    
    private ArrayList<FavoritesClass> mFavorites;

    private ArrayList<String> data;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        data = new ArrayList<String>();

        Bundle args = getArguments();
        if (args != null) {
            setUpList((ArrayList<FavoritesClass>) args.getSerializable(ARG));

        }
    }



    public void setUpList(ArrayList<FavoritesClass> fav) {

        ArrayList<FavoritesClass> favorite = fav;
        int size = favorite.size();
        String favName;
        for (int i = 0; i < size ; i++) {
            FavoritesClass getFavName = favorite.get(i);
            favName = getFavName.getmName();


            data.add(favName);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, data);
            setListAdapter(adapter);

    }
}
