package com.extraware.xwormapt;


import javax.lang.model.element.Element;

public class AnotacaoExcecao extends RuntimeException {

    private static final long serialVersionUID = -4915405018254078755L;
    private Element elemento;

    /**
     * Construtor principal
     *
     * @param mensagem Mensagem da exceção
     * @param elemento Elemento da anotação em erro
     */
    public AnotacaoExcecao(String mensagem, Element elemento) {
        super(mensagem);
        this.elemento = elemento;
    }

    public Element getElemento() {
        return elemento;
    }
}
