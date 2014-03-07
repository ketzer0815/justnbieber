package de.hypoport.activity;

import android.app.*;
import android.os.*;
import android.widget.*;

public class ActivityEnhanced extends Activity
  {
  	protected Bundle savedInstanceState;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			this.savedInstanceState = new Bundle();	
		  } else {
			this.savedInstanceState = savedInstanceState;
		  }
	  }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putAll(savedInstanceState);
		super.onSaveInstanceState(outState);
	  }

	public void showToastMessage(int id) {
		showToastMessage(getString(id));
	  }

	public void showToastMessage(String message) {
		System.out.println(message); //FIXME: Das hier ist nur zum Spielen :)
		Toast toast = Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG);
		toast.show();
	  }
  }
