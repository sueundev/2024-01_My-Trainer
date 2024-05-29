package com.example.mytrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExerciseLogDao {
    private ExerciseLogDatabaseHelper dbHelper;

    public ExerciseLogDao(Context context) {
        dbHelper = new ExerciseLogDatabaseHelper(context);
    }

    public void insertLog(ExerciseLog log) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("timestamp", log.getTimestamp());
        values.put("upper_body_count", log.getUpperBodyCount());
        values.put("lower_body_count", log.getLowerBodyCount());
        values.put("date", log.getDate());

        db.insert("exercise_logs", null, values);
        db.close();
    }

    public List<ExerciseLog> getLogsByDate(String date) {
        List<ExerciseLog> logs = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("exercise_logs", null, "date=?", new String[]{date}, null, null, "timestamp DESC");

        if (cursor.moveToFirst()) {
            do {
                long timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                String upperBodyCount = cursor.getString(cursor.getColumnIndex("upper_body_count"));
                String lowerBodyCount = cursor.getString(cursor.getColumnIndex("lower_body_count"));

                logs.add(new ExerciseLog(timestamp, upperBodyCount, lowerBodyCount, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return logs;
    }
}
