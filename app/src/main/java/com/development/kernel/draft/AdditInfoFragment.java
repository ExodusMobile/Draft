package com.development.kernel.draft;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AdditInfoFragment extends Fragment implements View.OnClickListener {

    private static int companyID;
    private static String TOKEN;
    private EditText projectName;
    private EditText projectDesc;
    private String projectNameString;
    private String projectDescString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addit_info, null);
        Button createProjectButton = (Button) view.findViewById(R.id.create_project);
        createProjectButton.setOnClickListener(this);
        projectName = (EditText) view.findViewById(R.id.name_of_project);
        projectDesc = (EditText) view.findViewById(R.id.desc_of_project);

        return view;
    }
    public void invokeWS(RequestParams params){

        AsyncHttpClient client = new AsyncHttpClient();
        Toast.makeText(getActivity().getApplicationContext(), "Проект создается...", Toast.LENGTH_SHORT).show();

        client.get("http://195.19.44.155/anton/core/api.php?action=CreateProject&name="+projectName.getText().toString()+"&description="+projectDesc.getText().toString()+"&companyowner="+companyID+"&token="+TOKEN, params, new JsonHttpResponseHandler() { //наверное подключается к серверу и выполняется метод логин
            @Override // параметры метода client.get() идут в аргументах
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) { // если операция прошла успешно
                //super.onSuccess(statusCode,headers,res);
                try {
                    int i = obj.getInt("code");


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity().getApplicationContext(), "Сервер Json не отвечает", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {// если не успешно
                Toast.makeText(getActivity().getApplicationContext(),"Проект успешно создан!", Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    public void onClick(View view) {
        projectNameString = projectName.getText().toString();
        projectDescString = projectDesc.getText().toString();
        RequestParams params = new RequestParams();
        Log.d("1",projectName.getText().toString());
        Log.d("2",projectDesc.getText().toString());
        Log.d("3",String.valueOf(companyID));
        Log.d("4",TOKEN);
        invokeWS(new RequestParams());
    }
    public static void newInstance(String token, int companyIDMethod){
        companyID = companyIDMethod;
        TOKEN = token;

    }
    public static Fragment newInstance() {
        return new AdditInfoFragment();
    }
}
