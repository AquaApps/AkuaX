package fan.akua.protect.stringfucker.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import fan.akua.protect.stringfucker.IStringFucker;

public class DefaultMethodVisitor extends MethodVisitor {
    private List<StringField> mStaticFinalFields;
    private List<StringField> mFinalFields;
    private String mClassName;

    public DefaultMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM9, mv);
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
        if (cst instanceof String str && StringFuckerClassVisitor.Companion.canFuck(str)) {
            // If the value is a static final field
            Optional<StringField> firstStaticFinalField = mStaticFinalFields.parallelStream()
                    .filter(field -> cst.equals(field.value))
                    .findFirst();
            firstStaticFinalField.ifPresent(stringField -> DefaultMethodVisitor.super.visitFieldInsn(Opcodes.GETSTATIC, mClassName, stringField.name, StringFuckerClassVisitor.STRING_DESC));
            if (firstStaticFinalField.isPresent()) return;

            // If the value is a final field (not static)
            Optional<StringField> firstFinalField = mFinalFields.parallelStream()
                    // if the value of a final field is null, we ignore it
                    .filter(field -> cst.equals(field.value))
                    .findFirst();
            firstFinalField.ifPresent(field -> {
                DefaultMethodVisitor.super.visitVarInsn(Opcodes.ALOAD, 0);
                DefaultMethodVisitor.super.visitFieldInsn(Opcodes.GETFIELD, mClassName, field.name, StringFuckerClassVisitor.STRING_DESC);
            });
            if (firstFinalField.isPresent()) return;

            // local variables
            StringFuckerClassVisitor.Companion.encryptAndWrite(str, mv);
            return;
        }
        super.visitLdcInsn(cst);
    }
}

