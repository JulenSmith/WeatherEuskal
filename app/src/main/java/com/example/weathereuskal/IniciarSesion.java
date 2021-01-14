package com.example.weathereuskal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.weathereuskal.Conexion.ConexionBD;
import com.example.weathereuskal.Utilidades.Hash;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener{

    public static int SPLASH_TIME_OUT = 5000;
    private VideoView videoBg;
    MediaPlayer OmediaPlayer;
    int mCurrentVideoPosition;
    private Button botonVolumen;
    private ConnectivityManager connectivityManager = null;
    private TextView oTextView;
    private Button botonLogin;
    private Button botonRegistrarse;
    private EditText campoUsuario;
    private EditText campoContrasena;
    private Animation animacion1;
    private  Animation animacion2;
    private Animation animacion3;
    private  Animation animacion4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.iniciar_sesion);

         animacion1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
         animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
         animacion3 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_derecha);
         animacion4 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_izquierda);

        //Creamos el objeto que nos permitirá gestionar el audio del dispositivo en este caso solo nos interesa el de la música, pero se pueden gestionar todos.
        AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        //Si descomentamos la linea de abajo, el volver a la ventana principal el audio sonara si o sí, si no se guardará en caché la opcion elegida por el usuario.
        //mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);

        //Inicializamos los componentes con los que vamos a trabajar
        botonVolumen = (Button) findViewById(R.id.Botonvolumen);
        videoBg = (VideoView) findViewById(R.id.videoView);
        botonLogin = findViewById(R.id.botonLoginIS);
        botonRegistrarse = findViewById(R.id.botonRegistroIS);
        campoUsuario = findViewById(R.id.texNombreUsuarioIS);
        campoContrasena = findViewById(R.id.textContrasenaIS);

        botonLogin.setOnClickListener(this);
        botonRegistrarse.setOnClickListener(this);

        botonLogin.setAnimation(animacion4);
        botonRegistrarse.setAnimation(animacion3);


        //Preparamos el recurso de video guardado en la carpeta raw.
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.clase);

        //Lo seteamos y lo iniciamos
        videoBg.setVideoURI(uri);
        videoBg.start();

        //El video por sí necesita un reproductor, por lo que  le asignamos uno y seteamos el video al reproductor
        videoBg.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                OmediaPlayer = mp;

                //Al ser menú de inicio nos combiene que este dando vueltas constantemente.
                OmediaPlayer.setLooping(true);

                if (mCurrentVideoPosition != 0) {
                    OmediaPlayer.seekTo(mCurrentVideoPosition);
                    OmediaPlayer.start();
                }
            }
        });


        //Control de audio ON/OFF SE GUARDA EN CHACHÉ!

        botonVolumen.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View view) {
                if (!mAudioManager.isStreamMute(AudioManager.STREAM_MUSIC)) {
                    mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    

                } else {
                    mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.botonLoginIS:
                String nombreBD=null;
                String contraBD=null;

                try {
                    ConexionBD clientThread = new ConexionBD();
                    clientThread.setConsulta("select");
                    clientThread.setSentencia("Select nombre from usuario where nombre ='"+campoUsuario.getText().toString()+"'");
                    clientThread.setColumna("nombre");
                    Thread thread = new Thread(clientThread);
                    thread.start();
                    Log.i("iniciarSesion", "comparativa : "+nombreBD);
                    while(nombreBD == null) {
                        nombreBD = clientThread.getResponse();

                    }
                    thread.join(); // Esperar respusta del servidor...

                    ConexionBD clientThread2 = new ConexionBD();
                    Thread thread2 = new Thread(clientThread2);
                    clientThread2.setConsulta("select");
                    clientThread2.setSentencia("Select contrasena from usuario where nombre ='"+campoUsuario.getText().toString()+"'");
                    clientThread2.setColumna("contrasena");
                    thread2.start();

                    while(contraBD == null) {
                        contraBD = clientThread2.getResponse();
                    }

                    thread.join(); // Esperar respusta del servidor...

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String contrasenahasheada = Hash.crearHash(campoContrasena.getText().toString());

                if(contraBD.equals(contrasenahasheada) && nombreBD.equals(campoUsuario.getText().toString())) {

                    Intent i = new Intent(IniciarSesion.this, MenuPrincipal.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(this, "Los campos no están correctos", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.botonRegistroIS:


                Intent i = new Intent (IniciarSesion.this,Registrarse.class);
                startActivity(i);
                finish();
                break;

        }


    }





}














