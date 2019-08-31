package unsw.dungeon.enemy.strategy;

import java.util.Comparator;

import unsw.dungeon.Coordinates;

/**
 * @author Matthew Olsen
 * 
 */
public interface SearchStrategy extends Comparator<Node> {
	/**
	 * @return true if search succeeds, false otherwise
	 */
	public boolean search();

	/**
	 * @return next coordinate from source
	 */
	public Coordinates getNext();

	/**
	 * @param a
	 * @param b
	 * @return heruistic cost between two coordinates
	 */
	public double heuristic(Coordinates a, Coordinates b);

}
