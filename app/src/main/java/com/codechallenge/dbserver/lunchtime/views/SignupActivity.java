package com.codechallenge.dbserver.lunchtime.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.presenter.LoginPresenter;
import com.codechallenge.dbserver.lunchtime.models.User;
import com.codechallenge.dbserver.lunchtime.utils.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thulioaraujo on 1/7/2017.
 */
public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    /**
     * Keep track of the Signup task to ensure we can cancel it if requested.
     */
    private UserSignupTask mAuthTask = null;
    private ProgressDialog progressDialog;

    @InjectView(R.id.input_name) EditText nameText;
    @InjectView(R.id.input_email) EditText emailText;
    @InjectView(R.id.input_password) EditText passwordText;
    @InjectView(R.id.btn_signup) Button signupButton;
    @InjectView(R.id.link_login) TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);
        showProgress(true);

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        User user = new User(name, email, password);

        mAuthTask = new UserSignupTask(user);
        mAuthTask.execute((Void) null);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Intent intent = getIntent();
        intent.putExtra(Constants.NAME_PARAM, nameText.getText().toString());
        intent.putExtra(Constants.EMAIL_PARAM, emailText.getText().toString());
        intent.putExtra(Constants.PASSWORD_PARAM, passwordText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Failed to sign up on LunchTime app.", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
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

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("At least 3 characters");
            nameText.requestFocus();
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            emailText.requestFocus();
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Enter a valid password between 4 and 10 alphanumeric characters");
            passwordText.requestFocus();
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    /**
     * Represents an asynchronous registration task used to authenticate
     * the user.
     */
    public class UserSignupTask extends AsyncTask<Void, Void, Boolean> {

        private User mUser;

        UserSignupTask(User user) {
            mUser = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            if (LoginPresenter.getInstance().signupUser(mUser)){
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                onSignupSuccess();
            } else {
                onSignupFailed();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
