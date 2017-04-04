package com.extraware.xwormapt.basedados;

import javax.lang.model.element.Element;

import com.extraware.xwormapi.api.Basedados;
import com.extraware.xwormapt.Ambiente;
import com.extraware.xwormapt.ProcessadorClasse;
import com.extraware.xwormapt.UtilitariosSQL;

public class ProcessadorBasedados extends ProcessadorClasse {

    private ModeloBasedados modeloBasedados;

    /**
     * Construtor principal da classe
     *
     * @param elemento Elemento do modelo
     * @param xwormAmbiente Ambiente do XwORM
     */
    public ProcessadorBasedados(Element elemento, Ambiente xwormAmbiente) {
        super(elemento, xwormAmbiente);
    }

    @Override
    public ModeloBasedados getModelo() {
        return modeloBasedados;
    }

    @Override
    public void preencherModelo() {
        // Obter o elemento com a anotação
        Basedados basedados = this.tipoElemento.getAnnotation(Basedados.class);
        // Validar o nome dado à base de dados
        validarNomeBasedados(basedados.nome());

        // Validade se existe
        ModeloBasedados basedadosExistente = xwormAmbiente.getBasedadosPorNome(getNomeQualificado());
        if (basedadosExistente != null) {
            // Atualizar as propriedades
            basedadosExistente.setBasedados(basedados.nome());
            basedadosExistente.setVersao(basedados.versao());
            modeloBasedados = basedadosExistente;
        } else {
            // Criar uma classe gestora de base de dados
            modeloBasedados = new ModeloBasedados(basedados.nome(), basedados.versao(), getNomeQualificado());
            super.preencherModelo();
        }
    }

    private void validarNomeBasedados(String nomeBasedados) {
        if (!UtilitariosSQL.isJavaValido(nomeBasedados)) {
            abortar(nomeBasedados + " é um nome Java inválido");
        }
    }
}
