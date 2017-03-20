package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { boolean.class, Boolean.class }, tipoLigacao = TipoLigacao.INT, tipoSQL = TipoSQL.INTEGER)
public class ConversorBoleano extends ConversorTipo <Boolean, Integer>{

    public static final ConversorBoleano LER = new ConversorBoleano();

    @Override
    public Integer paraSQL(Boolean valorJava) {
        if (valorJava == null) return null;
        return (valorJava == Boolean.TRUE) ? 1 : 0;
    }

    @Override
    public Boolean deSQL(Integer valorSQL) {
        if (valorSQL == null) return null;
        return (valorSQL == 0) ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public Integer deString(String valor) {
        if (valor == null) return null;
        return Integer.valueOf(valor);
    }
}
