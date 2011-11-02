package com.taxicontrol;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class OkListener implements OnClickListener, TextWatcher, OnCheckedChangeListener {
	Context context;
	TextView textView;
	EditText editText;
	CheckBox festivo;
	CheckBox aeropuerto;
	
	public void onClick(View v) {
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
	}	

	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
	}
	
	public String operateUnits(String value) {
		try {
			long units = Long.parseLong(value);
			//si es menor a 50 cobrar carrera minima
			if (units < 50) {
				units = 50;
			}
			//si es nocturno o festivo agregar las unidades por recargo nocturno o festivo
			Calendar ahora = new GregorianCalendar();
			int hora = ahora.get(Calendar.HOUR_OF_DAY);
			int dia = ahora.get(Calendar.DAY_OF_WEEK);
			if (hora >= 20 || hora < 5 || dia == Calendar.SUNDAY || festivo.isChecked()) {
				units += Constants.recargoNocturnoFestivo;
			}
			if (aeropuerto.isChecked()) {
				units += Constants.recargoPuenteAereo;
			}
			//multiplica las unidades por el valor por unidad
			long result = units*Constants.pesosPorUnidad;
			result = Math.round(result/100.0)*100;
			return "Valor carrera: $"+result;
		} catch (NumberFormatException e) {
			return "Entrada Invalida";
		}
		
	}
	
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	public EditText getEditText() {
		return editText;
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
	}
	
	public void setFestivo(CheckBox festivo) {
		this.festivo = festivo;
	}
	
	public void setAeropuerto(CheckBox aeropuerto) {
		this.aeropuerto = aeropuerto;
	}

	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
		return false;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
		return false;
	}

	public void afterTextChanged(Editable s) {
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	
}
