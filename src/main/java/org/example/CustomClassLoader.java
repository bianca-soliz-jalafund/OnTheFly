package org.example;

import java.io.InputStream;
import java.io.IOException;

/**
 * The {CustomClassLoader} class extends the Java {ClassLoader} to provide
 * custom functionality for loading and instrumenting a specific class.
 * This loader intercepts the loading process, instruments the class bytecode for coverage tracking,
 * and then loads the modified class.
 */
public class CustomClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.equals("org.example.Myclass")) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name.replace('.', '/') + ".class");

            if (inputStream == null) {
                throw new ClassNotFoundException("Class not found: " + name);
            }

            try {
                byte[] classBytes = inputStream.readAllBytes();

                Class<?> loggerClass = Class.forName("org.example.Logger");

                byte[] instrumentedClassBytes = Instrumenter.instrumentClass(classBytes, loggerClass);

                return defineClass(name, instrumentedClassBytes, 0, instrumentedClassBytes.length);
            } catch (IOException | ClassNotFoundException e) {
                throw new ClassNotFoundException("Error loading or instrumenting class: " + name, e);
            }
        }
        return super.loadClass(name);
    }
}
