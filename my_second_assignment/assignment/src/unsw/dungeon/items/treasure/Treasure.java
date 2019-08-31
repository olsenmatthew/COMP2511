package unsw.dungeon.items.treasure;

import unsw.dungeon.Dungeon;
import unsw.dungeon.items.Item;

/**
 * {@link Treasure} extends {@link Item}, {@link Treasure} is a leaf item in
 * {@link Item}'s composite pattern
 */
public class Treasure extends Item {

	/**
	 * value of treasure
	 */
	private int value;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param value
	 */
	public Treasure(Dungeon dungeon, int x, int y, int value) {
		super(dungeon, x, y);
		this.value = value;
	}

	/*
	 * @return value of treasure
	 * 
	 * @see unsw.dungeon.items.Item#getValue()
	 */
	@Override
	public int getValue() {
		return this.value;
	}

	/*
	 * @return true
	 * 
	 * @see unsw.dungeon.items.Item#use()
	 */
	@Override
	public boolean use() {
		System.out.println("Treasure Value: " + this.value);
		return true;
	}

	/*
	 * @return true if value of treasure is invalid, false otherwise
	 * 
	 * @see unsw.dungeon.items.Item#remove()
	 */
	@Override
	public boolean remove() {
		return getValue() <= 0;
	}

}
