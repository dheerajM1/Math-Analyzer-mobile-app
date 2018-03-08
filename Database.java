package com.arnoldas.mathteacher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MathTeacher.db";

    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student (id  INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL," +
                "additionLevel INTEGER NOT NULL, subtractionLevel INTEGER NOT NULL,multiplicationLevel INTEGER NOT NULL,divisionLevel INTEGER NOT NULL, testTime INTEGER NOT NULL);");
        db.execSQL("CREATE TABLE exams (id INTEGER PRIMARY KEY AUTOINCREMENT, studentId INTEGER NOT NULL, date INTEGER," +
                "additionAttempted INTEGER NOT NULL, additionSucceeded INTEGER NOT NULL, additionLevel INT NOT NULL," +
                "subtractionAttempted INTEGER NOT NULL, subtractionSucceeded INTEGER NOT NULL, subtractionLevel INT NOT NULL," +
                "multiplicationAttempted INTEGER NOT NULL, multiplicationSucceeded INTEGER NOT NULL, multiplicationLevel INT NOT NULL," +
                "divisionAttempted INTEGER NOT NULL, divisionSucceeded INTEGER NOT NULL, divisionLevel INT NOT NULL);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS exams");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
