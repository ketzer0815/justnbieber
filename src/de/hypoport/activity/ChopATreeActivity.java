package de.hypoport.activity;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.widget.*;
import de.hypoport.*;

public class ChopATreeActivity extends ActivityEnhanced implements SensorEventListener
  {

	public static final String KEY_PROGRESS = "ChopATreeActivity.progress";

	private SensorManager sensorManager; 
	private boolean isHitting = false;  
	private long lastUpdate;
	private ProgressBar progressBar; 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chopatree);

		int progress = this.savedInstanceState.getInt(KEY_PROGRESS, 0);
		progressBar = (ProgressBar) findViewById(R.id.chopatreeProgressBar);
		progressBar.setProgress(progress);

		ImageView image = (ImageView) findViewById(R.id.chopatreeImageView);
		image.setImageResource(R.drawable.tree);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
		lastUpdate = System.currentTimeMillis();

	  }
	// <SensorEventListener>
	@Override public void onSensorChanged(SensorEvent event) { 
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) { 
			getAccelerometer(event); 
		  }
	  }

	private void getAccelerometer(SensorEvent event) { 
		float[] values = event.values; // Movement 
		float x = values[0]; 
		float y = values[1]; 
		float z = values[2];

		float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH); 
		long actualTime = System.currentTimeMillis(); 
		if (accelationSquareRoot >= 2) { 
			if (actualTime - lastUpdate < 100) { 
				return; 
			  } 
			lastUpdate = actualTime; 
			if (isHitting) { 
				progressBar.incrementProgressBy(Math.round(accelationSquareRoot * 2.5f));
				if (progressBar.getProgress() >= 100) {
					showToastMessage(R.string.msg_tree_down);
					//return to caller
					Intent returnIntent = new Intent(); 
					setResult(RESULT_OK, returnIntent);
					finish();
				  }
			  }
			isHitting = !isHitting; 
		  } 
	  }

	@Override public void onAccuracyChanged(Sensor sensor, int accuracy) {
	  }
// </SensorEventListener>
	
	@Override protected void onResume() { 
		super.onResume(); 
// register this class as a listener for the orientation and 
// accelerometer sensors 
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL); 
	  }

	@Override protected void onPause() { 
// unregister listener 
		super.onPause(); 
		sensorManager.unregisterListener(this); 
	  }
	  
  }
