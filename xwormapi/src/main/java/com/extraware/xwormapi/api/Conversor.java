package com.extraware.xwormapi.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;
/**
 * Created by JP on 18-03-2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Conversor {

    // Utilizado para gerar o nome o método cursor apropriado
    // Utilizado somente por templates de generação de código
    TipoLigacao tipoLigacao();

    // Tipo de coluna SQLite que representa o tipo de Java
    TipoSQL tipoSQL();

    // Lista de tipos Java que usam este conversor
    Class[] paraTipos();
}
