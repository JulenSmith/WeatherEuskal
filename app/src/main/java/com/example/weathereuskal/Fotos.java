package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weathereuskal.Conexion.ConexionBD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Fotos extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CAMERA = 1;
    private Button botonSacarFotos;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String rutaImagen;
    private ImageView imageView;
    private File imagen;
    private Button botonGaleria;
    private Button botonSubirFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        botonSacarFotos = findViewById(R.id.botonSacarFoto);
        botonSacarFotos.setOnClickListener(this);
        imageView = findViewById(R.id.imageViewFoto);
        botonGaleria = findViewById(R.id.botonGaleria);
        botonSubirFoto = findViewById(R.id.botonSubir);

        botonSubirFoto.setEnabled(false);

    }

    @Override
    public void onClick(View v){

        switch (v.getId()){

            case R.id.botonSacarFoto:

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        File imagenArchivo = null;

                        try {

                            imagenArchivo = crearImagen();

                        }catch (IOException ex){

                            Log.e("Error", ex.toString());

                        }

                        if(imagenArchivo != null){

                            Uri fotoUri = FileProvider.getUriForFile(this, "com.example.weathereuskal.fileprovider", imagenArchivo);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }

                    } else {
                        Log.e("Mensaje", "Nulo");
                    }

                } else {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

                }

                break;

            case R.id.botonSubir:

                if (getIntent().getExtras().getString("botonOrigen").equals("municipios")){

                    ConexionBD clientThread = new ConexionBD();
                    clientThread.setConsulta("insertFoto");
                    clientThread.setSentencia("Insert into fotosmunicipios (Usuario, Municipio, Foto) " +
                            "VALUES ((SELECT Id from usuario where Nombre = '" + getIntent().getExtras().getString("nombreUsuario") + "'), " +
                            "(SELECT Id from municipios where Nombre = '" + getIntent().getExtras().getString("nombreLugar") + "'), " +
                            "?)");
                    boolean subida = true;
                    try {
                        clientThread.setFoto(imagen);
                        Thread thread = new Thread(clientThread);
                        thread.start();
                        thread.join(); // Esperar respusta del servidor...
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        subida = false;
                    }

                    if (subida) {

                        Toast.makeText(this, "La foto ha sido subida", Toast.LENGTH_SHORT).show();
                        botonSubirFoto.setEnabled(false);

                    }

                } else if (getIntent().getExtras().getString("botonOrigen").equals("espacios")){

                    ConexionBD clientThread = new ConexionBD();
                    clientThread.setConsulta("insertFoto");
                    clientThread.setSentencia("Insert into fotosentornos (Usuario, Entorno, Foto) " +
                            "VALUES ((SELECT Id from usuario where Nombre = '" + getIntent().getExtras().getString("nombreUsuario") + "'), " +
                            "(SELECT Id from entornos where Nombre = '" + getIntent().getExtras().getString("nombreLugar") + "'), " +
                            "?)");
                    boolean subida = true;
                    try {
                        clientThread.setFoto(imagen);
                        Thread thread = new Thread(clientThread);
                        thread.start();
                        thread.join(); // Esperar respusta del servidor...
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        subida = false;
                    }

                    if (subida) {

                        Toast.makeText(this, "La foto ha sido subida", Toast.LENGTH_SHORT).show();
                        botonSubirFoto.setEnabled(false);

                    }

                } else {

                    ConexionBD clientThread = new ConexionBD();
                    clientThread.setConsulta("insertFoto");
                    clientThread.setSentencia("Insert into fotos (Usuario, Foto) VALUES ((SELECT Id from usuario where Nombre = '" + getIntent().getExtras().getString("nombreUsuario") + "'), ?)");
                    Log.e("Mensaje", getIntent().getExtras().getString("nombreUsuario"));
                    boolean subida = true;
                    try {
                        clientThread.setFoto(imagen);
                        Thread thread = new Thread(clientThread);
                        thread.start();
                        thread.join(); // Esperar respusta del servidor...
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        subida = false;
                    }

                    if (subida) {

                        Toast.makeText(this, "La foto ha sido subida", Toast.LENGTH_SHORT).show();
                        botonSubirFoto.setEnabled(false);

                    }
                }

                break;

            case R.id.botonGaleria:

                Intent galeria = new Intent(Fotos.this,GaleriaActivity.class);
                Bundle extras = new Bundle();
                extras.putString("botonOrigen", getIntent().getExtras().getString("botonOrigen"));
                extras.putString("nombreLugar", getIntent().getExtras().getString("nombreLugar"));
                galeria.putExtras(extras);
                startActivity(galeria);

                    break;

        }

    }

    private File crearImagen() throws IOException {

        String nombreImagen = ("foto_");
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imagen = File.createTempFile(nombreImagen, ".jpg", directorio);

        rutaImagen = imagen.getAbsolutePath();

        return imagen;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){

            Bitmap imagenBit = BitmapFactory.decodeFile(rutaImagen);
            imageView.setImageBitmap(imagenBit);
            botonSubirFoto.setEnabled(true);

        }

    }

}
