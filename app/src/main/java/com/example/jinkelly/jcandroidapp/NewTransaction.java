package com.example.jinkelly.jcandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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

import java.text.SimpleDateFormat;
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
    private TextView locValue;
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
        locValue = (EditText)findViewById(R.id.etloc);
        locValue.setInputType(InputType.TYPE_NULL);
        locValue.setTextIsSelectable(true);
        locTarget = (TextView)findViewById(R.id.tvbartarget);


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
        locValue.setError(null);
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
        String displayDept = "";
        String displayBoxNum = "";
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
            locValue.setText(displaySlotTxt);
            locValue.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_24dp, 0);}else{
                locValue.setError("Not Match!");
                locValue.setText(displaySlotTxt);
            }
        }
    }

    public void subTrans(View view) {
        //check barcodes
        if (TextUtils.isEmpty(boxNumber.getText().toString())){
            boxNumber.setError("Cannot be blank");
        }else if (TextUtils.isEmpty(boxDept.getText().toString())){
                boxDept.setError("Cannot be blank");
        }else if(TextUtils.isEmpty(locValue.getText().toString())){
            locValue.setError("Cannot be blank");
        }else{
            TranSubmit(boxNumber.getText().toString(), locValue.getText().toString());
        }

    }

    public void TranSubmit(String boxnum , String location ){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = df.format(c.getTime());
        //make json
        JSONObject sTransaction = new JSONObject();
        try {
            sTransaction.put("boxnumber", boxnum);
            sTransaction.put("location", location);
            sTransaction.put("timestamp",formattedDate);
            sTransaction.put("poststatus",poststatus);
        }catch(JSONException ex){
            xError = ex.toString();
            statusMessage.setText("JSON conversion failed");
        }
        if( TextUtils.isEmpty(xError) ){
            //TODO:do some posting
            //TODO:append file
        }



    }
}
