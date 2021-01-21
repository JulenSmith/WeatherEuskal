package com.example.weathereuskal.Utilidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathereuskal.Objetos.EspacioNatural;
import com.example.weathereuskal.R;
import com.example.weathereuskal.Ventanas.EspaciosNaturales;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MiViewHolder> {
    private List<EspacioNatural> playasList;
    /**
     * Clase ViewHolder * */
    public class MiViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView calidad;
        public TextView municipio;


        public MiViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.nombreEspacio);
            calidad = (TextView) view.findViewById(R.id.calidadEspacio);
            municipio = (TextView) view.findViewById(R.id.municipioEspacio);
        }
    }
    // Constructor del Adaptador.
    public RecyclerAdapter(List<EspacioNatural> playasList) {
        this.playasList = playasList;
    }
    // Este metodo es llamado por el RecyclerView para mostrar los datos del elemento de esa posición.


    @Override
    public void onBindViewHolder(MiViewHolder holder, int position) {
        EspacioNatural eN = playasList.get(position);
        holder.nombre.setText(eN.getNombre());
        holder.calidad.setText(eN.getCalidad());
        holder.municipio.setText(eN.getMunicipio());
    }
    @Override
    public int getItemCount() {
        return playasList.size();
    }

    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.miviewholder,parent,
                        false);
        return new MiViewHolder(v);
    }
}