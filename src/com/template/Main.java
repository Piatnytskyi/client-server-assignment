package com.template;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        String[] applicationArgs = new String[args.length - 1];
        if (args.length - 1 >= 0)
            System.arraycopy(args, 1, applicationArgs, 0, args.length - 1);

        try {
            Class<?> requestedClass = Class.forName(args[0]);
            Constructor<?> requestedClassConstructor =
                requestedClass.getConstructor(
                    InputStream.class,
                    PrintStream.class,
                    PrintStream.class);
            Program instance =
                (Program)
                    requestedClassConstructor.newInstance(
                        System.in,
                        System.out,
                        System.err);

            instance.main(applicationArgs);
        } catch (
            ClassNotFoundException | NoSuchMethodException | SecurityException
            | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}