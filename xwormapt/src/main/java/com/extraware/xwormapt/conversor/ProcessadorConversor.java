package com.extraware.xwormapt.conversor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.extraware.xwormapi.api.Conversor;
import com.extraware.xwormapi.types.ConversorTipo.TipoLigacao;
import com.extraware.xwormapi.types.ConversorTipo.TipoSQL;
import com.extraware.xwormapt.Ambiente;
import com.extraware.xwormapt.ProcessadorClasse;

public class ProcessadorConversor extends ProcessadorClasse {

    private ModeloConversor modeloConversor;

    /**
     * Construtor principal da classe
     *
     * @param elemento Elemento do modelo
     * @param xwormAmbiente Ambiente do XwORM
     */
    public ProcessadorConversor(Element elemento, Ambiente xwormAmbiente) {
        super(elemento, xwormAmbiente);
    }

    @Override
    protected ModeloConversor getModelo() {
        return modeloConversor;
    }

    @Override
    public void preencherModelo() {

        // Obter o nome da classe conversora
        String classeConversora = this.tipoElemento.getQualifiedName().toString();
        // Obter a anotação do Conversor
        Conversor anotacao = this.tipoElemento.getAnnotation(Conversor.class);
        // Obter o tipo de ligação
        TipoLigacao tipoLigacao = anotacao.tipoLigacao();
        // Obter o tipo SQL
        TipoSQL tipoSQL = anotacao.tipoSQL();

        // Obter o paraTipos num String[]. Isto é complicado porque não é possível referencial valores de classes
        // directamente nas anotações.
        // Obter a lista de referências de anotações do tipo de elemento
        List<? extends AnnotationMirror> referenciasAnotacao = this.tipoElemento.getAnnotationMirrors();
        // Para cada referência de anotação
        for (AnnotationMirror referenciaAnotacao : referenciasAnotacao) {
            // Obter um mapa de valores da referência da anotação
            Map<? extends ExecutableElement, ? extends AnnotationValue> valoresAnotacao =
                    referenciaAnotacao.getElementValues();
            // Para cada entrada no mapa de valores
            for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entrada : valoresAnotacao.entrySet()) {
                // Obter a chave da entrada
                String chave = entrada.getKey().getSimpleName().toString();
                // Se é um valor "paraTipos"
                if ("paraTipos".equals(chave)) {
                    // Obter o valor da entrada
                    AnnotationValue valor = entrada.getValue();
                    // Obter os tipos
                    String[] tipos = valor.accept(new VerValoresConversorTipo(), xwormAmbiente.getRegistador());

                    // Inicializar o modelo do conversor
                    this.modeloConversor = new ModeloConversor(classeConversora, tipos, tipoLigacao, tipoSQL);

                    // Se não for possível adicionar o modelo
                    if (!xwormAmbiente.adicionarConversor(this.modeloConversor)) {
                        xwormAmbiente.getRegistador().erro("Já existe um conversor para o tipo ", this.tipoElemento);
                    }
                }
            }
        }
    }

}
