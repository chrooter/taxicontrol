package com.taxicontrol;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
        final Button llamar = (Button)findViewById(R.id.llamar);
        final Spinner year = (Spinner)findViewById(R.id.year);

        //ad
        adView = new AdView(this, AdSize.BANNER, "a14eaeb3d03c19a");
        
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainLayout);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.BELOW, R.id.terminal);
//      p.addRule(RelativeLayout.ALIGN_BASELINE);
        adView.setLayoutParams(p);
        layout.addView(adView,p);
        AdRequest request = new AdRequest();
//      request.setTesting(true);
        adView.loadAd(request); 

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        year.setAdapter(adapter);
        
        //create listener
        final OkListener listener = new OkListener();
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
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg0.getSelectedItemPosition() == 0) {
					Constants.pesosPorUnidad = 70;
				} else {
					Constants.pesosPorUnidad = 72;
				}
				listener.calcular();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
        	
		});
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
        
        llamar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				call();
				
			}
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exit:
            	//moves the application to the back
                moveTaskToBack(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void call() {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:0312222222"));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
             Log.e("myphone dialer", "Call failed", activityException);
        }
    }
}