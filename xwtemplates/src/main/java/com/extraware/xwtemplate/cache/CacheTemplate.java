package com.extraware.xwtemplate.cache;

import com.extraware.xwtemplate.template.Configuracao;
import com.extraware.xwtemplate.utilitario.Registador;

public class CacheTemplate {

    private static final Registador registador =
            Registador.getRegistador("com.extraware.xwtemplate.utilitario.Registador");

    private final CarregadorTemplate carregadorTemplate;
    private final ArmazenadorCache armazenadorCache;
    private final boolean eArmazenadorCacheConcorrencial;

    // O atraso de atualização por defeito em milisegundos
    private long atraso = 5000;

    private Configuracao configuracao;

    /**
     * Construtor da classe.
     */
    public CacheTemplate() {
        this(criarCarregadorTemplatePorDefeito());
    }
    /**
     * Construtor da classe que cria uma nova cache de templates com um carregador de templates criado à medida.
     * @param carregadorTemplate O carregador de templates a utilizar
     */
    public CacheTemplate(CarregadorTemplate carregadorTemplate) {
        this(carregadorTemplate, new ArmazenadorCacheSoft());
    }

    /**
     * Construtor da classe que cria uma nova cache de templates com um carregador de templates criado à medida.
     *
     * @param carregadorTemplate O carregador de templates a utilizar
     * @param armazenadorCache Armazenador de cache a utilizar
     */
    public CacheTemplate(CarregadorTemplate carregadorTemplate, ArmazenadorCache armazenadorCache) {
        this.carregadorTemplate = carregadorTemplate;
        this.armazenadorCache = armazenadorCache;
        if (armazenadorCache == null) throw new IllegalArgumentException("armazenadorCache == null");
        this.eArmazenadorCacheConcorrencial = armazenadorCache instanceof ArmazenadorCacheConcorrencial &&
                ((ArmazenadorCacheConcorrencial) armazenadorCache).eConcorrencial();
    }

    /**
     * Método para obter o armazenador de cache
     * @return Armazenador de Cache
     */
    public ArmazenadorCache getArmazenadorCache() {
        return armazenadorCache;
    }

    /**
     * Método para obter a classe de configuração.
     * @return Class de configuração interna
     */
    public Configuracao getConfiguracao() {
        return configuracao;

    }

    /**
     * Método para iniciar a classe de configuração
     * @param configuracao Classe de configuração nova
     */
    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
        limpar();
    }

    /**
     * Método para inicializar o atraso
     * @param atraso Atraso novo
     */
    public synchronized void setAtraso(long atraso) {
        this.atraso = atraso;
    }

    /**
     * Método para criar um carregador de templates por defeito
     * @return Carregador de templates
     */
    private static CarregadorTemplate criarCarregadorTemplatePorDefeito() {
        try {
            return new CarregadorTemplateFicheiro();
        } catch (Exception excecao) {
            registador.aviso("Não foi possível criar um carregador de ficheiros de template na directoria "
            + "corrente", excecao);
            return null;
        }
    }

    public void limpar() {
        synchronized (armazenadorCache) {
            armazenadorCache.limpar();
            if (carregadorTemplate instanceof CarregadorTemplateEstado) {
                ((CarregadorTemplateEstado)carregadorTemplate).iniciaEstado();
            }
        }
    }
}
