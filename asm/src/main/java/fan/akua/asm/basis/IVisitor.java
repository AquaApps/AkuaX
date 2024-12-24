package fan.akua.asm.basis;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;

public interface IVisitor {
    void visitCode();

    void visitInsn(final int index, final int opcode);

    void visitIntInsn(final int index, final int opcode, final int operand);

    void visitVarInsn(final int index, final int opcode, final int varIndex);

    void visitTypeInsn(final int index, final int opcode, final String type);

    void visitFieldInsn(
            final int index, final int opcode, final String owner, final String name, final String descriptor);

    void visitMethodInsn(
            final int index,
            final int opcodeAndSource,
            final String owner,
            final String name,
            final String descriptor,
            final boolean isInterface);

    void visitInvokeDynamicInsn(
            final int index,
            final String name,
            final String descriptor,
            final Handle bootstrapMethodHandle,
            final Object... bootstrapMethodArguments);

    void visitJumpInsn(final int index, final int opcode, final Label label);

    void visitLabel(final int index, final Label label);

    void visitLdcInsn(final int index, final Object value);

    void visitIincInsn(final int index, final int varIndex, final int increment);

    void visitTableSwitchInsn(
            final int index, final int min, final int max, final Label dflt, final Label... labels);

    void visitLookupSwitchInsn(final int index, final Label dflt, final int[] keys, final Label[] labels);

    void visitMultiANewArrayInsn(final int index, final String descriptor, final int numDimensions);

    void visitTryCatchBlock(
            final Label start, final Label end, final Label handler, final String type);

    void visitLineNumber(final int index, final int line, final Label start);

    void visitMax(final int maxLocals, final int maxStacks);

    void visitEnd();
}

