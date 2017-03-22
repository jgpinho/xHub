package com.extraware.xwormapi.api;

import com.extraware.xwormapi.SQLiteDAO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Entidade {

    String basedados() default "";
    String tabela() default "";
    Class<? extends SQLiteDAO> classeBaseDAO() default SQLiteDAO.class;
}
