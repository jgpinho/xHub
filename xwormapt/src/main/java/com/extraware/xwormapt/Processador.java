package com.extraware.xwormapt;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.persistence.Entity;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.extraware.xwormapi.api.Basedados;
import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.api.Entidade;
import com.extraware.xwormapt.basedados.ModeloBasedados;
import com.extraware.xwormapt.basedados.ProcessadorBasedados;
import com.extraware.xwormapt.basedados.TemplateFabricaBaseDados;
import com.extraware.xwormapt.conversor.ProcessadorConversor;
import com.extraware.xwormapt.entidade.ProcessadorEntidade;
import com.extraware.xwormapt.entidade.TemplateEntidadeDAO;
import com.extraware.xwormapt.entidade.TemplateGestorTabela;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@SupportedAnnotationTypes({ "com.extraware.xwormapi.api.Basedados","com.extraware.xwormapi.api.Entidade",
        "javax.persistence.Entity","com.extraware.xwormapi.api.Conversor" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class Processador extends AbstractProcessor {

    private Registador registador;
    private Configuration configuracao = new Configuration();
    private static String MENSAGEM_ERRO = "Erro a executar a anotação XwORM";

    @Override
    public boolean process(Set<? extends TypeElement> anotacoes, RoundEnvironment ambiente) {

        // Iniciar o XwTemplates
        configuracao.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/templates"));

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
            } catch (AnotacaoExcecao excecao) {
                registador.erro(excecao.getMessage(), excecao.getElemento());
                return true;
            } catch (Exception excecao) {
                registador.erro(MENSAGEM_ERRO, excecao, elemento);
                return true;
            }
        }

        // Primeira passagem na anotações @Basedados para obter todos os nomes das base de dados
        for (Element elemento : ambiente.getElementsAnnotatedWith(Basedados.class)) {
            try {
                ProcessadorBasedados processadorBasedados = new ProcessadorBasedados(elemento, xwormAmbiente);
                processadorBasedados.preencherModelo();
                xwormAmbiente.adicionarBasedados(processadorBasedados.getModelo());
            } catch (AnotacaoExcecao excecao) {
                registador.erro(excecao.getMessage(), excecao.getElemento());
                return true;
            } catch (Exception excecao) {
                registador.erro(MENSAGEM_ERRO, excecao, elemento);
                return true;
            }
        }

        // Tratamento das entidades
        Set<TypeElement> entidades = new HashSet<TypeElement>();
        try {
            entidades.addAll((Collection<? extends TypeElement>) ambiente.getElementsAnnotatedWith(Entidade.class));
            entidades.addAll((Collection<? extends TypeElement>) ambiente.getElementsAnnotatedWith(Entity.class));
        } catch (Exception excecao) {}
        for (Element elemento: entidades) {
            try {
                // Obter a classe processadora da entidade
                ProcessadorClasse processadorClasse = new ProcessadorEntidade(elemento, xwormAmbiente);

                // Preencher o modelo da classe processadora
                processadorClasse.preencherModelo();

                // Gerar o DAO da entidade
                TemplateEntidadeDAO templateEntidadeDAO = new TemplateEntidadeDAO(processadorClasse.getModelo());
                processarTemplate(processingEnv, configuracao, templateEntidadeDAO);

                // Gerar tabela da entidade
                TemplateGestorTabela templateGestorTabela = new TemplateGestorTabela(processadorClasse.getModelo());
                processarTemplate(processingEnv, configuracao, templateGestorTabela);

            } catch (AnotacaoExcecao excecao) {
                registador.erro(excecao.getMessage(), excecao.getElemento());
                return true;
            } catch (Exception excecao) {
                registador.erro(MENSAGEM_ERRO, excecao, elemento);
                return true;
            }
        }

        // Segunda passagem para gerar os templates FabricaBaseDados agora que todas as entidades estão associadas à BD
        for (ModeloBasedados modeloBasedados : xwormAmbiente.getModelosBasedados()) {
            try {
                TemplateFabricaBaseDados templateFabricaBaseDados = new TemplateFabricaBaseDados(modeloBasedados);
                processarTemplate(processingEnv, configuracao, templateFabricaBaseDados);
            } catch (Exception excecao) {
                registador.erro(MENSAGEM_ERRO, excecao);
                return true;
            }
        }

        // Escrever todas as base de dados processadas para um ficheiro indice para suportar compilações incrementais
        xwormAmbiente.escreverIndice(processingEnv.getFiler());

        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.values()[SourceVersion.values().length - 1];
    }

    /**
     * Método para processar um template.
     *
     * @param processingEnvironment Ambiente de processamento
     * @param configuracao Configuração da classe de processamento de template
     * @param templateClasse Classe de template
     */
    void processarTemplate(ProcessingEnvironment processingEnvironment, Configuration configuracao,
                           TemplateClasse templateClasse) {
        JavaFileObject ficheiro;

        try {
            ficheiro = processingEnvironment.getFiler().createSourceFile(templateClasse.getClasseGerada());
            Writer saida = ficheiro.openWriter();
            Template template = configuracao.getTemplate(templateClasse.getCaminhoTemplate());
            registador.informacao("Generando " + ficheiro.getName() + " com " + template.getName());
            template.process(templateClasse.getModelo(), saida);
            saida.flush();
            saida.close();
        } catch (Exception excecao) {
            registador.erro("Erro de template", excecao);
        }
    }
}
