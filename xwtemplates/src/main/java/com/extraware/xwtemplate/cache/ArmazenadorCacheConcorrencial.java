package com.extraware.xwtemplate.cache;

/**
 * Interface opcional para armazenadores de cache que sabem se são acessíveis concorrencialemente sem sincronização.
 */
public interface ArmazenadorCacheConcorrencial extends ArmazenadorCache {

    /**
     * Devolve verdadeiro se esta instância de armazenador de cache é concorrencionalmente acessível através de
     * múltiplos threads sem sincronização.
     *
     * @return Verdadeiro se fôr acessível
     */
    public boolean eConcorrencial();
}
