package com.development.kernel.draft;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MainInfoFragment extends Fragment implements View.OnClickListener {
    private EditText companyName;
    private EditText companyDescription;
    private ProgressDialog progressDialog;
    private static View view;
    private String companyNameString;
    private String companyDescriptionString;
    private ProfileActivity profileActivity;
    private static boolean checkForCompany;;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_info, null);
        companyName = (EditText) view.findViewById(R.id.companyName);
        companyDescription = (EditText) view.findViewById(R.id.companyDescription);
        Button createCompanyButton = (Button) view.findViewById(R.id.btnCreateCompany);

        profileActivity = new ProfileActivity();
        createCompanyButton.setOnClickListener(this);
        return view;
    }

    public static  void getInfoAboutCompany(RequestParams params){ //Получение информации о компании пользователя
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://195.19.44.155/anton/core/api.php?action=GetInfoAboutCompany", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                // Hide Progress Dialog

                try {
                    JSONObject apimessage = obj.getJSONObject("apimessage");
                    createCompany(apimessage.getString("name"), apimessage.getString("description"));
                    Log.d("ошибка","вот тута");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
            }
        });
    }

    public void invokeWS(RequestParams params){
        // Show Progress Dialog
       Toast.makeText(getActivity().getApplicationContext(),"Идет создание, подождите...", Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://195.19.44.155/anton/core/api.php?action=CreateCompany", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                // Hide Progress Dialog
                Toast.makeText(getActivity().getApplicationContext(),"Проект успешно создан!", Toast.LENGTH_LONG).show();
                try {
                    int t = obj.getInt("code");
                    createCompany();
                    Log.d("olas","hehehe");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity().getApplicationContext(), "Сервер Json не отвечает!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // Hide Progress Dialog
                progressDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getActivity().getApplicationContext(), "Ресурс не найден", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getActivity().getApplicationContext(), "Что-то пошло не так с сервером =(", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Неизвестная ошибка, возможно дефайс не подключен к Интернету", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createCompany(){
        CardView createCompanyCardView = (CardView)  view.findViewById(R.id.create_company_card);
        CardView CompanyCardView = (CardView)  view.findViewById(R.id.company_card);
        createCompanyCardView.setVisibility(View.GONE);
        CompanyCardView.setVisibility(View.VISIBLE);
        TextView companyNameInfo = (TextView) view.findViewById(R.id.companyNameInfo);
        TextView companyDescriptionInfo = (TextView) view.findViewById(R.id.companyDescriptionInfo);
        TextView companyDetributorInfo = (TextView) view.findViewById(R.id.companyDetributorInfo);
        companyNameInfo.setText(companyNameString);
        companyDescriptionInfo.setText(companyDescriptionString);
    }
    private static void createCompany(String companyNameMethod, String companyDescriptionMethod){
        CardView createCompanyCardView = (CardView)  view.findViewById(R.id.create_company_card);
        CardView CompanyCardView = (CardView)  view.findViewById(R.id.company_card);
        createCompanyCardView.setVisibility(View.GONE);
        CompanyCardView.setVisibility(View.VISIBLE);
        TextView companyNameInfo = (TextView) view.findViewById(R.id.companyNameInfo);
        TextView companyDescriptionInfo = (TextView) view.findViewById(R.id.companyDescriptionInfo);
        TextView companyDetributorInfo = (TextView) view.findViewById(R.id.companyDetributorInfo);
        companyNameInfo.setText(companyNameMethod);
        companyDescriptionInfo.setText(companyDescriptionMethod);
    }
    private static String TOKEN;
    private static int companyID;

    public static void newInstance(String token, boolean checkForCompanyMethod, int companyIDMethod){
        checkForCompany = checkForCompanyMethod;
        companyID = companyIDMethod;
        TOKEN = token;
        if(checkForCompany){ //Проверка на наличее компании у пользотеля, если есть, то загрузить о ней данные.
            RequestParams requestParams = new RequestParams();
            requestParams.put("company", companyID);
            getInfoAboutCompany(requestParams);
        }
    }



    @Override
    public void onClick(View view) {
        companyNameString = companyName.getText().toString();
        companyDescriptionString = companyDescription.getText().toString();
        RequestParams params = new RequestParams();
        String TOKEN1 = TOKEN;
        params.put("name", companyNameString);
        params.put("description", companyDescriptionString);
        params.put("token", TOKEN1);
        Log.d("token", TOKEN1);
        invokeWS(params);
    }

    public static Fragment newInstance() {
        return new MainInfoFragment();
    }
}
