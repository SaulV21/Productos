package com.saul.examen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdSupermercado extends SQLiteOpenHelper {
    public static final String Bdnombre="Tienda.db";
    public static final String tblname="productos";
    public static final int VERSION=1;
    public BdSupermercado(@Nullable Context context) {
        super(context, Bdnombre, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String query="create table "+tblname+"(id_usuario integer primary key," +
            "nombre text, precio text, codigo text, foto blob)";
    db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String query="drop table if exists "+tblname+"";
    db.execSQL(query);
    }
}
