package com.extraware.xwtemplate.utilitario;

import java.util.logging.Level;

public class _JDK14FabricaRegistador implements FabricaRegistador {

    @Override
    public Registador getRegistador(String categoria) {
        return new JDK14Registador(java.util.logging.Logger.getLogger(categoria));
    }

    private static class JDK14Registador extends Registador {

        private final java.util.logging.Logger registador;

        JDK14Registador(java.util.logging.Logger registador) {
            this.registador = registador;
        }

        @Override
        public void debug(String mensagem) {
            registador.log(Level.FINE, mensagem);
        }

        @Override
        public void debug(String mensagem, Throwable t) {
            registador.log(Level.FINE, mensagem, t);
        }

        @Override
        public void info(String mensagem) {
            registador.log(Level.INFO, mensagem);
        }

        @Override
        public void info(String mensagem, Throwable t) {
            registador.log(Level.INFO, mensagem, t);
        }

        @Override
        public void aviso(String mensagem) {
            registador.log(Level.WARNING, mensagem);
        }

        @Override
        public void aviso(String mensagem, Throwable t) {
            registador.log(Level.WARNING, mensagem, t);
        }

        @Override
        public void erro(String mensagem) {
            registador.log(Level.SEVERE, mensagem);
        }

        @Override
        public void erro(String mensagem, Throwable t) {
            registador.log(Level.SEVERE, mensagem, t);
        }

        @Override
        public boolean debugAtivo() {
            return registador.isLoggable(Level.FINE);
        }

        @Override
        public boolean infoAtivo() {
            return registador.isLoggable(Level.INFO);
        }

        @Override
        public boolean avisoAtivo() {
            return registador.isLoggable(Level.WARNING);
        }

        @Override
        public boolean erroAtivo() {
            return registador.isLoggable(Level.SEVERE);
        }

        @Override
        public boolean fatalAtivo() {
            return registador.isLoggable(Level.SEVERE);
        }
    }
}
