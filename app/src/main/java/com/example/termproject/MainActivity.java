package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class    MainActivity extends AppCompatActivity {
    SharedPreferences shared;
    Button logout, search, mypage, deposit, installment, loan;
    String text1;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout =(Button)findViewById(R.id.logout);
        search =(Button)findViewById(R.id.search);
        mypage =(Button)findViewById(R.id.mypage);
        deposit =(Button)findViewById(R.id.deposit);
        installment =(Button)findViewById(R.id.installment);
        loan =(Button)findViewById(R.id.loan);

        final String[] data = getResources().getStringArray(R.array.bank);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
        Spinner bank = (Spinner) findViewById(R.id.bank);
        bank.setAdapter(adapter);

        //스피너 클릭(은행 선택)
        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i ==0){
                    text1 = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
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

        //로그아웃 버튼 클릭시
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        //계좌조회 버튼 클릭시
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //마이페이지 버튼 클릭시
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //예금상품 버튼 클릭시
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                alertDialog.setTitle("예금상품");
                alertDialog.setMessage("예금상품을 보시겠습니까?");
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
                        SharedPreferences shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("bank",text1);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, DepositProduct.class);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });

        //적금상품 버튼 클릭시
        installment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                alertDialog.setTitle("적금상품");
                alertDialog.setMessage("적금상품을 보시겠습니까?");
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
                        Intent intent = new Intent(MainActivity.this, InstallmentProduct.class);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });

        //대출상품 버튼 클릭시
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                alertDialog.setTitle("대출상품");
                alertDialog.setMessage("대출상품 보시겠습니까?");
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
                        Intent intent = new Intent(MainActivity.this, LoanProduct.class);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed(){

    }
}