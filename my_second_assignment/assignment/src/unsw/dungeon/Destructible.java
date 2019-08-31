package unsw.dungeon;

public class Destructible extends Entity {
	/**
	 * dungeon 
	 */
	private Dungeon dungeon;

	/**
	 * initialize with location and dungeon
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Destructible(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
	}

	/**
	 * get dungeon
	 * @return
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}
	/**
	 * override canCoexist in Entity
	 */
	@Override
	public boolean canCoexist() {
		return true;
	}

}
