package com.example.weathereuskal.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.R;

public class Top extends AppCompatActivity {
    private Button botonProvinciaProv1;
    private Button botonProvinciaProv2;
    private Button botonProvinciaProv3;
    private TextView pos1;
    private TextView pos2;
    private TextView pos3;
    private TextView pos4;
    private TextView pos5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        botonProvinciaProv1 = findViewById(R.id.prov1);
        botonProvinciaProv2 = findViewById(R.id.prov2);
        botonProvinciaProv3 = findViewById(R.id.prov3);
        pos1 = findViewById(R.id.top1);
        pos2 = findViewById(R.id.top2);
        pos3 = findViewById(R.id.top3);
        pos4 = findViewById(R.id.top4);
        pos5 = findViewById(R.id.top5);
        botonProvinciaProv1.setOnClickListener(this::onClick);
        botonProvinciaProv2.setOnClickListener(this::onClick);
        botonProvinciaProv3.setOnClickListener(this::onClick);

        pos1.setText("");
        pos2.setText("");
        pos3.setText("");
        pos4.setText("");
        pos5.setText("");
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.prov1:
                arrancarTop(botonProvinciaProv1.getText().toString());
                break;
            case R.id.prov2:
                arrancarTop(botonProvinciaProv2.getText().toString());
                break;
            case R.id.prov3:
                arrancarTop(botonProvinciaProv3.getText().toString());
                break;
        }
    }

    public void arrancarTop(String provincia)
    {
        String[][] lista = null;
        ConexionBD clientThread = new ConexionBD();
        clientThread.setConsulta("top");
        clientThread.setSentencia("SELECT * from (\n" +
                "select m.Nombre, max(h.NOgm3) as top from municipios as m, estaciones as e, informes as i, horario as h where m.id = e.Municipio and e.id = i.Estacion and i.Id = h.Informe and m.Provincia = (SELECT id from provincias where lower(nombre) like lower('%" + provincia + "%')) group by m.Nombre order by h.NOgm3 DESC limit 5\n" +
                ") as datos order by top DESC");

        Thread thread = new Thread(clientThread);
        thread.start();
        while(thread.isAlive())
            lista = clientThread.devolverTop();
        if(lista[0][0] != null && lista[0][1] != null)
            pos1.setText("Municipio: " + lista[0][0] + " NoGm3: " + lista[0][1]);
        else
            pos1.setText("");
        if(lista[1][0] != null && lista[1][1] != null)
            pos2.setText("Municipio: " + lista[1][0] + " NoGm3: " + lista[1][1]);
        else
            pos2.setText("");
        if(lista[2][0] != null && lista[2][1] != null)
            pos3.setText("Municipio: " + lista[2][0] + " NoGm3: " + lista[2][1]);
        else
            pos3.setText("");
        if(lista[3][0] != null && lista[3][1] != null)
            pos4.setText("Municipio: " + lista[3][0] + " NoGm3: " + lista[3][1]);
        else
            pos4.setText("");
        if(lista[4][0] != null && lista[4][1] != null)
            pos5.setText("Municipio: " + lista[4][0] + " NoGm3: " + lista[4][1]);
        else
            pos5.setText("");

    }
}