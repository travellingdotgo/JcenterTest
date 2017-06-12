package com.bewant2be.doit.jcentertest.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bewant2be.doit.jcentertest.sqlite.UserInfoEntry.UserEntry;

/**
 * Created by user on 6/12/17.
 */
public class UserinfoDbHelper extends SQLiteOpenHelper {
    public final static String TAG = "UserinfoDbHelper";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "userinfo.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_USER + " TEXT," +
                    UserEntry.COLUMN_NAME_TIME + " TEXT," +
                    UserEntry.COLUMN_NAME_BINARY_FEATURE + " BLOB," +
                    UserEntry.COLUMN_NAME_BINARY_IMG + " BLOB)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public UserinfoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //  . . . .  . . . . biz operation below . . . .  . . . .
    public void insert( String user, String time, byte[] feature, byte[] image ){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_USER, user);
        values.put(UserEntry.COLUMN_NAME_TIME, time);
        values.put(UserEntry.COLUMN_NAME_BINARY_FEATURE, feature);
        values.put(UserEntry.COLUMN_NAME_BINARY_IMG, image);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);

        Log.w(TAG, "newRowId=" + newRowId);
    }


    public void queryAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT _id,user,time,feature,image FROM "+UserEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                //assing values
                String column1 = cursor.getString(0);
                String column2 = cursor.getString(1);
                String column3 = cursor.getString(2);
                byte[] feature = cursor.getBlob(3);
                byte[] image = cursor.getBlob(4);

                //Do something Here with values
                Log.w(TAG, "_id="+column1+", user="+column2+ ", time="+column3
                        + ", feature="+feature[0] + ", image="+image[0]);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void query(){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_NAME_USER,
                UserEntry.COLUMN_NAME_TIME,
                UserEntry.COLUMN_NAME_BINARY_FEATURE,
                UserEntry.COLUMN_NAME_BINARY_IMG
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserEntry.COLUMN_NAME_USER + " = ?";
        String[] selectionArgs = { "title." /*"My Title"*/ };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserEntry.COLUMN_NAME_TIME + " DESC";

        Cursor cursor = db.query(
                UserEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToFirst()){
            do{
                //assing values
                String column1 = cursor.getString(0);
                String column2 = cursor.getString(1);
                String column3 = cursor.getString(2);

                //Do something Here with values
                Log.w(TAG, "_id="+column1+", user="+column2+ ", time="+column3);
            }while(cursor.moveToNext());
        }

        cursor.close();
    }
}