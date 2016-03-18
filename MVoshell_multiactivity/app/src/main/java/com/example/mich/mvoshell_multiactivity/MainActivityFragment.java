package com.example.mich.mvoshell_multiactivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Mich on 3/15/16.
 */
public class MainActivityFragment extends Fragment {


    Button addButton;
    //sets a var for the edit text field
    EditText nameField;
    EditText favFoodField;
    EditText favColorField;
    FavoritesClass fav;


    passBackFromFrag listener;

    private ArrayList<FavoritesClass> mFavorites;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof passBackFromFrag){
            listener = (passBackFromFrag) activity;
        } else {
            throw new IllegalArgumentException("not connected");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // calls addButton function
        addButtonFunc();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    // method to keep my oncreate clear
    public void addButtonFunc() {
        addButton = (Button) getActivity().findViewById(R.id.button);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // gets data from the textfield and stores in a variable
                nameField = (EditText) getActivity().findViewById(R.id.editText);
                favColorField = (EditText) getActivity().findViewById(R.id.editText2);
                favFoodField = (EditText) getActivity().findViewById(R.id.editText3);




                // gets the string value of the textField
                String name = String.valueOf(nameField.getText());
                String color = String.valueOf(favColorField.getText());
                String food = String.valueOf(favFoodField.getText());

                fav = new FavoritesClass(name, color, food);

                nameField.setText("");
                favColorField.setText("");
                favFoodField.setText("");

                listener.passBack(fav);

            }
        });
    }
}
