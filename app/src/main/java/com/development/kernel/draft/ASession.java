package com.development.kernel.draft;

/**
 * Created by Acer on 11.12.2016.
 */


class ASession {

    private int _id;
    private String _session;
    //Date _date;

    ASession(){
    }

    ASession(int id, String session){
        this._id = id;
        this._session = session;
       // this._date = _date;
    }


    int getID(){
        return this._id;
    }

    void setID(int id){
        this._id = id;
    }

    String getSession(){
        return this._session;
    }

    void setSession(String session){
        this._session = session;
    }

   // public Date getDate(){
   //     return this._date;
   // }

   // public void setDate(Date date){
   //     this._date = date;
   // }

}