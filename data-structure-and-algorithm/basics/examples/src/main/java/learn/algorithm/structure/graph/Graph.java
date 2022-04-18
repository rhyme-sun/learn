package learn.algorithm.structure.graph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图
 */
public class Graph {

	public HashMap<Integer, Node> nodes;
	public HashSet<Edge> edges;
	
	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
