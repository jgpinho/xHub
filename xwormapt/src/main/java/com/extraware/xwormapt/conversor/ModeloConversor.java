package com.extraware.xwormapt.conversor;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;
import com.extraware.xwormapt.ModeloClasse;

/**
 * Created by JP on 18-03-2017.
 */
public class ModeloConversor extends ModeloClasse {

    private String[] tiposConvertiveis;
    private TipoLigacao tipoLigacao;
    private TipoSQL tipoSQL;
    private boolean integrado;

    /**
     * Construtor principal com atributos do processador de anotações ou leitor de indíces
     *
     * @param conversorClasse Classe do conversor
     * @param tipos Lista de tipos convertíveis
     * @param tipoLigacao Tipo de ligação Java
     * @param tipoSQL Tipo de SQLite
     */
    public ModeloConversor(String conversorClasse, String[] tipos, TipoLigacao tipoLigacao, TipoSQL tipoSQL) {

        // Analisar o nome da classe qualificada
        analisarNomeClasseQualificada(conversorClasse);

        // Inicializar as variaveis internas
        this.tiposConvertiveis = tipos;
        this.tipoLigacao = tipoLigacao;
        this.tipoSQL = tipoSQL;
    }

    public ModeloConversor(ConversorTipo conversorTipo) {

        integrado = true;

        // Analisar o nome da classe qualificada
        analisarNomeClasseQualificada(conversorTipo.getClass().getCanonicalName());

        // Inicializar as variaveis internas
        Conversor anotacao = conversorTipo.getClass().getAnnotation(Conversor.class);
        this.tipoLigacao = anotacao.tipoLigacao();
        this.tipoSQL = anotacao.tipoSQL();
        this.tiposConvertiveis = new String[anotacao.paraTipos().length];
        int i = 0;
        for (Class tipoConvertivel : anotacao.paraTipos()) {
            this.tiposConvertiveis[i++] = tipoConvertivel.getCanonicalName();
        }
    }

    public String[] getTiposConvertiveis() {
        return tiposConvertiveis;
    }
}
