package com.extraware.xwormapi;

import android.content.Context;

/**
 * Classe DAO base para DAOs das entidades. A maior parte do desenvolvimento é implementado nesta classe.
 * Porque esta classe tem a referência para o Context (que passa para a FabricaBasedados gerada para obter
 * o singleton GestorSQLite), novas instâncias podem ser criadas em qualquer lugar.
 *
 * @param <T> Tipo de entidade
 */
public abstract class SQLiteDAO<T> {

    private final Context contexto;
    protected final GestorTabela<T> gestorTabela;

    @SuppressWarnings("unchecked")
    public SQLiteDAO(Context contexto) {
        this.contexto = contexto;
        this.gestorTabela = getGestorTabela();
    }

    @SuppressWarnings("rawtypes")
    public abstract GestorTabela getGestorTabela();

}
