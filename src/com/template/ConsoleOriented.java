package com.template;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;

public class ConsoleOriented extends Program {
    protected BufferedWriter outputWriter = null;
    protected BufferedReader inputReader = null;
    protected BufferedWriter errorWriter = null;

    protected boolean toReadInput = true;

    public ConsoleOriented(InputStream in, PrintStream out, PrintStream err) {
        super(in, out, err);
        System.setOut(out);
        System.setIn(in);
        System.setErr(err);
    }

    @Override
    public void main(String[] args) {
        this.setArguments(args);

        outputWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        inputReader = new BufferedReader(new InputStreamReader(System.in));
        errorWriter = new BufferedWriter(new OutputStreamWriter(System.err));

        try {
            try {
                if (!this.validateArguments()) {
                    errorWriter.write("Usage: " + this.usage() + "\n");
                    return;
                }

                this.preProcess();
                if (this.toReadInput) {
                    for (
                            String currentLine = inputReader.readLine();
                            currentLine != null;
                            currentLine = inputReader.readLine())
                        this.processLine(currentLine);
                }
                this.postProcess();
            } finally {
                outputWriter.close();
                inputReader.close();
                errorWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean validateArguments()
    {
        return true;
    }

    protected String usage()
    {
        return "";
    }

    protected void preProcess() {

    }

    protected void processLine(String line) {

    }

    protected void postProcess() {

    }
}
