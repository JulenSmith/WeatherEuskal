package com.example.weathereuskal.Utilidades;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathereuskal.Objetos.Foto;
import com.example.weathereuskal.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterGaleria extends RecyclerView.Adapter <AdapterGaleria.ViewHolder> implements View.OnClickListener {

    ArrayList<Foto> listaFotos;
    private View.OnClickListener listener;

    public AdapterGaleria(ArrayList<Foto> listaFotos) {
        this.listaFotos = listaFotos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.galeria_holder,null,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGaleria.ViewHolder holder, int position) {

        holder.asignarDatos(listaFotos.get(position));

    }

    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    @Override
    public void onClick(View v) {

        if (listener != null){

            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoGaleria;
        TextView autorGaleria;

        public ViewHolder(@NonNull View view) {
            super(view);
            fotoGaleria = view.findViewById(R.id.imageViewFotoGaleria);
            autorGaleria = view.findViewById(R.id.textViewAutor);

        }

        public void asignarDatos(Foto foto) {

            fotoGaleria.setImageBitmap(foto.getFoto());
            autorGaleria.setText(foto.getNombreUsuario());
        }
    }

}
