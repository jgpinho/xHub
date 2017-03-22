package com.extraware.xwormapt.basedados;

import com.extraware.xwormapi.csv.CSVUtilitarios;
import com.extraware.xwormapt.Ambiente;
import com.extraware.xwormapt.ModeloClasse;
import com.extraware.xwormapt.Registador;
import com.extraware.xwormapt.entidade.ModeloEntidade;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModeloBasedados extends ModeloClasse {

    private String basedados;
    private int versao;
    private List<ModeloEntidade> entidades = new ArrayList<ModeloEntidade>();
    private List<String> gestoresBasedados = new ArrayList<String>();

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
        modeloBasedados.gestoresBasedados = gestoresBasedados;

        return modeloBasedados;
    }
}
