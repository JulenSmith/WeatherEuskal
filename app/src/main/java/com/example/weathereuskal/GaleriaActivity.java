package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;

import com.example.weathereuskal.Conexion.ConexionBD;
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
    private GridView gridGaleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        ArrayList<Bitmap> listaFotosBitmap = new ArrayList<Bitmap>();
        AdapterGaleria aG = new AdapterGaleria(listaFotosBitmap);

        galeria = (RecyclerView) this.findViewById(R.id.recyclerGaleria);
        galeria.setAdapter(aG);

        ConexionBD clientThread = new ConexionBD();
        clientThread.setConsulta("selectFotos");
        clientThread.setSentencia("SELECT Foto from fotos order by Id desc");

        Thread thread = new Thread(clientThread);
        thread.start();

        try {
            thread.join(); // Esperar respusta del servidor...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Blob> listaFotos = clientThread.getListaImagenes();


        for (Blob foto: listaFotos){
            try {
                int blobLength = 0;
                blobLength = (int) foto.length();
                byte[] blobAsBytes = foto.getBytes(1, blobLength);
                Bitmap bm = BitmapFactory.decodeByteArray(blobAsBytes, 0 ,blobAsBytes.length);
                listaFotosBitmap.add(bm);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        aG.notifyDataSetChanged();
        galeria.setLayoutManager(new LinearLayoutManager(this));

    }
}