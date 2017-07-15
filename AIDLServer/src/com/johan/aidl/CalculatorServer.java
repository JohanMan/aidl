package com.johan.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class CalculatorServer extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return new CalculatorBinder();
	}
	
	public class CalculatorBinder extends Calculator.Stub {
		@Override
		public int add(int num1, int num2) throws RemoteException {
			return num1 + num2;
		}
		@Override
		public int sub(int num1, int num2) throws RemoteException {
			return num1 - num2;
		}
	}
	
}
