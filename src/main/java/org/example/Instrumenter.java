package org.example;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;
import java.io.IOException;

/**
 * The {Instrumenter} class provides bytecode instrumentation for Java classes,
 * allowing specific methods to be modified at runtime to include coverage probes.
 * The class uses the ASM library to modify the bytecode and inject probes that track
 * the execution of specific lines or blocks of code within methods.
 */
public class Instrumenter {

    public static byte[] instrumentClass(byte[] classBytes, Class<?> loggerClass) throws IOException {
        if (loggerClass == null) {
            throw new IllegalArgumentException("Logger class must not be null");
        }

        ClassReader classReader = new ClassReader(classBytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("myMethod")) {
                    return new MyMethodVisitor(Opcodes.ASM9, methodVisitor);
                }
                return methodVisitor;
            }
        };

        classReader.accept(classVisitor, 0);
        return classWriter.toByteArray();
    }

    /**
     * The {MyMethodVisitor} class is a custom {MethodVisitor} that adds probes to track line coverage.
     * Each line in the method is marked with a probe in {Logger.coverageProbes} to record whether
     * it has been executed.
     */
    static class MyMethodVisitor extends MethodVisitor {

        private static int probeCounter = 0;

        public MyMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitCode() {
            super.visitCode();

            mv.visitLdcInsn( 0);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/example/Logger", "logProbe", "(I)V", false);
        }

        @Override
        public void visitLineNumber(int line, org.objectweb.asm.Label start) {
            addProbe(probeCounter++, line);
            super.visitLineNumber(line, start);
        }

        private void addProbe(int probeId, int lineNumber) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "org/example/Logger", "coverageProbes", "[Z");
            mv.visitIntInsn(Opcodes.SIPUSH, probeId);
            mv.visitInsn(Opcodes.ICONST_1);
            mv.visitInsn(Opcodes.BASTORE);
        }
    }
}
