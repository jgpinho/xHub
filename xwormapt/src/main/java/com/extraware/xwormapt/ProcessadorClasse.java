package com.extraware.xwormapt;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Classe base que analisa uma classe anotada utilizador a API de Mirror e preenche um modelo para ser
 * utilizado num template de geração de código.
 */
public abstract class ProcessadorClasse {

    protected TypeElement tipoElemento;
    protected Ambiente xwormAmbiente;

    /**
     * Construtor criado para ser re-escrito por sub-classes.
     *
     * @param elemento Elemento
     * @param xwormAmbiente Ambiente do XwORM
     */
    protected ProcessadorClasse(Element elemento, Ambiente xwormAmbiente) {
        this.tipoElemento = (TypeElement) elemento;
        this.xwormAmbiente = xwormAmbiente;
    }

    protected abstract ModeloClasse getModelo();

    /**
     * Método re-escrito pela sub-classes para preencher o modelo.
     * Invocado pelo processador de anotações principal.
     */
    protected void preencherModelo() {
        inspeccionarClasse();
    }

    /**
     * Método para preencher os dados da classe modelo.
     */
    protected void inspeccionarClasse() {
        getModelo().setPacote(getPacote());
        getModelo().setClasse(getClasse());
    }

    public String getNomeQualificado() {
        String pacote = getPacote();
        if (pacote == null || pacote.length() < 1) {
            throw new IllegalArgumentException("O pacote de defeito não é permitido para tipo " + getClasse());
        }
        return getPacote() + "." + getClasse();
    }

    /**
     * Método para obter o nome do pacote da classe anotada.
     *
     * @return Nome do pacote
     */
    protected String getPacote() {
        PackageElement pacote = (PackageElement) tipoElemento.getEnclosedElements();
        return pacote.getQualifiedName().toString();
    }

    protected String getClasse() {
        return tipoElemento.getSimpleName().toString();
    }

    /**
     * Métodos para abortar do processamento com uma exceção de anotação.
     * @param mensagem Mensagem a enviar
     */
    protected void abortar(String mensagem) {
        abortar(mensagem, this.tipoElemento);
    }
    protected void abortar(String mensagem, Element elemento) {
        throw new AnotacaoExcecao(mensagem, elemento);
    }

}
