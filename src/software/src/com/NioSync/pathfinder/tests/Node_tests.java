package com.NioSync.pathfinder.tests;

import com.NioSync.pathfinder.Edge;
import com.NioSync.pathfinder.Node;

import junit.framework.TestCase;


public class Node_tests extends TestCase {
	
	Node n;
	
	public Node_tests(final String name) {
		super(name);
	}
	
	public void setUp() {
		n = new Node();
	}
	
	public void testNode() {
		assertNotNull(n);
		assertNotNull(n.getEdges());
		
	}
	
	public void testAddEdge() {
		Node dest = new Node();
		Edge e = new Edge(n, dest, 10);
		assertNotNull(e);
		assertNotNull(n);
		n.addEdge(e);	
		assertEquals(1, n.getEdges().size());
	}

}
