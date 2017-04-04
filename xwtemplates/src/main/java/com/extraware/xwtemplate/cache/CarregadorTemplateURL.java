package com.extraware.xwtemplate.cache;

import java.io.IOException;
import java.net.URL;

/**
 * Esta classe abstrata de carregador de templates lê templates cuja localização pode ser descrita num URL.
 */
public abstract class CarregadorTemplateURL implements CarregadorTemplate {

    public Object procurarFonteTemplate(String nome) throws IOException {
        URL url = getURL(nome);
        return url == null ? null : new FonteTemplateURL(url);
    }

    protected abstract URL getURL(String nome);
}
