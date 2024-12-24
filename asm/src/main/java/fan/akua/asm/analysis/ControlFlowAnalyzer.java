package fan.akua.asm.analysis;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import fan.akua.asm.basis.IVisitor;

public class ControlFlowAnalyzer implements IVisitor, Opcodes {
    private final List<Runnable> labelCollector = new ArrayList<>();
    private final Map<Label, Integer> label2Index;

    // index转分析时的指令
    private final Map<Integer, Structs.FlowInsn> index2FlowInsn;
    // index映射为'代码块id'
    private final Map<Integer, Integer> index2BlockId;
    // '代码块id'映射为代码块实体
    private final Map<Integer, Structs.Block> blocks;
    // 记录join下标
    private final HashSet<Integer> joinIndexes;
    // 记录跳转边
    private final Set<Structs.JumpEdge> jumps;
    // 代码块的id生成器
    private final AtomicInteger idRnd;

    public ControlFlowAnalyzer(final Map<Label, Integer> labelCache) {
        this.label2Index = labelCache;
        index2FlowInsn = new HashMap<>();
        index2BlockId = new HashMap<>();
        blocks = new HashMap<>();
        joinIndexes = new HashSet<>();
        jumps = new HashSet<>();
        idRnd = new AtomicInteger();
    }

    /**
     * getGraph 是一个入口函数，返回代码流图
     *
     * @return 有向有环图
     */
    public Structs.Graph getGraph() {
        return new Structs.Graph(blocks, jumps);
    }

    /**
     * newControlFlowEdge 是一个辅助函数，用于记录每条指令的后继。
     *
     * @Warning 这个函数会并行执行。
     */
    protected void newControlFlowEdge(int from, int to) {
        Structs.FlowInsn fromNode, toNode;
        synchronized (index2FlowInsn) {
            fromNode = index2FlowInsn.computeIfAbsent(from, Structs.FlowInsn::new);
            toNode = index2FlowInsn.computeIfAbsent(to, Structs.FlowInsn::new);
            fromNode.index = from;
            toNode.index = to;
        }
        synchronized (fromNode.successors) {
            fromNode.successors.add(toNode);
        }
        if (to != from + 1) {
            synchronized (joinIndexes) {
                joinIndexes.add(to);
            }
        }
    }

    @Override
    public void visitCode() {
        labelCollector.clear();
    }

    @Override
    public void visitEnd() {
        labelCollector.parallelStream().forEach(Runnable::run);
        Structs.FlowInsn rootNode = index2FlowInsn.get(0);
        internalMock(rootNode, -1); // -1代表根节点前驱
    }

    /**
     * internalMock 是一个递归函数，用于构建控制流图。
     * 将连续的指令（Structs.FlowInsn）合并，形成基本块（Structs.Block）。
     *
     * @param insnNode 当前指令
     * @param lastId   前驱基本块的id
     */
    private void internalMock(final Structs.FlowInsn insnNode, final int lastId) {
        if (index2BlockId.containsKey(insnNode.index)) {
            // 访问过
            jumps.add(new Structs.JumpEdge(lastId, index2BlockId.get(insnNode.index)));
            return;
        }

        final int blockId = idRnd.getAndIncrement();
        final Structs.Block block = new Structs.Block();

        index2BlockId.put(insnNode.index, blockId);
        if (lastId != -1)
            jumps.add(new Structs.JumpEdge(lastId, blockId));

        Structs.FlowInsn tmp = insnNode;
        List<Structs.FlowInsn> successors = tmp.successors.parallelStream().toList();

        for (; ; ) {
            block.nodes.add(tmp);
            switch (successors.size()) {
                case 1: {
                    tmp = successors.get(0);
                    successors = tmp.successors.parallelStream().toList();
                    if (joinIndexes.contains(tmp.index)) {
                        // join点
                        blocks.put(blockId, block);
                        internalMock(tmp, blockId);
                        return;
                    }
                    // 普通点
                    continue;
                }
                case 0: {
                    // 终止点
                    block.isEndBlock = true;
                    blocks.put(blockId, block);
                    return;
                }
                default: {
                    // fork点
                    blocks.put(blockId, block);
                    // todo: fix parallel's bug.
                    successors.forEach(successor -> internalMock(successor, blockId));
                    return;
                }
            }
        }
    }

    @Override
    public void visitLabel(int insnIndex, Label label) {
        processLabelLineFrame(insnIndex);
    }

    @Override
    public void visitLineNumber(int insnIndex, int line, Label start) {
        processLabelLineFrame(insnIndex);
    }

    private void processLabelLineFrame(int insnIndex) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitJumpInsn(int insnIndex, int opcode, Label label) {
        processJump(insnIndex, opcode, label);
    }

    private void processJump(int insnIndex, int opcode, Label label) {
        if (opcode != GOTO && opcode != JSR) {
            newControlFlowEdge(insnIndex, insnIndex + 1);
        }
        final int rInsnIndex = insnIndex;
        labelCollector.add(() -> {
            int jumpInsnIndex = label2Index.get(label);
            newControlFlowEdge(rInsnIndex, jumpInsnIndex);
        });
    }

    @Override
    public void visitLookupSwitchInsn(int insnIndex, Label dflt, int[] keys, Label[] labels) {
        processSwitch(insnIndex, dflt, labels);
    }

    @Override
    public void visitTableSwitchInsn(int insnIndex, int min, int max, Label dflt, Label... labels) {
        processSwitch(insnIndex, dflt, labels);
    }

    private void processSwitch(int rInsnIndex, Label dflt, Label[] labels) {
        labelCollector.add(() -> {
            int targetInsnIndex = label2Index.get(dflt);
            newControlFlowEdge(rInsnIndex, targetInsnIndex);
            for (Label label : labels) {
                targetInsnIndex = label2Index.get(label);
                newControlFlowEdge(rInsnIndex, targetInsnIndex);
            }
        });
    }

    // todo ret
    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        processTryCatch(start, end, handler);
    }

    private void processTryCatch(Label start, Label end, Label handler) {
        labelCollector.add(() -> {
            int startIndex = label2Index.get(start);
            int endIndex = label2Index.get(end);
            int handlerIndex = label2Index.get(handler);
            for (int i = startIndex; i < endIndex; i++) {
                newControlFlowEdge(i, handlerIndex);
            }
        });
    }

    @Override
    public void visitInsn(int insnIndex, int opcode) {
        if (opcode == RET) {
            // todo try catch
        } else if (opcode != ATHROW && (opcode < IRETURN || opcode > RETURN)) {
            newControlFlowEdge(insnIndex, insnIndex + 1);
        }
    }

    @Override
    public void visitIntInsn(int insnIndex, int opcode, int operand) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitVarInsn(int insnIndex, int opcode, int varIndex) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitTypeInsn(int insnIndex, int opcode, String type) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitFieldInsn(int insnIndex, int opcode, String owner, String name, String descriptor) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitMethodInsn(int insnIndex, int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitInvokeDynamicInsn(int insnIndex, String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitLdcInsn(int insnIndex, Object value) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitIincInsn(int insnIndex, int varIndex, int increment) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }

    @Override
    public void visitMultiANewArrayInsn(int insnIndex, String descriptor, int numDimensions) {
        newControlFlowEdge(insnIndex, insnIndex + 1);
    }
    @Override
    public void visitMax(int maxLocals, int maxStack) {

    }

}

