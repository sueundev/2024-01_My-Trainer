package com.example.mytrainer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseLogDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "exerciseLog.db";
    private static final int DATABASE_VERSION = 1;

    public ExerciseLogDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE exercise_logs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "timestamp LONG, " +
                "upper_body_count TEXT, " +
                "lower_body_count TEXT, " +
                "date TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS exercise_logs");
        onCreate(db);
    }
}
