package fan.akua.protect.stringfucker.core.mode;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class JavaModeModeWriter extends IModeWriter {
    public JavaModeModeWriter(String fuckerClassName) {
        super(fuckerClassName);
    }

    @Override
    public void modifyStr(byte[] key, byte[] value, MethodVisitor mv) {
        pushArray(mv, value);
        pushArray(mv, key);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, mFuckerClassName, "decrypt", "([B[B)Ljava/lang/String;", false);
    }

    private static void pushArray(MethodVisitor mv, byte[] buffer) {
        pushNumber(mv, buffer.length);
        mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BYTE);

        for (int i = 0; i < buffer.length; i++) {
            mv.visitInsn(Opcodes.DUP);
            pushNumber(mv, i);
            pushNumber(mv, buffer[i]);
            mv.visitInsn(Type.BYTE_TYPE.getOpcode(Opcodes.BASTORE));
        }
    }

    private static void pushNumber(MethodVisitor mv, final int value) {
        if (value >= -1 && value <= 5) {
            mv.visitInsn(Opcodes.ICONST_0 + value);
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.BIPUSH, value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }
}
