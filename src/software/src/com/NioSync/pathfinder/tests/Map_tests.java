package com.NioSync.pathfinder.tests;

import java.util.Vector;

import com.NioSync.pathfinder.Coord;
import com.NioSync.pathfinder.Edge;
import com.NioSync.pathfinder.Map;
import com.NioSync.pathfinder.Node;

import junit.framework.TestCase;

public class Map_tests extends TestCase {
	
	String xmlString;
	Map testMap;

	protected void setUp() {
		testMap = new Map();
		Node n1 = new Node();
		Node n2 = new Node();
		Node n3 = new Node();
		Node n4 = new Node();
		n1.setId("n1");
		n2.setId("n2");
		n3.setId("n3");
		n4.setId("n4");
		n1.setName("Node 1");
		n2.setName("Node 2");
		n3.setName("Node 3");
		n4.setName("Node 4");
		n1.setPos(new Coord(0,0));
		n2.setPos(new Coord(0,1));
		n3.setPos(new Coord(1,0));
		n4.setPos(new Coord(2,0));
		n1.setType("Room");
		n2.setType("Room");
		n3.setType("Hall");
		n4.setType("MWash");
		Edge e1 = new Edge(n1, n2, Coord.getDistance(n1.getPos(), n2.getPos()));
		Edge e2 = new Edge(n2, n1, Coord.getDistance(n1.getPos(), n2.getPos()));
		Edge e3 = new Edge(n1, n3, Coord.getDistance(n1.getPos(), n3.getPos()));
		Edge e4 = new Edge(n3, n1, Coord.getDistance(n1.getPos(), n3.getPos()));
		Edge e5 = new Edge(n3, n4, Coord.getDistance(n3.getPos(), n4.getPos()));
		Edge e6 = new Edge(n4, n3, Coord.getDistance(n3.getPos(), n4.getPos()));
		n1.addEdge(e1);
		n1.addEdge(e3);
		n2.addEdge(e2);
		n3.addEdge(e4);
		n3.addEdge(e5);
		n4.addEdge(e6);
		testMap.addNode(n1);
		testMap.addNode(n2);
		testMap.addNode(n3);
		testMap.addNode(n4);
	}

	public void testLoadMapFromFile() {
		Map map = Map.loadMapFromFile(null);
		assertNotNull(map);
		assertEquals(33, map.getNodeIDs().size());
	}


	public void testGetNodeNames() {
		Vector<String> names = testMap.getNodeNames();
		assertEquals(4, names.size());
		assertTrue(names.contains("Node 1"));
		assertTrue(names.contains("Node 2"));
		assertTrue(names.contains("Node 3"));
		assertTrue(names.contains("Node 4"));
	}

	public void testGetNodeIDs() {
		Vector<String> ids = testMap.getNodeIDs();
		assertEquals(4, ids.size());
		assertTrue(ids.contains("n1"));
		assertTrue(ids.contains("n2"));
		assertTrue(ids.contains("n3"));
		assertTrue(ids.contains("n4"));
	}

	public void testGetNodeNameFromID() {
		String t = testMap.getNodeNameFromID("n1");
		assertNotNull(t);
		assertEquals("Node 1", t);
	}

	public void testGetNodeIDFromName() {
		String t = testMap.getNodeIDFromName("Node 2");
		assertNotNull(t);
		assertEquals("n2", t);
	}

	public void testGetNodeFromName() {
		Node n = testMap.getNodeFromName("Node 3");
		assertNotNull(n);
		assertEquals("n3", n.getId());
		assertEquals("Node 3", n.getName());
	}

	public void testGetNodeFromID() {
		Node n = testMap.getNodeFromID("n4");
		assertNotNull(n);
		assertEquals("n4", n.getId());
		assertEquals("Node 4", n.getName());
	}

	public void testGetShortestPathNodes() {
		fail("Not yet implemented");
	}

	public void testGetShortestPathCoords() {
		fail("Not yet implemented");
	}

	public void testAddNode() {
		assertEquals(4, testMap.getNodeIDs().size());
		Node t = new Node();
		t.setName("T");
		t.setId("t");
		t.setPos(new Coord(4,4));
		t.setType("Room");
		testMap.addNode(t);
		assertEquals(5, testMap.getNodeIDs().size());
	}

}
