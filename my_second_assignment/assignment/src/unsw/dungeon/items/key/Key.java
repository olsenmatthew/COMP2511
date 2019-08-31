package unsw.dungeon.items.key;

import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.items.Item;

/**
 * {@link Key} extends {@link Item}, {@link Key} is a leaf item in
 * {@link Item}'s composite pattern
 */
public class Key extends Item {
	/**
	 * id of key
	 */
	private int id;
	/**
	 * true is key is used to unlock a door, false otherwise
	 */
	private boolean used;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param id
	 */
	public Key(Dungeon dungeon, int x, int y, int id) {
		super(dungeon, x, y);
		this.id = id;
		used = false;
	}

	/**
	 * @return id of key, String
	 */
	public int getID() {
		return this.id;
	}

	/*
	 * @see unsw.dungeon.items.Item#use()
	 * 
	 * @return true if key is used to unlock a door, false otherwise
	 */
	@Override
	public boolean use() {
		for (Entity e : getDungeon().getAdjacentEntities(this.getCoordinates(), Door.class)) {
			if (((Door) e).getID() == this.getID()) {
				((Door) e).open();
				this.hide();
				used = true;
				return true;
			}
		}
		return false;
	}

	/*
	 * @see unsw.dungeon.items.Item#remove()
	 * 
	 * return true if key is used up, false otherwise
	 */
	@Override
	public boolean remove() {
		return used;
	}

}
