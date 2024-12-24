package fan.akua.asm.tool;

import java.util.List;

import fan.akua.asm.analysis.Structs;
import fan.akua.asm.insn.AbstractInsn;

public class Dot {
    public static String generateDot(final Structs.Graph graph, final String methodName, final List<AbstractInsn> insnArrays){
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("digraph ").append(methodName).append(" {\n");
        graph.nodes.forEach((id, block) -> {
            stringBuilder.append("block").append(id).append(" [ ").append(block.isEndBlock ? "color=\"red\"" : "").append(" shape=\"box\" label=\"");
            for (Structs.FlowInsn node : block.nodes) {
                stringBuilder.append(OpcodeMap.toString(insnArrays.get(node.index).getOpcode())).append("\n");
            }
            stringBuilder.append("\"];\n");
        });
        stringBuilder.append("splines=compound;\n");
        graph.edges.forEach(jumpEdge -> {
            stringBuilder.append("block").append(jumpEdge.startIndex()).append(" -> block").append(jumpEdge.endIndex()).append(";\n");
        });
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}
