package com.extraware.xwormapt;

public abstract class ModeloClasse {

    private String classe;
    private String pacote;

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

    public String getQualifiedClassName() {
        return getPacote() + "." + getClasse();
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
