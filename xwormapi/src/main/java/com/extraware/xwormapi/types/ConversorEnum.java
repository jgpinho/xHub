package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { Enum.class }, tipoLigacao = TipoLigacao.STRING, tipoSQL = TipoSQL.TEXT)
public class ConversorEnum extends ConversorTipo<Enum, String> {

    public static final ConversorEnum LER = new ConversorEnum();

    @Override
    public String paraSQL(Enum valorJava) {
        return valorJava.name();
    }

    @Override
    public Enum deSQL(String valorSQL) {
        return null;
    }

    @Override
    public String deString(String valor) {
        return valor;
    }
}
