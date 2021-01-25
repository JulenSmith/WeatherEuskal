package com.example.weathereuskal.Objetos;

public class Horario {

    private String fecha;
    private String hora;
    private String nogm3;
    private String ica;
    private String no2;
    private String noxgm3;
    private String pm10;
    private String pm25;
    private String so2;

    public Horario(String fecha, String hora, String nogm3, String ica, String no2, String noxgm3, String pm10, String pm25, String so2) {
        this.fecha = fecha;
        this.hora = hora;
        this.nogm3 = nogm3;
        this.ica = ica;
        this.no2 = no2;
        this.noxgm3 = noxgm3;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.so2 = so2;
    }

    public Horario() {
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIca() {
        return ica;
    }

    public void setIca(String ica) {
        this.ica = ica;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNogm3() {
        return nogm3;
    }

    public void setNogm3(String nogm3) {
        this.nogm3 = nogm3;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNoxgm3() {
        return noxgm3;
    }

    public void setNoxgm3(String noxgm3) {
        this.noxgm3 = noxgm3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }
}
