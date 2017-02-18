package com.development.kernel.draft;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private Intent intent;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private DrawerLayout mDrawerLayout;
    private Fragment fragment;
    private FragmentTransaction fg;
    private TextView userName;
    private TextView userSubtitle;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;
    private SharedPreferences sPref;
    final String SAVED_NAME = "userName";
    final String SAVED_SUBTITLE = "userSubtitle";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loadText();

        userName = (TextView) findViewById(R.id.user_name);
        userSubtitle = (TextView) findViewById(R.id.user_subtitle);

        TabLayout tabs = (TabLayout) findViewById(R.id.materialup_tabs);
        ViewPager viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        tabs.addTab(tabs.newTab().setText("Основная").setTag(1));
        tabs.addTab(tabs.newTab().setText("Дополнительная").setTag(2));
        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
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
                        return true;
                    }
                });

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }
    void loadText() {
        sPref = getPreferences(MODE_APPEND);
        String savedName = sPref.getString("userName", " ");
        Log.d("savedtext", "4343434");

        String savedSubtitle = sPref.getString("userSubtitle", " ");
        try {


            userName.setText(savedName);
            userSubtitle.setText(savedSubtitle);
        }
        catch (Exception e){
            Log.d("savedtext", savedName);
        }

    }
    class TabsAdapter extends FragmentPagerAdapter {
        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch(i) {
                case 0: return MainInfoFragment.newInstance();
                case 1: return AdditInfoFragment.newInstance();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0: return "Основная";
                case 1: return "Дополнительная";
            }
            return "";
        }
    }
}