package it15ns.friendscom.activities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

import io.grpc.serverPackage.Response;
import it15ns.friendscom.R;
import it15ns.friendscom.grpc.GrpcRunnable;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcSyncTask;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.grpc.runnables.UpdateProfileRunnable;
import it15ns.friendscom.handler.UserHandler;
import it15ns.friendscom.handler.LocalUserHandler;
import it15ns.friendscom.model.Calendar;
import it15ns.friendscom.model.DatePickerFragment;
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
    // user to store temporarily the changes made by the user
    private User tempUser = LocalUserHandler.getLocalUser(this);;

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
            user = UserHandler.getUserDetails(nickname);
            setTitle(user.getSurname());
        } else {
            user = LocalUserHandler.getLocalUser(this);
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
        if(user != LocalUserHandler.getLocalUser(this)) {
            title.setText((user.hasChat(this) ? "Ihr habt schon gechattet" : "Ihr habt noch nie gechattet"));
            linear.addView(title);
        }

        nickname.setText(user.getNickname());
        name.setText(user.getName());
        surname.setText(user.getSurname());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        birthday.setText(user.getBirthday().getTimeInMillis() != 0 ? format.format(user.getBirthday().getTime()) : "");
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
        Button remove_btn = new Button(this);
        remove_btn.setText("Als Freund entfernen");
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Möchten sie diesen Account aus ihrer Freundesliste entfernen?")
                        .setTitle("Entfernen");


                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(UserHandler.removeUser(user)) {
                            MainActivity.update();
                            Toast.makeText(ProfileActivity.this,user.getNickname() + " entfernt!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProfileActivity.this,"Es ist ein Fehler aufgetreten!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button delete_btn = new Button(this);
        delete_btn.setText("Account löschen");
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Geben Sie ihr Passwort ein um den Account zu löschen:")
                        .setTitle("Account Löschen");


                // Set up the input
                final EditText input = new EditText(ProfileActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(LocalUserHandler.deleteProfile(ProfileActivity.this,input.getText().toString())) {
                            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(ProfileActivity.this,"Profil gelöscht!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProfileActivity.this,"Es ist ein Fehler aufgetreten!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        if(user != LocalUserHandler.getLocalUser(this)) {
            linear.addView(remove_btn);
        } else {
            linear.addView(delete_btn);
        }

    }

    public void loadEditLayout(){
        linear.removeAllViews();
        title.setText("Edit");
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
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        e_birthday.setText(user.getBirthday().getTimeInMillis() != 0 ? format.format(user.getBirthday().getTime()) : "");

        e_birthday.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));
        e_birthday.setEnabled(false);
        Button button = new Button(this);
        button.setText("Set");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment().setProfileActivity(ProfileActivity.this);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));

        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        horizontalLayout.addView(e_birthday);
        horizontalLayout.addView(button);

        linear.addView(horizontalLayout);

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

    public void setBirthday(java.util.Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        e_birthday.setText(format.format(calendar.getTime()));
        tempUser.setBirthday(calendar);
    }

    // save methods
    // will be invoked by the save button
    private void updateLocalUser() {
        tempUser.setName(e_name.getText().toString());
        tempUser.setSurname(e_surname.getText().toString());

        tempUser.setTelNumber(e_telnr.getText().toString());
        tempUser.setMail(e_email.getText().toString());
        // start async task
        // will invoke updateResult
        new GrpcTask(new UpdateProfileRunnable(tempUser, this), getApplicationContext()).execute();
    }

    public void updateResult(Response response) {
        if(response.getSuccess() == true) {
            user = tempUser;
            LocalUserHandler.setLocalUser(tempUser);
            Snackbar.make(linear, "Erfolgreich", Snackbar.LENGTH_LONG).show();
        }
        else
            Snackbar.make(linear, "Es sind Probleme aufgetreten", Snackbar.LENGTH_LONG).show();

    }
}
