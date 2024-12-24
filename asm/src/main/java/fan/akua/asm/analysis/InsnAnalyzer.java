package fan.akua.asm.analysis;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fan.akua.asm.AsmConfig;
import fan.akua.asm.basis.IVisitor;
import fan.akua.asm.insn.AbstractInsn;
import fan.akua.asm.insn.Instructions;

public class InsnAnalyzer implements IVisitor {
    private final Map<Label, Instructions.LabelInsn> labelInsnMap = new HashMap<>();
    private final Map<Label, Integer> label2Index = new HashMap<>();
    private final List<AbstractInsn> insnList = new ArrayList<>();
    private final int api;


    public InsnAnalyzer() {
        api = AsmConfig.api;
    }

    public Map<Label, Integer> shareLabelPool() {
        return label2Index;
    }

    public List<AbstractInsn> getInsnList() {
        return insnList;
    }

    private Instructions.LabelInsn getLabelInsn(Label label) {
        return labelInsnMap.computeIfAbsent(label, Instructions.LabelInsn::new);
    }

    private Instructions.LabelInsn[] getLabelInsns(Label[] labels) {
        return Arrays.stream(labels).map(this::getLabelInsn).toList().toArray(new Instructions.LabelInsn[0]);
    }

    @Override
    public void visitCode() {
        this.labelInsnMap.clear();
        this.label2Index.clear();
        this.insnList.clear();
    }

    @Override
    public void visitInsn(int index, int opcode) {
        insnList.add(new Instructions.Insn(opcode));
    }

    @Override
    public void visitIntInsn(int index, int opcode, int operand) {
        insnList.add(new Instructions.IntInsn(opcode, operand));
    }

    @Override
    public void visitVarInsn(int index, int opcode, int varIndex) {
        insnList.add(new Instructions.VarInsn(opcode, varIndex));
    }

    @Override
    public void visitTypeInsn(int index, int opcode, String type) {
        insnList.add(new Instructions.TypeInsn(opcode, type));
    }

    @Override
    public void visitFieldInsn(int index, int opcode, String owner, String name, String descriptor) {
        insnList.add(new Instructions.FieldInsn(opcode, owner, name, descriptor));
    }

    @Override
    public void visitMethodInsn(int index, int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        if (api < Opcodes.ASM5 && (opcodeAndSource & Opcodes.SOURCE_DEPRECATED) == 0) {
            // Redirect the call to the deprecated version of this method.
            return;
        }
        int opcode = opcodeAndSource & ~Opcodes.SOURCE_MASK;

        insnList.add(new Instructions.MethodInsn(opcode, owner, name, descriptor, isInterface));
    }

    @Override
    public void visitInvokeDynamicInsn(int index, String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        insnList.add(new Instructions.InvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments));
    }

    @Override
    public void visitJumpInsn(int index, int opcode, Label label) {
        Instructions.LabelInsn labelInsn = getLabelInsn(label);
        insnList.add(new Instructions.JumpInsn(opcode, labelInsn));
    }

    @Override
    public void visitLabel(int index, Label label) {
        label2Index.put(label, index);
        Instructions.LabelInsn labelInsn = getLabelInsn(label);
        insnList.add(labelInsn);
    }

    @Override
    public void visitLdcInsn(int index, Object value) {
        insnList.add(new Instructions.LdcInsn(value));
    }

    @Override
    public void visitIincInsn(int index, int varIndex, int increment) {
        insnList.add(new Instructions.IincInsn(varIndex, increment));
    }

    @Override
    public void visitTableSwitchInsn(int index, int min, int max, Label dflt, Label... labels) {
        insnList.add(new Instructions.TableSwitchInsn(min, max, getLabelInsn(dflt), getLabelInsns(labels)));
    }

    @Override
    public void visitLookupSwitchInsn(int index, Label dflt, int[] keys, Label[] labels) {
        insnList.add(new Instructions.LookupSwitchInsn(getLabelInsn(dflt), keys, getLabelInsns(labels)));
    }

    @Override
    public void visitMultiANewArrayInsn(int index, String descriptor, int numDimensions) {
        insnList.add(new Instructions.MultiANewArrayInsn(descriptor, numDimensions));
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {

    }

    @Override
    public void visitLineNumber(int index, int line, Label start) {
        label2Index.put(start, index);
        insnList.add(new Instructions.LineNumberInsn(line, getLabelInsn(start)));
    }

    @Override
    public void visitMax(int maxLocals, int maxStack) {

    }

    @Override
    public void visitEnd() {

    }
}

