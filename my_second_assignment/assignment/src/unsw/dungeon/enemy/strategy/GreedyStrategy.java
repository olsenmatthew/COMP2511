package unsw.dungeon.enemy.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;

/*
 * {@link GreedyStrategy} implements {@link SearchStrategy}
 * This search strategy find the closest option based on path length
 * This search initialized the paths to the closest option using an iterative deepening search
 */
public class GreedyStrategy implements SearchStrategy {

	/**
	 * path is a list of nodes from the src to dest
	 */
	private List<Node> path;
	/**
	 * this is the bound on the depth of the search & path
	 */
	private int depth;
	/**
	 * start Coordinates
	 */
	private Coordinates start;
	/**
	 * source Node
	 */
	private Node src;
	/**
	 * destination Node
	 */
	private Node dest;
	/**
	 * options is a list of coordinates that we are going to choose dest from
	 */
	private List<Coordinates> options;
	/**
	 * this is the dungeon we are searching for paths in
	 */
	private Dungeon dungeon;

	/**
	 * @param start
	 * @param options
	 * @param depth
	 * @param dungeon
	 */
	public GreedyStrategy(Coordinates start, List<Coordinates> options, int depth, Dungeon dungeon) {
		this.options = options;
		this.start = start;
		this.path = new ArrayList<Node>();
		this.depth = depth;
		this.dungeon = dungeon;
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
	 * search for the shortest path to any option init paths to min cost to nearest
	 * option
	 * 
	 * return true if a soltion is found, false otherwise
	 * 
	 * @see unsw.dungeon.enemy.strategy.SearchStrategy#search()
	 */
	@Override
	public boolean search() {
		int minDist = Integer.MAX_VALUE;
		Coordinates best = null;
		for (Coordinates c : options) {
			initSearchSpace(start, c, dungeon);
			int cost = findSolution();
			if (cost < minDist) {
				best = c;
				minDist = cost;
			}
		}

		initSearchSpace(start, best, dungeon);
		findSolution();

		return minDist < Integer.MAX_VALUE;
	}

	/**
	 * complete iterative deepening search for nearest src and dest
	 * 
	 * @return int, cost of path
	 */
	private int findSolution() {
		if (src == null || dest == null) {
			return Integer.MAX_VALUE;
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
				return path.size();
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
		return Integer.MAX_VALUE;
	}

	/*
	 * get the next coordinate in the path to the destination starting from the
	 * source
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
	 * 
	 * initialize search space of dungeon by nodes that can be traversed initialize
	 * src and dest nodes
	 * 
	 * @param source
	 * @param destination
	 * @param dungeon
	 * @return whether search space has been successfully initialized
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