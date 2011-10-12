package com.taxicontrol;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class OkListener implements OnClickListener, OnKeyListener {
	Context context;
	TextView textView;
	EditText editText;
	int constantUnits;
	int minimalUnits;
	
	public void onClick(View v) {
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		String text = editText.getText().toString();
		String unitsOperated = operateUnits(text);
		textView.setText(unitsOperated);
		return false;
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
			if (hora > 20 || hora < 5 || dia == Calendar.SUNDAY) {
				units += Constants.recargoNocturnoFestivo;
			}
			//multiplica las unidades por el valor por unidad
			long result = units*Constants.pesosPorUnidad;
			return Long.toString(result);
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

}
