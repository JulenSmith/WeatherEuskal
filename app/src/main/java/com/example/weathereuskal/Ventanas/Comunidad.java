package com.example.weathereuskal.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Objetos.Provincia;
import com.example.weathereuskal.R;
import com.example.weathereuskal.Utilidades.AdapterSimple;
import com.example.weathereuskal.Utilidades.RecyclerAdapter;

import java.util.ArrayList;

public class Comunidad extends AppCompatActivity {

    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad);

        recycler = (RecyclerView) findViewById(R.id.recyclerProvincias);

        ConexionBD clientThread = new ConexionBD();
        clientThread.setConsulta("arraySelect");
        clientThread.setSentencia("SELECT distinct Provincia from estaciones");
        clientThread.setColumna("Provincia");

        Thread thread = new Thread(clientThread);
        thread.start();

        try {
            thread.join(); // Esperar respusta del servidor...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        AdapterSimple aS = new AdapterSimple(clientThread.getArrayResultados());
        recycler.setAdapter(aS);

        Toast.makeText(this, clientThread.getArrayResultados().get(0), Toast.LENGTH_SHORT).show();
    }
}