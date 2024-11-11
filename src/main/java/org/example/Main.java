package org.example;

/**
 * The {Main} class serves as the entry point for testing the custom class loading and instrumentation
 * of {org.example.Myclass}. It loads the target class using {CustomClassLoader}, invokes
 * {myMethod}, and finally prints a coverage report showing which probes were executed.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class<?> myClass = customClassLoader.loadClass("org.example.Myclass");

        try {
            myClass.getMethod("myMethod");

            Object myClassInstance = myClass.getDeclaredConstructor().newInstance();
            myClass.getMethod("myMethod").invoke(myClassInstance);
        } catch (NoSuchMethodException e) {
            System.err.println("Method 'myMethod' not found in class 'Myclass'.");
            e.printStackTrace();
        }
        finally {
            Logger.printCoverageReport();
        }
    }
}
