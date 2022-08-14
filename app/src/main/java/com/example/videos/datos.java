package com.example.videos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class datos extends AppCompatActivity {
    EditText nombre,edad;
    Button registrar,leerb,eliminar,update;
    TextView contenidob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        nombre=(EditText)findViewById(R.id.nombre);
        edad=(EditText)findViewById(R.id.edad);
        registrar=(Button)findViewById(R.id.guardar);
        leerb=(Button)findViewById(R.id.leerb);
        eliminar=(Button)findViewById(R.id.eliminar);
        update=(Button)findViewById(R.id.update);
        contenidob=(TextView)findViewById(R.id.contenidob);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().equals("")&&!edad.getText().toString().equals(""))
                    GuardarRegistro(nombre.getText().toString(),edad.getText().toString());
                else
                    Toast.makeText(getApplicationContext(),"Debes capturar en ambos campos",Toast.LENGTH_SHORT).show();
            }
        });
        leerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeerRegistros();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarRegistro(nombre.getText().toString());
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aztualizarRegistro(nombre.getText().toString(),edad.getText().toString());
            }
        });
    }

    private void aztualizarRegistro(String nombres, String materias) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this,"UniversidadBD",null,1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        ContentValues registro= new ContentValues();
        registro.put("materia",materias);
        basedatos.update("alumnos",registro,"nombre='"+nombres+"'",null);
        basedatos.close();
        nombre.setText("");
        edad.setText("");
    }

    private void eliminarRegistro(String nombres) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this,"UniversidadBD",null,1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        basedatos.delete("alumnos","nombre='"+nombres+"'",null);
        basedatos.close();
        Toast.makeText(getApplicationContext(),"Registro eliminado con exito!!",Toast.LENGTH_SHORT).show();
        nombre.setText("");
    }

    private void LeerRegistros() {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this,"UniversidadBD",null,1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        try{
            Cursor cursor = basedatos.rawQuery("SELECT * FROM alumnos",null);
            String cont="";
            while (cursor.moveToNext()){
                cont+="Nombre :"+cursor.getString(1)+"       Materia:"+cursor.getString(2)+"\n";
            }
            cursor.close();
            contenidob.setText(cont);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"ERROR:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void GuardarRegistro(String nombres, String materias) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(getApplicationContext(),"UniversidadBD",null,1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre",nombres);
        registro.put("edad",materias);
        basedatos.insert("alumnos",null,registro);
        basedatos.close();
        Toast.makeText(getApplicationContext(),"Registro Insertado con exito",Toast.LENGTH_SHORT).show();
        nombre.setText("");
        edad.setText("");

    }

}