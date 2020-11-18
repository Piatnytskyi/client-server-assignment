package com.template;

import java.io.InputStream;
import java.io.PrintStream;

public abstract class Program {
    protected String[] arguments;

    InputStream in = null;
    PrintStream out = null;
    PrintStream err = null;

    public Program(InputStream in, PrintStream out, PrintStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    public abstract void main(String[] args);

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }
    public String[] getArguments() {
        return arguments;
    }
}
