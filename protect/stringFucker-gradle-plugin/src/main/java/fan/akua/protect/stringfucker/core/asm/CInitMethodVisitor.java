package fan.akua.protect.stringfucker.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import fan.akua.protect.stringfucker.IStringFucker;

public class CInitMethodVisitor extends MethodVisitor {
    private List<StringField> mStaticFinalFields;
    private List<StringField> mStaticFields;
    private String mClassName;
    private String lastStashCst;

    public CInitMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM9, mv);
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
        // Here init static final fields.
        mStaticFinalFields.parallelStream().filter(field -> StringFuckerClassVisitor.Companion.canFuck(field.value)).forEach(field -> {
            StringFuckerClassVisitor.Companion.encryptAndWrite(field.value, mv);
            CInitMethodVisitor.super.visitFieldInsn(Opcodes.PUTSTATIC, mClassName, field.name, StringFuckerClassVisitor.STRING_DESC);
        });
    }

    @Override
    public void visitLdcInsn(Object cst) {
        // Here init static or static final fields, but we must check field name int 'visitFieldInsn'
        if (cst instanceof String str && StringFuckerClassVisitor.Companion.canFuck(str)) {
            lastStashCst = str;
            StringFuckerClassVisitor.Companion.encryptAndWrite(lastStashCst, mv);
        } else {
            lastStashCst = null;
            super.visitLdcInsn(cst);
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if (mClassName.equals(owner) && lastStashCst != null) {

            boolean isContain = mStaticFields.parallelStream().anyMatch(field -> field.name.equals(name));

            if (!isContain) {
                Optional<StringField> first = mStaticFinalFields.parallelStream().filter(field -> field.name.equals(name) && field.value == null).findFirst();
                first.ifPresent(field -> field.value = lastStashCst);
            }
        }
        lastStashCst = null;
        super.visitFieldInsn(opcode, owner, name, desc);
    }
}
