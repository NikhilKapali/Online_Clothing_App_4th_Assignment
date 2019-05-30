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


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    Button login;
    EditText ed_name, ed_pass;
    Boolean isLoggedIn=false;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
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

        preferences = getActivity().getSharedPreferences("APP", Context.MODE_PRIVATE);
        editor = preferences.edit();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (validate()){
                preferences=getActivity().getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
                editor=preferences.edit();
                String getusername=ed_name.getText().toString();
                String getpassword=ed_pass.getText().toString();
                String getSharedusername=preferences.getString("USERNAME","");
                String getSharedpassword=preferences.getString("PASSWORD","");

                if (getusername.equals(getSharedusername) && getpassword.equals(getSharedpassword)){
                    isLoggedIn=true;
                    editor.putBoolean("isLoggedIn",isLoggedIn).commit();
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),Dashboard.class);
                    //progress bar
                    final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setTitle("Login in process");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    startActivity(intent);
                    getActivity().finish();
                }
                else{

                    Toast.makeText(getActivity(), "Username and Password do not match", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean validate(){
        if(TextUtils.isEmpty(ed_name.getText().toString())){
            ed_name.setError("Enter Username");
            ed_name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ed_pass.getText().toString())){
            ed_pass.setError("Enter password");
            ed_pass.requestFocus();
            return false;
        }

        return true;
    }
}

