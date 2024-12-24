package fan.akua.asm.insn;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public class Instructions {
    public record LabelInsn(Label label) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return -1;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitLabel(label);
        }
    }

    public record LineNumberInsn(int line, LabelInsn start) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return -1;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitLineNumber(line, start.label);
        }
    }

    public record JumpInsn(int opcode, LabelInsn labelInsn) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return opcode;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitJumpInsn(opcode, labelInsn.label);
        }
    }

    public record Insn(int opcode) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return opcode;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitInsn(opcode);
        }
    }

    public record IntInsn(int opcode, int operand) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return opcode;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitIntInsn(opcode, operand);
        }
    }

    public record VarInsn(int opcode, int varIndex) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return opcode;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitVarInsn(opcode, varIndex);
        }
    }

    public record TypeInsn(int opcode, String type) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return opcode;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitTypeInsn(opcode, type);
        }
    }

    public record FieldInsn(int opcode, String owner, String name, String descriptor) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return opcode;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitFieldInsn(opcode, owner, name, descriptor);
        }
    }

    public record MethodInsn(int opcode,
                             String owner,
                             String name,
                             String descriptor,
                             boolean isInterface
    ) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return opcode;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    public record InvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle,
                                    Object... bootstrapMethodArguments) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return Opcodes.INVOKEDYNAMIC;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
        }
    }

    public record LdcInsn(Object value) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return Opcodes.LDC;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitLdcInsn(value);
        }
    }

    public record IincInsn(int varIndex, int increment) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return Opcodes.IINC;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitIincInsn(varIndex, increment);
        }
    }

    public record TableSwitchInsn(int min, int max, LabelInsn dflt, LabelInsn[] labels) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return Opcodes.TABLESWITCH;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitTableSwitchInsn(min, max, dflt.label, Arrays.stream(labels).map(labelInsn -> labelInsn.label).toArray(Label[]::new));
        }
    }

    public record LookupSwitchInsn(LabelInsn dflt, int[] keys, LabelInsn[] labels) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return Opcodes.LOOKUPSWITCH;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitLookupSwitchInsn(dflt.label, keys, Arrays.stream(labels).map(labelInsn -> labelInsn.label).toArray(Label[]::new));
        }
    }

    public record MultiANewArrayInsn(String descriptor, int numDimensions) implements AbstractInsn {

        @Override
        public int getOpcode() {
            return Opcodes.MULTIANEWARRAY;
        }

        @Override
        public void accept(MethodVisitor methodVisitor) {
            methodVisitor.visitMultiANewArrayInsn(descriptor, numDimensions);
        }
    }


}

