package com.example.mich.voshell_fundamentals;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Mich on 3/2/16.
 */
public class DisplayListFragment extends ListFragment {

    // list of cities for the fraglist
    String[] data = {"Dover", "Smyrna", "Camden", "Newark", "Laurel"};

    onDataItemClickListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof onDataItemClickListener) {
            listener = (onDataItemClickListener) activity;
        } else {
            throw new IllegalArgumentException("Application not connected to interface");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, data);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Toast.makeText(getActivity(), "You Selcted " + data[position], Toast.LENGTH_LONG).show();

        listener.dataItemSelected(data[position]);


    }
}


