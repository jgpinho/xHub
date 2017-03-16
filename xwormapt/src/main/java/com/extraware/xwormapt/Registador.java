package com.extraware.xwormapt;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by JP on 16-03-2017.
 */
public class Registador {

    // Mensageiro do registador
    private final Messager mensageiro;

    // Construtor para enviar o mensageiro
    public Registador(Messager mensageiro) {
        this.mensageiro = mensageiro;
    }

    // Método para registar uma mensagem
    private void regista(Kind tipoMensagem, String mensagem, Element elemento) {
        if (elemento != null) {
            mensageiro.printMessage(tipoMensagem, mensagem, elemento);
        } else {
            mensageiro.printMessage(tipoMensagem, mensagem);
        }
    }
    // Método para criar a mensagem de erro com a informação da excepção
    private String formatarMensagemErro(String mensagem, Exception excecao) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if (excecao != null) {
            excecao.printStackTrace(pw);
        }
        return mensagem + ": " + sw.toString();
    }

    // Métodos públicos para registar notas de informação
    public void informacao(String mensagem, Element elemento) {
        regista(Kind.NOTE, mensagem, elemento);
    }
    public void informacao(String mensagem) {
        regista(Kind.NOTE, mensagem, null);
    }

    // Métodos públicos para registar avisos
    public void aviso(String mensagem, Element elemento) {
        regista(Kind.WARNING, mensagem, elemento);
    }
    public void aviso(String mensagem) {
        regista(Kind.WARNING, mensagem, null);
    }

    // Métodos públicos para registar erros
    public void erro(String mensagem, Exception excecao, Element elemento) {
        String mensagemTemp = formatarMensagemErro(mensagem, excecao);
        regista(Kind.ERROR, mensagemTemp, elemento);

        // Assegurar que a mensagem de erro é apresentada no registo de erros
        System.err.println(mensagemTemp);
    }
    public void erro(String mensagem, Element elemento) {
        erro(mensagem, null, elemento);
    }
    public void erro(String mensagem, Exception excecao) {
        erro(mensagem, excecao, null);
    }
}
