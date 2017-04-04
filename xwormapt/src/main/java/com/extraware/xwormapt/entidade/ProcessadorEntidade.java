package com.extraware.xwormapt.entidade;

import javax.lang.model.element.Element;
import javax.persistence.Entity;

import com.extraware.xwormapi.api.Entidade;
import com.extraware.xwormapt.Ambiente;
import com.extraware.xwormapt.ModeloClasse;
import com.extraware.xwormapt.ProcessadorClasse;
import com.extraware.xwormapt.UtilitariosSQL;
import com.extraware.xwormapt.basedados.ModeloBasedados;

public class ProcessadorEntidade extends ProcessadorClasse {

    private ModeloEntidade modeloEntidade;

    /**
     * Construtor principal da classe.
     *
     * @param elemento Elemento do modelo
     * @param xwormAmbiente Ambiente do XwORM
     */
    public ProcessadorEntidade(Element elemento, Ambiente xwormAmbiente) {
        super(elemento, xwormAmbiente);
    }

    @Override
    protected ModeloClasse getModelo() {
        return modeloEntidade;
    }

    @Override
    public void preencherModelo() {

        // Inicialização do modelo de entidade
        Entidade entidade = this.tipoElemento.getAnnotation(Entidade.class);
        if (entidade != null) {
            this.modeloEntidade = new ModeloEntidade(entidade);
        } else {
            Entity jpEntidade = this.tipoElemento.getAnnotation(Entity.class);
            if (jpEntidade != null) {
                this.modeloEntidade = new ModeloEntidade(jpEntidade);
            }
        }

        super.preencherModelo();

        // Adicionar uma importação
        this.modeloEntidade.adicionarImportacao(getNomeQualificado());
        // Validar nome da tabela
        validarNomeTabela(modeloEntidade.getTabela());
        escolherBasedados(modeloEntidade.getBasedados());
    }

    /**
     * Método para validar o nome da tabela e inicializar esse valor na classe.
     *
     * @param nomeTabela Nome da tabela
     */
    private void validarNomeTabela(String nomeTabela) {

        // Caso o nome enviado seja válido, usar o mesmo. Senão, usar o nome da classe como nome da tabela.
        if (nomeTabela != null && nomeTabela.length() > 0) {
            this.modeloEntidade.setTabela(nomeTabela);
        } else {
            this.modeloEntidade.setTabela(getClasse());
        }

        if (!UtilitariosSQL.isIdentificadorValido(this.modeloEntidade.getTabela())) {
            abortar(nomeTabela + " não é um identificador válido. Use [] para palavras reservadas.");
        }
    }

    /**
     * Método para escolher a base de dados.
     *
     * @param nomeBasedados Nome da base de dados
     */
    protected void escolherBasedados(String nomeBasedados) {

        // Obter o modelo de base de dados por defeito
        ModeloBasedados basedados = xwormAmbiente.getBasedadosPorDefeito();

        // Se o nome da base de dados não estiver vazio
        if (nomeBasedados != null && nomeBasedados.length() > 0) {
            // Adicionar a base de dados no modelo e vice-versa
            ModeloBasedados modeloBasedados = xwormAmbiente.getBasedadosPorNome(nomeBasedados);
            if (modeloBasedados != null) {
                this.modeloEntidade.setModeloBasedados(modeloBasedados);
            } else {
                abortar("Não existe a @Basedados chamada " + nomeBasedados);
            }
        } else if (basedados != null) {
            modeloEntidade.setModeloBasedados(basedados);
        } else {
            abortar("Pelo menos uma @Basedados tem de ser criada");
        }
    }
}
