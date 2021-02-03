package com.example.weathereuskal.Conexion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.weathereuskal.Objetos.EspacioNatural;
import com.example.weathereuskal.Objetos.Foto;
import com.example.weathereuskal.Objetos.Horario;
import com.example.weathereuskal.Objetos.Municipio;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConexionBD implements Runnable {

    private String sResultado = null;
    private String consulta;
    private String sentencia;
    private String columna;
    private ArrayList<String> arrayResultados;
    private String result = null;
    private ArrayList <String> lista;
    private ArrayList <Municipio> listaMunicipios;
    private ArrayList <EspacioNatural> listaEspacios;
    private ArrayList <Horario> listaHorarios;
    private File foto;
    private ArrayList <Foto> listaImagenes;

    public ConexionBD() {

    }

    public ConexionBD(String consulta) {
        this.consulta = consulta;
    }

    public ConexionBD(String consulta,String sentencia,String columna) {
        this.consulta = consulta;
        this.sentencia = sentencia;
        this.columna = columna;
    }

    @Override
    public void run() {
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection con = null;
        String sIP;
        String sPuerto;
        String sBBDD;



        try{

            Class.forName("com.mysql.jdbc.Driver");
            //Aqui pondriamos la IP y puerto.
            sIP = "192.168.0.11";
            sPuerto = "3306";
            sBBDD = "euskalmet";
            String url = "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "?serverTimezone=UTC";
            con = DriverManager.getConnection( url, "root", "");



            switch(consulta) {
                case "select":

                    String sqlSelect = sentencia;

                    st = con.prepareStatement(sqlSelect);
                    rs = st.executeQuery();
                    //--


                    while (rs.next()) {

                        this.sResultado = rs.getString(columna);

                        result = sResultado;
                    }


                    Log.i("iniciarSesion", "dentroBD : "+ sResultado );
                    break;

                case "update":
                case "insert":
                case "delete":

                    String sqlSinRetorno = sentencia;
                    Log.i("iniciarSesion", "sentencia : "+ sentencia );

                    st = con.prepareStatement(sqlSinRetorno);
                    st.execute(sqlSinRetorno);

                    break;

                case "arraySelect":

                    arrayResultados = new ArrayList <String>();

                    st = con.prepareStatement(sentencia);
                    rs = st.executeQuery();
                    //--

                    while (rs.next()) {

                        arrayResultados.add(rs.getString(1));

                    }

                    break;

                case "selectArrayMunicipios":

                    listaMunicipios = new ArrayList<Municipio>();

                    st = con.prepareStatement(sentencia);
                    rs = st.executeQuery();
                    //--

                    while (rs.next()) {
                        Municipio municipio = new Municipio();
                        municipio.setNombre(rs.getString(2));
                        municipio.setDescripcion(rs.getString(4));
                        municipio.setLatitud(rs.getDouble(5));
                        municipio.setLongitud(rs.getDouble(6));
                      listaMunicipios.add(municipio);
                    }

                    break;

                case "selectArrayEspacios":

                    listaEspacios = new ArrayList<EspacioNatural>();
                    st = con.prepareStatement(sentencia);
                    rs = st.executeQuery();
                    //--

                    while (rs.next()) {
                        EspacioNatural espacio = new EspacioNatural();
                        espacio.setNombre(rs.getString(2));
                        espacio.setDescripcion(rs.getString(3));
                        espacio.setTipo(rs.getString(4));
                        espacio.setTerritorio(rs.getString(5));
                        espacio.setLatitud(rs.getDouble(6));
                        espacio.setLongitud(rs.getDouble(7));
                        listaEspacios.add(espacio);
                    }

                    break;

                case "selectArrayHorarios":

                    listaHorarios = new ArrayList<Horario>();
                    st = con.prepareStatement(sentencia);
                    rs = st.executeQuery();

                    while (rs.next()) {
                        Horario horario = new Horario();
                        horario.setFecha(rs.getString(1));
                        horario.setHora(rs.getString(2));
                        horario.setNogm3(rs.getString(3));
                        horario.setNo2(rs.getString(4));
                        horario.setNoxgm3(rs.getString(5));
                        horario.setPm10(rs.getString(6));
                        horario.setPm25(rs.getString(7));
                        horario.setSo2(rs.getString(8));
                        horario.setIca(rs.getString(9));
                        listaHorarios.add(horario);
                    }

                    break;

                case "insertFoto":

                    FileInputStream convertir_imagen = new FileInputStream (foto);
                    st = con.prepareStatement(sentencia);
                    st.setBlob(1, convertir_imagen, foto.length());
                    st.executeUpdate();

                    break;

                case "selectFotos":

                   listaImagenes = new ArrayList <Foto>();

                    st = con.prepareStatement(sentencia);
                    rs = st.executeQuery();
                    //--

                    while (rs.next()) {
                        Foto foto = new Foto();
                        Bitmap fotoBitmap;
                        Blob fotoBlob;

                        foto.setNombreUsuario(rs.getString(1));
                        fotoBlob = rs.getBlob(2);

                        int blobLength = 0;
                        blobLength = (int) fotoBlob.length();
                        byte[] blobAsBytes = fotoBlob.getBytes(1, blobLength);
                        fotoBitmap = BitmapFactory.decodeByteArray(blobAsBytes, 0 ,blobAsBytes.length);

                        foto.setFoto(fotoBitmap);
                        listaImagenes.add(foto);
                    }

                    break;

            }

        } catch (ClassNotFoundException e) {
            Log.e("ClassNotFoundException", "");
            e.printStackTrace();
        } catch (SQLException e) {
            Log.e("SQLException", "");
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", "");
            e.printStackTrace();
        } finally {
            // Intentamos cerrar _todo.
            try {
                // Cerrar ResultSet
                if(rs!=null) {
                    rs.close();
                }
                // Cerrar PreparedStatement
                if(st!=null) {
                    st.close();
                }
                // Cerrar Connection
                if(con!=null) {
                    con.close();
                }
            } catch (Exception e) {
                Log.e("Exception_cerrando todo", "");
                e.printStackTrace();
            }
        }
        Log.i("iniciarSesion", "OtroDato : "+sResultado );
    }
    public String getResponse() {
        return sResultado;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getSentencia() {
        return sentencia;
    }

    public void setSentencia(String sentencia) {
        this.sentencia = sentencia;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public ArrayList<String> getArrayResultados() {
        return arrayResultados;
    }

    public void setArrayResultados(ArrayList<String> arrayResultados) {
        this.arrayResultados = arrayResultados;
    }

    public ArrayList<Municipio> getListaMunicipios() {
        return listaMunicipios;
    }

    public ArrayList<EspacioNatural> getListaEspacios() {
        return listaEspacios;
    }

    public ArrayList<Horario> getListaHorarios() {
        return listaHorarios;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public ArrayList<Foto> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(ArrayList<Foto> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }
}

