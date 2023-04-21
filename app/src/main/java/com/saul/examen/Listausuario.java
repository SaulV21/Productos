package com.saul.examen;

import static com.saul.examen.BdSupermercado.tblname;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class Listausuario extends AppCompatActivity {
BdSupermercado bdSupermercado;
SQLiteDatabase sqLiteDatabase;
RecyclerView recyclerView;
MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listausuario);
        bdSupermercado=new BdSupermercado(this);
        findId();
        Listar();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    private void Listar() {
        sqLiteDatabase=bdSupermercado.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+tblname+"",null);
        ArrayList<Model> models=new ArrayList<>();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);

            String name=cursor.getString(1);
            String precio=cursor.getString(2);
            String codigo=cursor.getString(3);
            byte[] foto=cursor.getBlob(4);
            models.add(new Model(id,foto,name,precio,codigo));
        }
        cursor.close();
        myAdapter=new MyAdapter(this, R.layout.listasimple,models,sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }

    private void findId() {
        recyclerView=findViewById(R.id.rv);
    }
}