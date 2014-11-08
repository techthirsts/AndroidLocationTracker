package com.example.locationbasedservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class track extends Activity implements View.OnClickListener{
	
	 private Button home,view,save;
	 String value;
	 EditText name,from_date,to_date,t1,t2;
	 RadioButton yes,no;
	
	 trackdb track_obj=new trackdb();
	 trackcontroller logic=new trackcontroller(this);
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.track);
			logic.openDB();
			value=getIntent().getStringExtra("username");
			name = (EditText)findViewById(R.id.editText1);
			from_date = (EditText)findViewById(R.id.editText2);
			to_date= (EditText)findViewById(R.id.editText3);
			yes=(RadioButton)findViewById(R.id.radioButton1);
			no=(RadioButton)findViewById(R.id.radioButton2);
			t1=(EditText)findViewById(R.id.editText4);
			t2=(EditText)findViewById(R.id.editText5);
			home=(Button)findViewById(R.id.button2);
			view=(Button)findViewById(R.id.button1);
			save=(Button)findViewById(R.id.button3);
			home.setOnClickListener(new OnClickListener(){

					public void onClick(View v) {
						Intent main = new Intent(track.this,MainActivity.class);
						startActivity(main);
					}
				});
		    view.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
			 
				Intent main = new Intent(track.this,Load.class);
				main.putExtra("username", value);
				main.putExtra("trackname", name.toString());
				startActivity(main);
			}
		});
		    save.setOnClickListener(this);
		}
				public void onClick(View v) {
				 track_obj.setuser(value);
				 track_obj.settrack(name.getText().toString());
				 track_obj.setfromdate(from_date.getText().toString());
				 track_obj.settodate(to_date.getText().toString());
				// String t1_time=Integer.toString(t1.getCurrentHour())+":"+Integer.toString(t1.getCurrentMinute());
				// String t2_time=Integer.toString(t2.getCurrentHour())+":"+Integer.toString(t2.getCurrentMinute());
				 track_obj.setfromtime(t1.getText().toString());
				 track_obj.settotime(t2.getText().toString());
				 if(yes.isChecked()){
					 track_obj.setrepeat("Yes");
					 //repeat();
				 }
				 else if(no.isChecked())
				 {
					 track_obj.setrepeat("No");
				 }
				 logic.addTrack(track_obj);
		          Toast.makeText(track.this,"Saved",Toast.LENGTH_LONG).show();
		          logic.close();	
				}
				public void repeat(String uname,String tname,String fromdate,String todate,String fromtime,String totime)
				{
					Date sch_from =new Date();
					Date sch_to=new Date();
					track_obj.setuser(uname);
					 track_obj.settrack(tname);
					 SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
	    	    	 
	                  try {
						 sch_from = df1.parse(fromdate);
						 sch_to = df1.parse(todate);
						
						} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                  Calendar c = Calendar.getInstance();
	                  c.setTime(sch_from);
	                  c.add(Calendar.DATE, 1);  // number of days to add
	                  
					 track_obj.setfromdate(df1.format(c.getTime())); 
					
	                  c.setTime(sch_to);
	                  c.add(Calendar.DATE, 1); 
					 track_obj.settodate(df1.format(c.getTime()));
					
					 track_obj.setfromtime(fromtime);
					 track_obj.settotime(totime);
					 track_obj.setrepeat("Yes");
					 logic.addTrack(track_obj);
			          Toast.makeText(track.this,"Repeated",Toast.LENGTH_LONG).show();
			          logic.close();	
				}

}
