package com.example.locationbasedservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbLogic extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "location_service.db";
	private static final int DATABSE_VERSION = 2;
	
	private static final String TABLE_NAME = "tb_user_names";
	
	private SQLiteDatabase database;

	public DbLogic(Context context) {
		super(context, DATABASE_NAME, null, DATABSE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL (" CREATE TABLE "+ TABLE_NAME + " ( " + "_id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT NOT NULL,username  TEXT NOT NULL,password TEXT NOT NULL,confirm TEXT NOT NULL );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// db.execSQL (" CREATE TABLE " + "new_ table"+ " ( " + "_id INTEGER PRIMARY KEY AUTHENTICATION , names TEXT NOT NULL,
		// numbers  TEXT NOT NULL );");
		
		// Alter or modify the data. .. 
		onCreate(db);
	}
	
	public void openDB() throws SQLException{
		database = getWritableDatabase();
	}
	
	public void closeDB()
	{
		database.close();
	}
	
	
	public long insertQuery ( ContentValues cValues ){
		 
		return database.insert(TABLE_NAME, null, cValues);
				
	}
	
	public int updateQuery(ContentValues cValues)
	{
		String WHERE = "_id = " + cValues.getAsString("_id");//_id = 1
		return database.update(TABLE_NAME, cValues, WHERE,null);
		
		
	}
	

	public int deleteQuery(String id){
		String WHERE = "_id = " + id;
		return database.delete(TABLE_NAME, WHERE, null);
	}
	
	public Cursor getString (String[] columnsNames){
		
		Cursor cursor = database.query(TABLE_NAME, columnsNames, null, null, null, null,null);
		return cursor;
		
	}

}
