package com.extraware.xwtemplate.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class FonteTemplateURL {

    private final URL url;
    private URLConnection urlConnection;
    private InputStream inputStream;

    public FonteTemplateURL(URL url) throws IOException {
        this.url = url;
        this.urlConnection = url.openConnection();
    }

    public boolean equals(Object objeto) {
        if (objeto instanceof FonteTemplateURL) return url.equals(((FonteTemplateURL) objeto).url);
        return false;
    }
}
