package it15ns.friendscom.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it15ns.friendscom.R;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.model.User;
import it15ns.friendscom.xmpp.XMPPClient;

public class ProfileActivity extends AppCompatActivity {

    private TextView mWelcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWelcomeText = (TextView) findViewById(R.id.welcomeTextView);

        Bundle bundle = getIntent().getExtras();
        String nickname = bundle.getString("nickname");

        User user = Handler.getInstance().getUser(nickname);
        mWelcomeText.setText("Alle Informationen zu " + user.getName() + ". \n Hast du mit dem User schon gechattet: " + (user.hasChat() ? "ja" : "nein"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
