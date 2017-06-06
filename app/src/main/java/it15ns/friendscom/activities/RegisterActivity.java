package it15ns.friendscom.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.grpc.serverPackage.LoginResponse;
import it15ns.friendscom.R;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.model.FormTools;
import it15ns.friendscom.xmpp.XMPPClient;

public class RegisterActivity extends AppCompatActivity {

    private EditText text_nickname;
    private EditText text_password;
    private EditText text_password2;

    private View registerView;
    private View progressView;
    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get Items
        text_nickname = (EditText)findViewById(R.id.nickname);
        text_password = (EditText)findViewById(R.id.password);
        text_password2 = (EditText)findViewById(R.id.password2);
        btn_register = (Button)findViewById(R.id.register_button);
        registerView = findViewById(R.id.username_login_form);
        progressView = findViewById(R.id.register_progress);

        //register button action listener
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        text_nickname.setError(null);
        text_password.setError(null);

        // Store values at the time of the login attempt.
        String username = text_nickname.getText().toString();
        String password = text_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !FormTools.isValidPassword(password)) {
            text_password.setError(getString(R.string.error_invalid_password));
            focusView = text_password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            text_nickname.setError(getString(R.string.error_field_required));
            focusView = text_nickname;
            cancel = true;
        } else if (!FormTools.isValidNickname(username)) {
            text_nickname.setError(getString(R.string.error_invalid_username));
            focusView = text_nickname;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            // start async background thread for grpc authentication

            new GrpcTask(GrpcRunnableFactory.getRegisterRunnable(username, password , this)).execute();
        }
    }

    public void xmppLoginFinished(boolean success) {
        if(success) {
            showProgress(false);
            Intent chatActivity = new Intent(this,MainActivity.class);
            startActivity(chatActivity);
        } else {
            showProgress(false);
            Toast.makeText(RegisterActivity.this, "Es gibt Probleme mit dem Nachrichtenserver!", Toast.LENGTH_LONG).show();
            text_password.requestFocus();
        }
    }

    public void registerResult(LoginResponse response) {
        if(response.getSuccess()) {
            String username = text_nickname.getText().toString();
            String token = response.getToken();
            SharedPreferences sharedPrefs = getSharedPreferences("data", Context.MODE_PRIVATE);
            sharedPrefs.edit().putString("token", token).commit();
            sharedPrefs.edit().putString("username", username).commit();

            try {
                XMPPClient.init(text_nickname.getText().toString(), token);
                // start async task
                XMPPClient.connect(this);
            } catch (Exception ex) {
                Toast.makeText(RegisterActivity.this, "Es gibt Probleme mit dem Nachrichtenserver!", Toast.LENGTH_LONG).show();
                Log.d("XMPP Error", ex.getMessage());
            }

            // TODO: Abfangen nach Registrieren --> Direkter Login
            Intent chatActivity = new Intent(this,MainActivity.class);
            startActivity(chatActivity);
        } else {
            showProgress(false);
            Toast.makeText(RegisterActivity.this, "Registrierung fehlgeschlagen", Toast.LENGTH_LONG).show();
            text_password.requestFocus();
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 5;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
