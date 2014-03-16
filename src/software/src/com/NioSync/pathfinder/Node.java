package com.NioSync.pathfinder;

import android.R.string;

public class Node {
	string name;
	int x,y;
	Node neighbour[];
	
	public Node(){
		neighbour= new Node[2];
	}
}
