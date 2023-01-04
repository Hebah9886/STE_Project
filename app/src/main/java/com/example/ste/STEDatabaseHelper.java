package com.example.ste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class STEDatabaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASENAME = "ste.db";
    public static final String TABLENAME = "customers";
    public static final String COL1 = "ID", COL2 = "SERVICES", COL3 = "TYPE", COL4 = "ADDRESS";

    //this is the constructor
    public STEDatabaseHelper(Context context)
    {
        super(context, DATABASENAME, null, 1);
    }

    //this function will create tha database according to the specified name
    public void onCreate(SQLiteDatabase ste)
    {
        ste.execSQL("create table " + TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, SERVICES TEXT, TYPE TEXT, ADDRESS TEXT)");
    }

    //this function has the version number and will upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase ste, int oldVersion, int newVersion)
    {
        ste.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(ste);
    }

    //this function will take the data and inset it in tha table
    public boolean insertData(String service, String type, String address)
    {
        //set the data
        SQLiteDatabase ste = this.getReadableDatabase();
        ContentValues CValues = new ContentValues();
        CValues.put(COL2, service);
        CValues.put(COL3, type);
        CValues.put(COL4, address);

        //check if the data inserted or not
        long result = ste.insert(TABLENAME, null, CValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //this function will update the table according id specified
    public boolean updateData(String id, String service, String type, String address)
    {
        //set the data in columns
        SQLiteDatabase ste = this.getWritableDatabase();
        ContentValues CValues = new ContentValues();

        //update data
        CValues.put(COL1, id);
        CValues.put(COL2, service);
        CValues.put(COL3, type);
        CValues.put(COL4, address);

        //update the data according to id
        ste.update(TABLENAME, CValues, "ID=?", new String[]{id});

        //return true if the data updated
        return true;
    }

    //this function to delete the record based on id
    public Integer deleteData(String id)
    {
        SQLiteDatabase ste = this.getWritableDatabase();
        return ste.delete(TABLENAME, "ID=?", new String[]{id});
    }

    //this function will return all the data in the table
    public Cursor getAllData()
    {
        SQLiteDatabase ste = this.getWritableDatabase();
        Cursor r = ste.rawQuery("select * from " + TABLENAME, null);
        return r;
    }
}

