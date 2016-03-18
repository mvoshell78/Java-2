package com.example.mich.mvoshell_multiactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mich on 3/17/16.
 */
public class DisplayFragment extends Fragment{


     static final String TAG = "DisplayFragment";
    private static final String ARG = "arg_String" ;
    public TextView nameText;
    TextView favColorText;
    TextView favFoodText;

    public static DisplayFragment newInstanceOf(FavoritesClass item){

        DisplayFragment fragment = new DisplayFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG, item);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args != null){
        loadText((FavoritesClass) args.getSerializable(ARG));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display,container,false);

    }

    public void loadText(FavoritesClass fav){
        ((TextView)getActivity().findViewById(R.id.favNameTextView)).setText("Name : " + fav.getmName());
        ((TextView)getActivity().findViewById(R.id.favColorTextView)).setText("Favorite Color : " + fav.getmFavoriteColor());
        ((TextView)getActivity().findViewById(R.id.favFoodTextView)).setText("Favorite Color : " + fav.getmFavoriteFood());
    }
}
