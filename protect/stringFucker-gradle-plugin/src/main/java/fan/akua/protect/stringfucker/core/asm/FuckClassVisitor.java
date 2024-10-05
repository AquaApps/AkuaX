package fan.akua.protect.stringfucker.core.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class FuckClassVisitor extends ClassVisitor {
    public static final String IGNORE_ANNOTATION =
            "Lfan/akua/protect/stringfucker/annotation/IgnoreStringFucker;";

    private final VisitorParams params;

    private boolean mIgnoreClass;

    public FuckClassVisitor(final ClassVisitor cv, VisitorParams params) {
        super(Opcodes.ASM9, cv);
        this.params = params;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        mIgnoreClass = IGNORE_ANNOTATION.equals(descriptor);
        if (mIgnoreClass && params.deleteIgnoreAnnotation())
            return null;
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        var mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (mIgnoreClass || mv == null) return mv;
        return new MethodVisitor(Opcodes.ASM9, mv) {
            @Override
            public void visitLdcInsn(Object value) {
                if (value instanceof String str) {
                    byte[] key = params.impl().keygen(str);
                    byte[] encrypt = params.impl().encrypt(str, key);

                    params.modeWriter().modifyStr(key, encrypt, this);
                    return;
                }
                super.visitLdcInsn(value);
            }
        };
    }


}
