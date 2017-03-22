package com.extraware.xwormapi.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Basedados {
    String nomeBD() default "";
    String nome()   default "";
    int    versao() default 1;
}
