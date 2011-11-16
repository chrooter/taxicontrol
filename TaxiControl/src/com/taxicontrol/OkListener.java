package com.taxicontrol;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

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
	CheckBox auto;
	CheckBox terminal;
	CheckBox puertaapuerta;
	static Hashtable<String, Hashtable<String, Hashtable<String, Boolean>>> festivos = new Hashtable<String, Hashtable<String,Hashtable<String,Boolean>>>();
	
	public OkListener() {
		//actualiza festivos
		festivos = new Hashtable<String, Hashtable<String,Hashtable<String,Boolean>>>();
		Hashtable<String, Boolean> festivosNoviembre2011 = new Hashtable<String, Boolean>();
		festivosNoviembre2011.put("14", true);
		Hashtable<String, Boolean> festivosDiciembre2011 = new Hashtable<String, Boolean>();
		festivosNoviembre2011.put("8", true);
		Hashtable<String, Hashtable<String, Boolean>> festivos2011 = new Hashtable<String, Hashtable<String,Boolean>>();
		festivos2011.put("11", festivosNoviembre2011);
		festivos2011.put("12", festivosDiciembre2011);
		festivos.put("2011",festivos2011);
	}
	
	public void calcular() {
		festivo.setEnabled(!auto.isChecked());
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
	}
	
	public void onClick(View v) {
		calcular();
	}	

	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		calcular();
	}
	
	public String operateUnits(String value) {
		try {
			long units = Long.parseLong(value);
			//si es menor a 50 cobrar carrera minima
			if (units < 50) {
				units = 50;
			}
			//si es nocturno o festivo agregar las unidades por recargo nocturno o festivo
			
			if (isFestivoONocturno()) {
				units += Constants.recargoNocturnoFestivo;
			}
			if (aeropuerto.isChecked()) {
				units += Constants.recargoPuenteAereo;
			}
			if (puertaapuerta.isChecked()) {
				units += Constants.puertaAPuerta;
			}
			if (terminal.isChecked()) {
				units += Constants.desdeTerminalTransporte;
			}
			//multiplica las unidades por el valor por unidad
			long result = units*Constants.pesosPorUnidad;
			result = Math.round(result/100.0)*100;
			if (isFestivoONocturno()) {
				festivo.setChecked(true);
			} else {
				festivo.setChecked(false);
			}
			return "Valor carrera: $"+result;
		} catch (NumberFormatException e) {
			return "Entrada Invalida";
		}
		
	}
	
	public boolean isFestivoONocturno() {
		boolean resultado = false;
		if (auto.isChecked()) {
			Calendar ahora = new GregorianCalendar();
			int hora = ahora.get(Calendar.HOUR_OF_DAY);
			int dia = ahora.get(Calendar.DAY_OF_WEEK);
			if (hora >= 20 || hora < 5 || dia == Calendar.SUNDAY || isFestivo(ahora)) {
				resultado = true;
			}
		} else {
			if (festivo.isChecked()) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	public boolean isFestivo(Calendar calendario) {
		boolean resultado = false;
		int mes = calendario.get(Calendar.MONTH);
		int anio = calendario.get(Calendar.YEAR);
		int dia = calendario.get(Calendar.DAY_OF_MONTH);
		try {
			if (festivos.get(anio).get(mes).get(dia)==true) {
				resultado = true;
			}
		} catch (Exception e) {
			//el festivo no existe (NullPointerException)
		}
		return resultado;
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
		calcular();
		return false;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		calcular();
		return false;
	}

	public void afterTextChanged(Editable s) {
		calcular();
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	public void setAuto(CheckBox auto) {
		this.auto = auto;
	}

	public void setTerminal(CheckBox terminal) {
		this.terminal = terminal;
	}

	public void setPuertaapuerta(CheckBox puertaapuerta) {
		this.puertaapuerta = puertaapuerta;
	}

	
}
