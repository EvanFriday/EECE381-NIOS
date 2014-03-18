package com.NioSync.pathfinder;

import java.util.Vector;


public class Node {
	String id;
	String name;
	Coord pos;
	Vector<Edge> edges;
	String type; //Exit, Room, Hall, Door, MWash, FWash, DWash, Stairway, Elevator
	
	public Node(){
	}

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coord getPos() {
		return pos;
	}

	public void setPos(Coord pos) {
		this.pos = pos;
	}

	public Vector<Edge> getEdges() {
		return edges;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void addEdge(Edge e) {
		edges.add(e);
	}
}
