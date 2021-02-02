package com.example.weathereuskal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Objetos.Horario;

import java.util.ArrayList;

public class DetallesLugarActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nombreLugar;
    private Button favorito;
    private Button localizacion;
    private ListView listview;
    private ListView listaCalidad;
    private Spinner spinner;
    private ListView listaNoxgm3;
    private Button buttonFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_lugar);

        nombreLugar = this.findViewById(R.id.nombreLugar);
        favorito = this.findViewById(R.id.botonFavorito);
        localizacion = this.findViewById(R.id.botonLocalizacion);
        listview = this.findViewById(R.id.list1);
        listaCalidad = this.findViewById(R.id.listCalidad);
        spinner = this.findViewById(R.id.spinnerEstaciones);
        listaNoxgm3 = this.findViewById(R.id.listNoxgm3);
        buttonFotos = this.findViewById(R.id.buttonFotos);

        nombreLugar.setText(getIntent().getExtras().getString("nombre"));

        if (getIntent().getExtras().getDouble("latitud") == 0) {

            localizacion.setEnabled(false);

        }

        ConexionBD conexion = new ConexionBD();

        if (getIntent().getExtras().get("botonOrigen").equals("municipios")) {

            conexion.setSentencia("SELECT Nombre from estaciones where Municipio = (select Id from municipios where Nombre = " +
                    "'" + getIntent().getExtras().get("nombre") + "')");
        } else {

            conexion.setSentencia("SELECT Nombre from estaciones where Municipio in (select Municipio from entornosmuni where Entorno = " +
                    "(SELECT Id from entornos where Nombre = '" + getIntent().getExtras().get("nombre") + "'))");

        }

        conexion.setConsulta("arraySelect");

        Thread thread = new Thread(conexion);
        thread.start();

        try {
            thread.join(); // Esperar respusta del servidor...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> nombresEstaciones = conexion.getArrayResultados();

        Log.i("Tamaño array", String.valueOf(nombresEstaciones.size()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombresEstaciones);
        spinner.setAdapter(adapter);

        if (nombresEstaciones.size() > 0) {

            ConexionBD conexionHorarios = new ConexionBD();

            conexionHorarios.setSentencia("Select Fecha, Hora, NOgm3, NO2, NOXgm3, PM10, PM25, SO2, ICAEstacion " +
                    "from horario where Informe in (Select Id from informes where estacion in (SELECT Id from estaciones where Nombre = '" + nombresEstaciones.get(0) + "'))" +
                    " and Hora is not null limit 5");

            conexionHorarios.setConsulta("selectArrayHorarios");

            Thread threadHorarios = new Thread(conexionHorarios);
            threadHorarios.start();

            try {
                threadHorarios.join(); // Esperar respusta del servidor...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Horario> listaHorarios = conexionHorarios.getListaHorarios();
            ArrayList<String> horas = new ArrayList<String>();
            ArrayList<String> calidad = new ArrayList<String>();
            ArrayList<String> noxgm3 = new ArrayList<String>();

            if (listaHorarios.size() > 0) {

                for (int i = 0; i < 5; i++) {

                    horas.add(listaHorarios.get(i).getHora());
                    calidad.add(listaHorarios.get(i).getIca());

                    if (listaHorarios.get(i).getNoxgm3() != null){

                        noxgm3.add(listaHorarios.get(i).getNoxgm3());
                    } else {

                        noxgm3.add("No disponible");
                    }


                }

                ArrayAdapter<String> adapterHora = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, horas);

                ArrayAdapter<String> adapterCalidad = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, calidad);

                ArrayAdapter<String> adapterNoxgm3 = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, noxgm3);

                listview.setAdapter(adapterHora);
                listaCalidad.setAdapter(adapterCalidad);
                listaNoxgm3.setAdapter(adapterNoxgm3);

            } else {

                Toast.makeText(getApplicationContext(), "La estación no dispone de datos en este momento", Toast.LENGTH_SHORT).show();

            }

        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("Mensaje", "Hola");

                ConexionBD conexionHorarios = new ConexionBD();

                conexionHorarios.setSentencia("Select Fecha, Hora, NOgm3, NO2, NOXgm3, PM10, PM25, SO2, ICAEstacion " +
                        "from horario where Informe in (Select Id from informes where estacion in (SELECT Id from estaciones where Nombre = '" + parent.getItemAtPosition(position).toString() + "'))" +
                        " and Hora is not null limit 5");

                conexionHorarios.setConsulta("selectArrayHorarios");

                Thread threadHorarios = new Thread(conexionHorarios);
                threadHorarios.start();

                try {
                    threadHorarios.join(); // Esperar respusta del servidor...
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                ArrayList<Horario> listaHorarios = conexionHorarios.getListaHorarios();
                ArrayList<String> horas = new ArrayList<String>();
                ArrayList<String> calidad = new ArrayList<String>();

                ArrayAdapter<String> adapterHora = new ArrayAdapter<String>
                        (DetallesLugarActivity.this, android.R.layout.simple_list_item_1, horas);

                ArrayAdapter<String> adapterCalidad = new ArrayAdapter<String>
                        (DetallesLugarActivity.this, android.R.layout.simple_list_item_1, calidad);

                listview.setAdapter(adapterHora);
                listaCalidad.setAdapter(adapterCalidad);


                Log.e("Mensaje", String.valueOf(listaHorarios.size()));
                if (listaHorarios.size() != 0) {

                    for (int i = 0; i < 5; i++) {

                        horas.add(listaHorarios.get(i).getHora());
                        calidad.add(listaHorarios.get(i).getIca());

                    }



                } else {

                    horas.clear();
                    calidad.clear();

                    Toast.makeText(getApplicationContext(), "La estación no dispone de datos en este momento", Toast.LENGTH_SHORT).show();
                    Log.e("Mensaje", "No sé por qué no salgo");

                }

                adapterHora.notifyDataSetChanged();
                adapterCalidad.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



    @Override
    public void onClick(View v){

        Bundle extras = new Bundle();

        switch (v.getId()){

            case R.id.botonLocalizacion:

                Intent localizacion = new Intent(DetallesLugarActivity.this, MapaActivity.class);
                extras = new Bundle();
                extras.putString("nombre", getIntent().getExtras().getString("nombre"));
                extras.putDouble("latitud", getIntent().getExtras().getDouble("latitud"));
                extras.putDouble("longitud", getIntent().getExtras().getDouble("longitud"));
                localizacion.putExtras(extras);
                startActivity(localizacion);

                break;

            case R.id.botonFavorito:

                String tabla = new String();
                String tablaInsertar = new String();

                if (getIntent().getExtras().get("botonOrigen").equals("municipios")){

                    tabla = "municipios";
                    tablaInsertar = "municipiosfavoritos";

                } else {

                    tabla = "entornos";
                    tablaInsertar = "entornosfavoritos";
                }

                ConexionBD conexion = new ConexionBD();
                conexion.setSentencia("Insert into " +  tablaInsertar +
                        " VALUES ((SELECT Id from usuario where Nombre = '" + getIntent().getExtras().get("nombreUsuario") + "')," +
                                " (SELECT Id from " + tabla + " where Nombre = '" + getIntent().getExtras().get("nombre") + "'))");

                conexion.setConsulta("insert");

                Thread thread = new Thread(conexion);
                thread.start();

                try {
                    thread.join(); // Esperar respusta del servidor...
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.buttonFotos :

                Intent fotos = new Intent (DetallesLugarActivity.this, Fotos.class);
                extras = new Bundle();
                extras.putString("botonOrigen", getIntent().getExtras().getString("botonOrigen"));
                extras.putString("nombreUsuario", getIntent().getExtras().getString("nombreUsuario"));
                extras.putString("nombreLugar", nombreLugar.getText().toString());
                fotos.putExtras(extras);
                startActivity(fotos);
                break;

        }

    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.share, menu);
//        MenuItem item = menu.findItem(R.id.share_item);
//        actionProvider = (ShareActionProvider) item.getActionProvider();
//
//        // Create the share Intent
//        String shareText = URL_TO_SHARE;
//        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
//                .setType("text/plain").setText(shareText).getIntent();
//        actionProvider.setShareIntent(shareIntent);
//        return true;
//    }
}