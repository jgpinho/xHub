package com.extraware.xwormapt.basedados;

import com.extraware.xwormapi.csv.CSVUtilitarios;
import com.extraware.xwormapt.Ambiente;
import com.extraware.xwormapt.ModeloClasse;
import com.extraware.xwormapt.Registador;
import com.extraware.xwormapt.entidade.ModeloEntidade;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloBasedados extends ModeloClasse {

    private String basedados;
    private int versao;
    private List<ModeloEntidade> entidades = new ArrayList<ModeloEntidade>();
    private List<String> gestoresTabela = new ArrayList<String>();

    /**
     * Construtor principal da classe.
     *
     * @param basedados Nome da base de dados
     * @param versao Versão da base de dados
     * @param classeGestora Nome da classe gestora da base de dados
     */
    public ModeloBasedados(String basedados, int versao, String classeGestora) {
        super();
        setBasedados(basedados);
        setVersao(versao);
        analisarNomeClasseQualificada(classeGestora);
    }

    public String getBasedados() {
        return basedados;
    }
    public void setBasedados(String basedados) {
        this.basedados = basedados;
    }

    public int getVersao() {
        return versao;
    }
    public void setVersao(int versao) {
        this.versao = versao;
    }

    /**
     * Método para adicionar entidade
     *
     * @param modeloEntidade Modelo da entidade a ser adicionado
     */
    public void adicionarEntidade(ModeloEntidade modeloEntidade) {

        // Adicionar o modelo de entidade à lista de entidades
        this.entidades.add(modeloEntidade);

        // Duplicar a informação do Gestor de Tabelas para ser utilizado no escritor do índice
        String nomeClasseGestorTabelas = modeloEntidade.getClasseGestorTabela();
        if (!gestoresTabela.contains(nomeClasseGestorTabelas)) {
            gestoresTabela.add(modeloEntidade.getClasseGestorTabela());
        }
    }

    /**
     * Enriquecimento do modelo da base de dados e as tabelas associadas a partir de um ficheiro que
     * suporta a compilação incremental.
     *
     * @param leitor Leitor do ficheiro de suporte
     * @param registador Registador das mensagens de informação sobre o modelo que está a ser tratado
     * @return Retorna o modelo de base de dados gerado a partir do ficheiro aberto no Leitor
     * @throws IOException Erros de acesso ao ficheiro de suporte
     */
    public static ModeloBasedados lerIndice(BufferedReader leitor, Registador registador) throws IOException {

        // Carregamento da informação da base de dados
        String informacaoBasedados = leitor.readLine();
        registador.informacao(informacaoBasedados);

        // Carregamento das propriedades
        Map<String, String> propriedades = CSVUtilitarios.lerMapa(informacaoBasedados);
        String basedados = propriedades.get("basedados");
        int versao = Integer.parseInt(propriedades.get("versao"));
        String classeGestora = propriedades.get("classeGestora");

        // Criação do modelo da base de dados
        ModeloBasedados modeloBasedados = new ModeloBasedados(basedados, versao, classeGestora);

        // Tratamento dos Gestores de Tabelas
        List<String> gestoresBasedados = new ArrayList<String>();
        String linha = leitor.readLine();
        while (linha != null && !linha.equals(Ambiente.FIM_BASEDADOS)) {
            gestoresBasedados.add(linha);
            linha = leitor.readLine();
        }
        modeloBasedados.gestoresTabela = gestoresBasedados;

        return modeloBasedados;
    }

    /**
     * Escrever a informação da base de dados e as tabelas associadas para um ficheiro de suporte que
     * suporta a compilação incremental.
     *
     * @param saida Escreve a saida
     */
    public void escreverIndice(PrintWriter saida) {
        saida.println(Ambiente.INICIO_BASEDADOS);
        Map<String,String> mapaBasedados = new HashMap<String,String>();
        mapaBasedados.put("basedados", this.getBasedados());
        mapaBasedados.put("versao", String.valueOf(this.getVersao()));
        mapaBasedados.put("classeGestora", this.getQualifiedClassName());
        String infoBasedados = CSVUtilitarios.mapaParaCSV(mapaBasedados);
        saida.println(infoBasedados);

        // Escreve os GestoresTabela
        for (String gestorTabela : this.gestoresTabela) saida.println(gestorTabela);
        saida.println(Ambiente.FIM_BASEDADOS);
    }
}
