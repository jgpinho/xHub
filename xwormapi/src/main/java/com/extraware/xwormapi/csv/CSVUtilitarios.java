package com.extraware.xwormapi.csv;

import java.util.HashMap;
import java.util.Map;

public class CSVUtilitarios {

    public static Map<String, String> lerMapa(String paresCSV) {

        // Criação do mapa
        Map<String, String> mapa = new HashMap<String, String>();
        // Análise dos pares
        String[] pares = paresCSV.split(",");

        // Adicionar os pares ao mapa
        for (String par : pares) {
            String[] separacao = par.split(",");
            mapa.put(separacao[0], separacao[1]);
        }
        return mapa;
    }
}
