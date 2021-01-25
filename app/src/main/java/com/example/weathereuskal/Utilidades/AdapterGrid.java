package com.example.weathereuskal.Utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdapterGrid extends BaseAdapter {

    private Context contexto;
    private ArrayList<Bitmap> imagenes;

    public AdapterGrid(Context contexto, ArrayList<Bitmap> imagenes){

        this.contexto = contexto;
        this.imagenes = imagenes;

    }

    @Override
    public int getCount() {
        return imagenes.size();
    }

    @Override
    public Object getItem(int position) {
        return imagenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(contexto);
        imageView.setImageResource(imagenes.get(position).getGenerationId());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        return imageView;
    }


}
