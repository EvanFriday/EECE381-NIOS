package com.NioSync.pathfinder;

import java.util.Vector;

import android.R.string;

public class Node {
	enum Type {
		Exit, Room, Hall, Door, Washroom, Stairway, Elevator
	}
	string id;
	string name;
	Coord pos;
	Vector<Edge> neighbours;
	Type type;
	
	public Node(){
	}
}
