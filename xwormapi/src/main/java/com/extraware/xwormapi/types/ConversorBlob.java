package com.extraware.xwormapi.types;

import android.util.Base64;
import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { byte[].class }, tipoLigacao = TipoLigacao.BLOB, tipoSQL = TipoSQL.BLOB)
public class ConversorBlob extends ConversorTipo<byte [], byte[]> {

    public static final ConversorBlob LER = new ConversorBlob();

    @Override
    public byte[] paraSQL(byte[] valorJava) {
        return valorJava;
    }

    @Override
    public byte[] deSQL(byte[] valorSQL) {
        return valorSQL;
    }

    @Override
    public byte[] deString(String valor) {
        return Base64.decode(valor, Base64.DEFAULT);
    }

    @Override
    public String toString(byte[] valorSQL) {
        return (valorSQL == null) ? null : Base64.encodeToString(valorSQL, Base64.NO_WRAP);
    }
}
