package it15ns.friendscom.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import io.grpc.serverPackage.AddFriendToFriendlistRequest;
import io.grpc.serverPackage.Response;
import io.grpc.serverPackage.SearchUserResponse;
import it15ns.friendscom.R;
import it15ns.friendscom.adapters.SearchProfileAdapter;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcSyncTask;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.grpc.runnables.AddToFriendlistRunnable;
import it15ns.friendscom.handler.UserHandler;
import it15ns.friendscom.model.User;

public class SearchProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText profileName;
    Button btnSeach;
    ScrollView scrollView;
    ListView searchResult;
    SearchProfileAdapter searchProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileName = (EditText) findViewById(R.id.profileName);
        btnSeach = (Button) findViewById(R.id.btn_seachProfile);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.fullScroll(View.FOCUS_DOWN);

        searchProfileAdapter = new SearchProfileAdapter(this);

        searchResult = (ListView) findViewById(R.id.seachResult);
        searchResult.setAdapter(searchProfileAdapter);
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchProfileActivity.this);
                builder.setMessage("Möchten sie diesen Account in ihre Freundesliste hinzufügen?")
                        .setTitle("Hinzufügen");

                final User user = (User) parent.getItemAtPosition(position);

                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(UserHandler.addUser(user)) {
                            MainActivity.update();
                            Toast.makeText(SearchProfileActivity.this,user.getNickname() + " hinzugefügt!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SearchProfileActivity.this,"Es ist ein Fehler aufgetreten!", Toast.LENGTH_LONG).show();
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

        btnSeach.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String searchString = profileName.getText().toString();
        new GrpcTask(GrpcRunnableFactory.getSearchProfileRunnable(searchString, this)).execute();
    }

    public void searchResult(SearchUserResponse response) {
        if (response.getSuccess()) {
            List<User> users = new ArrayList<>();
            for (String nickname : response.getNicknameResultList()) {
                users.add(new User(nickname));
            }

            searchProfileAdapter.setResult(users);
        } else {
            searchProfileAdapter.setResult(new ArrayList<User>());
            Toast.makeText(SearchProfileActivity.this, "Nichts gefunden", Toast.LENGTH_LONG).show();
        }
    }

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
