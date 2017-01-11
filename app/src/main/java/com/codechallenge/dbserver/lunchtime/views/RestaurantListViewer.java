package com.codechallenge.dbserver.lunchtime.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codechallenge.dbserver.lunchtime.R;

/**
 * Created by thulioaraujo on 1/7/2017.
 */

public class RestaurantListViewer extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_list, null);
        return view;
    }
}
