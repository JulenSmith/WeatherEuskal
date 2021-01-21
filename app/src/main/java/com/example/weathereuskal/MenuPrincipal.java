package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.weathereuskal.Ventanas.EspaciosNaturales;
import com.example.weathereuskal.Ventanas.Top;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    private Button botonEspacios;
    private Button botonComunidad;
    private Button botonTop;
    private Button botonFotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_principal);

        botonEspacios = findViewById(R.id.botonEspaciosNaturales);
        botonComunidad = findViewById(R.id.botonMunicipios);
        botonTop = findViewById(R.id.botonTop);
        botonFotos = findViewById(R.id.botonFotos);

        botonEspacios.setOnClickListener(this);
        botonComunidad.setOnClickListener(this);
        botonTop.setOnClickListener(this);
        botonTop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botonEspaciosNaturales:
                Intent espaciosNaturales = new Intent(MenuPrincipal.this,EspaciosNaturales.class);
                startActivity(espaciosNaturales);
                break;

            case R.id.botonMunicipios:
                Intent municipios = new Intent(MenuPrincipal.this, MunicipiosActivity.class);
                startActivity(municipios);
                break;

            case R.id.botonTop:
                Intent top = new Intent(MenuPrincipal.this, Top.class);
                startActivity(top);
                break;

            case R.id.botonFotos:

                Intent fotos = new Intent(MenuPrincipal.this, Fotos.class);
                startActivity(fotos);
                break;

        }
    }
}