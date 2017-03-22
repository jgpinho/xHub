package com.extraware.xwormapt.entidade;

import com.extraware.xwormapi.api.Entidade;
import com.extraware.xwormapt.ModeloClasse;

public class ModeloEntidade extends ModeloClasse {

    private String basedados;
    private String tabela;

    public ModeloEntidade(Entidade entidade) {
        setTabela(entidade.tabela());
        setBasedados(entidade.basedados());
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
}
