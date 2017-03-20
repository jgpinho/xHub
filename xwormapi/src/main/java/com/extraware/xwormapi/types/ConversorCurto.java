package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { short.class, Short.class }, tipoLigacao = TipoLigacao.SHORT, tipoSQL = TipoSQL.INTEGER)
public class ConversorCurto extends ConversorTipo<Short, Short> {

    public static final ConversorCurto LER = new ConversorCurto();

    @Override
    public Short paraSQL(Short valorJava) {
        return valorJava;
    }

    @Override
    public Short deSQL(Short valorSQL) {
        return valorSQL;
    }

    @Override
    public Short deString(String valor) {
        return Short.valueOf(valor);
    }
}
