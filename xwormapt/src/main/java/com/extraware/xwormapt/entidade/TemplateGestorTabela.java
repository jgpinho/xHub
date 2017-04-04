package com.extraware.xwormapt.entidade;

import com.extraware.xwormapt.ModeloClasse;
import com.extraware.xwormapt.TemplateClasse;

public class TemplateGestorTabela extends TemplateClasse {

    public TemplateGestorTabela(ModeloClasse modeloClasse) {
        super(modeloClasse);
    }

    @Override
    public String getCaminhoTemplate() {
        return "GestorTabela.ftl";
    }

    @Override
    public String getPacote() {
        return ((ModeloEntidade) modeloClasse).getPacoteDAO();
    }

    @Override
    public String getClasseGerada() {
        return ((ModeloEntidade) modeloClasse).getClasseGestorTabela();
    }
}
