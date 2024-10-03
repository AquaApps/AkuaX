package fan.akua.protect.stringfucker.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import fan.akua.protect.stringfucker.IStringFucker;

public class InitMethodVisitor extends MethodVisitor {
    public InitMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM9, mv);
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
