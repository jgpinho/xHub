package com.extraware.xwormapt;

/**
 * Created by JP on 15-03-2017.
 */

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({ "com.extraware.xwormapi.api.BD" }) //TODO,"com.extraware.xwormapi.api.XEntidade","javax.persistence.Entity","com.extraware.xwormapi.api.XConversor" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class Processador extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //TODO
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.values()[SourceVersion.values().length - 1];
    }
}
