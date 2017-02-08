package com.codechallenge.dbserver.lunchtime.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.presenter.VotingController;
import com.codechallenge.dbserver.lunchtime.models.Restaurant;
import com.codechallenge.dbserver.lunchtime.models.Vote;
import com.codechallenge.dbserver.lunchtime.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thulioaraujo on 1/7/2017.
 */
public class VoteDialogActivity extends AppCompatActivity {

    @InjectView(R.id.restaurant_name) TextView restaurantName;
    @InjectView(R.id.restaurant_address) TextView restaurantAdress;
    @InjectView(R.id.restaurant_votes) TextView restaurantVotes;
    @InjectView(R.id.btn_vote) Button voteButton;

    private ProgressDialog progressDialog;
    private VotingTask mVotingTask;
    private RetriveRestaurantDailyVotesTask mRetriveRestaurantDailyVotesTask;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_dialog);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(VoteDialogActivity.this);
        progressDialog.setIndeterminate(true);

        Intent it = getIntent();
        restaurant = (Restaurant) it.getSerializableExtra(Restaurant.class.getName());
        restaurantName.setText(restaurant.getRestaurantName());
        restaurantAdress.setText(restaurant.getRestaurantAddress());

        voteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Recording vote...");
                showProgress(true);
                // Get the user email from the SharedPreferences stored during login
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constants.APPLICATION_NAME, 0);
                Vote vote = new Vote();
                vote.setUserUserName(sharedPref.getString(Constants.EMAIL_PARAM, null));
                vote.setRestaurantName(restaurant.getRestaurantName());
                mVotingTask = new VotingTask(vote, getApplicationContext());
                mVotingTask.execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.setMessage("Loading...");
        showProgress(true);
        mRetriveRestaurantDailyVotesTask = new RetriveRestaurantDailyVotesTask(restaurant.getRestaurantName());
        mRetriveRestaurantDailyVotesTask.execute();
    }

    /**
     * Shows the progress UI
     */
    private void showProgress(final boolean show) {
        if (show){
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the Parent Activity
        moveTaskToBack(true);
    }

    /**
     * Represents an asynchronous access to the web service used to
     * store new nearby restaurants
     */
    public class RetriveRestaurantDailyVotesTask extends AsyncTask<Object, String, String> {

        private String mRestaurantName;
        private int votes;

        public RetriveRestaurantDailyVotesTask(String restaurantName){
            mRestaurantName = restaurantName;
            votes = 0;
        }

        @Override
        protected String doInBackground(Object... objects) {
            votes = VotingController.getInstance().getDailyRestaurantVotes(mRestaurantName);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(false);
            restaurantVotes.setText(String.valueOf(votes));
            mRetriveRestaurantDailyVotesTask = null;
        }
    }

    public void showAlertVote(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(VoteDialogActivity.this);
        builder.setTitle(msg)
                .setPositiveButton("Ok", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Represents an asynchronous access to the web service used to
     * store new nearby restaurants
     */
    public class VotingTask extends AsyncTask<Object, String, String> {

        private Vote mVote;
        private JSONObject object;
        private Context mContext;

        public VotingTask(Vote vote, Context context) {
            mVote = vote;
            mContext = context;
            object = null;
        }

        @Override
        protected String doInBackground(Object... objects) {
            object = VotingController.getInstance().registerVote(mVote);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(false);
            mVotingTask = null;
            try {
                if(object.getBoolean("status")){
                    Toast.makeText(mContext, "Your vote has been successfully registered!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    showAlertVote(object.getString("error_msg"));
                }
            } catch (JSONException e) {
                Toast.makeText(mContext, "Problem to process the result retrieved!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
            mVotingTask = null;
        }
    }
}
