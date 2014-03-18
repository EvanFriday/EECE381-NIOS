package com.NioSync.pathfinder;

public class Edge {
	Node source;
	Node target;
	int distance;
	
	Edge(Node Source, Node Target, int Dist) {
		source = Source;
		target = Target;
		distance = Dist;
	}

	public Node getSource() {
		return source;
	}

	public Node getTarget() {
		return target;
	}

	public int getDistance() {
		return distance;
	}
}
