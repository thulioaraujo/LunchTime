package com.codechallenge.dbserver.lunchtime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulioaraujo on 1/11/2017.
 */

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {

    private ArrayList<Restaurant> mRestaurants;
    private Context mContext;

    public RestaurantListAdapter(Context context, int resource, ArrayList<Restaurant> items) {
        super(context, resource, items);

        mRestaurants = items;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.restaurant_list_row, null);
        }

        Restaurant restaurant = mRestaurants.get(position);
        if (restaurant != null) {
            TextView restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
            restaurantName.setText(restaurant.getRestaurantName());

            TextView restaurantAddress = (TextView) view.findViewById(R.id.restaurant_address);
            restaurantAddress.setText(restaurant.getRestaurantAddress());
        }

        return view;
    }
}

