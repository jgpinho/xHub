package com.extraware.xwtemplate.utilitario;

import org.apache.log4j.Level;

public class _Log4JFabricaRegistador implements FabricaRegistador {

    @Override
    public Registador getRegistador(String categoria) {
        return new Log4JRegistador(org.apache.log4j.Logger.getLogger(categoria));
    }

    private static class Log4JRegistador extends Registador {

        private final org.apache.log4j.Logger registador;

        Log4JRegistador(org.apache.log4j.Logger registador) {
            this.registador = registador;
        }

        @Override
        public void debug(String mensagem) {
            registador.debug(mensagem);
        }

        @Override
        public void debug(String mensagem, Throwable t) {
            registador.debug(mensagem, t);
        }

        @Override
        public void info(String mensagem) {
            registador.info(mensagem);
        }

        @Override
        public void info(String mensagem, Throwable t) {
            registador.info(mensagem, t);
        }

        @Override
        public void aviso(String mensagem) {
            registador.warn(mensagem);
        }

        @Override
        public void aviso(String mensagem, Throwable t) {
            registador.warn(mensagem, t);
        }

        @Override
        public void erro(String mensagem) {
            registador.error(mensagem);
        }

        @Override
        public void erro(String mensagem, Throwable t) {
            registador.error(mensagem, t);
        }

        @Override
        public boolean debugAtivo() {
            return registador.isDebugEnabled();
        }

        @Override
        public boolean infoAtivo() {
            return registador.isInfoEnabled();
        }

        @Override
        public boolean avisoAtivo() {
            return registador.isEnabledFor(Level.WARN);
        }

        @Override
        public boolean erroAtivo() {
            return registador.isEnabledFor(Level.ERROR);
        }

        @Override
        public boolean fatalAtivo() {
            return registador.isEnabledFor(Level.FATAL);
        }
    }
}
