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
			String XML = "<graph><Image Height=\"469\" Width=\"503\"/><!-- Rooms --><!-- Vert Hall Left Side --><node Type=\"Room\" id=\"r101\" name=\"Room 101\" x=\"350\" y=\"400\"/><node Type=\"Room\" id=\"r105\" name=\"Room 105\" x=\"350\" y=\"340\"/><node Type=\"Room\" id=\"r113\" name=\"Room 113\" x=\"350\" y=\"285\"/><node Type=\"Room\" id=\"r117\" name=\"Room 117\" x=\"350\" y=\"250\"/><node Type=\"Room\" id=\"r119\" name=\"Room 119\" x=\"350\" y=\"230\"/><node Type=\"Room\" id=\"r121\" name=\"Room 121\" x=\"350\" y=\"196\"/><node Type=\"Room\" id=\"r121a\" name=\"Room 121A\" x=\"336\" y=\"194\"/><node Type=\"Room\" id=\"r139\" name=\"Room 139\" x=\"350\" y=\"50\"/><!-- Vert Hall, Right Side --><node Type=\"Room\" id=\"r112b\" name=\"Room 112B\" x=\"350\" y=\"382\"/><node Type=\"Room\" id=\"r112\" name=\"Room 112\" x=\"350\" y=\"254\"/><node Type=\"Room\" id=\"r112a\" name=\"Room 112A\" x=\"372\" y=\"223\"/><node Type=\"Room\" id=\"r130\" name=\"Room 130\" x=\"350\" y=\"195\"/><node Type=\"Room\" id=\"r132\" name=\"Room 132\" x=\"350\" y=\"100\"/><node Type=\"Room\" id=\"r132a\" name=\"Room 132A\" x=\"372\" y=\"61\"/><!-- Horiz Hall Bottom --><node Type=\"Room\" id=\"r149b\" name=\"Room 149B\" x=\"336\" y=\"108\"/><node Type=\"Room\" id=\"r149a\" name=\"Room 149A\" x=\"281\" y=\"124\"/><node Type=\"Room\" id=\"r149\" name=\"Room 149\" x=\"256\" y=\"108\"/><node Type=\"Room\" id=\"r155\" name=\"Room 155\" x=\"232\" y=\"108\"/><node Type=\"Room\" id=\"r155a\" name=\"Room 155A\" x=\"189\" y=\"108\"/><!-- Horiz Hall Top --><node Type=\"Room\" id=\"r158\" name=\"Room 158\" x=\"135\" y=\"108\"/><node Type=\"Room\" id=\"r154\" name=\"Room 154\" x=\"185\" y=\"108\"/><node Type=\"Room\" id=\"r148b\" name=\"Room 148B\" x=\"209\" y=\"47\"/><node Type=\"Room\" id=\"r148\" name=\"Room 148\" x=\"222\" y=\"108\"/><node Type=\"Room\" id=\"r148a\" name=\"Room 148A\" x=\"248\" y=\"56\"/><node Type=\"Room\" id=\"r142\" name=\"Room 142\" x=\"301\" y=\"108\"/><node Type=\"Room\" id=\"r142a\" name=\"Room 142A\" x=\"306\" y=\"77\"/><node Type=\"Room\" id=\"r140\" name=\"Room 140\" x=\"338\" y=\"108\"/>	<!-- WashRooms --><node Type=\"MWash\" id=\"r125\" name=\"Mens Wash: First Floor\" x=\"350\" y=\"158\"/><node Type=\"DWash\" id=\"r125a\" name=\"Handicap Wash: First Floor\" x=\"350\" y=\"144\"/>	<!--Entrances --><node Type=\"Exit\" id=\"kaiser\" name=\"Exit to Kaiser\" x=\"350\" y=\"425\"/><node Type=\"Exit\" id=\"southExit\" name=\"Exit to South\" x=\"350\" y=\"26\"/><node Type=\"Exit\" id=\"eastExit\" name=\"Exit to East\" x=\"111\" y=\"108\"/>	<!-- Junction --><node Type=\"Hall\" id=\"floor1junc\" name=\"Floor 1 Junction\" x=\"350\" y=\"108\"/>	<!-- edges --><edge s=\"kaiser\" d=\"r101\"/><edge s=\"r101\" d=\"r112b\"/><edge s=\"r112b\" d=\"r105\"/><edge s=\"r105\" d=\"r113\"/><edge s=\"r113\" d=\"r112\"/><edge s=\"r112\" d=\"r117\"/><edge s=\"r112\" d=\"r112a\"/><edge s=\"r117\" d=\"r119\"/><edge s=\"r119\" d=\"r121\"/><edge s=\"r121\" d=\"r130\"/><edge s=\"r112a\" d=\"r130\"/><edge s=\"r121\" d=\"r121a\"/><edge s=\"r130\" d=\"r125\"/><edge s=\"r125\" d=\"r125a\"/><edge s=\"r125a\" d=\"floor1junc\"/><edge s=\"floor1junc\" d=\"r132\"/><edge s=\"r132a\" d=\"r132\"/><edge s=\"r125a\" d=\"floor1junc\"/><edge s=\"r132\" d=\"r139\"/><edge s=\"r139\" d=\"southExit\"/><edge s=\"floor1junc\" d=\"r140\" /><edge s=\"r140\" d=\"r149b\"/><edge s=\"r149b\" d=\"r142\"/><edge s=\"r142\" d=\"r149\"/><edge s=\"r149\" d=\"r149a\"/><edge s=\"r149\" d=\"r148\"/><edge s=\"r148\" d=\"r155\"/><edge s=\"r155\" d=\"r154\"/><edge s=\"r154\" d=\"r155a\"/><edge s=\"r155a\" d=\"r158\"/><edge s=\"r158\" d=\"eastExit\"/><edge s=\"r105\" d=\"r113\"/><edge s=\"r148\" d=\"r148b\"/><edge s=\"r148\" d=\"r148a\"/></graph>";
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