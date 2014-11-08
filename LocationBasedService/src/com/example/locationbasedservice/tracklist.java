package com.example.locationbasedservice;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class tracklist extends Activity implements View.OnClickListener{
	TableLayout table_layout;
	//LinearLayout linear_layout,parent;
    int cols = 7;
	  String name;
	   //Date sch_from,sch_to,time_from, time_to,d,t;
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.tracklist);
     name = getIntent().getStringExtra("username");
         
      final trackcontroller t_db = new trackcontroller(this);
   //  parent = (LinearLayout) findViewById(R.id.linearlayout1);
      table_layout = (TableLayout) findViewById(R.id.tableLayout1);
      //linear_layout = (LinearLayout) findViewById(R.id.linearlayout2);
      	  int rows = t_db.getDatasCount();
      	
      	  Toast.makeText(this,"Row count "+rows+" "+name,Toast.LENGTH_SHORT).show();
            	  // outer for loop

      	 
      	  t_db.openDB();
      	  
            Cursor c1=t_db.getData();
           
            
      	  String[] Fields=new String[]{ "Trackname","FromDate","ToDate","FromTime","ToTime","Repeat" };
      	 
        	//c1 = t_db.getString(null);
      	  c1.moveToFirst();
      	  TableRow row = new TableRow(this);
      	   row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
      	     LayoutParams.WRAP_CONTENT));
      	   for (int j =0; j <6; j++) {

	        	    TextView tv = new TextView(this);
	        	    tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	        	      LayoutParams.MATCH_PARENT));
	        	  //  tv.setMovementMethod(LinkMovementMethod.getInstance());
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
      	
      	      for (int j = 2; j <= cols; j++) {
              
      	    TextView tv = new TextView(this);
      	    tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
      	      LayoutParams.MATCH_PARENT));
      	    tv.setBackgroundColor(Color.TRANSPARENT);
      	    tv.setGravity(Gravity.CENTER);
      	    tv.setTextSize(18);
      	    tv.setPadding(0, 0, 0, 0);

      	    tv.setText(c1.getString(j));
            final String trackname=c1.getString(1);
            final String repeat=c1.getString(7);
            final Cursor cur=c1;
      	    row.addView(tv);
      	  tv.setOnClickListener(new OnClickListener(){
          
  			public void onClick(View v) {
  			   
  				Intent main = new Intent(tracklist.this,Load.class);
  				main.putExtra("username", name);
  				main.putExtra("trackname", trackname);
  				startActivity(main);
  				/*if(repeat.equals("Yes"))
  				{	 
  		            repeat(cur.getString(1),cur.getString(2),cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6));
  				}*/
    			}
  		   });
      	  
      	   }
      	   c1.moveToNext();

      	   table_layout.addView(row);

      	  }
      	row = new TableRow(this);
   	   row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
   	     LayoutParams.WRAP_CONTENT));
      	Button b = new Button(this);
  	    b.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
        	      LayoutParams.WRAP_CONTENT));
  	    b.setText("Add");
  	    row.addView(b);
  	    table_layout.addView(row);
  	  
  	    b.setOnClickListener(this);
      	 t_db.close();
     }
	public void onClick(View v) {
		 
		Intent main = new Intent(tracklist.this,track.class);
		main.putExtra("username", name);
		//main.putExtra("trackname", name.toString());
		startActivity(main);
	}
}
