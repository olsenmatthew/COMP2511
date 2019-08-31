package unsw.dungeon.items;

import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;

/**
 * {@link Item} extends {@link Destructible}
 */
public abstract class Item extends Destructible {
	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Item(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
	}

	/**
	 * @return true if item is used, false otherwise
	 */
	public abstract boolean use();

	/**
	 * @return true if item has no use, false otherwise
	 */
	public boolean remove() {
		return false;
	}

	/**
	 * @return value of item
	 */
	public int getValue() {
		return 0;
	}
}
