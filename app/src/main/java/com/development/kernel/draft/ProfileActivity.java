package com.development.kernel.draft;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ProfileActivity extends AppCompatActivity {

    private Intent intent;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
// Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state

                        menuItem.setChecked(true);
                        // TODO: handle navigation
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.first:

                                break;
                            case R.id.second:
                                intent = new Intent(ProfileActivity.this, ProjectsActivity.class);
                                startActivity(intent);

                                break;
                            case R.id.third:
                                intent = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.settings:
                                intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                                startActivity(intent);
                                break;

                        }
                        finish();
                        // Closing drawer on item click
                        return true;
                    }
                });

    }
}