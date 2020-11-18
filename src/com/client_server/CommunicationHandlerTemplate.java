package com.client_server;

import java.net.Socket;

public class CommunicationHandlerTemplate implements Runnable
{
    private Thread worker;

    protected Socket socket;
    
    public CommunicationHandlerTemplate(Socket socket)
    {
        this.socket = socket;
    }

    final public void start()
    {
        this.worker = new Thread(this);
        this.worker.start();
    }

    @Override
    public void run()
    {
          
    }
}
