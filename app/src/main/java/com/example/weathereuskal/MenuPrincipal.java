package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.weathereuskal.Ventanas.Comunidad;
import com.example.weathereuskal.Ventanas.EspaciosNaturales;
import com.example.weathereuskal.Ventanas.Top;
import com.google.android.gms.maps.GoogleMap;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    private Button botonEspacios;
    private Button botonComunidad;
    private Button botonTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_principal);

        botonEspacios = findViewById(R.id.botonEspaciosNaturales);
        botonComunidad = findViewById(R.id.botonComunidad);
        botonTop = findViewById(R.id.botonTop);


        botonEspacios.setOnClickListener(this);
        botonComunidad.setOnClickListener(this);
        botonTop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botonEspaciosNaturales:
                Intent espaciosNaturales = new Intent(MenuPrincipal.this,EspaciosNaturales.class);
                startActivity(espaciosNaturales);
                finish();
                break;

            case R.id.botonComunidad:
                Intent comunidad = new Intent(MenuPrincipal.this, Comunidad.class);
                startActivity(comunidad);
                finish();
                break;

            case R.id.botonTop:
                Intent top = new Intent(MenuPrincipal.this, Top.class);
                startActivity(top);
                finish();
                break;


        }
    }
}