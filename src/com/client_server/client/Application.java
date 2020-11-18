package com.client_server.client;

import com.template.ConsoleOriented;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.IOException;

import java.net.Socket;

public class Application extends ConsoleOriented
{
    private Socket socket;
    private int serverPort;
    private String serverIp;

    private ObjectInputStream serverObjectIn;
    private ObjectOutputStream serverObjectOut;

    public Application(InputStream in, PrintStream out, PrintStream error)
    {
        super(in, out, error);
    }

    protected boolean validateArguments()
    {
        try {
            serverIp = arguments[0];
            serverPort = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    protected String usage()
    {
        return "[ip address] [port]";
    }

    protected void preProcess()
    {
        //
        //
        //TODO: Make this a template class, as it supposed to be.
        // Communication handler template needs to be provided here.
        //

        try
        {
            socket = new Socket(serverIp, serverPort);
            serverObjectOut =
                new ObjectOutputStream(socket.getOutputStream());
            serverObjectIn =
                new ObjectInputStream(socket.getInputStream());
            System.out.println(socket.getInetAddress().getHostAddress() +
                ": " + serverObjectIn.readObject());
        } catch (IOException | SecurityException | IllegalArgumentException |
            ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    //
    //
    //TODO: This should be template method.
    //
    //For the present it just reads and outputs String objects
    //
    protected void processLine(String line)
    {
        if (line.equals("."))
        {
            try { inputReader.close(); }
            catch (IOException e) { e.printStackTrace(); }
            inputReader = new BufferedReader(
                new InputStreamReader(InputStream.nullInputStream()));

            return;
        }

        try
        {
            serverObjectOut.writeObject(line);
            System.out.println(socket.getInetAddress().getHostAddress() +
                ": " + serverObjectIn.readObject());
        } catch (IOException | ClassNotFoundException e)
        {
			e.printStackTrace();
		}
    }
        
    protected void postProcess()
    {
        try 
        {
            serverObjectOut.close();
            serverObjectIn.close();
            socket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
