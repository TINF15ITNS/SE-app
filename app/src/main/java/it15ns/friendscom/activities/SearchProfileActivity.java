package it15ns.friendscom.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.grpc.serverPackage.SearchForProfileResponse;
import io.grpc.serverPackage.SearchUserResponse;
import it15ns.friendscom.R;
import it15ns.friendscom.adapters.SearchProfileAdapter;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcTask;
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

        btnSeach.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String searchString = profileName.getText().toString();
        new GrpcTask(GrpcRunnableFactory.getSearchProfileRunnable(searchString, this)).execute();
    }

    public void searchResult(SearchUserResponse response) {
        List<User> users = new ArrayList<>();
        for (String nickname : response.getNicknameResultList()) {
            users.add(new User(nickname));
        }

        searchProfileAdapter.setResult(users);
    }
}
