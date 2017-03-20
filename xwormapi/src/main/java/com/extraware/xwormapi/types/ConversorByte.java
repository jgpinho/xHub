package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { byte.class, Byte.class }, tipoLigacao = TipoLigacao.SHORT, tipoSQL = TipoSQL.INTEGER)
public class ConversorByte extends ConversorTipo<Byte, Short> {

    public static final ConversorByte LER = new ConversorByte();

    @Override
    public Short paraSQL(Byte valorJava) {
        if (valorJava == null) return null;
        return valorJava.shortValue();
    }

    @Override
    public Byte deSQL(Short valorSQL) {
        if (valorSQL == null) return null;
        return valorSQL.byteValue();
    }

    @Override
    public Short deString(String valor) {
        if (valor == null) return null;
        return Short.valueOf(valor);
    }
}
