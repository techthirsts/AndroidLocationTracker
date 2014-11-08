package com.example.locationbasedservice;

public class Datas {
	 //private variables
    int _id;
    String _name;
    String _lat;
    String _longit;
    String _addr;
    String _date;
    String _time;

    // Empty constructor
    public Datas(){

    }
    // constructor
    public Datas(int id,  String _name,String lat, String longit, String _addr,String date, String time){
        this._id = id;
        this._lat = lat;
        this._longit = longit;
        this._addr = _addr;
        this._date = date;
        this._time = time;
    }

    // constructor
    public Datas( String _name,String lat, String longit, String _addr, String date, String time){
        this._lat = lat;
        this._longit = longit;
        this._addr = _addr;
        this._date = date;
        this._time = time;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    public String getuser(){
        return this._name;
    }

  
    public void setuser(String name){
        this._name = name;
    }
    public String getlat(){
        return this._lat;
    }

  
    public void setlat(String lat){
        this._lat = lat;
    }

 
    public String getlongit(){
        return this._longit;
    }
    public void setlongit(String longit){
        this._longit = longit;
    }
    public void setaddr(String _addr){
        this._addr = _addr;
    }

    
    public String getaddr(){
        return this._addr;
    }
    
    

   
    public String getDate(){
        return this._date;
    }

    
    public void setDate(String date){
        this._date = date;
    }

    
    public String getTime(){
        return this._time;
    }

  
    public void setTime(String time){
        this._time = time;
    }
}

