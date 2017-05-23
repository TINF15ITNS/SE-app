package it15ns.friendscom.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import it15ns.friendscom.R;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.model.User;
import it15ns.friendscom.xmpp.XMPPClient;

public class ProfileActivity extends AppCompatActivity {

    private TextView title;
    private TextView nickname;
    private TextView name;
    private TextView surname;
    private TextView birthday;
    private TextView telnr;
    private TextView email;
    private TextView nickname_title;
    private TextView name_title;
    private TextView surname_title;
    private TextView birthday_title;
    private TextView telnr_title;
    private TextView email_title;
    private Button edit;
    private LinearLayout linear;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linear = (LinearLayout) findViewById(R.id.linear);
        title = (TextView) findViewById(R.id.title);
        nickname = (TextView) findViewById(R.id.nickname);
        name = (TextView) findViewById(R.id.name);
        surname = (TextView) findViewById(R.id.surname);
        birthday = (TextView) findViewById(R.id.birthday);
        telnr = (TextView) findViewById(R.id.telNumber);
        email = (TextView) findViewById(R.id.email);
        nickname_title = (TextView) findViewById(R.id.nickname_title);
        name_title = (TextView) findViewById(R.id.name_title);
        surname_title = (TextView) findViewById(R.id.surname_title);
        birthday_title = (TextView) findViewById(R.id.birthday_title);
        telnr_title = (TextView) findViewById(R.id.telNumber_title);
        email_title = (TextView) findViewById(R.id.email_title);
        edit = (Button) findViewById(R.id.edit);

        Bundle bundle = getIntent().getExtras();
        String nickname = bundle.getString("nickname");

        user = Handler.getInstance().getUser(nickname);

        //if its my profile im aber to edit my settings
        if(true)    //TODO: me?
            edit.setVisibility(View.VISIBLE);
        //on click load new layout
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadEditLayout();
            }
        });

        loadUser();
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

    public void loadUser(){
        linear.removeAllViews();
        title.setText("Alle Informationen zu " + user.getName() + ". \n" + (user.hasChat() ? "Ihr habt schon gechatet" : "Ihr habt noch nie gechatet"));
        nickname.setText(user.getNickname());
        name.setText(user.getName());
        surname.setText(user.getSurname());
        birthday.setText(user.getBirthday().toString());
        telnr.setText(user.getTelNumber());
        email.setText(user.geteMail());
        linear.addView(title);
        linear.addView(nickname_title);
        linear.addView(nickname);
        linear.addView(name_title);
        linear.addView(name);
        linear.addView(surname_title);
        linear.addView(surname);
        linear.addView(birthday_title);
        linear.addView(birthday);
        linear.addView(telnr_title);
        linear.addView(telnr);
        linear.addView(email_title);
        linear.addView(email);
    }

    public void loadEditLayout(){
        linear.removeAllViews();
        linear.addView(title);
        linear.addView(nickname_title);
        EditText nickname_e = new EditText(this);
        nickname_e.setText(user.getNickname());
        linear.addView(nickname_e);
        linear.addView(name_title);
        EditText name_e = new EditText(this);
        name_e.setText(user.getName());
        linear.addView(name_e);
        linear.addView(surname_title);
        EditText surname_e = new EditText(this);
        surname_e.setText(user.getSurname());
        linear.addView(surname_e);
        linear.addView(birthday_title);
        EditText birthday_e = new EditText(this);
        birthday_e.setText(user.getBirthday().toString());
        linear.addView(birthday_e);
        linear.addView(telnr_title);
        EditText telnr_e = new EditText(this);
        telnr_e.setText(user.getTelNumber());
        linear.addView(telnr_e);
        linear.addView(email_title);
        EditText email_e = new EditText(this);
        email_e.setText(user.geteMail());
        linear.addView(email_e);

        //edit button to save and exit settings
        Button save_btn = new Button(this);
        save_btn.setText("Speichern");
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: tell server to update user data
            }
        });
        linear.addView(save_btn);
        Button exit_btn = new Button(this);
        exit_btn.setText("Beenden");
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUser();
            }
        });
        linear.addView(exit_btn);
    }
}
