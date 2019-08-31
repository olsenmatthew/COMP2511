package unsw.dungeon.moveable.strategies;

import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;

public interface Movable {
	/**
	 * different strategy for move up
	 * @param dungeon
	 * @param movable
	 */
	public void moveUp(Dungeon dungeon, Destructible movable);
	/**
	 * different strategy for move down
	 * @param dungeon
	 * @param movable
	 */
	public void moveDown(Dungeon dungeon, Destructible movable);
	/**
	 * different strategy for move left
	 * @param dungeon
	 * @param movable
	 */
	public void moveLeft(Dungeon dungeon, Destructible movable);
	/**
	 * different strategy for move right
	 * @param dungeon
	 * @param movable
	 */
	public void moveRight(Dungeon dungeon, Destructible movable);
}
