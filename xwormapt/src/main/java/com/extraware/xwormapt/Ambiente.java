package com.extraware.xwormapt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extraware.xwormapi.types.*;
import com.extraware.xwormapt.conversor.ModeloConversor;

/**
 * Created by JP on 18-03-2017.
 */
public class Ambiente {

    private Registador registador;
    private List<ModeloConversor> conversores = new ArrayList<ModeloConversor>();
    private Map<String, ModeloConversor> mapaTipos = new HashMap<String, ModeloConversor>();

    // Construtor principal
    Ambiente(Registador registador) {
        this.registador = registador;
        adicionarConversores();
    }

    // Método para adicionar os conversores do ambiente
    private void adicionarConversores() {
        adicionarConversor(new ModeloConversor(new ConversorBlob()));
        adicionarConversor(new ModeloConversor(new ConversorBoleano()));
        adicionarConversor(new ModeloConversor(new ConversorByte()));
        adicionarConversor(new ModeloConversor(new ConversorCaractere()));
        adicionarConversor(new ModeloConversor(new ConversorData()));
        adicionarConversor(new ModeloConversor(new ConversorDouble()));
        adicionarConversor(new ModeloConversor(new ConversorEnum()));
        adicionarConversor(new ModeloConversor(new ConversorFloat()));
        adicionarConversor(new ModeloConversor(new ConversorInteiro()));
        adicionarConversor(new ModeloConversor(new ConversorLongo()));
        adicionarConversor(new ModeloConversor(new ConversorCurto()));
        adicionarConversor(new ModeloConversor(new ConversorString()));
    }

    /**
     * Adicionar um conversor para determinado tipo.
     * Este método é chamado durante a compilação pelo processador de anotações. Para que
     * o tipo de conversor (ConversorTipo) esteja vísivel, ele tem de estar no jar no caminho
     * do processador de anotações do projeto do cliente.
     *
     * @param conversor Conversor de tipos a ser adicionado
     * @return OK se adicionou ou se já existia, KO se não adicionou
     */
    public boolean adicionarConversor(ModeloConversor conversor) {
        // Se o conversor já existe, sair
        if (conversores.contains(conversor)) return true;

        // Se algum dos tipos convertíveis já existe, sair
        for (String tipoConvertivel : conversor.getTiposConvertiveis()) {
            if (mapaTipos.containsKey(tipoConvertivel) && !mapaTipos.get(tipoConvertivel).equals(conversor)) {
                return false;
            }
        }

        // Adicionar os tipos conversíveis ao mapa de tipos
        for (String tipoConvertivel : conversor.getTiposConvertiveis()) {
            mapaTipos.put(tipoConvertivel, conversor);
        }

        // Adicionar conversor à lista de conversores
        conversores.add(conversor);

        // Adicionar mensagem de sucesso
        registador.informacao("Foi adicionado o " + conversor.getQualifiedClassName());

        return true;
    }
}
