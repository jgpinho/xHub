package com.extraware.xwtemplate.utilitario;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExcecaoNaoDeclarada extends RuntimeException {

    private final Throwable t;

    public ExcecaoNaoDeclarada(Throwable t) {
        this.t = t;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace (PrintStream ps) {
        synchronized (ps) {
            ps.print("Erro não declarado:");
            t.printStackTrace(ps);
        }
    }

    public void printStackTrace (PrintWriter pw) {
        synchronized (pw) {
            pw.print("Erro não declarado:");
            t.printStackTrace(pw);
        }
    }

    public Throwable getExcecaoNaoDeclarada() {
        return t;
    }

    public Throwable getCause() {
        return t;
    }
}