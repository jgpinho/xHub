package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { String.class }, tipoLigacao = TipoLigacao.STRING, tipoSQL = TipoSQL.TEXT)
public class ConversorString extends ConversorTipo<String, String> {

    public static final ConversorString LER = new ConversorString();

    @Override
    public String paraSQL(String valorJava) {
        return valorJava;
    }

    @Override
    public String deSQL(String valorSQL) {
        return valorSQL;
    }

    @Override
    public String deString(String valor) {
        return valor;
    }
}
