package it15ns.friendscom.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.grpc.serverPackage.LoginReply;
import io.grpc.serverPackage.LoginResponse;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.R;
import it15ns.friendscom.model.FormTools;
import it15ns.friendscom.xmpp.XMPPClient;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText text_username;
    private EditText text_password;

    private View view_progress;
    private View view_login_form;

    private Button btn_register;
    private Button btn_signIn;

    private static XMPPClient xmppClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xmppClient = XMPPClient.getInstance();

        // Set up the login form.
        text_username = (EditText) findViewById(R.id.username);
        text_password = (EditText) findViewById(R.id.password);
        view_progress = findViewById(R.id.login_progress);
        view_login_form = findViewById(R.id.login_form);

        btn_signIn = (Button) findViewById(R.id.sign_in_button);
        btn_signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_register = (Button) findViewById(R.id.register_button);
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(registerActivity);
            }
        });
    }

    @Override
    protected void onStop() {
        /**
        try {
            xmppClient.sendStanze(new Presence(Presence.Type.unavailable));
        } catch (Exception ex) {
            Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        **/

        super.onStop();
    }
    
    private void attemptLogin() {
        // Reset errors.
        text_username.setError(null);
        text_password.setError(null);

        // Store values at the time of the login attempt.
        String username = text_username.getText().toString();
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
            text_username.setError(getString(R.string.error_field_required));
            focusView = text_username;
            cancel = true;
        } else if (!FormTools.isValidNickname(username)) {
            text_username.setError(getString(R.string.error_invalid_username));
            focusView = text_username;
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
            new GrpcTask(GrpcRunnableFactory.getLoginRunnable(username, password , this)).execute();
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

     // Wird von der asynchronen grpc login thread aufgerufen
    public void loginResult(LoginResponse response) {
        String username = text_username.getText().toString();
        String password = text_password.getText().toString();

        if(response.getSuccess()) {
            String token = response.getToken();
            try {
                xmppClient.init(username, token);
                // start async task
                xmppClient.connectConnection(this);
            } catch (Exception ex) {
                Toast.makeText(LoginActivity.this, "Es gibt Probleme mit dem Nachrichtenserver!", Toast.LENGTH_LONG).show();
                Log.d("XMPP Error", ex.getMessage());
            }
        } else {
            showProgress(false);
            Toast.makeText(LoginActivity.this, R.string.error_incorrect_login, Toast.LENGTH_LONG).show();

            text_password.requestFocus();
        }
    }

    public void xmppLoginFinished(boolean success) {
        if(success) {
            showProgress(false);
            Intent chatActivity = new Intent(this,ChatActivity.class);
            startActivity(chatActivity);
        } else {
            showProgress(false);
            Toast.makeText(LoginActivity.this, "Es gibt Probleme mit dem Nachrichtenserver!", Toast.LENGTH_LONG).show();
            text_password.requestFocus();
        }
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

            view_login_form.setVisibility(show ? View.GONE : View.VISIBLE);
            view_login_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view_login_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            view_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            view_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            view_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            view_login_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

