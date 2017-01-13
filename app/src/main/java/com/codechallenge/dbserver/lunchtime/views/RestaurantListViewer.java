package com.codechallenge.dbserver.lunchtime.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.adapters.RestaurantListAdapter;
import com.codechallenge.dbserver.lunchtime.controller.RestaurantController;
import com.codechallenge.dbserver.lunchtime.models.Restaurant;
import com.codechallenge.dbserver.lunchtime.utils.GoogleApiUtility;
import com.codechallenge.dbserver.lunchtime.utils.GooglePlacesDataParser;
import com.codechallenge.dbserver.lunchtime.utils.GooglePlacesDownloadUrl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thulioaraujo on 1/7/2017.
 */

public class RestaurantListViewer extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private View view;
    private double latitude;
    private double longitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private ListView lvRestaurant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_list, null);

        lvRestaurant = (ListView) view.findViewById(R.id.restaurant_lv);
        lvRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Restaurant restaurant = (Restaurant) lvRestaurant.getItemAtPosition(position);
                Intent it = new Intent(getActivity(), VoteDialogActivity.class);
                it.putExtra(Restaurant.class.getName(), restaurant);
                startActivity(it);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            GoogleApiUtility.checkLocationPermission(getActivity());
        }

        //Check if Google Play Services Available or not
        if (!GoogleApiUtility.CheckGooglePlayServices(getActivity())) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            getActivity().finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }

        buildGoogleApiClient();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();

                String Restaurant = "restaurant";
                String url = GoogleApiUtility.getUrl(latitude, longitude, Restaurant, getActivity());
                ListNearbyPlacesTask getNearbyPlacesData = new ListNearbyPlacesTask(getActivity());
                getNearbyPlacesData.execute(url);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Represents an asynchronous Google Maps task used to
     * search for nearby restaurants
     */
    public class ListNearbyPlacesTask extends AsyncTask<Object, String, String> {

        private String googlePlacesData;
        private String url;
        private ProgressDialog pDialog;
        private Context mContext;

        private ArrayList<Restaurant> listRestaurants;
        private RestaurantListAdapter restaurantListAdapter;

        public ListNearbyPlacesTask(Context context){
            this.listRestaurants = new ArrayList<Restaurant>();
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Searching nearby restaurants...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                url = (String) params[0];
                GooglePlacesDownloadUrl googlePlacesDownloadUrl = new GooglePlacesDownloadUrl();
                googlePlacesData = googlePlacesDownloadUrl.readUrl(url);
            } catch (Exception e) {
                Log.d("GooglePlacesReadTask", e.toString());
            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            List<HashMap<String, String>> nearbyPlacesList = null;
            GooglePlacesDataParser googlePlacesDataParser = new GooglePlacesDataParser();
            nearbyPlacesList =  googlePlacesDataParser.parse(result);
            showNearbyPlaces(nearbyPlacesList);
        }

        private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
            for (int i = 0; i < nearbyPlacesList.size(); i++) {
                HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
                Restaurant restaurant = new Restaurant(
                        googlePlace.get("place_name"),
                        googlePlace.get("vicinity"),
                        Double.valueOf(googlePlace.get("lat")),
                        Double.valueOf(googlePlace.get("lng"))
                );
                this.listRestaurants.add(restaurant);
            }

            SyncronizeNearbyPlacesTask registerNewNearbyPlacesTask = new SyncronizeNearbyPlacesTask(listRestaurants);
            registerNewNearbyPlacesTask.execute();

            this.restaurantListAdapter = new RestaurantListAdapter(mContext, R.layout.restaurant_list_row, listRestaurants);
            lvRestaurant.setAdapter(this.restaurantListAdapter);
        }
    }

    /**
     * Represents an asynchronous access to the web service used to
     * store new nearby restaurants
     */
    public class SyncronizeNearbyPlacesTask extends AsyncTask<Object, String, String> {

        ArrayList<Restaurant> mRestaurants;

        public SyncronizeNearbyPlacesTask(ArrayList<Restaurant> restaurants) {
            mRestaurants = restaurants;
        }

        @Override
        protected String doInBackground(Object... objects) {
            for (Restaurant restaurant : mRestaurants) {
                if (!RestaurantController.getInstance().
                        checkIfRestaurantExists(restaurant.getRestaurantName())) {
                    RestaurantController.getInstance().registerRestaurant(restaurant);
                }
            }
            
            return null;
        }
    }
}
