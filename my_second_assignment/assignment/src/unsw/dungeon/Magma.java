package unsw.dungeon;

public class Magma extends Entity {

	/**
	 * initialize with location
	 * @param x
	 * @param y
	 */
	public Magma(int x, int y) {
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
