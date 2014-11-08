package com.example.locationbasedservice;

public class trackdb {
	 //private variables
    int _id;
    String _uname;
    String _tname;
    String _fromdate;
    String _todate;
    String _fromtime;
    String _totime;
    String _repeat;

    // Empty constructor
    public trackdb(){

    }
    // constructor
    public trackdb(int id,  String _uname,String _tname,String fromdate, String todate, String _fromtime,String _totime, String repeat){
        this._id = id;
        this._fromdate = fromdate;
        this._todate = todate;
        this. _fromtime= _fromtime;
        this._totime= _totime;
        this._repeat = repeat;
    }

    // constructor
    public trackdb( String _uname,String _tname,String fromdate, String todate, String  _fromtime, String _totime, String repeat){
        this._fromdate = fromdate;
        this._todate = todate;
        this. _fromtime = _fromtime;
        this._totime = _totime;
        this._repeat = repeat;
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
        return this._uname;
    }

  
    public void setuser(String name){
        this._uname = name;
    }
   
    public String gettrack(){
        return this._tname;
    }

  
    public void settrack(String name){
        this._tname = name;
    }
    public String getfromdate(){
        return this._fromdate;
    }
    public void setfromdate(String fromdate){
        this._fromdate = fromdate;
    }

 
    public String gettodate(){
        return this._todate;
    }
    public void settodate(String todate){
        this._todate = todate;
    }
    public void setfromtime(String  _fromtime){
        this. _fromtime =  _fromtime;
    }

    
    public String getfromtime(){
        return this. _fromtime;
    }
    
    

   
    public String gettotime(){
        return this._totime;
    }

    
    public void settotime(String _totime){
        this._totime = _totime;
    }

    
    public String getrepeat(){
        return this._repeat;
    }

  
    public void setrepeat(String repeat){
        this._repeat = repeat;
    }
}
