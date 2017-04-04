package com.extraware.xwtemplate.cache;

import com.extraware.xwtemplate.core.FabricaMapaConcorrencial;
import com.extraware.xwtemplate.utilitario.ExcecaoNaoDeclarada;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Classe de armazenador de cache que utiliza referências soft de objetos permitindo assim que o compactador de lixo
 * limpe essa cache se houver a necessidade de memória.
 */
public class ArmazenadorCacheSoft implements ArmazenadorCacheConcorrencial {

    private static final Method remocaoAtomica = getMetodoRemocaoAtomica();

    private final ReferenceQueue fila = new ReferenceQueue();
    private final Map mapa;
    private final boolean concorrente;

    public ArmazenadorCacheSoft() {
        this(FabricaMapaConcorrencial.criarMapaConcorrencial());
    }
    public ArmazenadorCacheSoft(Map mapa) {
        this.mapa = mapa;
        this.concorrente = FabricaMapaConcorrencial.eConcorrente(this.mapa);
    }

    @Override
    public Object ler(Object chave) {
        processarFila();
        Reference referencia = (Reference) mapa.get(chave);
        return referencia == null ? null : referencia.get();
    }

    @Override
    public void por(Object chave, Object valor) {
        processarFila();
        mapa.put(chave, new ReferenciaValorSoft(chave, valor, fila));
    }

    @Override
    public void remover(Object chave) {
        processarFila();
        mapa.remove(chave);
    }

    @Override
    public void limpar() {
        mapa.clear();
        processarFila();
    }

    @Override
    public boolean eConcorrencial() {
        return concorrente;
    }

    private void processarFila() {
        ReferenciaValorSoft referenciaValorSoft = (ReferenciaValorSoft) fila.poll();
        while (referenciaValorSoft != null) {
            Object chave = referenciaValorSoft.getChave();

            if (concorrente) {
                try {
                    remocaoAtomica.invoke(mapa, new Object[] { chave, referenciaValorSoft});
                } catch (IllegalAccessException excecao) {
                    throw new ExcecaoNaoDeclarada(excecao);
                } catch (InvocationTargetException excecao) {
                    throw new ExcecaoNaoDeclarada(excecao);
                }
            } else if (mapa.get(chave) == referenciaValorSoft) {
                mapa.remove(chave);
            }

            referenciaValorSoft = (ReferenciaValorSoft) fila.poll();
        }
    }

    /**
     * Class de referência de valores soft.
     */
    private static final class ReferenciaValorSoft extends SoftReference {

        // Chave da referência
        private final Object chave;

        /**
         * Construtor principal da classe.
         *
         * @param chave Chave da referência
         * @param valor Valor da referência
         * @param fila Fila de referências
         */
        public ReferenciaValorSoft(Object chave, Object valor, ReferenceQueue fila) {
            super(valor, fila);
            this.chave = chave;
        }

        /**
         * Método para obter a chave da referência
         * @return O objeto da chave de referência
         */
        Object getChave() {
            return chave;
        }
    }

    /**
     * Método para obter o método de remoção atómica
     *
     * @return O método de remoção atómica
     */
    private static Method getMetodoRemocaoAtomica() {
        try {
            return Class.forName("java.util.concurrent.ConcurrentMap").getMethod("remove", new Class[] { Object.class, Object.class});
        } catch (ClassNotFoundException excecao) {
            return null;
        } catch (NoSuchMethodException excecao) {
            throw new ExcecaoNaoDeclarada(excecao);
        }
    }
}
