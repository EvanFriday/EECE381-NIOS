package com.NioSync.pathfinder;

import java.lang.Math;

public class Coord {
	private int x,y;
	public Coord(int X, int Y) {
		this.x = X;
		this.y = Y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public static int getDistance(Coord c1, Coord c2) {
		return (int)Math.sqrt(Math.pow((c2.getX() - c1.getX()),2) + Math.pow((c2.getY() - c1.getY()),2));
	}
}
