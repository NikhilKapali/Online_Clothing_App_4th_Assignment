package com.example.online_clothing_app_4th_assignment;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
public class RegisterFragment extends Fragment implements View.OnClickListener {
    EditText id,fname,lname,username, password;
    Button register;
    private static final String BASE_URL = "http://10.0.2.2:2000/signup/";

    MasterApi masterApi;
    Retrofit retrofit;
    UserModel userModel;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        id=view.findViewById(R.id.et_id);
        fname= view.findViewById(R.id.et_fname);
        lname= view.findViewById(R.id.et_lname);
        username= view.findViewById(R.id.et_username_res);
        password= view.findViewById(R.id.et_password_res);
        register=view.findViewById(R.id.btn_register);
        register.setOnClickListener(this);

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
        if (v.getId() == R.id.btn_register){
            RegisterUser();

        }

    }
    private void RegisterUser(){
        createInstance();

        Call<Void> addcall = masterApi.registerUser(new UserModel(0,
                fname.getText().toString(),
                lname.getText().toString(),
                username.getText().toString(),
                password.getText().toString()));
        addcall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent=new Intent(getActivity(),LoginFragment.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validate(){
        if(TextUtils.isEmpty(fname.getText().toString())){
            fname.setError("Enter First name");
            fname.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(lname.getText().toString())){
            lname.setError("Enter Last name");
            lname.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(username.getText().toString())){
            username.setError("Enter Username");
            username.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())){
            password.setError("Enter password");
            password.requestFocus();
            return false;
        }

        return true;
    }
}
