package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetallesLugarActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nombreLugar;
    private TextView descripcion;
    private Button favorito;
    private Button localizacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_lugar);

        nombreLugar = this.findViewById(R.id.nombreLugar);
        favorito = this.findViewById(R.id.botonFavorito);
        localizacion = this.findViewById(R.id.botonLocalizacion);
        descripcion = this.findViewById(R.id.descripcion);

        nombreLugar.setText(getIntent().getExtras().getString("nombre"));
        descripcion.setText(getIntent().getExtras().getString("descripcion"));

        if (getIntent().getExtras().getDouble("latitud") == 0){

            localizacion.setEnabled(false);

        }

    }
    @Override
    public void onClick(View v){

        switch (v.getId()){

            case R.id.botonLocalizacion:

                Intent localizacion = new Intent(DetallesLugarActivity.this, MapaActivity.class);
                Bundle extras = new Bundle();
                extras.putString("nombre", getIntent().getExtras().getString("nombre"));
                extras.putDouble("latitud", getIntent().getExtras().getDouble("latitud"));
                extras.putDouble("longitud", getIntent().getExtras().getDouble("longitud"));
                localizacion.putExtras(extras);
                startActivity(localizacion);

                break;
        }

    }
}