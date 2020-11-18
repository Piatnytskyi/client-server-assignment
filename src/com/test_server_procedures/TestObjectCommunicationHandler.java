package com.test_server_procedures;

import com.client_server.CommunicationHandlerTemplate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.text.SimpleDateFormat;

import java.util.Calendar;

public class TestObjectCommunicationHandler extends CommunicationHandlerTemplate
{
    public TestObjectCommunicationHandler(Socket socket)
    {
        super(socket);
    }

    @Override
    public void run()
    {
        synchronized(this)
        {
            try (
                ObjectOutputStream objectOut =
                    new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectIn =
                    new ObjectInputStream(socket.getInputStream()))
            {
                System.out.println(socket.getInetAddress().getHostAddress() +
                    " on thread " + Thread.currentThread().getId() + " connected.");
                objectOut.writeObject((String) "[" +
                    new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(
                        Calendar.getInstance().getTime()) +
                    "] Welcome! Vitalii Piatnytskyi, SE-34, Task ?.");

                String request;
                while ((request = (String)objectIn.readObject()) != null)
                {
                    System.out.println(Thread.currentThread().getId() + ": " + request);
                    objectOut.writeObject("Length: " + request.length() + " symbols.");
                }
            } catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    socket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
