package com.extraware.xwormapt;

/**
 * Created by JP on 18-03-2017.
 */
public abstract class ModeloClasse {

    private String nomeClasse;
    private String nomePacote;

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }
    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomePacote(String nomePacote) {
        this.nomePacote = nomePacote;
    }
    public String getNomePacote() {
        return nomePacote;
    }

    public String getQualifiedClassName() {
        return this.getNomePacote() + "." + this.getNomeClasse();
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
        setNomePacote(classeAuxiliar.substring(0, ultimoPonto));

        // Alterar o nome da classe
        setNomeClasse(classeAuxiliar.substring(ultimoPonto + 1);
    }


}
