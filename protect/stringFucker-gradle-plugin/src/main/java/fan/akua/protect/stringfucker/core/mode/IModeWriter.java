package fan.akua.protect.stringfucker.core.mode;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public abstract class IModeWriter {

    protected final String mFuckerClassName;

    IModeWriter(String fuckerClassName) {
        mFuckerClassName = fuckerClassName;
    }

    public abstract void modifyStr(byte[] key, byte[] value, MethodVisitor mv);
}
