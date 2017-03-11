package com.development.kernel.draft;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private Intent intent;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private DrawerLayout mDrawerLayout;
    private Fragment fragment;
    private FragmentTransaction fg;
    private TextView userName;
    private TextView userSubtitle;
    private TextView userSubcribes;
    private boolean mIsAvatarShown = true;

    private TextView companyName;
    private TextView companyDesc;
    private TextView companyLongDesc;
    private Fragment frag1;
    private ViewPager viewPager;

    private ImageView mProfileImage;
    private int mMaxScrollSize;
    private SharedPreferences sPref;
    final String SAVED_NAME = "userName";
    final String SAVED_SUBTITLE = "userSubtitle";

    private ProgressDialog prgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Пожалуйста, подождите...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        RequestParams params = new RequestParams();
        Intent getId = getIntent();


        String TOKEN = getId.getStringExtra("TOKEN");
        params.put("token", TOKEN);

        userName = (TextView) findViewById(R.id.user_name);
        userSubtitle = (TextView) findViewById(R.id.user_subtitle);




        TabLayout tabs = (TabLayout) findViewById(R.id.materialup_tabs);
        viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);
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


        invokeWS(params);

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


    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        prgDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://195.19.44.155/anton/core/api.php?action=GetInfoAboutUser", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {

                    userSubcribes = (TextView) findViewById(R.id.subscribes);
                    userName = (TextView) findViewById(R.id.user_name);
                    userSubtitle = (TextView) findViewById(R.id.user_subtitle);
                    String json = obj.getString("apimessage");
                    JSONObject infoUser = obj.getJSONObject("apimessage");


                    userName.setText(infoUser.getString("first_name")+infoUser.getString("last_name"));
                    userSubcribes.setText(infoUser.getString("followers"));





                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Сервер Json не отвечает!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Ресурс не найден", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так с сервером =(", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Неизвестная ошибка, возможно дефайс не подключен к Интернету", Toast.LENGTH_LONG).show();
                }
            }
        });
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