package com.example.locationbasedservice;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

	public class Load extends Activity {

		TableLayout table_layout;
		  int cols = 5;
		  String name,tname;
		  int flag=0,year, month, day, hourOfDay, minute;
		  Date sch_from,sch_to,time_from, time_to,d,t;
		  long time1,time2,time3;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_list);
	        name = getIntent().getStringExtra("username");
	        tname = getIntent().getStringExtra("trackname");
	        final Locationdb db = new Locationdb(this);
	        final trackcontroller t_db = new trackcontroller(this);
	        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
	        	  int rows = db.getDatasCount();
	        	
	        	  Toast.makeText(this,"Row count "+rows+" "+name,Toast.LENGTH_SHORT).show();
	              	  // outer for loop

	        	  db.openDB();
	        	  t_db.openDB();
	        	  Cursor c = db.getData();
                  Cursor c1=t_db.getData();
                  c1.moveToFirst();
                  while (c1.isAfterLast() == false) {
                	//  Toast.makeText(this,"Cursor "+c1.getString(2),Toast.LENGTH_SHORT).show();
                  if(c1.getString(2).equals(tname)){
                	//  Toast.makeText(this,"Cursor "+c1.getString(2),Toast.LENGTH_SHORT).show();
                	 break;
                    }
                  else
                	  c1.moveToNext();
                  }
                c1.moveToPrevious();
             //   Toast.makeText(this,"Cursor "+c1.getString(1),Toast.LENGTH_SHORT).show();
    	    	  SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
    	    	  SimpleDateFormat df2 = new SimpleDateFormat("HH:mm",Locale.getDefault());
                  try {
					sch_from = df1.parse(c1.getString(3));
					 sch_to = df1.parse(c1.getString(4));
					time_from = df2.parse(c1.getString(5));
					time_to = df2.parse(c1.getString(6));
					d= df1.parse(c.getString(5));
					t=df2.parse(c.getString(6));
					} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                  
	        	  String[] Fields=new String[]{ "Username","Latitude","Longititude","Address","Date","Time" };
	        	 
	        	  Date cal = Calendar.getInstance().getTime();
	        	  
	        	/*  year=cal.YEAR;
	        	  month=cal.MONTH;
	        	  day=cal.DATE;
	        	  hourOfDay=cal.HOUR_OF_DAY;
	        	  minute=cal.MINUTE;
	              cal.set(year, month, day, hourOfDay, minute);*/
	            cal.setYear(d.getYear()+1900);
	            cal.setMonth(d.getMonth()+1);
	            cal.setTime(t.getTime());
	            time1=cal.getTime();
	            time2=sch_from.getTime();
	            time3=sch_to.getTime();
	        	  c.moveToFirst();
	        	  TableRow row = new TableRow(this);
	        	   row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	        	     LayoutParams.WRAP_CONTENT));
	        	   for (int j =0; j < 6; j++) {

		        	    TextView tv = new TextView(this);
		        	    tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		        	      LayoutParams.MATCH_PARENT));
		        	   
		        	    tv.setBackgroundColor(Color.LTGRAY);
		        	    tv.setGravity(Gravity.CENTER);
		        	    tv.setTextSize(18);
		        	    tv.setPadding(0, 0, 0, 0);
                        tv.setTextColor(Color.RED);
		        	    tv.setText(Fields[j]);

		        	    row.addView(tv);

		        	   }
		        	   table_layout.addView(row);

	        	  // outer for loop
	        	  for (int i = 0; i < rows; i++) {

	        	   row = new TableRow(this);
	        	   row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	        	     LayoutParams.WRAP_CONTENT));
	        	//   Toast.makeText(this,"username "+c.getString(1),Toast.LENGTH_SHORT).show();
	              	  // outer for loop
	        	   if(c.getString(1).equals(name) && c1.getString(1).equals(name))
	        	   {
	        	   // inner for loop
	        	   Toast.makeText(this,"year "+ cal.getYear(),Toast.LENGTH_SHORT).show();
	        	   Toast.makeText(this,"month "+ cal.getMonth(),Toast.LENGTH_SHORT).show();
	        	  Toast.makeText(this,"  date "+cal.getDate(),Toast.LENGTH_SHORT).show();
	        	//  Toast.makeText(this,"date_track "+c.getString(5),Toast.LENGTH_SHORT).show();
	         //    Toast.makeText(this,"  time _track"+c.getString(6),Toast.LENGTH_SHORT).show();
	        	    if( (d.after(sch_from) || d.equals(sch_from))&& (d.before(sch_to) ||d.equals(sch_to)) )
	        	    {	
	        	   Toast.makeText(this,"inside date "+ "t:" + t + "time_from:"+time_from + "time1:"+time1 + "time2:"+ time2 + t.compareTo(time_from),Toast.LENGTH_LONG).show();
	        	    if(time1 >= time2){
	        	    	Toast.makeText(this,"inside fromdate "+ d.getDate(),Toast.LENGTH_SHORT).show();
	        	    if(time1 <= time3)
	        		{
	        	    	Toast.makeText(this,"inside time "+ t.getHours(),Toast.LENGTH_SHORT).show();
	        	    flag=1;
	        	      for (int j = 1; j <=6 ; j++) {
                    
	        	    TextView tv = new TextView(this);
	        	    tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	        	      LayoutParams.MATCH_PARENT));
	        	   
	        	    tv.setBackgroundColor(Color.TRANSPARENT);
	        	    tv.setGravity(Gravity.CENTER);
	        	    tv.setTextSize(18);
	        	    tv.setPadding(0, 0, 0, 0);

	        	    tv.setText(c.getString(j));

	        	    row.addView(tv);
	        	     }
	        		}
	        	   }
	        	   }
	        	   }
	        	   c.moveToNext();

	        	   table_layout.addView(row);

	        	  }
	        	  if(flag==0)
	        	  {
	        		  row = new TableRow(this);
		        	   row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		        	     LayoutParams.WRAP_CONTENT));
		        		   TextView tv = new TextView(this);
			        	    tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
			        	      LayoutParams.MATCH_PARENT));
			        	   
			        	    tv.setBackgroundColor(Color.TRANSPARENT);
			        	    tv.setGravity(Gravity.CENTER);
			        	    tv.setTextSize(18);
			        	    tv.setPadding(0, 0, 0, 0);

			        	    tv.setText("No Record");

			        	    row.addView(tv);
			        	    table_layout.addView(row);
	        	  }
	        	 db.close();
	       }
}

