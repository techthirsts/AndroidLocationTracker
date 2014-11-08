package com.example.locationbasedservice;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class TrackService extends Service implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,LocationListener{
	  private Timer timer;
	  LocationClient mLocationClient;
		// boolean mUpdatesRequested;
	  LocationRequest mLocationRequest;
	  TrackService t=this;
	  final Locationdb db = new Locationdb(this);
	  Datas d = new Datas();
	  private static final String TAG = TrackService.class.getSimpleName();
	  Signin s;
	  String user;
	  private TimerTask updateTask = new TimerTask() {
	    @Override
	    public void run() {
	      Log.i(TAG, "Timer task doing work");
	    }
	  };
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onCreate() {
	    super.onCreate();
	    
	    Log.i(TAG, "Service creating");
	
	    timer = new Timer("Track");
	    timer.schedule(updateTask, 1000L, 300 * 1000L);
	    mLocationClient = new LocationClient(this, this, this);
	    mLocationRequest = LocationRequest.create();
	    mLocationClient.connect();
       
	    mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 10 mins -- 600000
        mLocationRequest.setInterval(600000);
      
	   // mUpdatesRequested = false;
	  }
	public int onStartCommand(Intent intent, int flags, int startId) {
	    
	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
		Log.i(TAG, "In start");
		if(intent!=null)
		{
			Log.i(TAG, "In Intent");
			user= intent.getExtras().getString("username");
			
		}
		if(mLocationClient.isConnected())
		{
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		}
		else
			mLocationClient.connect();
	    return START_STICKY;
	}
	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	    Log.i(TAG, "Service destroying");
	    mLocationClient.disconnect();
	    timer.cancel();
	    timer = null;
	  }

	@Override
	   public void onConnected(Bundle dataBundle) {
	      // Display the connection status
	      Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	      trackLocation();
	   }

	   @Override
	   public void onDisconnected() {
	      // Display the connection status
	      Toast.makeText(this, "Disconnected. Please re-connect.",
	      Toast.LENGTH_SHORT).show();
	   }
	   
	   

	   @Override
	   public void onConnectionFailed(ConnectionResult connectionResult) {
	      // Display the error code on failure
	      Toast.makeText(this, "Connection Failure : " + 
	      connectionResult.getErrorCode(),
	      Toast.LENGTH_SHORT).show();
	   }
	   public void trackLocation() {
	      // Get the current location's latitude & longitude
		  
	      Location currentLocation = mLocationClient.getLastLocation();
	 //     String msg = "Current Location: " +
	 //     Double.toString(currentLocation.getLatitude()) + "," +
	 //     Double.toString(currentLocation.getLongitude());
	  //    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	    /*  SharedPreferences.Editor mEditor = mPrefs.edit();
	         mEditor.putString("Latitude", Double.toString(currentLocation.getLatitude()));
	         mEditor.putString("Longitude", Double.toString(currentLocation.getLongitude()));*/
	     
	      // Display the current location in the UI
	      DecimalFormat df=new DecimalFormat("0.00");
	      String lat = df.format(currentLocation.getLatitude()); 
	      String longit=df.format(currentLocation.getLongitude());
	      d.setuser(user);
	      d.setlat(lat);
	      d.setlongit(longit);
	      
	      // To display the current address in the UI
	      (new GetAddressTask(this)).execute(currentLocation);
	   }
	   /*
	    * Following is a subclass of AsyncTask which has been used to get
	    * address corresponding to the given latitude & longitude.
	    */
	   private class GetAddressTask extends AsyncTask<Location, Void, String>{
	      Context mContext;
	      public GetAddressTask(Context context) {
	         super();
	         mContext = context;
	      }

	      /*
	       * When the task finishes, onPostExecute() displays the address. 
	       */
	      @Override
	      protected void onPostExecute(String address) {
	         // Display the current address in the UI
	    	/*  SharedPreferences.Editor mEditor = mPrefs.edit();
	         mEditor.putString("Address", address);*/
	    	  
	    	  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	    	  d.setDate(df.format(Calendar.getInstance().getTime()));
	    	  SimpleDateFormat df1 = new SimpleDateFormat("HH:mm",Locale.getDefault());
	    	  d.setTime(df1.format(Calendar.getInstance().getTime()));
	    	  d.setaddr(address);
	    	//String msg=address+" "+df.format(Calendar.getInstance().getTime())+" "+df1.format(Calendar.getInstance().getTime());
	    	//  Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
	    	//  label.setText(msg);
	    	  db.addData(d);
	      }
	      @Override
	      protected String doInBackground(Location... params) {
	         Geocoder geocoder =
	         new Geocoder(mContext, Locale.getDefault());
	         // Get the current location from the input parameter list
	         Location loc = params[0];
	         // Create a list to contain the result address
	         List<Address> addresses = null;
	         try {
	            addresses = geocoder.getFromLocation(loc.getLatitude(),
	            loc.getLongitude(), 1);
	         } catch (IOException e1) {
	            Log.e("LocationSampleActivity", 
	            "IO Exception in getFromLocation()");
	            e1.printStackTrace();
	            return ("IO Exception trying to get address");
	         } catch (IllegalArgumentException e2) {
	            // Error message to post in the log
	            String errorString = "Illegal arguments " +
	            Double.toString(loc.getLatitude()) +
	            " , " +
	            Double.toString(loc.getLongitude()) +
	            " passed to address service";
	            Log.e("LocationSampleActivity", errorString);
	            e2.printStackTrace();
	            return errorString;
	         }
	         // If the reverse geocode returned an address
	         if (addresses != null && addresses.size() > 0) {
	            // Get the first address
	            Address address = addresses.get(0);
	            /*
	            * Format the first line of address (if available),
	            * city, and country name.
	            */
	            String addressText = String.format(
	            "%s, %s, %s",
	            // If there's a street address, add it
	            address.getMaxAddressLineIndex() > 0 ?
	            address.getAddressLine(0) : "",
	            // Locality is usually a city
	            address.getLocality(),
	            // The country of the address
	            address.getCountryName());
	            // Return the text
	            return addressText;
	         } else {
	            return "No address found";
	         }
	      }
	   }// AsyncTask class
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		String msg = "Updated Location: " +
               Double.toString(arg0.getLatitude()) + "," +
               Double.toString(arg0.getLongitude());
       Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
       /*SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("Latitude", Double.toString(arg0.getLatitude()));
        mEditor.putString("Longitude", Double.toString(arg0.getLongitude()));*/
       DecimalFormat df=new DecimalFormat("0.00");
	      String lat = df.format(arg0.getLatitude()); 
	      String longit=df.format(arg0.getLongitude());
	      d.setlat(lat);
	      d.setlongit(longit);
        (new GetAddressTask(this)).execute(arg0);
	}

}
