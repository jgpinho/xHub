package com.extraware.xwtemplate.cache;

public interface ArmazenadorCache {

    public Object ler(Object chave);
    public void por(Object chave, Object valor);
    public void remover(Object chave);
    public void limpar();
}
