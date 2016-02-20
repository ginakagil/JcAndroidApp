package com.example.jinkelly.jcandroidapp;

import android.util.JsonReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by jin.chung on 19/2/2016.
 */
public class TransactionReader {

    InputStream gfis;


    public TransactionReader(InputStream fis) {
        gfis = fis;
    }

    public ArrayList<Transaction> readTransFile() throws IOException {
        JsonReader jreader =  new JsonReader(new InputStreamReader(gfis, "UTF-8"));
        try {
            return readTransactions(jreader);}
        finally{
            jreader.close();
        }
    }



    private  ArrayList<Transaction> readTransactions(JsonReader jreader) throws IOException{
        ArrayList<Transaction> transactionslist = new ArrayList<Transaction>();

        jreader.beginArray();
        while (jreader.hasNext()) {
            transactionslist.add(readtranscation(jreader));
        }
        jreader.endArray();

        return transactionslist;

    }

    private Transaction readtranscation(JsonReader jreader) throws IOException{
        Box box = null;
        Location location = null;
        String actionmode = null;
        String timestamp = null;

        jreader.beginObject();
        while (jreader.hasNext()){
            String name = jreader.nextName();
            if(name.equals("actionmode")){
                actionmode = jreader.nextString();
            }else if(name.equals("timestamp")){
                timestamp = jreader.nextString();
            }else if(name.equals("boxdetail")){
                box = readBoxdetail(jreader);
            }else if(name.equals("location")) {
                location = readLocation(jreader);
            }else{jreader.skipValue();
            }
        }
        jreader.endObject();
        return new Transaction(actionmode,timestamp, box, location);
    }

    private Location readLocation(JsonReader jreader) throws IOException {

        String slot_id = null;
        String slot_text = null;

        jreader.beginObject();
        while (jreader.hasNext()) {
            String name = jreader.nextName();
            if (name.equals("slot_id")) {
                slot_id = jreader.nextString();
            } else if (name.equals("slot_text")) {
                slot_text = jreader.nextString();
            } else {
                jreader.skipValue();
            }
        }
        jreader.endObject();
        return new Location(slot_id, slot_text);
    }

    private Box readBoxdetail(JsonReader jreader) throws IOException{
        String dept = null;
        String box_no = null;
        String slot_id = null;
        String slot_text = null;

        jreader.beginObject();
        while (jreader.hasNext()){
            String name = jreader.nextName();
            if(name.equals("dept")){
                dept = jreader.nextString();
            }else if(name.equals("box_no")){
                box_no = jreader.nextString();
            }else if(name.equals("slot_id")){
                slot_id = jreader.nextString();
            }else if(name.equals("slot_text")) {
                slot_text = jreader.nextString();
            }else{jreader.skipValue();
            }
        }
        jreader.endObject();
        return new Box(dept,box_no,slot_id,slot_text);
    }
}
