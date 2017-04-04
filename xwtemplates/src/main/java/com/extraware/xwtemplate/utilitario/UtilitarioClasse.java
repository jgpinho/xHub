package com.extraware.xwtemplate.utilitario;

public class UtilitarioClasse {

    private UtilitarioClasse() {}

    public static Class paraNome(String nomeClasse) throws ClassNotFoundException {
        try {
            return Class.forName(nomeClasse, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException excecao) {
            // Ignorado intencionalmente
        } catch (SecurityException excecao) {
            // Ignorado intencionalmente
        }
        return Class.forName(nomeClasse);
    }
}
