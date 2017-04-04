package com.extraware.xwtemplate.utilitario;

public class _NullFabricaRegistador implements FabricaRegistador {

    private static final Registador INSTANCIA = new Registador() {
        public void debug(String mensagem) {}
        public void debug(String mensagem, Throwable t) {}
        public void erro(String mensagem) {}
        public void erro(String mensagem, Throwable t) {}
        public void info(String mensagem) {}
        public void info(String mensagem, Throwable t) {}
        public void aviso(String mensagem) {}
        public void aviso(String mensagem, Throwable t) {}

        public boolean debugAtivo() {
            return false;
        }
        public boolean infoAtivo() {
            return false;
        }
        public boolean avisoAtivo() {
            return false;
        }
        public boolean erroAtivo() {
            return false;
        }
        public boolean fatalAtivo() {
            return false;
        }
    };

    public _NullFabricaRegistador() {}

    @Override
    public Registador getRegistador(String categoria) {
        return INSTANCIA;
    }
}
