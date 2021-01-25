package com.example.weathereuskal.Objetos;

import android.graphics.Bitmap;

import java.sql.Blob;

public class Foto {

    private Bitmap foto;
    private String nombreUsuario;

    public Foto(Bitmap foto, String nombreUsuario){

        this.foto = foto;
        this.nombreUsuario = nombreUsuario;

    }

    public Foto() {
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
