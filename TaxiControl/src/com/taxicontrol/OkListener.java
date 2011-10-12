package com.taxicontrol;
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
			long result = Long.parseLong(value)*Constants.pesosPorUnidad;
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
