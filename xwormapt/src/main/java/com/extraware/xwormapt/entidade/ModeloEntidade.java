package com.extraware.xwormapt.entidade;

import com.extraware.xwormapi.api.Entidade;
import com.extraware.xwormapt.ModeloClasse;
import com.extraware.xwormapt.basedados.ModeloBasedados;

import javax.persistence.Entity;

public class ModeloEntidade extends ModeloClasse {

    private static final String SUFIXO_TABELA = "Tabela";

    private String basedados;
    private String tabela;
    private ModeloBasedados modeloBasedados;

    /**
     * Construtor principal da classe.
     *
     * @param entidade Classe da entidade
     */
    public ModeloEntidade(Entidade entidade) {
        setTabela(entidade.tabela());
        setBasedados(entidade.basedados());
    }
    public ModeloEntidade(Entity entidade) {
        setTabela(entidade.name());
    }

    public String getBasedados() {
        return basedados;
    }
    public void setBasedados(String basedados) {
        this.basedados = basedados;
    }

    public String getTabela() {
        return tabela;
    }
    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public ModeloBasedados getModeloBasedados() {
        return modeloBasedados;
    }
    public void setModeloBasedados(ModeloBasedados modeloBasedados) {
        this.modeloBasedados = modeloBasedados;
        modeloBasedados.adicionarEntidade(this);
    }

    /**
     * Método para devolver o nome da entidade
     * @return Nome da entidade
     */
    public String getNomeEntidade() {
        return getClasse();
    }

    /**
     * Método para obter o nome da classe DAO
     *
     * @return Nome da classe DAO
     */
    public String getNomeDAO() {
        return getNomeEntidade() + "DAO";
    }

    /**
     * Método para obter o nome do pacote da classe DAO
     *
     * @return Nome do pacote da classe DAO
     */
    public String getPacoteDAO() {
        return getPacote() + ".dao";
    }

    /**
     * Método para obter o nome da classe do gestor da tabela
     *
     * @return Nome da classe gestora da tabela
     */
    public String getClasseGestorTabela() {
        return getPacoteDAO() + "." + getNomeGestorTabela();
    }

    /**
     * Método para obter o nome do gestor da tabela
     *
     * @return Nome do gestor da tabela
     */
    public String getNomeGestorTabela() {
        return capitalizarPrimeira(getTabela().replace("[", "").replace("]", "") + SUFIXO_TABELA);
    }
}
