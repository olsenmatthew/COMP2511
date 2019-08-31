package unsw.dungeon.enemy.strategy;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.Coordinates;

/*
 * Node has Coordinates of its own and a list of it's neighboring Nodes
 */
public class Node {

	/**
	 * coordinates of the node
	 */
	private Coordinates coordinates;
	/**
	 * list of neighboring nodes
	 */
	private List<Node> neighbors;

	/**
	 * @param coordinates
	 */
	public Node(Coordinates coordinates) {
		this.coordinates = coordinates;
		this.neighbors = new ArrayList<Node>();
	}

	/**
	 * add neighboring node
	 * 
	 * @param node
	 */
	public void addNeighbor(Node node) {
		this.neighbors.add(node);
	}

	/**
	 * remove neighboring node
	 * 
	 * @param node
	 */
	public void removeNeighbor(Node node) {
		this.neighbors.remove(node);
	}

	/**
	 * @return List<Node>, list of neighboring nodes
	 */
	public List<Node> getNeighbors() {
		return this.neighbors;
	}

	/**
	 * @return Coordinates, this nodes coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

}
