package com.example.locationbasedservice;





import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Signup extends Activity implements View.OnClickListener{
	EditText etname, etuser,etpass,etconfirm;
	Button btnRegister,home;
	final DbLogic logic = new DbLogic(Signup.this);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		logic.openDB();
		etname = (EditText)findViewById(R.id.editText1);
		etuser = (EditText)findViewById(R.id.editText2);
		etpass = (EditText)findViewById(R.id.editText3);
		etconfirm = (EditText)findViewById(R.id.editText4);
		btnRegister = (Button)findViewById(R.id.button1);
		
		btnRegister.setOnClickListener(this);
		home=(Button)findViewById(R.id.button2);
	    home.setOnClickListener(new OnClickListener(){

		public void onClick(View v) {
			Intent main = new Intent(Signup.this,MainActivity.class);
			startActivity(main);
		}
	    });
	    etuser.setOnKeyListener(new OnKeyListener() {
		    
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            // TODO Auto-generated method stub
	           Set<String> user_set;
	           String text=new String(etuser.getText().toString());
	    	   if (text!= null && !text.isEmpty() && ( keyCode==KeyEvent.KEYCODE_ENTER || keyCode== KeyEvent.KEYCODE_TAB)) 
	    	   {
	    		   user_set=new HashSet<String>();
	   			Cursor cursor = logic.getString(null);
	   			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
	   				 user_set.add(cursor.getString(2));
	   			//	 Toast.makeText(MainActivity.this,cursor.getString(3),Toast.LENGTH_LONG).show();	
	   			}
	   			cursor.close();
	   			if(user_set.contains(text))
	   	         {
	                	Toast.makeText(Signup.this,"Username already exists",Toast.LENGTH_LONG).show();
	                	etuser.setText("");
	   	         }
	   			
	    	   } 
	    	   return false;
	        } 

			
	    });
}

	
	public void onClick(View view) {
		
			String name = etname.getText().toString();
			String user = etuser.getText().toString();
			String passwd = etpass.getText().toString();
			String confirm= etconfirm.getText().toString();
			
			if (passwd.equals(confirm)){
			String[] columns = { "name" , "username" ,"password","confirm"};
			String[] colValues = { name,user,passwd,confirm};
			ContentValues cValues = new ContentValues();
			
			for (int i =0 ; i < columns.length;i++){
				cValues.put(columns[i],colValues[i]);
			  }
			try {
				long rowId = logic.insertQuery(cValues);
				Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_LONG).show();
			   } 
			catch (Exception e) {
				e.printStackTrace();
			   }
			}
			else
			{
				Toast.makeText(Signup.this,"Password mismatch",Toast.LENGTH_SHORT).show();
				etpass.setText("");
				etconfirm.setText("");
				return;
			}
			etname.setText("");
			etuser.setText("");
			etpass.setText("");
			etconfirm.setText("");
	}
}
