package com.taxicontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class TaxiControlActivity extends Activity {
	
	Context context = TaxiControlActivity.this;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //load items
        final Button button = (Button)findViewById(R.id.ok);
        final TextView textView = (TextView)findViewById(R.id.result);
        final EditText editText = (EditText)findViewById(R.id.entry);
        final CheckBox festivo = (CheckBox)findViewById(R.id.festivo);
        final CheckBox aeropuerto = (CheckBox)findViewById(R.id.aeropuerto);
        		
        //create listener
        OkListener listener = new OkListener();
        listener.setTextView(textView);
        listener.setEditText(editText);
        listener.setContext(context);
        listener.setFestivo(festivo);
        listener.setAeropuerto(aeropuerto);
        //add listener
        button.setOnClickListener(listener);
        editText.setOnKeyListener(listener);
        festivo.setOnCheckedChangeListener(listener);
        aeropuerto.setOnCheckedChangeListener(listener);
    }
    
    
}