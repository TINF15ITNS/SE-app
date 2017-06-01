package it15ns.friendscom.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;

import it15ns.friendscom.R;
import it15ns.friendscom.handler.UserHandler;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.handler.LocalUserHandler;
import it15ns.friendscom.model.User;

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

    private TextView e_title;
    private TextView e_nickname;
    private TextView e_name;
    private TextView e_surname;
    private TextView e_birthday;
    private TextView e_telnr;
    private TextView e_email;

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

        if(bundle != null) {
            edit.setVisibility(View.INVISIBLE);
            String nickname = bundle.getString("nickname");
            user = UserHandler.getUser(nickname);
            setTitle(user.getSurname());

        } else {
            user = LocalUserHandler.getLocalUser();
            setTitle("Einstellungen");
        }


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
        if(user != LocalUserHandler.getLocalUser()) {
            title.setText((user.hasChat() ? "Ihr habt schon gechattet" : "Ihr habt noch nie gechattet"));
            linear.addView(title);
        }

        nickname.setText(user.getNickname());
        name.setText(user.getName());
        surname.setText(user.getSurname());
        birthday.setText(user.getBirthday() != null ? user.getBirthday().toString() : "");
        telnr.setText(user.getTelNumber());
        email.setText(user.geteMail());

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
        linear.addView(edit);
    }

    public void loadEditLayout(){
        linear.removeAllViews();
        linear.addView(title);
        linear.addView(nickname_title);
        //e_nickname = new EditText(this);
        //e_nickname.setText(user.getNickname());
        //linear.addView(e_nickname);
        linear.addView(nickname);
        linear.addView(name_title);
        e_name = new EditText(this);
        e_name.setText(user.getName());
        linear.addView(e_name);
        linear.addView(surname_title);
        e_surname = new EditText(this);
        e_surname.setText(user.getSurname());
        linear.addView(e_surname);
        linear.addView(birthday_title);
        e_birthday = new EditText(this);
        e_birthday.setText(user.getBirthday() != null ? user.getBirthday().toString() : "");
        linear.addView(e_birthday);
        linear.addView(telnr_title);
        e_telnr = new EditText(this);
        e_telnr.setText(user.getTelNumber());
        linear.addView(e_telnr);
        linear.addView(email_title);
        e_email = new EditText(this);
        e_email.setText(user.geteMail());
        linear.addView(e_email);

        //edit button to save and exit settings

        Button save_btn = new Button(this);
        save_btn.setText("Speichern");
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocalUser();
                Snackbar.make(linear, "Aktualisiert!", Snackbar.LENGTH_LONG).show();
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

    private void updateLocalUser() {
        user.setName(e_name.getText().toString());
        user.setSurname(e_surname.getText().toString());
        if(!e_birthday.getText().toString().equals("")) {
            user.setBirthday(Date.valueOf(e_birthday.getText().toString()));
            //TODO:passt der string?
        }
        user.setTelNumber(e_telnr.getText().toString());
        user.setMail(e_email.getText().toString());
        LocalUserHandler.setLocalUser(user);
    }
}
