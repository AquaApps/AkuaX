package fan.akua.asm.analysis;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;

import fan.akua.asm.basis.IVisitor;

public class MaxAnalyzer implements IVisitor {
    private int maxLocal, maxStack;

    public int getMaxLocal() {
        return maxLocal;
    }

    public int getMaxStack() {
        return maxStack;
    }

    @Override
    public void visitCode() {
        maxLocal = 0;
        maxStack = 0;
    }

    @Override
    public void visitInsn(int index, int opcode) {

    }

    @Override
    public void visitIntInsn(int index, int opcode, int operand) {

    }

    @Override
    public void visitVarInsn(int index, int opcode, int varIndex) {

    }

    @Override
    public void visitTypeInsn(int index, int opcode, String type) {

    }

    @Override
    public void visitFieldInsn(int index, int opcode, String owner, String name, String descriptor) {

    }

    @Override
    public void visitMethodInsn(int index, int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {

    }

    @Override
    public void visitInvokeDynamicInsn(int index, String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {

    }

    @Override
    public void visitJumpInsn(int index, int opcode, Label label) {

    }

    @Override
    public void visitLabel(int index, Label label) {

    }

    @Override
    public void visitLdcInsn(int index, Object value) {

    }

    @Override
    public void visitIincInsn(int index, int varIndex, int increment) {

    }

    @Override
    public void visitTableSwitchInsn(int index, int min, int max, Label dflt, Label... labels) {

    }

    @Override
    public void visitLookupSwitchInsn(int index, Label dflt, int[] keys, Label[] labels) {

    }

    @Override
    public void visitMultiANewArrayInsn(int index, String descriptor, int numDimensions) {

    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {

    }

    @Override
    public void visitLineNumber(int index, int line, Label start) {

    }

    @Override
    public void visitMax(int maxLocals, int maxStacks) {
        maxLocal = Math.max(maxLocal, maxLocals);
        maxStack = Math.max(maxStack, maxStacks);
    }

    @Override
    public void visitEnd() {

    }
}

