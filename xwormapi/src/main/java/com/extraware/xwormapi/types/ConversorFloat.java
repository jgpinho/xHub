package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { float.class, Float.class }, tipoLigacao = TipoLigacao.FLOAT, tipoSQL = TipoSQL.REAL)
public class ConversorFloat extends ConversorTipo<Float, Float> {

    public static final ConversorFloat LER = new ConversorFloat();

    @Override
    public Float paraSQL(Float valorJava) {
        return valorJava;
    }

    @Override
    public Float deSQL(Float valorSQL) {
        return valorSQL;
    }

    @Override
    public Float deString(String valor) {
        return Float.valueOf(valor);
    }
}
