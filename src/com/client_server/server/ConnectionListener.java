package com.client_server.server;

import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.atomic.AtomicBoolean;

import com.client_server.CommunicationHandlerTemplate;

class ConnectionListener implements Runnable
{
    private Thread worker;
    private final AtomicBoolean toAccept = new AtomicBoolean(false);

    private ServerSocket serverSocket;

    Class<?> communicationHandlerClass;

    public ConnectionListener(
        ServerSocket serverSocket,
        Class<?> communicationHandlerClass)
    {
        this.serverSocket = serverSocket;
        this.communicationHandlerClass = communicationHandlerClass;
    }

    public void start()
    {
        this.worker = new Thread(this);
        this.worker.start();
    }

    public void stop()
    {
        toAccept.set(false);
    }

    @Override
    public void run()
    {
        toAccept.set(true);
        try
        {
            while (toAccept.get())
            {
                Constructor<?> communicationHandlerConstructor =
                    communicationHandlerClass.getConstructor(Socket.class);
                CommunicationHandlerTemplate instance =
                    (CommunicationHandlerTemplate)
                        communicationHandlerConstructor.newInstance(serverSocket.accept());
                instance.start();
            }
        } catch (
            SecurityException | InstantiationException | IllegalAccessException |
            IllegalArgumentException | InvocationTargetException | IOException |
            NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }
}
