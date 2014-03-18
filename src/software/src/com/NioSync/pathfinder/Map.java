package com.NioSync.pathfinder;

import java.io.File;

import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



public class Map {
	
	int mapWidth, mapHeight;
	
	private Vector<Node> nodes;
	
	public static Document loadXMLFromString(String xml)
	{
		try {
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
		    return builder.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map loadMapFromFile(String Filename) {
		Document xmlDoc;
		if (Filename == null) {
			String XML = "<graph><Image Height=\"469\" Width=\"503\"/><node Type=\"Exit\" id=\"kaiserExit\" name=\"Entrance from Kaiser Atrium\" x=\"353\" y=\"425\"/><node Type=\"Room\" id=\"r101\" name=\"Room 101\" x=\"350\" y=\"396\"/><node Type=\"Room\" id=\"r112B\" name=\"Room 112B\" x=\"350\" y=\"383\"/><node Type=\"Room\" id=\"r105\" name=\"Room 105\" x=\"350\" y=\"359\"/><!--...--><node Type=\"MWash\" id=\"mw125\" name=\"Mens Washroom 125\" x=\"350\" y=\"151\"/><node Type=\"Hall\" id=\"f1junction\" name=\"Floor 1 Hallway\" x=\"350\" y=\"108\"/><edge s=\"f1junction\" d=\"r101\"/><edge s=\"kaiserExit\" d=\"r101\"/><edge s=\"kaiserExit\" d=\"f1junction\"/></graph>";
			xmlDoc = loadXMLFromString(XML);
		}
		else {
			File xmlFile = new File(Filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				xmlDoc = dBuilder.parse(xmlFile);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		Map map = new Map();
		NodeList nList = xmlDoc.getElementsByTagName("node");
		for (int nCount = 0; nCount < nList.getLength(); nCount++) {
			org.w3c.dom.Node nNode = nList.item(nCount);			
			Element elem = (Element) nNode;
			Node n = new Node();
			n.setType(elem.getAttribute("Type"));
			n.setId(elem.getAttribute("id"));
			n.setName(elem.getAttribute("name"));
			int x = Integer.parseInt(elem.getAttribute("x"));
			int y = Integer.parseInt(elem.getAttribute("y"));
			Coord c = new Coord(x,y);
			n.setPos(c);
			map.addNode(n);
			System.out.println("INFO: Map: Adding node: " + n.getId());
		}
		nList = xmlDoc.getElementsByTagName("edge");
		for (int nCount=0; nCount < nList.getLength(); nCount++) {
			org.w3c.dom.Node nNode = nList.item(nCount);
			Element elem = (Element) nNode;
			Node source = map.getNodeFromID(elem.getAttribute("s"));
			Node dest = map.getNodeFromID(elem.getAttribute("d"));
			int dist = Coord.getDistance(source.getPos(), dest.getPos());
			Edge e = new Edge(source, dest, dist);
			source.addEdge(e);	
			e = new Edge(dest, source, dist);
			dest.addEdge(e);
			System.out.println("INFO: Map: Adding Edge: " + source.getId() + " -> " + dest.getId());
		}
		nList = xmlDoc.getElementsByTagName("Image");
		Element elem = (Element) nList.item(0);
		map.mapHeight = Integer.parseInt(elem.getAttribute("Height"));
		map.mapWidth = Integer.parseInt(elem.getAttribute("Width"));
		return map;
	}
	
	public Map() {
		nodes = new Vector<Node>();
	}
	
	public Vector<String> getNodeNames() {
		Vector<String> names = new Vector<String>();
		for (Node n : nodes) {
			names.add(n.getName());
		}
		return names;
	}
	
	public Vector<String> getNodeIDs() {
		Vector<String> ids = new Vector<String>();
		for (Node n : nodes) {
			ids.add(n.getId());
		}
		return ids;
	}
	
	public String getNodeNameFromID(String id) {
		return getNodeFromID(id).getName();
	}
	
	public String getNodeIDFromName(String Name) {
		return getNodeFromName(Name).getId();
	}
	
	public Node getNodeFromName(String name) {
		for (Node n : nodes) {
			if (n.getName().equals(name)) {
				return n;
			}
		}
		return null;
	}
	
	public Node getNodeFromID(String id) {
		for (Node n : nodes) {
			if (n.getId().equals(id)) {
				return n;
			}
		}
		System.out.println("ERROR: Map.getNodeFromID(" + id + ") returning NULL!");
		return null;
	}
	
	public Vector<Node> getShortestPathNodes(Node start, Node end, Boolean avoidStairs) {
		//TODO: Implement
		
		return null;
	}
	
	public Vector<Coord> getShortestPathCoords(Node start, Node end, Boolean avoidStairs) {
		Vector<Coord> coords = new Vector<Coord>();
		for (Node n : getShortestPathNodes(start,end,avoidStairs)) {
			coords.add(n.getPos());
		}
		return null;
	}
	
	public void addNode(Node target) {
		nodes.add(target);		
	}
}