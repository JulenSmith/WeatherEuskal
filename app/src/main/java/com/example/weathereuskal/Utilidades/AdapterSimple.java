package com.example.weathereuskal.Utilidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathereuskal.R;

import java.util.ArrayList;

public class AdapterSimple extends RecyclerView.Adapter <AdapterSimple.ViewHolder> implements View.OnClickListener {

    ArrayList<String> listaDatos;
    private View.OnClickListener listener;

    public AdapterSimple(ArrayList<String> listaDatos) {
        this.listaDatos = listaDatos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_simple,null,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.asignarDatos(listaDatos.get(position));

    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }

    public void setOnClickListener (View.OnClickListener listener){

        this.listener = listener;

    }

    @Override
    public void onClick(View v) {

        if (listener != null){

            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mensaje;

        public ViewHolder(@NonNull View view) {
            super(view);
            mensaje = view.findViewById(R.id.idMensaje);

        }

        public void asignarDatos(String mensajes) {

            mensaje.setText(mensajes);
        }
    }
}
