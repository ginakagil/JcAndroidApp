package com.jsee.sphwarehouse;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jin.chung on 20/2/2016.
 * Adapter for TransArray
 */
public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(Context context,int ResourceId, ArrayList<Transaction> transactions) {
        super(context, ResourceId, transactions);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        Transaction transaction = getItem(position);

        if(row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.trans_itemlayout, parent, false);
        }
            TextView tvtimestamp = (TextView) row.findViewById(R.id.litimestamp);
            TextView tvbox = (TextView) row.findViewById(R.id.libox);
            TextView tvlocation = (TextView) row.findViewById(R.id.liloc);
            LinearLayout lirow = (LinearLayout) row.findViewById(R.id.lirow);

            tvtimestamp.setText(transaction.getTimestamp());
        tvbox.setText(transaction.getBox().getDept() + " " +transaction.getBox().getBoxno() );
        tvlocation.setText(transaction.getLoc().getSlottxt() );

        if(position%2==0)
        {
            // even position color
            lirow.setBackgroundColor(Color.rgb(255, 255, 230));
        }
        else
        {
            // odd position color
        }

        return row;
        }




}




