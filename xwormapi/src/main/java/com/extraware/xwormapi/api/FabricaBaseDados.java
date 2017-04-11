package com.extraware.xwormapi.api;

import com.extraware.xwormapi.GestorTabela;

/**
 * Interface implementado por todas as classes geradas com fábrica de base de dados.
 */
public interface FabricaBaseDados {

    /**
     * Método para obter o nome da base de dados.
     *
     * @return Nome da base de dados
     */
    String getNome();

    /**
     * Método para obter a versão da base de dados.
     *
     * @return Versão da base de dados
     */
    int getVersao();

    /**
     * Método para obter todos os gestores de tabela associados à base de dados.
     *
     * @return Array com os gestores de tabelas.
     */
    GestorTabela[] getGestoresTabela();
}
