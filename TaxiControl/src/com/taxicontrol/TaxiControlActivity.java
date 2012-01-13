package com.taxicontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.ads.*;

public class TaxiControlActivity extends Activity {
	private AdView adView;
	Context context = TaxiControlActivity.this;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //load items
        final TextView textView = (TextView)findViewById(R.id.result);
        final EditText editText = (EditText)findViewById(R.id.entry);
        final CheckBox festivo = (CheckBox)findViewById(R.id.festivo);
        final CheckBox aeropuerto = (CheckBox)findViewById(R.id.aeropuerto);
        final CheckBox auto = (CheckBox)findViewById(R.id.auto);
        final CheckBox terminal = (CheckBox)findViewById(R.id.terminal);
        final CheckBox puertaapuerta = (CheckBox)findViewById(R.id.puertaapuerta);
        final Button mas = (Button)findViewById(R.id.mas);
        final Button menos = (Button)findViewById(R.id.menos);
//        final CheckBox primaNav = (CheckBox)findViewById(R.id.prima);
        //ad
        adView = new AdView(this, AdSize.BANNER, "a14eaeb3d03c19a");
        
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainLayout);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.BELOW, R.id.terminal);
//        p.addRule(RelativeLayout.ALIGN_BASELINE);
        adView.setLayoutParams(p);
        layout.addView(adView,p);
        AdRequest request = new AdRequest();
//        request.setTesting(true);
        adView.loadAd(request); 
        
        //create listener
        OkListener listener = new OkListener();
        listener.setTextView(textView);
        listener.setEditText(editText);
        listener.setContext(context);
        listener.setFestivo(festivo);
        listener.setAeropuerto(aeropuerto);
        listener.setAuto(auto);
        listener.setPuertaapuerta(puertaapuerta);
        listener.setTerminal(terminal);
//        listener.setMas(mas);
//        listener.setMenos(menos);
//        listener.setPrimaNav(primaNav);
        listener.calcular();
        //add listener
        editText.addTextChangedListener(listener);
        festivo.setOnCheckedChangeListener(listener);
        aeropuerto.setOnCheckedChangeListener(listener);
        auto.setOnCheckedChangeListener(listener);
        terminal.setOnCheckedChangeListener(listener);
        puertaapuerta.setOnCheckedChangeListener(listener);
        menos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					editText.setText((Long.parseLong(editText.getText().toString())-1)+"");
				} catch (Exception e) {
					
				}
			}
		});
        mas.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					editText.setText((Long.parseLong(editText.getText().toString())+1)+"");
				} catch (Exception e) {
					
				}
			}
		});
//        primaNav.setOnCheckedChangeListener(listener);
    }
    
    
}