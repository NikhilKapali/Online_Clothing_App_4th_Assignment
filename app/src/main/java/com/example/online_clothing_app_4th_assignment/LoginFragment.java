package com.example.online_clothing_app_4th_assignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_clothing_app_4th_assignment.API.MasterApi;
import com.example.online_clothing_app_4th_assignment.Models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    Button login;
    EditText ed_name, ed_pass;
    Boolean isLoggedIn=false;

    private static final String BASE_URL = "http://10.0.2.2:2000/";

    MasterApi masterApi;
    Retrofit retrofit;
    UserModel userModel;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ed_name = view.findViewById(R.id.et_username);
        ed_pass = view.findViewById(R.id.et_password);

        login = view.findViewById(R.id.btn_login);
        login.setOnClickListener(this);

        return view;
    }


    private void createInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        masterApi = retrofit.create(MasterApi.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            userLogin();

    }
}
    private void userLogin(){

        createInstance();
        String username = ed_name.getText().toString();
        String password = ed_pass.getText().toString();
        Call<Void> userCall = masterApi.checkUser(username, password);
        userCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Username Or Password Error",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    if (response.isSuccessful()){
                        Intent intent = new Intent(getActivity(), Dashboard.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(ed_name.getText().toString())) {
            ed_name.setError("Enter Username");
            ed_name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ed_pass.getText().toString())) {
            ed_pass.setError("Enter password");
            ed_pass.requestFocus();
            return false;
        }

        return true;
    }

}



