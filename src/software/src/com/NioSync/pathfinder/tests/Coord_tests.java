package com.NioSync.pathfinder.tests;

import com.NioSync.pathfinder.Coord;

import junit.framework.TestCase;

public class Coord_tests extends TestCase {
	
	Coord testCoord;
		
	protected void setUp() {
		testCoord = new Coord(0,0);
	}

	public void testCoord() {
		assertNotNull(testCoord);
		assertEquals(0, testCoord.getX());
		assertEquals(0, testCoord.getY());
	}

	public void testGetDistance() {
		Coord c1 = new Coord(0,10);
		Coord c2 = new Coord(10,0);
		Coord c3 = new Coord(4,3);
		assertEquals(10, Coord.getDistance(testCoord, c1));
		assertEquals(10, Coord.getDistance(testCoord, c2));
		assertEquals(5, Coord.getDistance(testCoord, c3));
	}

}
