package com.extraware.xwtemplate.template;

import com.extraware.xwtemplate.cache.ArmazenadorCache;
import com.extraware.xwtemplate.cache.CacheTemplate;
import com.extraware.xwtemplate.cache.CarregadorTemplate;
import com.extraware.xwtemplate.core.Configuravel;

public class Configuracao extends Configuravel implements Cloneable {

    private CacheTemplate cache;

    /**
     * Construtor principal da classe
     */
    public Configuracao () {
        cache = new CacheTemplate();
        cache.setConfiguracao(this);
        cache.setAtraso(5000);
        // TODO loadBuiltInSharedVariables();
    }

    public synchronized void setCarregadorTemplate(CarregadorTemplate carregadorTemplate) {
        criarCacheTemplate(carregadorTemplate, cache.getArmazenadorCache());
    }

    private void criarCacheTemplate(CarregadorTemplate carregadorTemplate, ArmazenadorCache armazenadorCache) {
        CacheTemplate cacheAntigo = cache;
        cache = new CacheTemplate(carregadorTemplate, armazenadorCache);
    }
}
