package com.example.locationbasedservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class trackcontroller extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION =4;

    // Database Name
    private static final String DATABASE_NAME = "track_service.db";

    // Contacts table name
    private static final String TABLE_DATAS = "tb_track";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String Name= "Name";
    private static final String tName= "TrackName";
    private static final String KEY_fromdate = "fromdate";
    private static final String KEY_todate = "todate";
    private static final String KEY_fromtime = "fromtime";
    private static final String KEY_totime = "totime";
    private static final String KEY_repeat = "repeat";
    
     public trackcontroller(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATAS_TABLE = "CREATE TABLE " + TABLE_DATAS + "(" 
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ Name +" TEXT, "+ tName +" TEXT, "+KEY_fromdate + " TEXT,"
                + KEY_todate + " TEXT," + KEY_fromtime + " TEXT,"+ KEY_totime + " TEXT," + KEY_repeat + " TEXT" + ")";
        db.execSQL(CREATE_DATAS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATAS);

        // Create tables again
        onCreate(db);
    }

    public void openDB() throws SQLException{
		this.getWritableDatabase();
    }
    public void closeDB()
	{
		this.close();
	}
    void addTrack(trackdb data) {
        SQLiteDatabase db = this.getWritableDatabase();
       
        ContentValues values = new ContentValues();
        values.put("Name", data.getuser());
        values.put("TrackName", data.gettrack());
        values.put(KEY_fromdate, data.getfromdate()); 
        values.put(KEY_todate, data.gettodate());
        values.put(KEY_fromtime, data.getfromtime()); 
        values.put(KEY_totime, data.gettotime()); 
        values.put(KEY_repeat, data.getrepeat()); 
        
        // Inserting Row
        db.insert(TABLE_DATAS, null, values);
        db.close(); // Closing database connection
    }

  
   Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DATAS, new String[] { KEY_ID,Name,tName,
                KEY_fromdate, KEY_todate, KEY_fromtime,KEY_totime, KEY_repeat}, null,null, null, null, null, null);
        if (cursor != null)
        {
        cursor.moveToFirst();
        }
        return cursor;
    }
   public Cursor getString (String[] columnsNames){
	   SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_DATAS, new String[] { KEY_ID,Name,tName,
                KEY_fromdate, KEY_todate, KEY_fromtime,KEY_totime, KEY_repeat}, null, null, null, null,null);
		return cursor;
		
	}

  

   
    public int getDatasCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


}
