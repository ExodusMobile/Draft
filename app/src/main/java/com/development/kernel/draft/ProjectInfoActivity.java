package com.development.kernel.draft;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProjectInfoActivity extends AppCompatActivity {

    private ProgressDialog prgDialog;
    public RequestParams requestParams;
    public View viewClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);

        prgDialog = new ProgressDialog(this);
        prgDialog.setTitle("Подождите...");
        prgDialog.setMessage("Идет загрузка проекта...");

        requestParams = new RequestParams();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewClick = view;
                invokeWS(requestParams);
            }
        });
    }
    public void invokeWS(final RequestParams params){
        // Show Progress Dialog


        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://195.19.44.155/anton/core/api.php?action=Get", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {

                    Snackbar.make(viewClick, "", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    obj.getString("name");

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
                    Toast.makeText(getApplicationContext(), "Ошибка 404", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так с сервером", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Неизвестная ошибка, возможно дефайс не подключен к Интернету", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
