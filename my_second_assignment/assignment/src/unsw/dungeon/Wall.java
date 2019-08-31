package unsw.dungeon;

public class Wall extends Entity {

	/**
	 * initialize with location
	 * @param x
	 * @param y
	 */
	public Wall(int x, int y) {
		super(x, y);
	}

	/**
	 * override canCoexist() in Entity
	 */
	@Override
	public boolean canCoexist() {
		return false;
	}

}
