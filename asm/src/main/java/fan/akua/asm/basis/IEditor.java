package fan.akua.asm.basis;

import org.objectweb.asm.MethodVisitor;

public interface IEditor {
    void accept(MethodVisitor methodVisitor);
}

