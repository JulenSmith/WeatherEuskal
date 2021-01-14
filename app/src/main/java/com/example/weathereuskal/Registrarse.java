package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Utilidades.Hash;

public class Registrarse extends AppCompatActivity implements View.OnClickListener {

    private Button botonRegistrarse;
    private Button botonSalir;
    private EditText campoUsuario;
    private EditText campoContrasena;
    private EditText campoContrasenaRepetir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registrarse);

        botonRegistrarse = (Button) findViewById(R.id.botonRegistrarseR);
        botonSalir = (Button) findViewById(R.id.botonSalirR);
         campoUsuario= findViewById(R.id.campoUsuarioR);
         campoContrasena=findViewById(R.id.campoContrasenaR);
         campoContrasenaRepetir=findViewById(R.id.campoRepetirContrasenaR);


        botonRegistrarse.setOnClickListener(this);
        botonSalir.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {



        switch(v.getId()){

            case R.id.botonRegistrarseR:
                if(!campoUsuario.getText().toString().equals("") && !campoContrasena.getText().toString().equals("") &&!campoContrasenaRepetir.getText().toString().equals("")) {

                if(campoContrasena.getText().toString().equals(campoContrasenaRepetir.getText().toString())) {
                    ConexionBD clientThread = new ConexionBD();
                    clientThread.setConsulta("insert");
                    clientThread.setSentencia("INSERT INTO usuario(nombre,contrasena) VALUES ('" + campoUsuario.getText().toString() + "','" + Hash.crearHash(campoContrasena.getText().toString()) + "')");
                    Thread thread = new Thread(clientThread);
                    thread.start();

                    try {
                        thread.join(); // Esperar respusta del servidor...
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(Registrarse.this, IniciarSesion.class);
                    startActivity(i);
                    finish();

                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

                }
                break;


            case R.id.botonSalirR:

                Intent i = new Intent(Registrarse.this,IniciarSesion.class);
                startActivity(i);
                finish();
                break;

        }


    }
}

