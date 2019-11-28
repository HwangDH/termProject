package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoanProduct2 extends AppCompatActivity {
    SharedPreferences shared;
    String myJSON;
    String bankid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_product2);
        shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        bankid = shared.getString("bankid","");
        getData("https://scv0319.cafe24.com/termProject/loanProduct2.php?bankid="+bankid+"");
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
                bankid[count] = object.getInt("Bank_ID3");
                productID[count] = object.getInt("LoanProduct_ID");
                productName[count] = object.getString("LoanProduct_Name");
                // adapter.addVO(bankid[count], productID[count], productName[count]);//리스트뷰에 값을 추가해줍니다
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
