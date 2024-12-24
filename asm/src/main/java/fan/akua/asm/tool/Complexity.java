package fan.akua.asm.tool;

import fan.akua.asm.analysis.Structs;

public class Complexity {
    public static int cyclomaticComplexity(final Structs.Graph graph){
        return (graph.edges.size() - graph.nodes.size() + 2);
    }
}
