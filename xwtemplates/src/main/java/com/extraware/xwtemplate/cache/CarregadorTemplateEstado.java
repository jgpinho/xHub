package com.extraware.xwtemplate.cache;

/**
 * Interface que pode ser implementado por carregadores de templates que mantêm um tipo de estado interno.
 */
public interface CarregadorTemplateEstado extends CarregadorTemplate {

    /**
     * Invocado para iniciar o estado interno e começa de novo.
     */
    public void iniciaEstado();
}
