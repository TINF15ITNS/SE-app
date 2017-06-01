package it15ns.friendscom.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import it15ns.friendscom.R;
import it15ns.friendscom.handler.LocalUserHandler;
import it15ns.friendscom.xmpp.XMPPClient;

public class SplashActivity extends AppCompatActivity {

    private View splash_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_progress = findViewById(R.id.splash_progress);
        showProgress(true);

        /**
        SharedPreferences sharedPrefs = getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = sharedPrefs.getString("token", "");
        String username = sharedPrefs.getString("username", "");
        **/


        if(!LocalUserHandler.isLoggedIn(this)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            try {
                XMPPClient.init(LocalUserHandler.getLocalUser().getNickname(), LocalUserHandler.getToken());
                // start async task
                XMPPClient.connect(this);
            } catch (Exception ex) {
                Toast.makeText(SplashActivity.this, "Es gibt Probleme mit dem Nachrichtenserver!", Toast.LENGTH_LONG).show();
                Log.d("XMPP Error", ex.getMessage());
            }
        }
    }

    public void xmppLoginFinished(boolean success) {
        if(success) {
            showProgress(false);
            Intent chatActivity = new Intent(this,MainActivity.class);
            startActivity(chatActivity);
        } else {
            showProgress(false);
            Toast.makeText(SplashActivity.this, "Es gibt Probleme mit dem Nachrichtenserver!", Toast.LENGTH_LONG).show();
        }
    }

    public void showProgress(final boolean show) {
            splash_progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
