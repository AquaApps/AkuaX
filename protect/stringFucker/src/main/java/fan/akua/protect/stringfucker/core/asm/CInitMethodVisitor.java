package fan.akua.protect.stringfucker.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

import fan.akua.protect.stringfucker.IStringFucker;

public class CInitMethodVisitor extends MethodVisitor {
    private final IStringFucker fucker;
    private List<StringField> mStaticFinalFields;
    private List<StringField> mStaticFields;
    private String mClassName;
    private String lastStashCst;

    public CInitMethodVisitor(MethodVisitor mv, IStringFucker fucker) {
        super(Opcodes.ASM9, mv);
        this.fucker = fucker;
    }

    public CInitMethodVisitor bindFields(List<StringField> staticFinalFields, List<StringField> staticFields) {
        this.mStaticFinalFields = staticFinalFields;
        this.mStaticFields = staticFields;
        return this;
    }

    public CInitMethodVisitor bindClassName(String className) {
        this.mClassName = className;
        return this;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        // todo: parallel
        // Here init static final fields.
        for (StringField field : mStaticFinalFields) {
            if (!fucker.canFuck(field.value)) {
                continue;
            }
            StringFuckerClassVisitor.Companion.encryptAndWrite(field.value, fucker, mv);
            super.visitFieldInsn(Opcodes.PUTSTATIC, mClassName, field.name, StringFuckerClassVisitor.STRING_DESC);
        }
    }

    @Override
    public void visitLdcInsn(Object cst) {
        // Here init static or static final fields, but we must check field name int 'visitFieldInsn'
        if (cst instanceof String str && fucker.canFuck(str)) {
            lastStashCst = str;
            StringFuckerClassVisitor.Companion.encryptAndWrite(lastStashCst, fucker, mv);
        } else {
            lastStashCst = null;
            super.visitLdcInsn(cst);
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if (mClassName.equals(owner) && lastStashCst != null) {
            boolean isContain = false;
            // todo: parallel
            for (StringField field : mStaticFields) {
                if (field.name.equals(name)) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                for (StringField field : mStaticFinalFields) {
                    if (field.name.equals(name) && field.value == null) {
                        field.value = lastStashCst;
                        break;
                    }
                }
            }
        }
        lastStashCst = null;
        super.visitFieldInsn(opcode, owner, name, desc);
    }
}
