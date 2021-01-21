package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Objetos.Municipio;
import com.example.weathereuskal.Utilidades.AdapterSimple;
import com.example.weathereuskal.Ventanas.EspaciosNaturales;

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

                Bundle mBundle = new Bundle();
                mBundle.putString("nombre", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getNombre());
                mBundle.putString("descripcion", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getDescripcion());
                mBundle.putDouble("latitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLatitud());
                mBundle.putDouble("longitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLongitud());
                municipio.putExtras(mBundle);
                startActivity(municipio);

            }
        });

    }

    public void clickBotonProvincias(View v){

        String provincia = new String();

        switch (v.getId()){
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

        for (Municipio municipio : listaMunicipios){

            lista.add(municipio.getNombre());

        }

        AdapterSimple aS = new AdapterSimple(lista);
        recycler.setAdapter(aS);
        aS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent municipio = new Intent(MunicipiosActivity.this, DetallesLugarActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putString("nombre", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getNombre());
                mBundle.putString("descripcion", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getDescripcion());
                mBundle.putDouble("latitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLatitud());
                mBundle.putDouble("longitud", listaMunicipios.get(recycler.getChildAdapterPosition(v)).getLongitud());
                municipio.putExtras(mBundle);
                startActivity(municipio);

            }
        });

    }

}