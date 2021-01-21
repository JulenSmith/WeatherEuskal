package com.example.weathereuskal.Conexion;

import android.util.Log;

import com.example.weathereuskal.Objetos.Municipio;

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
            sIP = "192.168.56.1";
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

                        arrayResultados.add(rs.getString(columna));

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
}

