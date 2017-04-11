package com.extraware.xwormapt.basedados;

import com.extraware.xwormapt.ModeloClasse;
import com.extraware.xwormapt.TemplateClasse;

public class TemplateFabricaBaseDados extends TemplateClasse {

    public TemplateFabricaBaseDados(ModeloClasse modeloClasse) {
        super(modeloClasse);
    }

    @Override
    public String getCaminhoTemplate() {
        return null;
    }

    @Override
    public String getPacote() {
        return null;
    }

    @Override
    public String getClasseGerada() {
        return null;
    }
}
