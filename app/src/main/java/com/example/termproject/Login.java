package com.example.termproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class Login extends Activity {
    EditText userid1, userpassword1;
    Button login, signup;
    CheckBox id_store, auto_login;
    String user_id, user_password;
    String token;
    SharedPreferences shared;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        userid1 = (EditText)findViewById(R.id.userid1);
        userpassword1 =(EditText)findViewById(R.id.userpassword1);
        login = (Button)findViewById(R.id.login);
        id_store = (CheckBox)findViewById(R.id.id_store);
        auto_login = (CheckBox)findViewById(R.id.auto_login);
        signup = (Button)findViewById(R.id.signup);

        if(((CheckBox)auto_login).isChecked()){
            String userid2 = shared.getString("userid", "");
            String userpassword2 = shared.getString("userpassword", "");
            login(userid2, userpassword2);
        }

        id_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    user_id = userid1.getText().toString();
                    SharedPreferences.Editor editor = shared.edit();
                    editor.clear();
                    editor.putString("userid", user_id);
                    editor.commit();
                }
            }
        });

        auto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    user_id = userid1.getText().toString();
                    user_password = userpassword1.getText().toString();

                    SharedPreferences.Editor editor = shared.edit();
                    editor.clear();
                    editor.putString("userid", user_id);
                    editor.putString("userpassword", user_password);
                    editor.commit();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = userid1.getText().toString();
                user_password = userpassword1.getText().toString();

                if(user_id.isEmpty()){
                    Toast.makeText(Login.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(user_password.isEmpty()){
                    Toast.makeText(Login.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    login(user_id, user_password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new AlertDialog.Builder(Login.this).create();

                alertDialog.setTitle("회원가입");
                alertDialog.setMessage("회원가입 하시겠습니까?");
                alertDialog.setCancelable(false);
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Login.this, Signup.class);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void login(final String user, final String pass){
        String url = "https://scv0319.cafe24.com/man/login.php?userid="+user+"&userpassword="+pass+"";
        Log.i("Hiteshurl",""+url);
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String userid2 = jsonObject1.getString("userid");
                    String userpassword2 = jsonObject1.getString("userpassword");
                    SharedPreferences shared = getSharedPreferences("Mypref",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("userid",userid2);
                    editor.putString("userpassword",userpassword2);
                    editor.commit();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("HiteshURLerror",""+error);
            }
        });
        requestQueue.add(stringRequest);
    }

}