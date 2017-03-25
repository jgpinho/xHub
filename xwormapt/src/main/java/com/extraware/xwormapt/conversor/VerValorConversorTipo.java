package com.extraware.xwormapt.conversor;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;

import com.extraware.xwormapt.Registador;

public class VerValorConversorTipo extends SimpleAnnotationValueVisitor6<String, Registador> {

    @Override
    public String visitType(TypeMirror tipo, Registador registador) {
        return tipo.toString();
    }
}
