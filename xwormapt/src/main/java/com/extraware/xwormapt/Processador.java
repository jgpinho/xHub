package com.extraware.xwormapt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapt.conversor.ProcessadorConversor;
import com.extraware.xwtemplate.template.Configuracao;

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

        Ambiente xwormAmbiente = new Ambiente(registador);

        // Ler as classes previamente processadas para suportar as compilações incrementais
        xwormAmbiente.lerIndice(processingEnv.getFiler());

        // Tratamento de todas as classes marcadas com a anotação Conversor
        for (Element elemento : ambiente.getElementsAnnotatedWith(Conversor.class)) {
            try {
                ProcessadorConversor processadorConversor = new ProcessadorConversor(elemento, xwormAmbiente);
                processadorConversor.preencherModelo();
            }
        }

        // TODO
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.values()[SourceVersion.values().length - 1];
    }
}
