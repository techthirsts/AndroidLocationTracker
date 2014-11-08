package com.example.locationbasedservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Signin extends Activity {
	 private TextView textValuePassed;
	 private Button home,loc,set;
	 String value;
	// SharedPreferences mPrefs;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.signin);
			textValuePassed = (TextView)findViewById(R.id.textView2);
			//label = (TextView)findViewById(R.id.textView3);
			Bundle bundle = getIntent().getExtras();
			value = (String)bundle.get("username");
			textValuePassed.setText(value);
			home=(Button)findViewById(R.id.button1);
			loc=(Button)findViewById(R.id.button2);
			set=(Button)findViewById(R.id.button3);
			
			// Open the shared preferences
	        //mPrefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
	        // Get a SharedPreferences editor
	      //  LocationManager mylocman = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	      //  mylocman.requestLocationUpdates(LocationManager.GPS_PROVIDER,50000,0,this);
	        
	        
		
			Intent intent = new Intent(Signin.this,TrackService.class);
            intent.putExtra("username",value);
            startService(intent);
			home.setOnClickListener(new OnClickListener(){

					public void onClick(View v) {
						Intent main = new Intent(Signin.this,MainActivity.class);
						startActivity(main);
					}
				});
		    loc.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
			 
				Intent main = new Intent(Signin.this,tracklist.class);
				main.putExtra("username", value);
				startActivity(main);
			}
		});
		    set.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					//mLocationClient.disconnect();
					 stopService(new Intent(Signin.this,TrackService.class));
					 Toast.makeText(Signin.this,"Disconnected..Please login again",Toast.LENGTH_LONG).show();
				}
			});
		    /*mLocationClient = new LocationClient(this, this, this);
		    mLocationRequest = LocationRequest.create();
		    mLocationClient.connect();
	       
		    mLocationRequest.setPriority(
	                LocationRequest.PRIORITY_HIGH_ACCURACY);
	        // Set the update interval to 10 mins -- 600000
	        mLocationRequest.setInterval(600000);

		   // mUpdatesRequested = false;*/
		    

		}
		 
}
