package com.extraware.xwormapt;

import java.util.ArrayList;
import java.util.List;

public abstract class ModeloClasse {

    private String classe;
    private String pacote;
    protected List<String> importacoes = new ArrayList<String>();

    public void setClasse(String classe) {
        this.classe = classe;
    }
    public String getClasse() {
        return classe;
    }

    public void setPacote(String pacote) {
        this.pacote = pacote;
    }
    public String getPacote() {
        return pacote;
    }

    public List<String> getImportacoes() {
        return importacoes;
    }
    public void adicionarImportacao(String caminhoImportacao) {
        if (!importacoes.contains(caminhoImportacao)) {
            importacoes.add(caminhoImportacao);
        }
    }

    public String getQualifiedClassName() {
        return getPacote() + "." + getClasse();
    }

    /**
     * Método para capitalizar a primeira letra
     *
     * @param nome Nome que será capitalizado
     * @return Nome com a primeira letra capitalizada
     */
    protected String capitalizarPrimeira(String nome) {
        String primeiraLetra = nome.substring(0, 1).toUpperCase();
        return primeiraLetra + nome.substring(1);
    }

    /**
     * Método para analisar o nome da classe qualificada
     *
     * @param classeAuxiliar String com o nome da classe qualificada
     */
    protected void analisarNomeClasseQualificada(String classeAuxiliar) {

        // Validação do nome da classe qualificada
        int ultimoPonto = classeAuxiliar.lastIndexOf('.');
        if (ultimoPonto < 1) {
            throw new IllegalArgumentException("O pacote por defeito não é permitido para o tipo " + classeAuxiliar);
        }

        // Alterar o nome do pacote
        setPacote(classeAuxiliar.substring(0, ultimoPonto));

        // Alterar o nome da classe
        setClasse(classeAuxiliar.substring(ultimoPonto + 1));
    }


}
