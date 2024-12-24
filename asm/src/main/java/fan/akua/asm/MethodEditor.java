package fan.akua.asm;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

import fan.akua.asm.basis.IEditor;
import fan.akua.asm.basis.IVisitor;

public abstract class MethodEditor extends MethodVisitor implements IEditor {
    private final MethodVisitor targetVisitor;
    private int insnIndex = 0;

    public MethodEditor() {
        this(null);
    }

    // 最终accept交给methodVisitor
    public MethodEditor(final MethodVisitor targetVisitor) {
        super(AsmConfig.api);
        this.targetVisitor = targetVisitor;
    }

    protected abstract List<IVisitor> getVisitors();

    @Override
    public void visitCode() {
        super.visitCode();
        getVisitors().forEach(IVisitor::visitCode);
    }

    @Override
    public void visitEnd() {
        getVisitors().forEach(IVisitor::visitEnd);
        if (targetVisitor != null) accept(targetVisitor);
    }

    @Override
    public abstract void accept(MethodVisitor methodVisitor);

    @Override
    public void visitInsn(int opcode) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitInsn(rInsnIndex, opcode));
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitIntInsn(rInsnIndex, opcode, operand));
    }

    @Override
    public void visitVarInsn(int opcode, int varIndex) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitVarInsn(rInsnIndex, opcode, varIndex));
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitTypeInsn(rInsnIndex, opcode, type));
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitFieldInsn(rInsnIndex, opcode, owner, name, descriptor));
    }

    @Override
    public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitMethodInsn(rInsnIndex, opcodeAndSource, owner, name, descriptor, isInterface));
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitInvokeDynamicInsn(rInsnIndex, name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments));
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitJumpInsn(rInsnIndex, opcode, label));
    }

    @Override
    public void visitLabel(Label label) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitLabel(rInsnIndex, label));
    }

    @Override
    public void visitLdcInsn(Object value) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitLdcInsn(rInsnIndex, value));
    }

    @Override
    public void visitIincInsn(int varIndex, int increment) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitIincInsn(rInsnIndex, varIndex, increment));
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitTableSwitchInsn(rInsnIndex, min, max, dflt, labels));
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitLookupSwitchInsn(rInsnIndex, dflt, keys, labels));
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitMultiANewArrayInsn(rInsnIndex, descriptor, numDimensions));
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        getVisitors().forEach(visitor -> visitor.visitTryCatchBlock(start, end, handler, type));
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        final int rInsnIndex = insnIndex++;
        getVisitors().forEach(visitor -> visitor.visitLineNumber(rInsnIndex, line, start));
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        getVisitors().forEach(visitor -> visitor.visitMax(maxStack, maxLocals));
    }
}
