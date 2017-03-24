package com.development.kernel.draft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProjectsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private Intent intent;
    private ProgressDialog prgDialog;
    private  LinearLayout linearLayoutProjects;
    private LinearLayout.LayoutParams layoutParams;
    private SwipeRefreshLayout mSwipeRefresh;
    private ProjectsActivity projectsActivity;
    private String TOKEN;
    private CardView[] cardViews;
    private int i = 1;

    private int countOfProject = 900; //TODO: В будущих версиях уничтожить эту переменную.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        linearLayoutProjects = (LinearLayout) findViewById(R.id.projectsLine);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardViews = new CardView[999999];

        prgDialog = new ProgressDialog(this);
        prgDialog.setTitle("Подождите");
        prgDialog.setMessage("Загрузка проектов");
        Intent getTOKEN = getIntent();

        TOKEN = getTOKEN.getStringExtra("TOKEN");

        projectsActivity = new ProjectsActivity();

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        mSwipeRefresh.setOnRefreshListener(this);
        //Настраиваем цветовую тему значка обновления, используя наши цвета:
        mSwipeRefresh.setColorSchemeResources
                (R.color.light_blue, R.color.middle_blue,R.color.deep_blue);

        RequestParams requestParams = new RequestParams();
        prgDialog.show();
        invokeWS(requestParams);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // Set item in checked state

                        menuItem.setChecked(true);
                        // TODO: handle navigation
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.first:
                                intent = new Intent(ProjectsActivity.this, ProfileActivity.class);
                                intent.putExtra("TOKEN",TOKEN);
                                startActivity(intent);

                                finish();
                                break;
                            case R.id.second:
                                break;
                            case R.id.third:
                                intent = new Intent(ProjectsActivity.this, MainActivity.class);
                                intent.putExtra("TOKEN",TOKEN);
                                Log.d("usertoken", TOKEN);
                                startActivity(intent);

                                finish();
                                break;
                            case R.id.settings:
                                intent = new Intent(ProjectsActivity.this, SettingsActivity.class);
                                intent.putExtra("TOKEN",TOKEN);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.exit:
                                intent = new Intent(ProjectsActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }

                        finish();
                        return true;
                    }
                });
    }
    public void invokeWS(final RequestParams params){
        // Show Progress Dialog


        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://195.19.44.155/anton/core/api.php?action=GetCountProjects&from=0&to="+String.valueOf(countOfProject), params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    createCardViews(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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

    private void createCardViews(JSONObject obj) throws JSONException {

        JSONObject apimessage = obj.getJSONObject("apimessage");
        JSONObject projectUser = apimessage.getJSONObject("projects");
        try {
            while (i < 900) {

                JSONObject project = projectUser.getJSONObject(String.valueOf(i));
                CardView projectCard = new CardView(getApplicationContext());

                projectCard.setBackgroundColor(Color.WHITE);
                projectCard.setMinimumWidth(1080);
                projectCard.setMinimumHeight(600);

                LinearLayout LLC = new LinearLayout(getApplicationContext());
                LLC.setOrientation(LinearLayout.VERTICAL);
                projectCard.addView(LLC);
                RelativeLayout Header = new RelativeLayout(getApplicationContext());

                LinearLayout Cellar = new LinearLayout(getApplicationContext());
                Cellar.setOrientation(LinearLayout.HORIZONTAL);
                LLC.addView(Header);
                LLC.addView(Cellar);
                ImageView projectLogo = new ImageView(getApplicationContext());
                Picasso.with(getApplicationContext())
                        .load(R.drawable.material_design)
                        .resize(1080, 500)
                        .into(projectLogo);

                Header.addView(projectLogo);
                TextView description = new TextView(getApplicationContext());
                description.setTextColor(Color.GRAY);
                description.setText(project.getString("description"));
                Cellar.addView(description);
                TextView edit = new TextView(getApplicationContext());
                edit.setText(project.getString("NameOfProject"));
                edit.setTextColor(Color.WHITE);
                edit.setTextSize(18);
                RelativeLayout.LayoutParams RelParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RelParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                RelParams.setMargins(10, 0, 0, 10);
                edit.setLayoutParams(RelParams);
                Header.addView(edit);
                projectCard.setElevation(4);
                //--------------------------------------\\
                projectCard.setLayoutParams(layoutParams);
                linearLayoutProjects.addView(projectCard);
                //--------------------------------------\\
                cardViews[i] = projectCard;
                cardViews[i].setOnClickListener(this);
                i++;

            }
        }
        catch (Exception ignored){}

    }

    @Override
    public void onRefresh() {

                Intent intent = new Intent(getApplicationContext(), ProjectsActivity.class);
                startActivity(intent);
                finish();
                //Останавливаем обновление:
                mSwipeRefresh.setRefreshing(false);

    }
    public  void goToProjectInfo(){
        Intent goToProjectInfoActivity = new Intent(ProjectsActivity.this, ProjectInfoActivity.class);
        startActivity(goToProjectInfoActivity);
    }
    @Override
    public void onClick(View view) {
        goToProjectInfo();
    }
}
