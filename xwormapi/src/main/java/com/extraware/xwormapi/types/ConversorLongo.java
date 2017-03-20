package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { long.class, Long.class }, tipoLigacao = TipoLigacao.LONG, tipoSQL = TipoSQL.INTEGER)
public class ConversorLongo extends ConversorTipo<Long, Long> {

    public static final ConversorLongo LER = new ConversorLongo();

    @Override
    public Long paraSQL(Long valorJava) {
        return valorJava;
    }

    @Override
    public Long deSQL(Long valorSQL) {
        return valorSQL;
    }

    @Override
    public Long deString(String valor) {
        return Long.valueOf(valor);
    }
}
