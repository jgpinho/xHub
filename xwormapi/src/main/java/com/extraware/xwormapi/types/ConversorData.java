package com.extraware.xwormapi.types;

import java.util.Date;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { Date.class }, tipoLigacao = TipoLigacao.LONG, tipoSQL = TipoSQL.INTEGER)
public class ConversorData extends ConversorTipo<Date, Long> {

    public static final ConversorData LER = new ConversorData();

    @Override
    public Long paraSQL(Date valorJava) {
        if (valorJava == null) return null;
        return valorJava.getTime();
    }

    @Override
    public Date deSQL(Long valorSQL) {
        if (valorSQL == null) return null;
        return new Date(valorSQL);
    }

    @Override
    public Long deString(String valor) {
        return Long.valueOf(valor);
    }
}
