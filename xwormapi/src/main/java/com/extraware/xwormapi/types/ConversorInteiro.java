package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { int.class, java.lang.Integer.class }, tipoLigacao = TipoLigacao.INT, tipoSQL = TipoSQL.INTEGER)
public class ConversorInteiro extends ConversorTipo<Integer, Integer> {

    public static final ConversorInteiro LER = new ConversorInteiro();

    @Override
    public Integer paraSQL(Integer valorJava) {
        return valorJava;
    }

    @Override
    public Integer deSQL(Integer valorSQL) {
        return valorSQL;
    }

    @Override
    public Integer deString(String valor) {
        return Integer.valueOf(valor);
    }
}
