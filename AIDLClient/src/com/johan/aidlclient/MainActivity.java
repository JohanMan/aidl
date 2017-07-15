package com.johan.aidlclient;

import com.johan.aidl.Calculator;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EditText num1View, num2View;
	private Button operatorButton;
	private TextView resultView;
	
	private Calculator calculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		num1View = (EditText) findViewById(R.id.num1);
		num2View = (EditText) findViewById(R.id.num2);
		operatorButton = (Button) findViewById(R.id.operator);
		resultView = (TextView) findViewById(R.id.result);
		
		Intent intent = new Intent("com.johan.aidl.CalculatorServer");
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
		
	}
	
	public void changeOperator(View view) {
		if (operatorButton.getText().toString().equals("+")) {
			operatorButton.setText("-");
		} else {
			operatorButton.setText("+");
		}
	}
	
	public void equal(View view) {
		if (calculator == null) {
			resultView.setText("calculator无法提供服务");
			return ;
		}
		try {
			int num1 = Integer.parseInt(num1View.getText().toString());
			int num2 = Integer.parseInt(num2View.getText().toString());
			int result;
			if (operatorButton.getText().toString().equals("+")) {
				result = calculator.add(num1, num2);
			} else {
				result = calculator.sub(num1, num2);
			}
			resultView.setText("计算结果：" + result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			calculator = null;
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			calculator = Calculator.Stub.asInterface(service);
		}
	};

}
