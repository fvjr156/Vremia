package com.ablsv.vremia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "TaskDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tasks_database";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_DAYOFMONTH = "dayofmonth";
    private static final String COLUMN_HOUR = "hour";
    private static final String COLUMN_MINUTE = "minute";
    private static final String COLUMN_IMAGE_DATA = "image_data";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME+" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_TITLE+" TEXT, "+COLUMN_DESCRIPTION+" TEXT, "+COLUMN_COLOR+" INTEGER, "+COLUMN_YEAR+" INTEGER, "+COLUMN_MONTH+" INTEGER, "+COLUMN_DAYOFMONTH+" INTEGER, "+COLUMN_HOUR+" INTEGER, "+COLUMN_MINUTE+" INTEGER, "+COLUMN_IMAGE_DATA+" BLOB);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addTask(String title, String description, int color,
                 int year, int month, int dayofmonth,
                 int hour, int minute,
                 String imageData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_MONTH, month);
        cv.put(COLUMN_DAYOFMONTH, dayofmonth);
        cv.put(COLUMN_HOUR, hour);
        cv.put(COLUMN_MINUTE, minute);
        cv.put(COLUMN_IMAGE_DATA, imageData);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "Failed to add the Task", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Task is added.", Toast.LENGTH_SHORT).show();
        }
    }

    void updateTask(String row_id, String title, String description, int color,
                    int year, int month, int dayofmonth,
                    int hour, int minute,
                    String imageData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_MONTH, month);
        cv.put(COLUMN_DAYOFMONTH, dayofmonth);
        cv.put(COLUMN_HOUR, hour);
        cv.put(COLUMN_MINUTE, minute);
        cv.put(COLUMN_IMAGE_DATA, imageData);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1)
        {
            Toast.makeText(context, "Failed to add the Task", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Task is added.", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }
}
