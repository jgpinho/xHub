package com.extraware.xwormapt;

/**
 * Created by JP on 15-03-2017.
 */

import com.extraware.xwtemplate.template.Configuracao;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({ "com.extraware.xwormapi.api.BD" }) //TODO,"com.extraware.xwormapi.api.XEntidade","javax.persistence.Entity","com.extraware.xwormapi.api.XConversor" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class Processador extends AbstractProcessor {

    private Registador registador;
    private Configuracao cfg = new Configuracao();
    private static String MENSAGEM_ERRO = "Erro a executar a anotação XwORM";

    @Override
    public boolean process(Set<? extends TypeElement> anotacoes, RoundEnvironment ambiente) {

        // Iniciar o XwTemplates
        // TODO: cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/res"));

        // Iniciar o Registador
        registador = new Registador(processingEnv.getMessager());
        registador.informacao("Processador em execução...");

        // Caso não exista nenhuma anotação a ser tratada nesta fase, sair para não escrever por cima do ficheiro
        // do ambiente
        if (anotacoes.size() < 1) return true;

        for (TypeElement tipoAnotacao : anotacoes) {
            registador.informacao("Processando elementos com @" + tipoAnotacao.getQualifiedName());
        }

        Ambiente xormAmbiente = new Ambiente(registador);
        xormAmbiente.lerIndice(processingEnv.getFiler());

        // TODO
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.values()[SourceVersion.values().length - 1];
    }
}
