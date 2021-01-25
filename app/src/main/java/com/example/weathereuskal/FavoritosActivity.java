package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Utilidades.AdapterSimple;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity {

    private RecyclerView recyclerMuni;
    private RecyclerView recyclerEspa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        recyclerMuni = (RecyclerView) findViewById(R.id.recyclerFavMuni);
        recyclerEspa = (RecyclerView) findViewById(R.id.recyclerFavEspa);

        ArrayList<String> municipiosFavoritos = new ArrayList<String>();
        ArrayList<String> espaciosFavoritos = new ArrayList<String>();

        ConexionBD conexionMunicipios = new ConexionBD();
        conexionMunicipios.setConsulta("arraySelect");
        conexionMunicipios.setSentencia("SELECT Nombre from municipios where Id in (SELECT municipio from municipiosfavoritos where usuario = " +
                "(SELECT Id from usuario where Nombre = '" + getIntent().getExtras().getString("nombreUsuario") + "'))");
        conexionMunicipios.setColumna("Nombre");
        Thread thread = new Thread(conexionMunicipios);
        thread.start();
        try {
            thread.join(); // Esperar respusta del servidor...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ConexionBD conexionEspacios = new ConexionBD();
        conexionEspacios.setConsulta("arraySelect");
        conexionEspacios.setSentencia("SELECT Nombre from entornos where Id in (SELECT entorno from entornosfavoritos where usuario = " +
                "(SELECT Id from usuario where Nombre = '" + getIntent().getExtras().getString("nombreUsuario") + "'))");
        conexionEspacios.setColumna("Nombre");
        Thread hilo = new Thread(conexionEspacios);
        hilo.start();
        try {
            hilo.join(); // Esperar respusta del servidor...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        municipiosFavoritos = conexionMunicipios.getArrayResultados();
        espaciosFavoritos = conexionEspacios.getArrayResultados();

        AdapterSimple adapterMuni = new AdapterSimple(municipiosFavoritos);
        AdapterSimple adapterEspa = new AdapterSimple(espaciosFavoritos);

        recyclerMuni.setAdapter(adapterMuni);
        recyclerMuni.setLayoutManager(new LinearLayoutManager(this));
        recyclerEspa.setAdapter(adapterEspa);
        recyclerEspa.setLayoutManager(new LinearLayoutManager(this));
    }
}