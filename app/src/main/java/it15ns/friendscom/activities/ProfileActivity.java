package it15ns.friendscom.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it15ns.friendscom.R;
import it15ns.friendscom.xmpp.XMPPClient;

public class ProfileActivity extends AppCompatActivity {

    private TextView mWelcomeText;
    XMPPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client = XMPPClient.getInstance();

        mWelcomeText = (TextView) findViewById(R.id.welcomeTextView);
        mWelcomeText.setText("Hallo user!");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.disconnectConnection();
            }
        });

    }

    @Override
    protected void onStop() {
        client.disconnectConnection();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        try {
            client.connectConnection();
        } catch (Exception ex) {
            Toast.makeText(ProfileActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
        }
        super.onRestart();
    }
}
