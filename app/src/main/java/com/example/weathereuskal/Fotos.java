package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Fotos extends AppCompatActivity implements View.OnClickListener {

    private Button botonSacarFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        botonSacarFotos = findViewById(R.id.botonSacarFoto);
        botonSacarFotos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        switch (v.getId()){

            case R.id.botonSacarFoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);


        }

    }
}
