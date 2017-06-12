package com.bewant2be.doit.jcentertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bewant2be.doit.jcentertest.FeedReaderContract.FeedEntry;

import java.util.ArrayList;
import java.util.List;


public class SqliteActivity extends AppCompatActivity {

    public final static String TAG = "SqliteActivity";

    FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        mDbHelper = new FeedReaderDbHelper(getApplicationContext());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        long id = System.currentTimeMillis();

        /*
        byte[] feature = new byte[]{0x01,0x02};
        byte[] image = new byte[]{0x03,0x04};

        insert( "title."+id, "subtitle."+id, feature,image );
        insert("title.", "subtitle." + id, feature, image);
        */

        query();

        Log.w(TAG, " - - - - - - - - - - - - - - - - - -");

        queryAll();
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    private void insert( String title, String subtitle, byte[] feature, byte[] image ){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

        values.put(FeedEntry.COLUMN_NAME_BINARY_FEATURE, feature);
        values.put(FeedEntry.COLUMN_NAME_BINARY_IMG, image);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);

        Log.w(TAG, "newRowId=" + newRowId);
    }


    private void queryAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql = "SELECT _id,title,subtitle,feature,image FROM "+FeedEntry.TABLE_NAME;
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
                Log.w(TAG, "column1="+column1+", column2="+column2+ ", column3="+column3
                        + ", feature="+feature[0] + ", image="+image[0]);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    private void query(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_SUBTITLE,
                FeedEntry.COLUMN_NAME_BINARY_FEATURE,
                FeedEntry.COLUMN_NAME_BINARY_IMG
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "title." /*"My Title"*/ };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,                     // The table to query
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
                Log.w(TAG, "column1="+column1+", column2="+column2+ ", column3="+column3);
            }while(cursor.moveToNext());
        }

        cursor.close();
    }
}
