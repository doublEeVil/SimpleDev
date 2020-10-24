package com.simpledev.tools.guava;

import com.google.common.graph.Graphs;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;

public class _06_Graph {
    public static void main(String ... args) {
        // Graph
        System.out.println("---graph");
        MutableNetwork<String, Integer> network = NetworkBuilder
                .undirected()
                .allowsSelfLoops(true)
                .allowsParallelEdges(true)
                .build();
        network.addEdge("A", "B", 1);
        network.addEdge("B", "C", 3);
        network.addEdge("C", "D", 2);

        System.out.println(Graphs.hasCycle(network));
    }
}
