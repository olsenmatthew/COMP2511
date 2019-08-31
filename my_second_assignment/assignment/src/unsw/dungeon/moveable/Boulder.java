package unsw.dungeon.moveable;

import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.moveable.strategies.Movable;

public class Boulder extends Destructible {

	/**
	 * strategy
	 */
	private Movable strategy;
	
	/**
	 * initialize the Boulder with position and strategy
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param strategy
	 */
	public Boulder(Dungeon dungeon, int x, int y, Movable strategy) {
		super(dungeon, x, y);
		this.strategy = strategy;
	}
	/**
	 * move up with strategy
	 */
	public void moveUp() {
		strategy.moveUp(getDungeon(), this);
	}
	/**
	 * move down with strategy
	 */
	public void moveDown() {
		strategy.moveDown(getDungeon(), this);
	}
	/**
	 * move left with strategy
	 */
	public void moveLeft() {
		strategy.moveLeft(getDungeon(), this);
	}
	/**
	 * move right with strategy
	 */
	public void moveRight() {
		strategy.moveRight(getDungeon(), this);
	}
	
	/**
	 * override canCoexist() in entity
	 */
	@Override
	public boolean canCoexist() {
		return false;
	}

}
