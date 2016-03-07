package com.example.mich.voshell_fundamentals;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mich on 3/2/16.
 */
public class DisplayDataFragment extends Fragment {
    TextView textView;

    //shortCut  logt
    public static final String TAG = "DisplayDataFragment";
    private static final String ARG_LAT = "arg_String" ;
    private static final String ARG_LONG = "arg_String" ;

    public DisplayDataFragment() {

    }

    public static DisplayDataFragment newInstance() {
        DisplayDataFragment displayDataFragment = new DisplayDataFragment();
        return displayDataFragment;
    }

    public static DisplayDataFragment newInstanceOf(String latitude, String longitude){
        DisplayDataFragment fragment = new DisplayDataFragment();


        Bundle args = new Bundle();

        args.putString(ARG_LAT, latitude);
        args.putString(ARG_LONG, longitude);

        System.out.println("DisplayDataFragment");
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");
        Bundle args = getArguments();

        if (args != null) {

            loadText(args.getString(ARG_LAT), args.getString(ARG_LONG));

        }
    }

    public void loadText(String latitude, String longitude) {

       System.out.println("loadText");


        ((TextView) getActivity().findViewById(R.id.detailFragment)).setText("Latitude " + (CharSequence) latitude);
        ((TextView) getActivity().findViewById(R.id.textView2)).setText("Longitude " + (CharSequence) longitude);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b, container, false);
    }
}
