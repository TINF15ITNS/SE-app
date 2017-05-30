package it15ns.friendscom.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import it15ns.friendscom.R;
import it15ns.friendscom.adapters.SectionsPagerAdapter;
import it15ns.friendscom.xmpp.XMPPClient;

public class ChatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private static FloatingActionButton fab;
    private static SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setup the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Setup des FloatingActionButtons
        final ChatActivity thisActivity = this;
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setFab(false);
                        Intent startSpecificChat = new Intent(thisActivity, SpecificChatActivity.class);
                        startActivity(startSpecificChat);
                    }
                }
        );

        // Setup des Tabbed Layouts
        // Set up the ViewPager with the sections adapter.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);

        // set the chatActivity to be notifyied by the xmpp client
        XMPPClient.getInstance().setChatActivity(this);
    }

    public static void setFab(boolean isActive) {
        if(isActive)
            fab.show();
        else
            fab.hide();
    }

    public static void update() {
        sectionsPagerAdapter.update();
    }

    // App Bar STUFF
    // Logout und Einstellungen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            XMPPClient.getInstance().disconnectConnection();
            SharedPreferences sharedPrefs = getSharedPreferences("data", Context.MODE_PRIVATE);
            sharedPrefs.edit().putString("token", "").commit();
            sharedPrefs.edit().putString("username", "").commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // OnTabSelect Listener implementierung für FloatingActionButton
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final Activity thisActivity = this;
        switch (tab.getPosition()) {
            case 0:
                fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_email));
                fab.show();
                fab.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                setFab(false);
                                Intent startSpecificChat = new Intent(thisActivity, SpecificChatActivity.class);
                                startActivity(startSpecificChat);
                                //fragmentManager.beginTransaction().replace(R.id.container, new NewMessageFragment()).addToBackStack(null).commit();
                            }
                        }
                );
                break;

            case 1:
                fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));
                fab.show();
                fab.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                setFab(false);
                                Intent startSpecificChat = new Intent(thisActivity, ProfileActivity.class);
                                startActivity(startSpecificChat);
                                //fragmentManager.beginTransaction().replace(R.id.container, new NewMessageFragment()).addToBackStack(null).commit();
                            }
                        }
                );
                break;

            case 2: fab.hide();

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            XMPPClient.getInstance().connectConnection();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
        }
        super.onRestart();
    }
}
