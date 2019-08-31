package unsw.dungeon.items.sword;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.WeakMortalEnemy;
import unsw.dungeon.items.Item;

/**
 * {@link Sword} extends {@link Item}, {@link Sword} is a leaf item in
 * {@link Item}'s composite pattern
 */
public class Sword extends Item {

	/**
	 * initial number of hits a sword can have
	 */
	private static int INITIAL_HITS = 5;
	/**
	 * number of hits left in sword
	 */
	private int hits;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Sword(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
		this.hits = Sword.INITIAL_HITS;
	}

	/*
	 * use sword to kill adjacent weak mortal enemies
	 * 
	 * @see unsw.dungeon.items.Item#use()
	 */
	@Override
	public boolean use() {
		for (Entity e : getDungeon().getAdjacentEntities(getCoordinates())) {
			if (e instanceof WeakMortalEnemy && this.hits > 0) {
				this.hits--;
				getDungeon().removeEntity(e);
				return true;
			}
		}
		return false;
	}

	/*
	 * @see unsw.dungeon.items.Item#remove()
	 * 
	 * @return true sword is out of hits, false otherwise
	 */
	@Override
	public boolean remove() {
		return hits <= 0;
	}

}
