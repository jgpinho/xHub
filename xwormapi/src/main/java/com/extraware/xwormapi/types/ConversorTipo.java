package com.extraware.xwormapi.types;

/**
 * Classe básica responsável pela conversão de tipos Java em um dos quatro tipos de SQLite.
 * Para criar um conversor, extender esta classe e anotar com o @Converter. Para além dos métodos
 * abstractos, cada classe ConversorTipo tem de ter também um campo estático chamado LER que é
 * utilizado pelo código generado para obter uma instância do conversor.
 *
 * @param <J> tipo nativo do Java
 * @param <S> tipo SQL
 */
public abstract class ConversorTipo<J extends Object, S extends Object> {

    // Enum representando os quatro tipos fundamentais no SQLite
    public enum TipoSQL {INTEGER, REAL, BLOB, TEXT};

    // Enum representando os tipos que têm métodos Cursor correspondentes e métodos de ligação.
    // Nunca chamado na execução.
    public enum TipoLigacao {BLOB, DOUBLE, FLOAT, INT, LONG, SHORT, STRING};

    /**
     * Converte um valor Java para uma representação que pode ser escrita num mapa de ValoresConteudo
     *
     * @param valorJava Valor de Java
     * @return Valor SQLite
     */
    public abstract S paraSQL(J valorJava);

    /**
     * Converte o valor obtido dum método getS do Cursor para o seu tipo Java
     *
     * @param valorSQL Valor de SQLite
     * @return Valor de Java
     */
    public abstract J deSQL(S valorSQL);

    /**
     * Converte o valor de String para o seu TipoSQL.
     * Este método é utilizado pelo importador CSV.
     *
     * @param valor Valor em String
     * @return Valor em SQLite
     */
    public abstract S deString(String valor);

    /**
     * Converte o valor de SQLite para String.
     * Este método é utilizado pelo importador CSV.
     *
     * @param valorSQL Valor em SQLite
     * @return Valor em String
     */
    public String toString(S valorSQL) {
        return (valorSQL == null) ? null : valorSQL.toString();
    }
}
