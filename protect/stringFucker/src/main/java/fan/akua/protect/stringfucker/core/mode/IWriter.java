package fan.akua.protect.stringfucker.core.mode;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public abstract class IWriter {

    private final String mFuckerClassName;

    IWriter(String fuckerClassName) {
        mFuckerClassName = fuckerClassName;
    }

    public abstract String write(byte[] key, byte[] value, MethodVisitor mv);

    protected void writeClass(MethodVisitor mv, String descriptor) {
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, mFuckerClassName, "decrypt", descriptor, false);
    }

}
