package learn.java.jvm.bytecode.enhancer.asm;

import java.util.Objects;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * BaseClassVisitor.
 */
public class BaseClassVisitor extends ClassVisitor {

    private static final String PROCESS_METHOD_NAME = "process";

    public BaseClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        // 排除 process 以外的方法
        if (Objects.equals(name, PROCESS_METHOD_NAME) && mv != null) {
            mv = new ProcessMethodVisitor(mv);
        }
        return mv;
    }

    static class ProcessMethodVisitor extends MethodVisitor implements Opcodes {

        public ProcessMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM5, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();

            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LSTORE, 1);
            Label var1 = new Label();
            mv.visitLabel(var1);
            // 织入代码行数
            mv.visitLineNumber(16, var1);
        }

        @Override
        public void visitInsn(int opcode) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LSTORE, 3);
            Label var3 = new Label();
            mv.visitLabel(var3);
            mv.visitLineNumber(18, var3);

            mv.visitFieldInsn(GETSTATIC, "learn/java/jvm/bytecode/enhancer/Base", "log", "Lorg/slf4j/Logger;");
            mv.visitLdcInsn("Duration: {}");
            mv.visitVarInsn(LLOAD, 3);
            mv.visitVarInsn(LLOAD, 1);
            mv.visitInsn(LSUB);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "org/slf4j/Logger", "info",
                    "(Ljava/lang/String;Ljava/lang/Object;)V", true);
            mv.visitInsn(opcode);
        }
    }
}
