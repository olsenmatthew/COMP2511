package unsw.dungeon;

public class Exit extends Entity {

	/**
	 * initialize with location
	 * @param x
	 * @param y
	 */
	public Exit(int x, int y) {
		super(x, y);
	}

	/**
	 * override hide() in Entity
	 */
	@Override
	public void hide() {
		// don't allow hiding
	}

	/**
	 * override canCoexist() in Entity
	 */
	@Override
	public boolean canCoexist() {
		return true;
	}

}
