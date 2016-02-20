package com.example.jinkelly.jcandroidapp;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by jin.chung on 19/2/2016.
 */
public class TransactionWriter {

    OutputStream gfos;


    public TransactionWriter(OutputStream fos) {
        gfos = fos;
    }


    public void writeTransFile(ArrayList<Transaction> TransactionList) throws IOException {
        JsonWriter jwriter = new JsonWriter(new OutputStreamWriter(gfos, "UTF-8"));
        jwriter.setIndent("  ");
        writeTransactions(jwriter, TransactionList);
        jwriter.close();

    }

    private void writeTransactions(JsonWriter jwriter, ArrayList<Transaction> transactionList) throws IOException{
        jwriter.beginArray();
        for (Transaction transaction:transactionList){
            writeTransaction(jwriter, transaction);
        }
        jwriter.endArray();
    }

private void writeTransaction(JsonWriter jwriter, Transaction transaction) throws IOException{
        jwriter.beginObject();
        jwriter.name("actionmode").value(transaction.getAction());
        jwriter.name("timestamp").value(transaction.getTimestamp());
        if(transaction.getBox()!=null){
            jwriter.name("boxdetail");
            writeBox(jwriter,transaction.getBox());
        }
    if(transaction.getLoc()!=null){
        jwriter.name("location");
        writeLoc(jwriter, transaction.getLoc());
    }
    jwriter.endObject();

    }

    private void writeLoc(JsonWriter jwriter, Location loc) throws IOException{
        jwriter.beginObject();
        jwriter.name("slot_id").value(loc.getSlotid());
        jwriter.name("slot_text").value(loc.getSlottxt());
        jwriter.endObject();
    }

    private void writeBox(JsonWriter jwriter, Box box) throws IOException{
        jwriter.beginObject();
        jwriter.name("dept").value(box.getDept());
        jwriter.name("box_no").value(box.getBoxno());
        jwriter.name("slot_id").value(box.getSlotid());
        jwriter.name("slot_text").value(box.getSlottxt());
        jwriter.endObject();
    }

}
