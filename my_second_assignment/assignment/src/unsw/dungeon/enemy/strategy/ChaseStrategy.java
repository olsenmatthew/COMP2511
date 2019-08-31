package unsw.dungeon.enemy.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;

/*
 * {@link ChaseStrategy} implements {@link SearchStrategy}
 * Chase Strategy implements a iterative deepening search to find paths between two coordinates in the given dungeon.
 */
public class ChaseStrategy implements SearchStrategy {

	/**
	 * path of nodes to take
	 */
	private List<Node> path;
	/**
	 * depth bound of search
	 */
	private int depth;
	/**
	 * source node
	 */
	private Node src;
	/**
	 * destination node
	 */
	private Node dest;

	/**
	 * @param src
	 * @param dest
	 * @param depth
	 * @param dungeon
	 */
	public ChaseStrategy(Coordinates src, Coordinates dest, int depth, Dungeon dungeon) {
		initSearchSpace(src, dest, dungeon);
		this.path = new ArrayList<Node>();
		this.depth = depth;
	}

	/*
	 * compare nodes by coordinates to destination
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Node o1, Node o2) {
		double first = heuristic(dest.getCoordinates(), o1.getCoordinates());
		double second = heuristic(dest.getCoordinates(), o2.getCoordinates());
		first = first * 100;
		second = second * 100;
		return (int) (first - second);
	}

	/*
	 * compute paths to destination via a bounded A* search
	 * 
	 * initialize paths when search is finished
	 * 
	 * return true if a solution is found, return false if no solution is found
	 * 
	 * @see unsw.dungeon.enemy.strategy.SearchStrategy#search()
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
			List<Node> remove = new ArrayList<Node>();
			for (Node n : q) {
				if (!curr.getCoordinates().adjacentTo(n.getCoordinates())) {
					remove.add(n);
				}
			}
			q.removeAll(remove);
			visited.add(curr);
			if (curr.getCoordinates().equals(dest.getCoordinates())) {
				path.addAll(visited);
				path.remove(src);
				path.add(dest);
				return true;
			} else {
				for (Node n : curr.getNeighbors()) {
					for (Node v : visited) {
						n.removeNeighbor(v);
					}
				}
				q.addAll(curr.getNeighbors());
				q.removeAll(visited);
				Collections.sort(q, this);
			}
			i++;
		}
		path.addAll(visited);
		path.remove(src);
		path.add(dest);
		return false;
	}

	/*
	 * get the next coordinate from the path
	 * 
	 * @see unsw.dungeon.enemy.strategy.SearchStrategy#getNext()
	 */
	@Override
	public Coordinates getNext() {
		if (this.path == null || this.path.size() == 0) {
			return null;
		}
		Node nextNode = this.path.get(0);
		Coordinates result = nextNode.getCoordinates();
		return result;
	}

	/**
	 * @param enemy
	 * @param player
	 * @param dungeon
	 * @return whether search space has been successfully initialized
	 */
	private boolean initSearchSpace(Coordinates enemy, Coordinates player, Dungeon dungeon) {
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
					if (toAdd.getCoordinates().equals(enemy)) {
						src = toAdd;
					}
					if (toAdd.getCoordinates().equals(player)) {
						dest = toAdd;
					}
				}
			}
		}
		return src != null && dest != null;
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
		return (double) (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY())
		/*
		 * + Math.pow(a.getX().doubleValue() - b.getX().doubleValue(), 2) +
		 * Math.pow(a.getY().doubleValue() - b.getY().doubleValue(), 2)
		 */);
	}

}
