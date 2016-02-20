package com.example.jinkelly.jcandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.drive.metadata.internal.ParentDriveIdSet;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * Created by jin.chung on 12/2/2016.
 */
public class NewTransaction extends AppCompatActivity {
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private TextView statusMessage;
    private TextView boxNumber;
    private TextView boxDept;
    private TextView slotId;
    private TextView slotTxt;
    private TextView locTarget;
    private TextView locSlottxt;
    private TextView locSlotid;
    private Button submitbtn;
    public String action_mode = "";
    public String poststatus = "PEND";
    private String xError;
    private String ScanMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent getActionMode = getIntent();

        setContentView(R.layout.transaction_form);
         statusMessage = (TextView)findViewById(R.id.tvbarcodemsg);
        boxDept = (EditText)findViewById(R.id.etdept);
        boxDept.setInputType(InputType.TYPE_NULL);
        boxDept.setTextIsSelectable(true);
        boxNumber = (EditText)findViewById(R.id.etbn);
        boxNumber.setInputType(InputType.TYPE_NULL);
        boxNumber.setTextIsSelectable(true);
        slotId = (TextView)findViewById(R.id.tvslotid);
        slotTxt = (TextView)findViewById(R.id.tvslottxt);
        locSlottxt = (EditText)findViewById(R.id.etloctxt);
        locSlotid = (EditText)findViewById(R.id.etlocid);
        locSlottxt.setInputType(InputType.TYPE_NULL);
        locSlottxt.setTextIsSelectable(true);
        locTarget = (TextView)findViewById(R.id.tvbartarget);
        submitbtn = (Button)findViewById(R.id.btntransub);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subTrans(v);
            }
        });


        TextView transTitle = (TextView)
                findViewById(R.id.tvtransmode);

        action_mode = getActionMode.getExtras().getString("action_mode");


        if( action_mode.equals("check_in")){
            transTitle.setText("NEW CHECK IN TRANSACTION");

        }else if(action_mode.equals("check_out")){
            transTitle.setText("NEW CHECK OUT TRANSACTION");

        }

    }


    public void ScanBn(View v) {
        boxNumber.setError(null);
        boxDept.setError(null);
        locSlottxt.setError(null);
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        if (v.getId() == R.id.btnbnscan) {
            ScanMode = "BOX";
        }else if (v.getId() == R.id.btnlocscan) {
            ScanMode = "LOC";
        };
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, true);
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    statusMessage.setText(R.string.barcode_success);
                    if(ScanMode.equals("BOX"))
                    {updateBoxDetails(barcode.displayValue);
                    }else if(ScanMode.equals("LOC")){
                        updateLoc(barcode.displayValue);
                    };
                    //barTarget.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateBoxDetails(String JSONBoxDetailsString){
        String ParseErrorMsg = null;
        String displayDept = "";
        String displayBoxNum = "";
        String displaySlot = "";
        String displaySlotTxt = "";
        try {
            JSONObject boxObject = new JSONObject(JSONBoxDetailsString);
            displayDept = boxObject.get("dept").toString();
            displayBoxNum = boxObject.get("box_no").toString();
            displaySlot = boxObject.get("slot_id").toString();
            displaySlotTxt = boxObject.get("slot_text").toString();
        }catch(JSONException ex){
            ParseErrorMsg = ex.toString();
            Log.d(TAG, "Parse Json Error");
            statusMessage.setText("Not a Valid Barcode");
        }
        if(TextUtils.isEmpty(ParseErrorMsg)){
            boxNumber.setText(displayBoxNum);
            boxDept.setText(displayDept);
            slotTxt.setText(displaySlotTxt);
            slotId.setText(displaySlot);
            locTarget.setText("Target Location:" + displaySlotTxt);
        }
    }


    public void updateLoc(String JSONBoxDetailsString){
        String ParseErrorMsg = null;
        String displaySlot = "";
        String displaySlotTxt = "";

        try {
            JSONObject boxObject = new JSONObject(JSONBoxDetailsString);
            displaySlot = boxObject.get("slot_id").toString();
            displaySlotTxt = boxObject.get("slot_text").toString();
        }catch(JSONException ex){
            ParseErrorMsg = ex.toString();
            Log.d(TAG, "Parse Json Error");
            statusMessage.setText("Not a Valid Barcode");
        }
        if(TextUtils.isEmpty(ParseErrorMsg)){
            if(slotId.getText().equals(displaySlot)){
                locSlotid.setText(displaySlot);
                locSlottxt.setText(displaySlotTxt);
                locSlottxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_24dp, 0);
                locSlotid.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_24dp, 0);
            }else{
                locSlottxt.setError("Not Match!");
                locSlottxt.setText(displaySlotTxt);
                locSlotid.setText(displaySlot);
                locSlotid.setError("Not Match!");
            }
        }
    }

    public void subTrans(View view) {
        //check barcodes
        if (TextUtils.isEmpty(boxNumber.getText().toString())){
            boxNumber.setError("Cannot be blank");
        }else if (TextUtils.isEmpty(boxDept.getText().toString())){
                boxDept.setError("Cannot be blank");
        }else if(TextUtils.isEmpty(locSlottxt.getText().toString())){
            locSlottxt.setError("Cannot be blank");
        }else{
            if (locSlotid.getError() == null && locSlottxt.getError() == null && boxDept.getError() == null && boxNumber.getError() == null){
            TranSubmit(action_mode,
                    new Box(boxDept.getText().toString(), boxNumber.getText().toString(), slotId.getText().toString(), slotTxt.getText().toString()),
                    new Location(locSlottxt.getText().toString(), locSlotid.getText().toString()));
            }
        }

    }

    public void TranSubmit(String ActionMode, Box inBox , Location inLocation ){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = df.format(c.getTime());
        //make arraylist
        ArrayList<Transaction> TransList = new ArrayList<Transaction>();

        try {
            FileInputStream fis = openFileInput("transJson.data");
            TransactionReader treader= new TransactionReader(fis);
            TransList = treader.readTransFile();

        }catch (Exception fex){
            //no file
        }

        TransList.add(new Transaction(action_mode,formattedDate,inBox,inLocation));

        try {
            FileOutputStream fos = openFileOutput("transJson.data", Context.MODE_PRIVATE);
            TransactionWriter twriter = new TransactionWriter(fos);
            twriter.writeTransFile(TransList);

        }catch (Exception fex){

        }
         finish();


    }




}
