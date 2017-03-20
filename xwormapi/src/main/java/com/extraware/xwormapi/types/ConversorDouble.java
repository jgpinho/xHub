package com.extraware.xwormapi.types;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;

@Conversor(paraTipos = { double.class, Double.class }, tipoLigacao = TipoLigacao.DOUBLE, tipoSQL = TipoSQL.REAL)
public class ConversorDouble extends ConversorTipo<Double, Double> {

    public static final ConversorDouble LER = new ConversorDouble();

    @Override
    public Double paraSQL(Double valorJava) {
        return valorJava;
    }

    @Override
    public Double deSQL(Double valorSQL) {
        return valorSQL;
    }

    @Override
    public Double deString(String valor) {
        // Utiliza o bits longo como hexadécimal para preservar o valor exato
        return Double.longBitsToDouble(Long.parseLong(valor, 16));
    }

    @Override
    public String toString(Double valorSQL) {
        // Utiliza o bits longo como hexadécimal para preservar o valor exato
        return (valorSQL == null) ? null : Long.toString(Double.doubleToLongBits(valorSQL), 16);
    }
}
