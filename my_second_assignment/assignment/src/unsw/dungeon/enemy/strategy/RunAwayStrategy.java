package unsw.dungeon.enemy.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;

/*
 * {@link RunAwayStrategy} implements {@link SearchStrategy}
 * This searches for paths from the source to further ways from the destination
 */
public class RunAwayStrategy implements SearchStrategy {

	/**
	 * path is a list of nodes containing the chosen path from the src
	 */
	private List<Node> path;
	/**
	 * bound on the depth of the search
	 */
	private int depth;
	/**
	 * this is the source Node
	 */
	private Node src;
	/**
	 * this is the destination Node
	 */
	private Node dest;

	/**
	 * @param src
	 * @param dest
	 * @param depth
	 * @param dungeon
	 */
	public RunAwayStrategy(Coordinates src, Coordinates dest, int depth, Dungeon dungeon) {
		initSearchSpace(src, dest, dungeon);
		this.depth = depth;
		this.path = new ArrayList<Node>();
	}

	/*
	 * This compares the distance of the two nodes from the destination (the further
	 * the better)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Node o1, Node o2) {
		double first = heuristic(dest.getCoordinates(), o1.getCoordinates());
		double second = heuristic(dest.getCoordinates(), o2.getCoordinates());
		first = first * 100;
		second = second * 100;
		return (int) (second - first);
	}

	/*
	 * search for solutions further from the destination
	 * 
	 * @see unsw.dungeon.enemy.strategy.SearchStrategy#search()
	 * 
	 * @return always true, since the location is either the source or path further
	 * from dest
	 */
	@Override
	public boolean search() {
		if (src == null || dest == null) {
			return false;
		}
		List<Node> q = new ArrayList<Node>();
		List<Node> visited = new ArrayList<Node>();
		q.add(src);
		int i = 0;
		while (!q.isEmpty() && i < depth) {
			Node curr = q.remove(0);
			visited.add(curr);
			q.addAll(curr.getNeighbors());
			q.removeAll(visited);
			Collections.sort(q, this);
			i++;
		}
		path.addAll(visited);
		path.remove(src);
		path.add(dest);
		return true;
	}

	/*
	 * get next coordinate in path
	 * 
	 * @see unsw.dungeon.enemy.strategy.SearchStrategy#getNext()
	 */
	@Override
	public Coordinates getNext() {
		Coordinates result = null;
		if (compare(src, path.get(1)) < 0) {
			result = src.getCoordinates();
		} else {

			Node next = path.get(0);
			result = next.getCoordinates();
		}
		return result;
	}

	/*
	 * This heuristic calculates the Manhattan distance from coordinate a to
	 * coordinate b.
	 * 
	 * @see unsw.dungeon.enemy.strategy.SearchStrategy#heuristic(unsw.dungeon.
	 * Coordinates, unsw.dungeon.Coordinates)
	 * 
	 * @param a, Coordinates
	 * 
	 * @param b, Coordinates
	 * 
	 * @return Manhattan distance between coordinates
	 */
	@Override
	public double heuristic(Coordinates a, Coordinates b) {
		return (int) (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
	}

	/**
	 * @param source
	 * @param destination
	 * @param dungeon
	 * @return true if search space has been initialized, false otherwise
	 */
	private boolean initSearchSpace(Coordinates source, Coordinates destination, Dungeon dungeon) {
		List<Node> nodes = new ArrayList<Node>();

		for (int i = 0; i < dungeon.getWidth(); i++) {
			for (int k = 0; k < dungeon.getHeight(); k++) {
				boolean add = true;
				Coordinates node = new Coordinates(i, k);
				for (Entity e : dungeon.getEntities(node)) {
					if (!e.canCoexist()) {
						add = false;
					}
				}
				if (add) {
					Node toAdd = new Node(node);
					for (Node n : nodes) {
						if (n.getCoordinates().adjacentTo(node)) {
							n.addNeighbor(toAdd);
							toAdd.addNeighbor(n);
						}
					}
					nodes.add(toAdd);
					if (toAdd.getCoordinates().equals(source)) {
						src = toAdd;
					}
					if (toAdd.getCoordinates().equals(destination)) {
						dest = toAdd;
					}
				}
			}
		}
		return src != null && dest != null;
	}

}
