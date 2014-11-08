package com.example.locationbasedservice;



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Locationdb extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION =4;

    // Database Name
    private static final String DATABASE_NAME = "location_service1.db";

    // Contacts table name
    private static final String TABLE_DATAS = "tb_location";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String Name= "Name";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONG = "long";
    private static final String ADDRESS = "addr";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    public Locationdb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATAS_TABLE = "CREATE TABLE " + TABLE_DATAS + "(" 
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ Name +" TEXT, "+ KEY_LAT + " TEXT,"
                + KEY_LONG + " TEXT," + ADDRESS + " TEXT,"+ KEY_DATE + " TEXT," + KEY_TIME + " TEXT" + ")";
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

   

  
    void addData(Datas data) {
        SQLiteDatabase db = this.getWritableDatabase();
       
        ContentValues values = new ContentValues();
        values.put("Name", data.getuser());
        values.put(KEY_LAT, data.getlat()); 
        values.put(KEY_LONG, data.getlongit());
        values.put(ADDRESS, data.getaddr()); 
        values.put(KEY_DATE, data.getDate()); 
        values.put(KEY_TIME, data.getTime()); 
        
        // Inserting Row
        db.insert(TABLE_DATAS, null, values);
        db.close(); // Closing database connection
    }

    public void openDB() throws SQLException{
		this.getWritableDatabase();
    }
   Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DATAS, new String[] { KEY_ID,"Name",
                KEY_LAT, KEY_LONG, ADDRESS,KEY_DATE, KEY_TIME}, null,null, null, null, null, null);
        if (cursor != null)
        {
        cursor.moveToFirst();
        }
        return cursor;
    }


    
   /* public List<Datas> getAllDatas() {
        List<Datas> dataList = new ArrayList<Datas>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Datas data = new Datas();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setlat(cursor.getString(1));
                data.setlongit(cursor.getString(2));
                data.setaddr(cursor.getString(3));
                data.setDate(cursor.getString(4));
                data.setTime(cursor.getString(5));
             
                dataList.add(data);
            } while (cursor.moveToNext());
        }

    
        return dataList;
    }*/

    // Updating single contact
  /*  public void updateDatas(int position, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "UPDATE datas SET comment = '"+ value +"' WHERE ID = " + position;
        db.execSQL(update);

    }

    // Deleting single contact
        public void deleteValues(int position) {
            SQLiteDatabase db = this.getWritableDatabase();
            position = position + 1;
            String id = String.valueOf(position);
            db.delete(TABLE_DATAS, KEY_ID + "="+id,null);
            //Updating table
            //Creating temporary table

            String CREATE_TABLE_COPY = "CREATE TABLE " + "COPIED_TABLE" + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LAT+ " TEXT,"
                    + KEY_LONG + " TEXT,"  + ADDRESS + " TEXT,"+ KEY_DATE + " TEXT," + KEY_TIME + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_COPY);
            // Copying necessary columns to new temporary table
            String db_insert_command;
            db_insert_command = "INSERT INTO COPIED_TABLE (" + KEY_ITEM +", " + KEY_COMMENT + ", " + KEY_DATE + ", " + KEY_TIME + ") SELECT " + KEY_ITEM +", " + KEY_COMMENT + ", " + KEY_DATE + ", " + KEY_TIME + " FROM "+ TABLE_DATAS;
            System.out.println(db_insert_command);
            db.execSQL(db_insert_command);
            //Dropping old table
            db.execSQL("DROP TABLE " + TABLE_DATAS);
            //Creating old table again
            String CREATE_TABLE = "CREATE TABLE " + TABLE_DATAS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ITEM + " TEXT,"
                    + KEY_COMMENT + " TEXT," + KEY_DATE + " TEXT," + KEY_TIME + " TEXT"  + ")";
            db.execSQL(CREATE_TABLE);
            //Copying all fields from temporary table to newly created old table
            db.execSQL("INSERT INTO " + TABLE_DATAS + " SELECT * FROM COPIED_TABLE");
            //Dropping temporary table
            db.execSQL("DROP TABLE COPIED_TABLE");
            db.close();
        }
*/

   
    public int getDatasCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

}