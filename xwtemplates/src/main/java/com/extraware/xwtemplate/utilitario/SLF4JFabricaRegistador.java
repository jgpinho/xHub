package com.extraware.xwtemplate.utilitario;

import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JFabricaRegistador implements FabricaRegistador {

    @Override
    public Registador getRegistador(String categoria) {
        Logger slf4jRegistador = LoggerFactory.getLogger(categoria);
        if (slf4jRegistador instanceof LocationAwareLogger) {
            return new LocationAwareSLF4JRegistador((LocationAwareLogger) slf4jRegistador);
        } else {
            return new LocationUnawareSLF4JRegistador(slf4jRegistador);
        }
    }

    private static final class LocationAwareSLF4JRegistador extends Registador {

        private static final String ADAPTADOR_FQCN = LocationAwareSLF4JRegistador.class.getName();

        private final LocationAwareLogger registador;

        LocationAwareSLF4JRegistador(LocationAwareLogger registador) {
            this.registador = registador;
        }

        @Override
        public void debug(String mensagem) {
            debug(mensagem, null);
        }

        @Override
        public void debug(String mensagem, Throwable t) {
            registador.log(null, ADAPTADOR_FQCN, LocationAwareLogger.DEBUG_INT, mensagem, null, t);
        }

        @Override
        public void info(String mensagem) {
            info(mensagem, null);
        }

        @Override
        public void info(String mensagem, Throwable t) {
            registador.log(null, ADAPTADOR_FQCN, LocationAwareLogger.INFO_INT, mensagem, null, t);
        }

        @Override
        public void aviso(String mensagem) {
            aviso(mensagem, null);
        }

        @Override
        public void aviso(String mensagem, Throwable t) {
            registador.log(null, ADAPTADOR_FQCN, LocationAwareLogger.WARN_INT, mensagem, null, t);
        }

        @Override
        public void erro(String mensagem) {
            erro(mensagem, null);
        }

        @Override
        public void erro(String mensagem, Throwable t) {
            registador.log(null, ADAPTADOR_FQCN, LocationAwareLogger.ERROR_INT, mensagem, null, t);
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
            return registador.isErrorEnabled();
        }
    }

    private static class LocationUnawareSLF4JRegistador extends Registador {

        private final Logger registador;

        public LocationUnawareSLF4JRegistador(Logger registador) {
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
            return registador.isErrorEnabled();
        }
    }
}
