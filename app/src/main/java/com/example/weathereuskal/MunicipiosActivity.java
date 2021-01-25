package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Objetos.EspacioNatural;
import com.example.weathereuskal.Objetos.Municipio;
import com.example.weathereuskal.Utilidades.AdapterSimple;


import java.util.ArrayList;

public class MunicipiosActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private Button botonBizkaia;
    private Button botonGipuzkoa;
    private Button botonAraba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipios);

        recycler = (RecyclerView) this.findViewById(R.id.recyclerProvincias);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        botonBizkaia = this.findViewById(R.id.botonBizkaia);
        botonGipuzkoa = this.findViewById(R.id.botonGipuzkoa);
        botonAraba = this.findViewById(R.id.botonAraba);

        if (getIntent().getExtras().getString("botonOrigen").equals("municipios")){

            ConexionBD clientThread = new ConexionBD();
            clientThread.setConsulta("selectArrayMunicipios");
            clientThread.setSentencia("SELECT * from municipios order by Nombre");

            Thread thread = new Thread(clientThread);
            thread.start();

            try {
                thread.join(); // Esperar respusta del servidor...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String> lista = new ArrayList<String>();
            ArrayList<Municipio> listaMunicipios = clientThread.getListaMunicipios();

            for (Municipio municipio : listaMunicipios){

                lista.add(municipio.getNombre());

            }

            AdapterSimple aS = new AdapterSimple(lista);
            recycler.setAdapter(aS);

            aS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent municipio = new Intent(MunicipiosActivity.this, DetallesLugarActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("botonOrigen", "municipios");
                    extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                    extras.putString("nombre", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getNombre());
                    extras.putString("descripcion", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getDescripcion());
                    extras.putDouble("latitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLatitud());
                    extras.putDouble("longitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLongitud());
                    municipio.putExtras(extras);
                    startActivity(municipio);

                }
            });

        } else if (getIntent().getExtras().getString("botonOrigen").equals("espacios")){

            ConexionBD clientThread = new ConexionBD();
            clientThread.setConsulta("selectArrayEspacios");
            clientThread.setSentencia("SELECT * from entornos order by Nombre");

            Thread thread = new Thread(clientThread);
            thread.start();

            try {
                thread.join(); // Esperar respusta del servidor...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String> lista = new ArrayList<String>();
            ArrayList<EspacioNatural> listaEspacios = clientThread.getListaEspacios();

            for (EspacioNatural espacioNatural : listaEspacios){

                lista.add(espacioNatural.getNombre());

            }

            AdapterSimple aS = new AdapterSimple(lista);
            recycler.setAdapter(aS);

            aS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent municipio = new Intent(MunicipiosActivity.this, DetallesLugarActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("botonOrigen", "entornos");
                    extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                    extras.putString("nombre", listaEspacios.get(recycler.getChildAdapterPosition(v)).getNombre());
                    extras.putString("descripcion", listaEspacios.get(recycler.getChildAdapterPosition(v)).getDescripcion());
                    extras.putDouble("latitud", listaEspacios.get(recycler.getChildAdapterPosition(v)).getLatitud());
                    extras.putDouble("longitud", listaEspacios.get(recycler.getChildAdapterPosition(v)).getLongitud());
                    municipio.putExtras(extras);
                    startActivity(municipio);

                }
            });

        }

    }

    public void clickBotonProvincias(View v) {

        String provincia = new String();

        switch (v.getId()) {
            case R.id.botonBizkaia:

                provincia = "bizkaia";

                break;
            case R.id.botonGipuzkoa:

                provincia = "gipuzkoa";

                break;
            case R.id.butonAraba:

                provincia = "araba/álava";

                break;
        }

        if (getIntent().getExtras().getString("botonOrigen").equals("municipios")) {

            ConexionBD clientThread = new ConexionBD();
            clientThread.setConsulta("selectArrayMunicipios");
            clientThread.setSentencia("SELECT * from municipios where Provincia = (SELECT Id from provincias where Nombre = '" + provincia + "') order by Nombre");

            Thread thread = new Thread(clientThread);
            thread.start();

            try {
                thread.join(); // Esperar respusta del servidor...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String> lista = new ArrayList<String>();
            ArrayList<Municipio> listaMunicipios = clientThread.getListaMunicipios();

            for (Municipio municipio : listaMunicipios) {

                lista.add(municipio.getNombre());

            }

            AdapterSimple aS = new AdapterSimple(lista);
            recycler.setAdapter(aS);
            aS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent municipio = new Intent(MunicipiosActivity.this, DetallesLugarActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("botonOrigen", "municipios");
                    extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                    extras.putString("nombre", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getNombre());
                    extras.putString("descripcion", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getDescripcion());
                    extras.putDouble("latitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLatitud());
                    extras.putDouble("longitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLongitud());
                    municipio.putExtras(extras);
                    startActivity(municipio);

                }
            });

        } else if (getIntent().getExtras().getString("botonOrigen").equals("espacios")) {

            ConexionBD clientThread = new ConexionBD();
            clientThread.setConsulta("selectArrayEspacios");
            clientThread.setSentencia("SELECT * from entornos where Territorio = '" + provincia + "' order by Nombre");

            Thread thread = new Thread(clientThread);
            thread.start();

            try {
                thread.join(); // Esperar respusta del servidor...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String> lista = new ArrayList<String>();
            ArrayList<EspacioNatural> listaEspacios = clientThread.getListaEspacios();

            for (EspacioNatural espacioNatural : listaEspacios) {

                lista.add(espacioNatural.getNombre());

            }

            AdapterSimple aS = new AdapterSimple(lista);
            recycler.setAdapter(aS);
            aS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent municipio = new Intent(MunicipiosActivity.this, DetallesLugarActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("botonOrigen", "espacios");
                    extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                    extras.putString("nombre", listaEspacios.get(recycler.getChildAdapterPosition(v)).getNombre());
                    extras.putString("descripcion", listaEspacios.get(recycler.getChildAdapterPosition(v)).getDescripcion());
                    extras.putDouble("latitud", listaEspacios.get(recycler.getChildAdapterPosition(v)).getLatitud());
                    extras.putDouble("longitud", listaEspacios.get(recycler.getChildAdapterPosition(v)).getLongitud());
                    municipio.putExtras(extras);
                    startActivity(municipio);

                }
            });

        }

    }

}