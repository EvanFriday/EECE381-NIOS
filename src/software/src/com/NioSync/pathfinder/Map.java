package com.NioSync.pathfinder;

import java.util.Vector;



public class Map {
	
	private Vector<Node> nodes;
	
	public static Map loadMapFromFile(String Filename) {
		return null;
	}
	
	public Map() {
		
	}
	
	public Vector<String> getNodeNames() {
		return null;
	}
	
	public Vector<String> getNodeIDs() {
		return null;
	}
	
	public String getNodeNameFromID(String id) {
		return null;
	}
	
	public String getNodeIDFromName(String Name) {
		return null;
	}
	
	public Node getNodeFromName(String name) {
		return null;
	}
	
	public Node getNodeFromID(String id) {
		return null;
	}
	
	public Vector<Node> getShortestPathNodes(Node start, Node end, Boolean avoidStairs) {
		return null;
	}
	
	public Vector<Coord> getShortestPathCoords(Node start, Node end, Boolean avoidStairs) {
		return null;
	}

	
}
