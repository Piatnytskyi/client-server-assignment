package com.client_server.server;

import com.template.ConsoleOriented;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.ServerSocket;

public class Application extends ConsoleOriented
{
    private ServerSocket serverSocket;
    private int port;

    private ConnectionListener connectionListener;
    private Class<?> communicationHandlerClass;

    public Application(InputStream in, PrintStream out, PrintStream error) {
        super(in, out, error);
    }

    @Override
    protected boolean validateArguments()
    {
        try
        {
            port = Integer.parseInt(arguments[0]);
            communicationHandlerClass = Class.forName(arguments[1]);
        } catch (NumberFormatException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected String usage() {
        return arguments[0] + "[port] [communication handler class]";
    }

    @Override
    protected void preProcess()
    {
        try {
            serverSocket = new ServerSocket(port);
            connectionListener = new ConnectionListener(
                serverSocket,
                communicationHandlerClass);
            connectionListener.start();
        } catch (IOException | SecurityException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void processLine(String line)
    {
        //
        //
        // TODO: Implement commands interpreter!
        //
        connectionListener.stop();
        try { inputReader.close(); }
        catch (IOException e) { e.printStackTrace(); }
        inputReader = new BufferedReader(
            new InputStreamReader(InputStream.nullInputStream()));
    }

    @Override
    protected void postProcess()
    {
        try 
        {
            serverSocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
