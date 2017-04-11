package com.extraware.xwormapi.csv;

import java.util.HashMap;
import java.util.Map;

public class CSVUtilitarios {

    public static final char LIMITADOR = ';';

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

    /**
     * Método que retorna uma string com uma lista separada por ";" dos pares chave/valor do mapa enviado.
     * @param mapa Mapa com os pares chave/valor
     * @return String com os pares separados por ";"
     */
    public static String mapaParaCSV(Map<String, String> mapa) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String chave : mapa.keySet()) {
            stringBuilder.append(LIMITADOR);
            String valor = mapa.get(chave);
            stringBuilder.append(chave + "=" + valor);
        }
        return stringBuilder.toString().substring(1);
    }
}
