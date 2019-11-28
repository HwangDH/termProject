package com.example.termproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.example.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;

public class Signup extends Activity {
    AlertDialog alertdialog;
    EditText userid, userpassword, username, userage, phonenumber;
    Button signup, idcheck;
    Spinner bank;
    int num=0;
    private boolean validate = false;
    String url = "https://scv0319.cafe24.com/termProject/register.php";
    String user_id, user_id2;
    String text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userid= (EditText) findViewById(R.id.userid);
        userpassword= (EditText) findViewById(R.id.userpassword);
        username= (EditText) findViewById(R.id.username);
        userage= (EditText) findViewById(R.id.userage);
        phonenumber=(EditText)findViewById(R.id.phonenumber);
        idcheck = (Button) findViewById(R.id.idcheck);
        signup = (Button) findViewById(R.id.signup);

        final String[] data = getResources().getStringArray(R.array.bank);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
        Spinner bank = (Spinner) findViewById(R.id.bank);
        bank.setAdapter(adapter);

        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i ==0){
                    text1 = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                    System.out.println(text1);
                }
                else if(i ==1){
                    text1 = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==2){
                    text1 = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==3){
                    text1 = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==4){
                    text1 = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==5){
                    text1 = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id= userid.getText().toString();
                user_id2 = user_id;
                if (user_id.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    alertdialog = builder.setMessage("ID is empty")
                            .setPositiveButton("확인", null)
                            .create();
                    alertdialog.show();
                }
                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try{
                                Toast.makeText(Signup.this, response, Toast.LENGTH_LONG).show();
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){//사용할 수 있는 아이디라면
                                    num=1;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                                    alertdialog = builder.setMessage("you can use ID")
                                            .setPositiveButton("OK", null)
                                            .create();
                                    alertdialog.show();
                                    //System.out.println(num);
                                    validate = true;//검증완료
                                }else{//사용할 수 없는 아이디라면`
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                                    alertdialog = builder.setMessage("already used ID")
                                            .setNegativeButton("OK", null)
                                            .create();
                                    alertdialog.show();
                                    userid.setText("");
                                }

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    ValidateRequest ValidateRequest = new ValidateRequest(user_id, text1, responseListener);
                    System.out.println(text1);
                    RequestQueue queue = Volley.newRequestQueue(Signup.this);
                    queue.add(ValidateRequest);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sbank =text1;
                String sid = userid.getText().toString();
                String spassword = userpassword.getText().toString();
                String sname = username.getText().toString();
                String sage = userage.getText().toString();
                String sphonenumber = phonenumber.getText().toString();

                if(sid.isEmpty() ||spassword.isEmpty()||sname.isEmpty()||sage.isEmpty()){
                    Toast.makeText(Signup.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }
                else if(num==0){
                    System.out.println(num);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    alertdialog = builder.setMessage("아이디 중복검사를 해주세요.")
                            .setPositiveButton("OK", null)
                            .create();
                    alertdialog.show();
                }
                else if(!user_id.equals(sid)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    alertdialog = builder.setMessage("아이디 중복검사를 해주세요.")
                            .setPositiveButton("OK", null)
                            .create();
                    alertdialog.show();
                }
                else {
                    signup(sbank, sid, spassword, sname, sage, sphonenumber);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("userid",sid);
                    editor.putString("userpassword",spassword);
                    editor.putString("username",sname);
                    editor.putString("userage",sage);
                    editor.commit();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    alertdialog = builder.setMessage("회원가입을 축하합니다.")
                            .setPositiveButton("OK", null)
                            .create();
                    alertdialog.show();
                    show();
                }
            }
        });
    }

    public void show(){
        Intent intent = new Intent(Signup.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void signup(final String bank, final String userid, final String userpassword,final String username, final String userage, final String phonenumber){
        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Hitesh",""+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("2");
                Log.i("Hitesh",""+error);
                Toast.makeText(Signup.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                System.out.println(bank);
                System.out.println(userid);
                stringMap.put("bank", bank);
                stringMap.put("userid",userid);
                stringMap.put("userpassword",userpassword);
                stringMap.put("username",username);
                stringMap.put("userage",userage);
                stringMap.put("phonenumber",phonenumber);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}