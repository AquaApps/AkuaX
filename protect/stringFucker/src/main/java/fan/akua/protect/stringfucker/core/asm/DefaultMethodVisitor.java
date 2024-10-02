package fan.akua.protect.stringfucker.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

import fan.akua.protect.stringfucker.IStringFucker;

public class DefaultMethodVisitor extends MethodVisitor {
    private final IStringFucker fucker;
    private List<StringField> mStaticFinalFields;
    private List<StringField> mFinalFields;
    private String mClassName;

    public DefaultMethodVisitor(MethodVisitor mv, IStringFucker fucker) {
        super(Opcodes.ASM9, mv);
        this.fucker = fucker;
    }

    public DefaultMethodVisitor bindFields(List<StringField> staticFinalFields, List<StringField> finalFields) {
        this.mStaticFinalFields = staticFinalFields;
        this.mFinalFields = finalFields;
        return this;
    }

    public DefaultMethodVisitor bindClassName(String className) {
        this.mClassName = className;
        return this;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof String str && fucker.canFuck(str)) {
            // todo: parallel
            // If the value is a static final field
            for (StringField field : mStaticFinalFields) {
                if (cst.equals(field.value)) {
                    super.visitFieldInsn(Opcodes.GETSTATIC, mClassName, field.name, StringFuckerClassVisitor.STRING_DESC);
                    return;
                }
            }
            // todo: parallel
            // If the value is a final field (not static)
            for (StringField field : mFinalFields) {
                // if the value of a final field is null, we ignore it
                if (cst.equals(field.value)) {
                    super.visitVarInsn(Opcodes.ALOAD, 0);
                    super.visitFieldInsn(Opcodes.GETFIELD, mClassName, field.name, StringFuckerClassVisitor.STRING_DESC);
                    return;
                }
            }
            // local variables
            StringFuckerClassVisitor.Companion.encryptAndWrite(str, fucker, mv);
            return;
        }
        super.visitLdcInsn(cst);
    }
}

