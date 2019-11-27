package com.example.termproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import com.example.*;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.myapplication.DBConnect;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import android.app.AlertDialog;

import androidx.annotation.NonNull;

import javax.xml.transform.Result;

import static com.android.volley.VolleyLog.TAG;

public class Signup extends Activity {
    AlertDialog alertdialog;
    EditText userid, userpassword, username, userage;
    TextView signup, already;
    int num=0;
    private boolean validate = false;
    String url = "https://scv0319.cafe24.com/man/register.php";
    String user_id, user_id2;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        userid= (EditText) findViewById(R.id.userid);
        userpassword= (EditText) findViewById(R.id.userpassword);
        username= (EditText) findViewById(R.id.username);
        userage= (EditText) findViewById(R.id.userage);
        signup = (TextView) findViewById(R.id.signup);
        already = (TextView) findViewById(R.id.already);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Log.d(TAG, msg);
                        //Toast.makeText(Signup.this, token, Toast.LENGTH_SHORT).show();
                        System.out.println(token);
                    }
                });

        final Button id_check = (Button) findViewById(R.id.id_check);

        id_check.setOnClickListener(new View.OnClickListener() {
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
                                //Toast.makeText(Signup.this, response, Toast.LENGTH_LONG).show();
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
                    ValidateRequest ValidateRequest = new ValidateRequest(user_id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Signup.this);
                    queue.add(ValidateRequest);
                }
            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog = new AlertDialog.Builder(Signup.this).create();

                alertdialog.setTitle("Already have id?");
                alertdialog.setMessage("아이디를 가지고 계십니까?");
                alertdialog.setCancelable(false);
                alertdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertdialog.dismiss();
                    }
                });

                alertdialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //SharedPreferences.Editor editor = shared.edit();
                        //editor.clear();
                        //editor.commit();
                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                alertdialog.show();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = userid.getText().toString();
                String spassword = userpassword.getText().toString();
                String sname = username.getText().toString();
                String sage = userage.getText().toString();
                String stoken = token;

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
                    signup(sid,spassword,sname,sage, stoken);
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
        Intent intent = new Intent(Signup.this,dogRegister.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void signup(final String userid, final String userpassword,final String username, final String userage, final String token){
        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Hitesh",""+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Hitesh",""+error);
                Toast.makeText(Signup.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("userid",userid);
                stringMap.put("userpassword",userpassword);
                stringMap.put("username",username);
                stringMap.put("userage",userage);
                stringMap.put( "token", token );
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);
    }
}