package com.example.quadrosmoveis;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	public static final String TAG = "quadros";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "on create");
		
		View view = new ViewQueSeMexe(this);
		setContentView(view);
	}

}
