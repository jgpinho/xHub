package com.extraware.xwtemplate.cache;

import java.io.IOException;
import java.io.Reader;

public class CarregadorTemplateFicheiro implements CarregadorTemplate {

    public CarregadorTemplateFicheiro() {
    }

    @Override
    public Object procurarFonteTemplate(String nome) throws IOException {
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
