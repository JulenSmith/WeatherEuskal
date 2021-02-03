package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.example.weathereuskal.Ventanas.Top;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    private Button botonEspacios;
    private Button botonComunidad;
    private Button botonTop;
    private Button botonFotos;
    private Button botonFavoritos;
    private TextView textBienvenida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_principal);

        botonEspacios = findViewById(R.id.botonEspaciosNaturales);
        botonComunidad = findViewById(R.id.botonMunicipios);
        botonTop = findViewById(R.id.botonTop);
        botonFotos = findViewById(R.id.botonFotos);
        botonFavoritos = findViewById(R.id.botonFavoritos);
        textBienvenida = findViewById(R.id.textViewBienvenida);

        botonEspacios.setOnClickListener(this);
        botonComunidad.setOnClickListener(this);
        botonTop.setOnClickListener(this);
        botonFavoritos.setOnClickListener(this);

        textBienvenida.setText("Hola, " + getIntent().getExtras().getString("nombreUsuario"));

        if (!getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){

            botonFotos.setEnabled(false);

        }

    }

    @Override
    public void onClick(View v) {

        Bundle extras = new Bundle();

        switch (v.getId()) {

            case R.id.botonEspaciosNaturales:
                Intent espaciosNaturales = new Intent(MenuPrincipal.this,MunicipiosActivity.class);
                extras.putString("botonOrigen", "espacios");
                extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                espaciosNaturales.putExtras(extras);
                startActivity(espaciosNaturales);
                break;

            case R.id.botonMunicipios:

                Intent municipios = new Intent(MenuPrincipal.this, MunicipiosActivity.class);
                extras.putString("botonOrigen", "municipios");
                extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                municipios.putExtras(extras);
                startActivity(municipios);
                break;

            case R.id.botonTop:
                Intent top = new Intent(MenuPrincipal.this, Top.class);
                startActivity(top);
                break;

            case R.id.botonFotos:

                Intent fotos = new Intent(MenuPrincipal.this, Fotos.class);
                extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                fotos.putExtras(extras);
                startActivity(fotos);
                break;
            case R.id.botonFavoritos:

                Intent favoritos = new Intent (MenuPrincipal.this, FavoritosActivity.class);
                extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                favoritos.putExtras(extras);
                startActivity(favoritos);
                break;
        }
    }
}