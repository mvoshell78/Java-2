package com.example.mich.mvoshell_contentresolver;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mich on 3/24/16.
 */
public class DisplayFragment extends Fragment {

    public static final String TAG = "DisplayFragment";
    private static final java.lang.String ARG_STRING = "arg_Strings";


    public DisplayFragment(){

    }

    public static DisplayFragment newInstanceOf(){
        DisplayFragment displayFragment = new DisplayFragment();
        return displayFragment;
    }

    public static DisplayFragment newInstanceOf(String item){
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.getString(ARG_STRING, item);

        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Bundle args = getArguments();

        if(args != null){
            loadText(args.getString(ARG_STRING));
        }
    }

    public void loadText(String string) {

        ((TextView)getActivity().findViewById(R.id.textView)).setText(string);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display,container,false);
    }
}
