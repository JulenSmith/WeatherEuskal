package com.example.weathereuskal.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.weathereuskal.Objetos.EspacioNatural;
import com.example.weathereuskal.R;
import com.example.weathereuskal.Utilidades.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class EspaciosNaturales extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recycler;
    private ArrayList<EspacioNatural> lenBizkaia;
    private ArrayList<EspacioNatural> lenAraba;
    private ArrayList<EspacioNatural> lenGuipuzkua;
    private ArrayList<EspacioNatural> len;
    private ArrayList<EspacioNatural> lenVacio;
    private Button botonBizkaia;
    private Button botonAraba;
    private Button botonGuipuzkoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacios_naturales);

        botonBizkaia = (Button) findViewById(R.id.botonVizcaya);
        botonAraba = (Button) findViewById(R.id.botonAraba);
        botonGuipuzkoa = (Button) findViewById(R.id.botonGuipuzkoa);
        
        lenVacio = new ArrayList<EspacioNatural>();
        EspacioNatural on = new EspacioNatural();
        lenVacio.add(on);

        lenBizkaia = new ArrayList<EspacioNatural>();
        for (int i = 0; i < 50; i++) {
            EspacioNatural EN = new EspacioNatural();
            lenBizkaia.add(EN);
            lenBizkaia.get(i).setNombre("Muskiz");
            lenBizkaia.get(i).setCalidad("Buena");
            lenBizkaia.get(i).setMunicipio("Bilbao");
        }


        lenGuipuzkua = new ArrayList<EspacioNatural>();
        for (int i = 0; i < 50; i++) {
            EspacioNatural EN = new EspacioNatural();
            lenGuipuzkua.add(EN);
            ;
            lenGuipuzkua.get(i).setNombre("La concha");
            lenGuipuzkua.get(i).setCalidad("Pija");
            lenGuipuzkua.get(i).setMunicipio("San Sebastian");
        }

        lenAraba = new ArrayList<EspacioNatural>();
        for (int i = 0; i < 50; i++) {
            EspacioNatural EN = new EspacioNatural();
            lenAraba.add(EN);
            ;
            lenAraba.get(i).setNombre("Pantano");
            lenAraba.get(i).setCalidad("Maaaaal");
            lenAraba.get(i).setMunicipio("Araba no tiene Playa");
        }


        len = new ArrayList<EspacioNatural>();
        for (int i = 0; i < 50; i++) {
            EspacioNatural EN = new EspacioNatural();
            len.add(EN);
            len.get(i).setMunicipio("Bakio");
            len.get(i).setMunicipio("San Sebastian");
            len.get(i).setMunicipio("No me se pueblos de Araba");
        }


        recycler = (RecyclerView) findViewById(R.id.recyclerViewEspaciosNaturales);

        RecyclerAdapter rA = new RecyclerAdapter(lenGuipuzkua);
        recycler.setAdapter(rA);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);


        botonBizkaia.setOnClickListener(this);
        botonAraba.setOnClickListener(this);
        botonGuipuzkoa.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botonGuipuzkoa:
                RecyclerAdapter ri = new RecyclerAdapter(lenGuipuzkua);
                recycler.setAdapter(ri);
                break;

            case R.id.botonAraba:
                RecyclerAdapter ru = new RecyclerAdapter(lenAraba);
                recycler.setAdapter(ru);
                break;
            case R.id.botonVizcaya:
                RecyclerAdapter re = new RecyclerAdapter(lenBizkaia);
                recycler.setAdapter(re);
                break;

        }
    }
}