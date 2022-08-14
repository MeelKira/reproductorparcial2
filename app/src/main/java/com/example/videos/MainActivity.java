package com.example.videos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button guardar, leer, externo, bases;
    TextView contenido;
    EditText captura;
    String nombreArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guardar = (Button) findViewById(R.id.guardar);
        leer = (Button) findViewById(R.id.leer);
        bases = (Button) findViewById(R.id.bases);
        externo = (Button) findViewById(R.id.externo);
        captura = (EditText) findViewById(R.id.captura);
        contenido = (TextView) findViewById(R.id.contenido);
        nombreArchivo = "archivo.txt";
        bases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), datos.class);
                startActivity(intent);
            }
        });
        externo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExernoStorage.class);
                startActivity(intent);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String capturado = captura.getText().toString();
                if (!capturado.equals("")) {
                    GuardarCaptura(capturado);
                } else {
                    Toast.makeText(getApplicationContext(), "Error no haz capturado nada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeerArchivo();
            }
        });
    }

    private void LeerArchivo() {
        String textoffile = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(nombreArchivo)));
            textoffile = bufferedReader.readLine();
            bufferedReader.close();
            if (textoffile != null) {
                contenido.setText(textoffile);
                Toast.makeText(getApplicationContext(), "Contenido Leido con exito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "El Archivo esta vacio...", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void GuardarCaptura(String capturado) {

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
            if (fos != null) {
                fos.write(capturado.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            if (fos != null) {
                fos.close();
                captura.setText("");
                Toast.makeText(getApplicationContext(), "Contenido guardado con exito", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
