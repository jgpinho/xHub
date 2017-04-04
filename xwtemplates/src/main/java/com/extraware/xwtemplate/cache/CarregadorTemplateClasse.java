package com.extraware.xwtemplate.cache;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

public class CarregadorTemplateClasse extends CarregadorTemplateURL {

    private Class carregadorClasse;
    private String caminho;



    @Override
    protected URL getURL(String nome) {
        return null;
    }

    @Override
    public long getUltimoModificado(Object fonteTemplate) {
        return 0;
    }

    @Override
    public Reader getLeitor(Object fonteTemplate, String codificacao) throws IOException {
        return null;
    }

    @Override
    public void fecharFonteTemplate(Object fonteTemplate) throws IOException {

    }
}
