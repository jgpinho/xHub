package com.extraware.xwormapt;

import java.io.*;
import java.util.*;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import android.graphics.PorterDuff;
import com.extraware.xwormapi.types.*;
import com.extraware.xwormapt.basedados.ModeloBasedados;
import com.extraware.xwormapt.conversor.ModeloConversor;

public class Ambiente {

    public static final String INICIO_CONVERSORES = ":CONV_INI";
    public static final String FIM_CONVERSORES = ":CONV_FIM";
    public static final String INICIO_BASEDADOS = ":BD_INI";
    public static final String FIM_BASEDADOS = ":BD_FIM";

    private static final String FICHEIRO_AMBIENTE = "xwormAmb";

    private Registador registador;
    private Map<String, ModeloBasedados> modelosBasedados = new TreeMap<String, ModeloBasedados>();
    private List<ModeloConversor> conversores = new ArrayList<ModeloConversor>();
    private Map<String, ModeloConversor> mapaTipos = new HashMap<String, ModeloConversor>();

    // Construtor principal
    Ambiente(Registador registador) {
        this.registador = registador;
        adicionarConversores();
    }

    public Registador getRegistador() {
        return registador;
    }

    // Método para adicionar os conversores do ambiente
    private void adicionarConversores() {
        adicionarConversor(new ModeloConversor(new ConversorBlob()));
        adicionarConversor(new ModeloConversor(new ConversorBoleano()));
        adicionarConversor(new ModeloConversor(new ConversorByte()));
        adicionarConversor(new ModeloConversor(new ConversorCaractere()));
        adicionarConversor(new ModeloConversor(new ConversorData()));
        adicionarConversor(new ModeloConversor(new ConversorDouble()));
        adicionarConversor(new ModeloConversor(new ConversorEnum()));
        adicionarConversor(new ModeloConversor(new ConversorFloat()));
        adicionarConversor(new ModeloConversor(new ConversorInteiro()));
        adicionarConversor(new ModeloConversor(new ConversorLongo()));
        adicionarConversor(new ModeloConversor(new ConversorCurto()));
        adicionarConversor(new ModeloConversor(new ConversorString()));
    }

    /**
     * Adicionar um conversor para determinado tipo.
     * Este método é chamado durante a compilação pelo processador de anotações. Para que
     * o tipo de conversor (ConversorTipo) esteja vísivel, ele tem de estar no jar no caminho
     * do processador de anotações do projeto do cliente.
     *
     * @param conversor Conversor de tipos a ser adicionado
     * @return OK se adicionou ou se já existia, KO se não adicionou
     */
    public boolean adicionarConversor(ModeloConversor conversor) {
        // Se o conversor já existe, sair
        if (conversores.contains(conversor)) return true;

        // Se algum dos tipos convertíveis já existe, sair
        for (String tipoConvertivel : conversor.getTiposConvertiveis()) {
            if (mapaTipos.containsKey(tipoConvertivel) && !mapaTipos.get(tipoConvertivel).equals(conversor)) {
                return false;
            }
        }

        // Adicionar os tipos conversíveis ao mapa de tipos
        for (String tipoConvertivel : conversor.getTiposConvertiveis()) {
            mapaTipos.put(tipoConvertivel, conversor);
        }

        // Adicionar conversor à lista de conversores
        conversores.add(conversor);

        // Adicionar mensagem de sucesso
        registador.informacao("Foi adicionado o " + conversor.getQualifiedClassName());

        return true;
    }

    /**
     * Método para adicionar um modelo de base de dados.
     *
     * @param modeloBasedados Modelo de base de dados que será adicionado.
     */
    void adicionarBasedados(ModeloBasedados modeloBasedados) {
        modelosBasedados.put(modeloBasedados.getQualifiedClassName(), modeloBasedados);
    }

    /**
     * Método para obter a coleção de modelo de base de dados.
     *
     * @return Uma coleção de modelos de base de dados.
     */
    Collection<ModeloBasedados> getModelosBasedados() {
        return modelosBasedados.values();
    }

    /**
     * Método para obter determinado modelo de base de dados através do nome.
     *
     * @param classeGestora Nome da classe gestora.
     * @return Retorna o modelo da base de dados pedido.
     */
    public ModeloBasedados getBasedadosPorNome(String classeGestora) {
        return modelosBasedados.get(classeGestora);
    }

    public ModeloBasedados getBasedadosPorDefeito() {
        if (modelosBasedados.size() > 0) return modelosBasedados.values().iterator().next();
        return null;
    }

    /**
     * Este método lê o modelo atual de um ficheiro que dá suporte à compilação incremental.
     * Isto é necessário porque o processador de anotações só tem acesso a classes anotadas
     * (e classes geradas nas rondas subsequentes), mas as classes GestorBasedados não estão disponiveis
     * durante as compilações incrementais de uma nova @Entidade.
     *
     * @param gestorFicheiros utilizado pelo processador de anotações
     */
    void lerIndice(Filer gestorFicheiros) {

        // Local de saída das fontes
        StandardLocation local = StandardLocation.SOURCE_OUTPUT;
        // Ficheiro do índice
        FileObject ficheiroIndice;

        try {
            // Obter o ficheiro de índice
            ficheiroIndice = gestorFicheiros.getResource(local, "com.extraware.xwormapi", FICHEIRO_AMBIENTE);
            registador.informacao("Lendo o índice " + ficheiroIndice.toUri());

            // Ler o ficheiro de índice
            Reader ficheiroLeitura = ficheiroIndice.openReader(true);
            BufferedReader leitor = new BufferedReader(ficheiroLeitura);

            // Leitura da linha INICIO_CONVERSORES
            String linha = leitor.readLine();

            // Tratar os conversores
            linha = leitor.readLine();
            while (linha != null && !linha.startsWith(FIM_CONVERSORES)) {
                ModeloConversor conversor = ModeloConversor.lerIndice(linha, registador);
                adicionarConversor(conversor);
                linha = leitor.readLine();
            }

            // Tratar da base de dados
            linha = leitor.readLine();
            while (linha != null && linha.startsWith(INICIO_BASEDADOS)) {
                ModeloBasedados modeloBasedados = ModeloBasedados.lerIndice(leitor, registador);
                adicionarBasedados(modeloBasedados);
                linha = leitor.readLine();
            }

            // Fechar o ficheiro de índice
            leitor.close();
        } catch (IOException e) {
            // TODO
        }
    }

    /**
     * Este método escreve o modelo atual num ficheiro para dar suporte à compilação incremental.
     *
     * @param gestorFicheiros Utilizado pelo processador de anotações
     */
    void escreverIndice(Filer gestorFicheiros) {

        // Local de saída das fontes
        StandardLocation local = StandardLocation.SOURCE_OUTPUT;
        // Ficheiro do índice
        FileObject ficheiroIndice;

        try {
            // Criar o ficheiro de índice
            ficheiroIndice = gestorFicheiros.createResource(local, "com.extraware.xwormapi", FICHEIRO_AMBIENTE);
            OutputStream ficheiroEscrita = ficheiroIndice.openOutputStream();
            PrintWriter saida = new PrintWriter(ficheiroEscrita);

            // Escrever os conversores
            saida.println(INICIO_CONVERSORES);
            for (ModeloConversor conversor : conversores) conversor.escreverIndice(saida);
            saida.println(FIM_CONVERSORES);

            // Escrever as bases de dados
            for (ModeloBasedados modeloBasedados : modelosBasedados.values()) modeloBasedados.escreverIndice(saida);

            saida.close();

        } catch (IOException excecao) {
            throw new RuntimeException(excecao);
        }

    }
}
