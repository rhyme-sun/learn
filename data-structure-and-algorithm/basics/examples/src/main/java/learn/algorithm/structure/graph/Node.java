package learn.algorithm.structure.graph;

import java.util.ArrayList;

/**
 * 图节点
 */
public class Node {
	/**
	 * 节点值
	 */
	public int value;
	/**
	 * 节点入度，指向该节点边的数量
	 */
	public int in;
	/**
	 * 节点出度，该节点指向其他节点边的数量
	 */
	public int out;
	/**
	 * 从该节点出发找到的邻居节点（考虑方向）
	 */
	public ArrayList<Node> nexts;
	/**
	 * 从该节点出发的边（考虑方向）
	 */
	public ArrayList<Edge> edges;

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}