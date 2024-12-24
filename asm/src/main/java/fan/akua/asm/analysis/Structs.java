package fan.akua.asm.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Structs {
    public static class FlowInsn {
        protected final Set<FlowInsn> successors = new HashSet<>();
        public volatile int index;

        public FlowInsn(int index) {
            this.index = index;
        }
    }

    public record JumpEdge(int startIndex, int endIndex) {

        public boolean isConditional(final Graph graph) {
            return isConditional(graph.nodes);
        }

        public boolean isConditional(final Map<Integer, Block> blockMap) {
            List<FlowInsn> startNodes = blockMap.get(startIndex).nodes;
            int startBlockLatestIndex = startNodes.get(startNodes.size() - 1).index;

            List<FlowInsn> endNodes = blockMap.get(endIndex).nodes;
            int endBlockFirstIndex = endNodes.get(0).index;
            return startBlockLatestIndex != endBlockFirstIndex - 1;
        }
    }

    public static class Block {
        public final List<FlowInsn> nodes;
        public boolean isEndBlock;

        public Block() {
            nodes = new ArrayList<>();
        }
    }

    public static class Graph {
        public final Map<Integer, Block> nodes;
        public final Set<JumpEdge> edges;

        public Graph(final Map<Integer, Block> blockMap, final Set<JumpEdge> jumpEdges) {
            nodes = blockMap;
            edges = jumpEdges;
        }
    }
}
