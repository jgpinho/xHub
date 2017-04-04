package com.extraware.xwormapt;

public abstract class TemplateClasse {

    protected ModeloClasse modeloClasse;

    /**
     * Construtor principal da classe
     *
     * @param modeloClasse Modelo da classe
     */
    public TemplateClasse(ModeloClasse modeloClasse) {
        this.modeloClasse = modeloClasse;
    }

    public abstract String getCaminhoTemplate();
    public abstract String getPacote();
    public abstract String getClasseGerada();

    public ModeloClasse getModelo() {
        return modeloClasse;
    }
}
