package com.development.kernel.draft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // Email Edit View Object
    EditText loginET;
    // Passwprd Edit View Object
    EditText pwdET;
    MorphingButton.Params circle;
    MorphingButton btnMorph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find Error Msg Text View control by ID
        errorMsg = (TextView)findViewById(R.id.login_error);
        // Find Email Edit View control by ID
        loginET = (EditText)findViewById(R.id.loginEmail);
        // Find Password Edit View control by ID
        pwdET = (EditText)findViewById(R.id.loginPassword);
        // Instantiate Progress Dialog object
        // Show Progress Dialog
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setTitle("Пожалуйста, подождите...");
        prgDialog.setMessage("Идет авторизация...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        btnMorph = (MorphingButton) findViewById(R.id.btnLogin);

        circle = MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(200) // 56 dp
                .width(200) // 56 dp
                .height(200) // 56 dp
                .color(Color.parseColor("#8BC34A")) // normal state color
                .colorPressed(Color.parseColor("#689F38")) // pressed state color
                .icon(R.drawable.ic_done_white_24dp); // icon
    }

    public void loginUser(View view){
        // Get Email Edit View Value
        String email = loginET.getText().toString();
        // Get Password Edit View Value
        String password = pwdET.getText().toString();
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(email) && Utility.isNotNull(password)){
            // When Email entered is Valid

                // Put Http parameter username with value of Email Edit View control
                params.put("login", email);
                // Put Http parameter password with value of Password Edit Value control
                params.put("password", password);

                params.put("useapi",1);
                invokeWS(params);
        } else{
            Toast.makeText(getApplicationContext(), "Пожалуйста, заполните все поля =)", Toast.LENGTH_LONG).show();
        }
    }


    public void invokeWS(RequestParams params){

        prgDialog.show(); //включаем окно загрузки

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://195.19.44.155/anton/core/api.php?action=Auth",params, new JsonHttpResponseHandler() { //наверное подключается к серверу и выполняется метод логин
            @Override // параметры метода client.get() идут в аргументах
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) { // если операция прошла успешно
                //super.onSuccess(statusCode,headers,res);
                prgDialog.hide();// прячем окно загрузки

                try {

                    if(obj.getInt("code")==25){
                        btnMorph.morph(circle);
                        navigatetoHomeActivity(obj.getString("apimessage")); //запускаем основное активити (не важно)(наверено) (не трогайте меня)
                    }
                    // Else display error message
                    else{
                        errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Сервер Json не отвечает", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {// если не успешно
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Ресурсы не найдены ", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так с сервером =(", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Неизвестная ошибка, возможно девайс не подключен к Интернету", Toast.LENGTH_LONG).show();
                }
            }


        });


    }


    public void navigatetoHomeActivity(String TOKEN){
        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.putExtra("TOKEN", TOKEN);
        startActivity(homeIntent);
    }

    public void navigatetoRegisterActivity(View view) {
        Intent goToRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(goToRegisterActivity);
        goToRegisterActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.finish();
    }
}
