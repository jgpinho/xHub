package com.extraware.xwtemplate.utilitario;

import org.apache.log.Hierarchy;

public class _AvalonFabricaRegistador implements FabricaRegistador {

    @Override
    public Registador getRegistador(String categoria) {
        return new AvalonRegistador(Hierarchy.getDefaultHierarchy().getLoggerFor(categoria));
    }

    private static class AvalonRegistador extends Registador {

        private final org.apache.log.Logger registador;

        AvalonRegistador(org.apache.log.Logger registador) {
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
            return registador.isWarnEnabled();
        }

        @Override
        public boolean erroAtivo() {
            return registador.isErrorEnabled();
        }

        @Override
        public boolean fatalAtivo() {
            return registador.isFatalErrorEnabled();
        }
    }
}
