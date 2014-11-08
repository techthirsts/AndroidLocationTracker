package com.example.locationbasedservice;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
	private Button signIn,signUp,exit;
	private CheckBox cb;
	private EditText editPass;
	private EditText editName;
	private Set<String> user;
	final DbLogic logic = new DbLogic(MainActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	requestWindowFeature(Window.FEATURE_LEFT_ICON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//	getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);
		getActionBar().setIcon(R.drawable.ic_launcher);
		signIn = (Button)findViewById(R.id.button1);
		editPass = (EditText)findViewById(R.id.editText2);
		editName = (EditText)findViewById(R.id.editText1);
		signUp = (Button)findViewById(R.id.button2);
		exit = (Button)findViewById(R.id.button3);
		cb=(CheckBox)findViewById(R.id.checkBox1);
		exit.setOnClickListener(this);
		user=new HashSet<String>();
		
		
		logic.openDB();
		Cursor cursor = logic.getString(null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			 user.add(cursor.getString(2));
		//	 Toast.makeText(MainActivity.this,cursor.getString(3),Toast.LENGTH_LONG).show();	
		}
		
		cursor.close();
	    editName.setOnKeyListener(new OnKeyListener() {
	    
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            // TODO Auto-generated method stub
	      
	        	String text=new String(editName.getText().toString());
	    	   if (text!= null && !text.isEmpty() && ( keyCode==KeyEvent.KEYCODE_ENTER || keyCode== KeyEvent.KEYCODE_TAB)) { 
	    		   if(user.contains(text))
			         {
	            	Toast.makeText(MainActivity.this,"User exists",Toast.LENGTH_LONG).show();
			         }
	             
	                else
	                {
	                	editName.setText("");
	                	Toast.makeText(MainActivity.this,"User doesn't exist. Please Signup",Toast.LENGTH_LONG).show();
	                }
	            }
	            return false;
	        } 

			
	    });
	    
		signIn.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				
				String s=editName.getText().toString();
				 String p=editPass.getText().toString();
				 Cursor cursor = logic.getString(null);
				  
		    int flag=0,uflag=0;
		  cursor = logic.getString(null);
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
						 
			 //Toast.makeText(MainActivity.this,cursor.getString(2),Toast.LENGTH_LONG).show();	
		         if(s.equals(cursor.getString(2)))
		         {
		        	 uflag=1;
		        	// Toast.makeText(MainActivity.this,cursor.getString(3),Toast.LENGTH_LONG).show();
			         if(p.equals(cursor.getString(3))){
		        				 flag=1;
		        				//Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_LONG).show();
		        				 Intent welcomeIntent = new Intent(MainActivity.this,Signin.class);
		        				 welcomeIntent.putExtra("username", s);
		        				 startActivity(welcomeIntent);
		        			 }
		          	  }
		    	}
			cursor.close();
			 if(uflag==0)
	         {
	        editName.setText("");
	        editPass.setText("");
				 Toast.makeText(MainActivity.this,"User doesn't exist. Please Signup",Toast.LENGTH_LONG).show();
				
	         }
		        if(uflag==1 && flag==0){
		        		 Toast.makeText(MainActivity.this,"Please enter correct password",Toast.LENGTH_LONG).show();
		        		 editPass.setText("");
		           }	
		       
			}
			
		});
		signUp.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent signupIntent = new Intent(MainActivity.this,Signup.class);
				startActivity(signupIntent);
			}
		});
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	           	     if(isChecked){
	           	    	editPass.setText("");
	           	    	editName.setText("");
	                  }         
	            }
		});
	}
	  public void onClick(View view) {
	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	     //   alertDialogBuilder.setTitle("Exit Application?");
	        alertDialogBuilder
	                .setTitle("Exit Application?")
	                .setMessage("Click yes to exit!")
	                .setCancelable(false)
	                .setPositiveButton("Yes",
	                        new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                                moveTaskToBack(true);
	                                android.os.Process.killProcess(android.os.Process.myPid());
	                                System.exit(1);
	                            }
	                        })

	                .setNegativeButton("No", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {

	                        dialog.cancel();
	                    }
	                });

	        AlertDialog alertDialog = alertDialogBuilder.create();
	        alertDialog.show();
	    }
}
