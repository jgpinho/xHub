package com.extraware.xwormapt;

public class UtilitariosSQL {

    private static String palavrasReservadas = "ABORT,ACTION,ADD,AFTER,ALL,ALTER,ANALYZE,AND,AS,ASC,ATTACH," +
        "AUTOINCREMENT,BEFORE,BEGIN,BETWEEN,BY,CASCADE,CASE,CAST,CHECK,COLLATE,COLUMN,COMMIT,CONFLICT,CONSTRAINT," +
        "CREATE,CROSS,CURRENT_DATE,CURRENT_TIME,CURRENT_TIMESTAMP,DATABASE,DEFAULT,DEFERRABLE,DEFERRED,DELETE,DESC," +
        "DETACH,DISTINCT,DROP,EACH,ELSE,END,ESCAPE,EXCEPT,EXCLUSIVE,EXISTS,EXPLAIN,FAIL,FOR,FOREIGN,FROM,FULL,GLOB," +
        "GROUP,HAVING,IF,IGNORE,IMMEDIATE,IN,INDEX,INDEXED,INITIALLY,INNER,INSERT,INSTEAD,INTERSECT,INTO,IS,ISNULL," +
        "JOIN,KEY,LEFT,LIKE,LIMIT,MATCH,NATURAL,NO,NOT,NOTNULL,NULL,OF,OFFSET,ON,OR,ORDER,OUTER,PLAN,PRAGMA,PRIMARY," +
        "QUERY,RAISE,REFERENCES,REGEXP,REINDEX,RELEASE,RENAME,REPLACE,RESTRICT,RIGHT,ROLLBACK,ROW,SAVEPOINT,SELECT," +
        "SET,TABLE,TEMP,TEMPORARY,THEN,TO,TRANSACTION,TRIGGER,UNION,UNIQUE,UPDATE,USING,VACUUM,VALUES,VIEW,VIRTUAL," +
        "WHEN,WHERE";

    /**
     * Método para validar se determinado identificador é válido ou não.
     *
     * @param identificador Identificador a ser validado.
     * @return Verdade se é válido, falso se não é válido
     */
    public static boolean isIdentificadorValido(String identificador) {
        // Identificadores de Java dentro de parêntesis retos são válidos
        if (identificador.matches("^\\[[_a-zA-z][_a-zA-Z0-9]*\\]$")) return true;

        // Aceitar sómente identificadores Java válidos porque o nome da tabela será também utilizado para o nome
        // da classe GestoraTabela
        if (!isJavaValido(identificador)) return false;

        // Não aceitar uma palavra reservada
        if (isPalavraReservada(identificador)) return false;

        return true;
    }

    private static boolean isPalavraReservada(String identificador) {
        String[] palavras = palavrasReservadas.split(",");
        for (String palavra : palavras) {
            if (palavra.equals(identificador.toUpperCase())) return true;
        }
        return false;
    }

    /**
     * Método para validar se determinado identificador Java é válido ou não.
     *
     * @param identificador Identificador Java a ser avaliado
     * @return Verdade se é válido, falso se não é válido
     */
    public static boolean isJavaValido(String identificador) {
        return identificador.matches("^[_a-zA-z][_a-zA-Z0-9]*$");
    }
}
