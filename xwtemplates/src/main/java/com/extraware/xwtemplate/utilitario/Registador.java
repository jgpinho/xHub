package com.extraware.xwtemplate.utilitario;

import java.util.HashMap;
import java.util.Map;

public abstract class Registador {

    // Constante que indica que o processo usa automaticamente uma libraria disponível.
    public static final int LIBRARIA_AUTO = -1;

    public static final int LIBRARIA_NONE = 0;
    public static final int LIBRARIA_JAVA = 1;
    public static final int LIBRARIA_AVALON = 2;
    public static final int LIBRARIA_LOG4J = 3;
    public static final int LIBRARIA_COMMONS = 4;
    public static final int LIBRARIA_SLF4J = 5;

    private static final String[] LIBINIC = {
            "com.extraware.xwtemplate.utilitario.Registador", "_Null",
            "java.util.logging.Logger", "_JDK14",
            "org.apache.log.Logger",    "_Avalon",
            "org.apache.log4j.Logger",  "_Log4J",
            "org.apache.commons.logging.Log",  "CommonsLogging",
            "org.slf4j.Logger",  "SLF4J",
    };

    private static int librariaReg;
    private static FabricaRegistador fabricaRegistador;
    private static String prefixoCategoria = "";

    private static final Map registadores = new HashMap();

    public abstract void debug(String mensagem);
    public abstract void debug(String mensagem, Throwable t);
    public abstract void info(String mensagem);
    public abstract void info(String mensagem, Throwable t);
    public abstract void aviso(String mensagem);
    public abstract void aviso(String mensagem, Throwable t);
    public abstract void erro(String mensagem);
    public abstract void erro(String mensagem, Throwable t);

    public abstract boolean debugAtivo();
    public abstract boolean infoAtivo();
    public abstract boolean avisoAtivo();
    public abstract boolean erroAtivo();
    public abstract boolean fatalAtivo();

    public static Registador getRegistador(String categoria) {
        if (fabricaRegistador == null) {
            synchronized (Registador.class) {
                if (fabricaRegistador == null) {
                    try {
                        selecionarLibrariaRegistador(LIBRARIA_AUTO);
                    } catch (ClassNotFoundException excecao) {
                        throw new RuntimeException(excecao.getMessage());
                    }
                }
            }
        }

        categoria = prefixoCategoria + categoria;

        synchronized (registadores) {
            Registador registador = (Registador) registadores.get(categoria);
            if (registador == null) {
                registador = fabricaRegistador.getRegistador(categoria);
                registadores.put(categoria, registador);
            }
            return registador;
        }
    }

    public static void selecionarLibrariaRegistador(int libraria) throws ClassNotFoundException {
        synchronized (Registador.class) {
            if (libraria < -1 || (libraria*2) >= LIBINIC.length) throw new IllegalArgumentException();
            librariaReg = libraria;
            fabricaRegistador = criarFabrica();
        }
    }

    private static FabricaRegistador criarFabrica() throws ClassNotFoundException {
        if (librariaReg == LIBRARIA_AUTO) {
            for (int i = LIBINIC.length / 2 - 1; i > 0; --i) {
                if (i == LIBRARIA_SLF4J || i == LIBRARIA_COMMONS) continue;

                try {
                    return criarFabrica(i);
                } catch (ClassNotFoundException excecao) {
                    // Intencionalemente vazio
                }
            }
            System.err.println("*** AVISO: O registador do XwTemplate está desligado.");
            return new _NullFabricaRegistador();
        } else {
            return criarFabrica(librariaReg);
        }
    }

    private static FabricaRegistador criarFabrica(int libraria) throws ClassNotFoundException {
        String nomeClasseRegistador = LIBINIC[libraria * 2];
        String tipoFabrica = LIBINIC[libraria * 2 + 1];

        try {
            UtilitarioClasse.paraNome(nomeClasseRegistador);
            return (FabricaRegistador) Class.forName("com.extraware.xwtemplate.utilitario." + tipoFabrica
                    + "FabricaRegistador").newInstance();
        } catch (IllegalAccessException excecao) {
            throw new IllegalAccessError(excecao.getMessage());
        } catch (InstantiationException excecao) {
            throw new InstantiationError(excecao.getMessage());
        }
    }
}
