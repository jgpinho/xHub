package com.extraware.xwtemplate.core;

import com.extraware.xwtemplate.utilitario.ExcecaoNaoDeclarada;
import com.extraware.xwtemplate.utilitario.UtilitarioClasse;

import java.util.HashMap;
import java.util.Map;

/**
 * Fábrica de mapas concorrenciais.
 */
public class FabricaMapaConcorrencial {

    private static final Class classeMapaConcorrencial = getClasseMapaConcorrencial();
    private static final Class classeMelhorMapa = getClasseMelhorMapa();

    /**
     * Método para obter a classe do mapa concorrencial.
     * @return Classe com o mapa concorrencial
     */
    private static Class getClasseMapaConcorrencial() {
        try {
            return UtilitarioClasse.paraNome("java.util.concurrent.ConcurrentMap");
        } catch (ClassNotFoundException excecao) {
            return null;
        }
    }

    /**
     * Método para obter a melhor classe de mapeamento.
     * @return Classe com o melhor mapa concorrencial
     */
    private static Class getClasseMelhorMapa() {
        try {
            return UtilitarioClasse.paraNome("java.util.concurrent.ConcurrentHashMap");
        } catch (ClassNotFoundException excecao) {
            return HashMap.class;
        }
    }

    /**
     * Método para criar o mapa concorrêncial.
     * @return O mapa concorrencial
     */
    public static Map criarMapaConcorrencial() {
        try {
            return (Map) classeMelhorMapa.newInstance();
        } catch (Exception excecao) {
            throw new ExcecaoNaoDeclarada(excecao);
        }
    }

    /**
     * Este método avalia se o mapa é concorrencial.
     * @param mapa Mapa que está a ser avaliado
     * @return Verdade se for concorrencial, falso caso contrário
     */
    public static boolean eConcorrente(Map mapa) {
        return classeMapaConcorrencial != null && classeMapaConcorrencial.isInstance(mapa);
    }

}
