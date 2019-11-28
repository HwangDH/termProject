package com.example.termproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.termproject.DepositProduct;
import com.example.termproject.List.Deposit;
import com.example.termproject.R;

import java.util.ArrayList;
import java.util.List;

public class DepositAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Deposit> productList = new ArrayList<Deposit>();

    public DepositAdapter(){

    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final int pos = i;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.deposit_list, parent, false);
        }

        TextView DepositProducId = (TextView)convertView.findViewById(R.id.depositID);
        TextView DepositProducName = (TextView)convertView.findViewById(R.id.depositName);
        TextView BankID = (TextView)convertView.findViewById(R.id.bankid);

        Deposit listViewItem = productList.get(i);

        BankID.setText(listViewItem.getBank_ID4());
        DepositProducId.setText(listViewItem.getDepositProduct_ID());
        DepositProducName.setText(listViewItem.getDepositProduct_Name());

        return convertView;
    }

    public void addVO(int bank_id, int productid, String productname){
        Deposit item = new Deposit();

        item.setBank_ID4(bank_id);
        item.setDepositProduct_ID(productid);
        item.setDepositProduct_Name(productname);
        productList.add(item);
    }
}
