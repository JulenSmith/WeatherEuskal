package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Objetos.Foto;
import com.example.weathereuskal.Objetos.Municipio;
import com.example.weathereuskal.Utilidades.AdapterGaleria;
import com.example.weathereuskal.Utilidades.AdapterGrid;
import com.example.weathereuskal.Utilidades.AdapterSimple;

import java.io.InputStream;
import java.sql.Array;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {

    private RecyclerView galeria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        ArrayList<Foto> listaFotos = new ArrayList<Foto>();


        galeria = (RecyclerView) this.findViewById(R.id.recyclerGaleria);


        ConexionBD clientThread = new ConexionBD();
        clientThread.setConsulta("selectFotos");
        clientThread.setSentencia("SELECT Nombre, Foto from fotos INNER JOIN usuario on usuario.Id = fotos.Usuario order by fotos.Id desc");

        Thread thread = new Thread(clientThread);
        thread.start();

        try {
            thread.join(); // Esperar respusta del servidor...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listaFotos = clientThread.getListaImagenes();
        AdapterGaleria aG = new AdapterGaleria(listaFotos);

        galeria.setAdapter(aG);
        galeria.setLayoutManager(new LinearLayoutManager(this));

    }
}