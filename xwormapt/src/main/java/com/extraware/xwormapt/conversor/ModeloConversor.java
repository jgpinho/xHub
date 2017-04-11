package com.extraware.xwormapt.conversor;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.csv.CSVUtilitarios;
import com.extraware.xwormapi.types.ConversorTipo;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;
import com.extraware.xwormapt.ModeloClasse;
import com.extraware.xwormapt.Registador;
import com.extraware.xwormapt.UtilitariosSQL;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModeloConversor extends ModeloClasse {

    private static final String SEPARADOR_TIPO = ";";

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

    public TipoLigacao getTipoLigacao() {
        return tipoLigacao;
    }

    public TipoSQL getTipoSQL() {
        return tipoSQL;
    }

    public static ModeloConversor lerIndice(String infoConversor, Registador registador) throws IOException {

        // Inicialização das propriedades
        Map<String, String> propriedades = CSVUtilitarios.lerMapa(infoConversor);

        // Obter as propriedades
        String classeConversora = propriedades.get("classeConversora");
        TipoLigacao tipoLigacao = TipoLigacao.valueOf(propriedades.get("tipoLigacao"));
        TipoSQL tipoSQL = TipoSQL.valueOf(propriedades.get("tipoSQL"));
        String[]tiposConvertiveis = propriedades.get("tiposConvertiveis").split(SEPARADOR_TIPO);

        return new ModeloConversor(classeConversora, tiposConvertiveis, tipoLigacao, tipoSQL);
    }

    public void escreverIndice(PrintWriter saida) {

        // Não escrever os conversores integrados para evitar duplicados
        if (integrado) return;

        // Criar a lista de tipos
        String listaTipos = new String();
        for (String tipo : this.tiposConvertiveis) listaTipos += SEPARADOR_TIPO + tipo;
        listaTipos = listaTipos.substring(1);

        Map<String,String> mapa = new LinkedHashMap<String,String>();
        mapa.put("classeConversora", this.getQualifiedClassName());
        mapa.put("tipoLigacao", this.tipoLigacao.name());
        mapa.put("tipoSQL", this.tipoSQL.name());
        mapa.put("tiposConvertiveis", listaTipos);
        saida.println(CSVUtilitarios.mapaParaCSV(mapa));
    }
}
