package com.jsee.sphwarehouse;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by jin.chung on 19/2/2016.
 */
public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
    }



}
