package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termproject.Adapter.DepositAdapter;
import com.example.termproject.List.Deposit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepositProduct extends Activity {
    SharedPreferences shared;
    String myJSON;
    Button deposit;
    private ListView listView;
    private DepositAdapter adapter;
    String bankid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_product);
        shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        adapter = new DepositAdapter();
        listView = (ListView) findViewById(R.id.listView);
        deposit = (Button)findViewById(R.id.deposit);
        listView.setAdapter(adapter);
        getData("https://192.168.224.3/depositProduct.php");

        final String[] data = getResources().getStringArray(R.array.bank);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
        Spinner bank = (Spinner) findViewById(R.id.bank);
        bank.setAdapter(adapter);

        //스피너 클릭(은행 선택)
        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i ==0){
                    bankid = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==1){
                    bankid = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==2){
                    bankid = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==3){
                    bankid = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==4){
                    bankid = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }
                else if(i ==5){
                    bankid = Integer.toString(i);
                    Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("bankid",bankid);
                editor.commit();
                Intent intent = new Intent(DepositProduct.this,DepositProduct2.class);
                startActivity(intent);
            }
        });
    }

    public void showList(){
            try{
                JSONObject jsonObject = new JSONObject(myJSON);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                int count = 0;
                int [] bankid = new int[30];
                int [] productID = new int[30];
                String [] productName = new String[30];

                //JSON 배열 길이만큼 반복문을 실행
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    bankid[count] = object.getInt("Bank_ID4");
                    productID[count] = object.getInt("DepositProduct_ID");
                    productName[count] = object.getString("DepositProduct_Name");
                    adapter.addVO(bankid[count], productID[count], productName[count]);//리스트뷰에 값을 추가해줍니다
                    System.out.println(bankid[count] +"  "+ productID[count]+ "  "+productName[count]);
                    count++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
