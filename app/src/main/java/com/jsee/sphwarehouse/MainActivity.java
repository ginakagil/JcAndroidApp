package com.jsee.sphwarehouse;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View backgroundimage = findViewById(R.id.frontbg);
        Drawable backgroundimg = backgroundimage.getBackground();
        backgroundimg.setAlpha(80);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.exitbtn) {
            DialogFragment myFragment = new MyDiagFrag();
            myFragment.show(getFragmentManager(), "theDialog");

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void chkinOpenTrans(View view) {
        Intent checkinTransIntent = new Intent(this, ProcessTrans.class);

        checkinTransIntent.putExtra("action_mode","check_in");
        startActivity(checkinTransIntent);
    }
}
