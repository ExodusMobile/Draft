package com.development.kernel.draft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // Name Edit View Object
    EditText nameET;
    // Email Edit View Object
    EditText emailET;
    // Password Edit View Object
    EditText pwdET;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Find Error Msg Text View control by ID
        errorMsg = (TextView)findViewById(R.id.register_error);
        // Find Name Edit View control by ID
        nameET = (EditText)findViewById(R.id.registerName);
        // Find Email Edit View control by ID
        emailET = (EditText)findViewById(R.id.registerEmail);
        // Find Password Edit View control by ID
        pwdET = (EditText)findViewById(R.id.registerPassword);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Пожалуйста, подождите...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        button = (Button) findViewById(R.id.btnLinkToLoginScreen);
        button.setOnClickListener(this);

    }
    public void registerUser(View view){
        // Get NAme ET control value
        String name = nameET.getText().toString();
        // Get Email ET control value
        String email = emailET.getText().toString();
        // Get Password ET control value
        String password = pwdET.getText().toString();


        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(name) && Utility.isNotNull(email) && Utility.isNotNull(password)){
            // When Email entered is Valid

                // Put Http parameter name with value of Name Edit View control
                params.put("name", name);
                // Put Http parameter username with value of Email Edit View control
                params.put("username", email);
                // Put Http parameter password with value of Password Edit View control
                params.put("password", password);

                invokeWS(params);
            // When Email is invalid

        }
        // When any of the Edit View control left blank
        else {
            Toast.makeText(getApplicationContext(), "Пожалуйста, заполните все поля =)", Toast.LENGTH_LONG).show();
        }


    }

    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://195.19.44.155/samsung/api/index.php?method=register", params, new JsonHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'

            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    if (obj.getBoolean("status")) {
                        // Set Default Values for Edit View controls
                        setDefaultValues();

                        navigateToLoginActivity();
                        // Display successfully registered message using Toast
                        Toast.makeText(getApplicationContext(), "Вы успешно зарегестрировались", Toast.LENGTH_LONG).show();

                    } else {
                        errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
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

    public void navigateToLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        this.finish();
    }

    public void setDefaultValues(){
        nameET.setText("");
        emailET.setText("");
        pwdET.setText("");
    }

    @Override
    public void onClick(View view) {
        navigateToLoginActivity();
    }
}
