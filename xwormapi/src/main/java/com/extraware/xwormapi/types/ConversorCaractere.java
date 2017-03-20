package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { char.class, Character.class }, tipoLigacao = TipoLigacao.INT, tipoSQL = TipoSQL.INTEGER)
public class ConversorCaractere extends ConversorTipo<Character, Integer> {

    public static final ConversorCaractere LER = new ConversorCaractere();

    @Override
    public Integer paraSQL(Character valorJava) {
        if (valorJava == null) return null;
        return (int) valorJava; // TODO .charValue();
    }

    @Override
    public Character deSQL(Integer valorSQL) {
        if (valorSQL == null) return null;
        return (char) valorSQL.intValue();
    }

    @Override
    public Integer deString(String valor) {
        if (valor == null) return null;
        return Integer.valueOf(valor);
    }
}
