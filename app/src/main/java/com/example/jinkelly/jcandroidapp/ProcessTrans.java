package com.example.jinkelly.jcandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jin.chung on 11/2/2016.
 */
public class ProcessTrans extends AppCompatActivity {
    public String action_mode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent getActionMode = getIntent();

        setContentView(R.layout.transaction_list);

        TextView transTitle = (TextView)
                findViewById(R.id.transtitle);
        Button transNew = (Button)
                findViewById(R.id.btnnewtran);

        action_mode = getActionMode.getExtras().getString("action_mode");


            if( action_mode.equals("check_in")){
                transTitle.setText("CHECK IN TRANSACTION");
                transNew.setText("NEW CHECK IN");
            }else if(action_mode.equals("check_out")){
                transTitle.setText("CHECK OUT TRANSACTION");
                transNew.setText("NEW CHECK OUT");
            }



    }

    public void createTrans(View view) {
        Intent callTransform = new Intent(this, NewTransaction.class);
        callTransform.putExtra("action_mode", action_mode);
        startActivity(callTransform);

    }
}
