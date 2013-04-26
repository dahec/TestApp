package com.example.testapp;

import java.text.NumberFormat;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalculationActivity extends FragmentActivity {
	
	private static final String TAG = "CalculationActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//if activity was restored - no need to setup it once again
		if(savedInstanceState != null) {
			return;
		}
		
		//adding calculation fragment
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
			.add(android.R.id.content, 
					Fragment.instantiate(this, 
							CalculationFragment.class.getName()), 
							"CalculationFragment")
			.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public static class CalculationFragment extends Fragment {
		
		private EditText mFirstNumber;
		private EditText mSecondNumber;
		private EditText mResult;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = (View)inflater.inflate(R.layout.activity_calculation, container, false);
			mFirstNumber = (EditText)v.findViewById(R.id.etFirstNumber);
			mSecondNumber = (EditText)v.findViewById(R.id.etSecondNumber);
			mResult = (EditText)v.findViewById(R.id.etResult);
			Button btnCalculate = (Button)v.findViewById(R.id.btnCalculate);
			//add callback to "Calculate" button
			btnCalculate.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					calculate();
				}
			});
			return v;
		}
		
		public void calculate() {
			
			String firstNumberString = mFirstNumber.getText().toString();
			String secondNumberString = mSecondNumber.getText().toString();
			
			Double firstNumber = 0d;
			Double secondNumber = 0d;
			
			if(!firstNumberString.equals("")) {
				firstNumber = Double.valueOf(firstNumberString);
			}
			if(!secondNumberString.equals("")) {
				secondNumber = Double.valueOf(secondNumberString);
			}
			
			//if user hasn't input anything - let him know
			if(firstNumber == 0d && secondNumber == 0d) {
				String warning = getResources().getString(R.string.warning_no_input);
				Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
				return;
			}
			
			Double result = firstNumber+secondNumber;
			Log.d(TAG, ""+result);
			//format result into more familiar look
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			nf.setMaximumIntegerDigits(16);
			nf.setMaximumFractionDigits(16);
			String resultString = nf.format(result);
			//replace delimiter to ".", to implement correct copy-pasting
			resultString = resultString.replace(",", ".");
			Log.d(TAG, resultString);
			mResult.setText(resultString);
			
		}
		
	}

}
