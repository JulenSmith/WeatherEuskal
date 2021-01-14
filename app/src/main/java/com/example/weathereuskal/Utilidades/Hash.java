package com.example.weathereuskal.Utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static String crearHash (String texto){
        Byte [] hash = null;
        String resumenAString = "";
        MessageDigest md;
        try {

            md = MessageDigest.getInstance("SHA");

            byte dataBytes[] = texto.getBytes();
            md.update(dataBytes);
            byte resumen[] = md.digest();
            resumenAString = new String(resumen);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error hash");
        }
        return resumenAString;

    }


}
