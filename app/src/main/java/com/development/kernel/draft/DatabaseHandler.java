package com.development.kernel.draft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "successSessions";
    private static final String TABLE_SESSIONS = "sessions";
    private static final String KEY_ID = "id";
    private static final String KEY_SESSION = "session";
   // private static final String KEY_DATE = "session_date";

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SESSIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SESSION + " TEXT)";
              //  + KEY_DATE + " DATETIME" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);

        onCreate(db);
    }

    @Override
    public void addSession(ASession asession) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SESSION, asession.getSession());
        //values.put(KEY_DATE, asession.getDate());
        db.insert(TABLE_SESSIONS, null, values);
        db.close();
    }

    @Override
    public List<ASession> getAllSessions() {
        List<ASession> sessionList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SESSIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ASession asession = new ASession();
                asession.setID(Integer.parseInt(cursor.getString(0)));
                asession.setSession(cursor.getString(1));
               // asession.setDate(cursor.);
                sessionList.add(asession);
            } while (cursor.moveToNext());
        }

        return sessionList;
    }

    @Override
    public void deleteSession(ASession asession) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESSIONS, KEY_ID + " = ?", new String[] { String.valueOf(asession.getID()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESSIONS, null, null);
        db.close();
    }
}
