package com.extraware.xwormapi.api;

/**
 * Created by JP on 15-03-2017.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface BD {
    String nomeBD() default "";
    String nome()   default "";
    int    versao() default 1;
}
