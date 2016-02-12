package com.example.jinkelly.jcandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jin.chung on 12/2/2016.
 */
public class NewTransaction extends Activity {

    public String action_mode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getActionMode = getIntent();

        setContentView(R.layout.transaction_form);

        TextView transTitle = (TextView)
                findViewById(R.id.tvtransmode);

        action_mode = getActionMode.getExtras().getString("action_mode");


        if( action_mode.equals("check_in")){
            transTitle.setText("NEW CHECK IN TRANSACTION");

        }else if(action_mode.equals("check_out")){
            transTitle.setText("NEW CHECK OUT TRANSACTION");

        }



    }
}
