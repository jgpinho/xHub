package com.extraware.xwormapt.conversor;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;

import com.extraware.xwormapt.Registador;

public class VerValoresConversorTipo extends SimpleAnnotationValueVisitor6<String[], Registador> {

    /**
     * Método para retornar um array de valores paraTipos de Conversores
     *
     * @param valores Lista de valores da anotação
     * @param registador Registador das mensagens
     * @return Retorna o array de tipos
     */
    @Override
    public String[] visitArray(List<? extends AnnotationValue> valores, Registador registador) {
        List<String> tipos = new ArrayList<String>();
        for (AnnotationValue valor : valores) {
            tipos.add(valor.accept(new VerValorConversorTipo(), registador));
        }
        return tipos.toArray(new String[]{});
    }
}
