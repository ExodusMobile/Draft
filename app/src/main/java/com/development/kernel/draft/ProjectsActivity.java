package com.development.kernel.draft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProjectsActivity extends AppCompatActivity {
    private Intent intent;
    private ProgressDialog prgDialog;
    private  LinearLayout linearLayoutProjects;
    LinearLayout.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        linearLayoutProjects = (LinearLayout) findViewById(R.id.projectsLine);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        prgDialog = new ProgressDialog(this);
        prgDialog.setTitle("Подождите");
        prgDialog.setMessage("Загрузка проектов");

        RequestParams requestParams = new RequestParams();
        prgDialog.show();
        invokeWS(requestParams);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
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
                                intent = new Intent(ProjectsActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.second:
                                break;
                            case R.id.third:
                                intent = new Intent(ProjectsActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.settings:
                                intent = new Intent(ProjectsActivity.this, SettingsActivity.class);
                                startActivity(intent);
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

        client.get("http://195.19.44.155/anton/core/api.php?action=GetCountProjects&from=0&to=10", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    int i=1;
                    JSONObject apimessage = obj.getJSONObject("apimessage");
                    JSONObject projectUser = apimessage.getJSONObject("projects");
                    while(i<5){
                        JSONObject project = projectUser.getJSONObject(String.valueOf(i));
                        CardView projectCard = new CardView(getApplicationContext());
                        projectCard.setCardBackgroundColor(Color.WHITE);
                        projectCard.setMinimumWidth(1080);
                        projectCard.setMinimumHeight(500);
                        TextView textView = new TextView(getApplicationContext());
                        textView.setTextColor(Color.GRAY);
                        textView.setText(project.getString("NameOfProject"));
                        projectCard.addView(textView);
                        //--------------------------------------\\
                        projectCard.setLayoutParams(layoutParams);
                        linearLayoutProjects.addView(projectCard);
                        //--------------------------------------\\

                        i++;
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Сервер не отвечает!", Toast.LENGTH_LONG).show();
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
}
