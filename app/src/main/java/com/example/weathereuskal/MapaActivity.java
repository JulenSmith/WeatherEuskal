package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        GoogleMap mapa = googleMap;
        LatLng latlng = new LatLng(getIntent().getExtras().getDouble("latitud"), getIntent().getExtras().getDouble("longitud"));

        mapa.addMarker(new MarkerOptions().position(latlng).title(getIntent().getExtras().getString("nombre")));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11));
    }
}