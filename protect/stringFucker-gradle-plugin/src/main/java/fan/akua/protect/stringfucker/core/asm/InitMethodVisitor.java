package fan.akua.protect.stringfucker.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import fan.akua.protect.stringfucker.IStringFucker;

public class InitMethodVisitor extends MethodVisitor {
    private final IStringFucker fucker;

    public InitMethodVisitor(MethodVisitor mv, IStringFucker fucker) {
        super(Opcodes.ASM9, mv);
        this.fucker = fucker;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        // We don't care about whether the field is final or normal
        // todo: sth went wrong.
//        if (cst instanceof String str && fucker.canFuck(str)) {
//            StringFuckerClassVisitor.Companion.encryptAndWrite(str, fucker, mv);
//        }
        super.visitLdcInsn(cst);
    }
}
