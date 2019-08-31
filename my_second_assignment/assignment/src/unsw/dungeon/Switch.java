package unsw.dungeon;

public class Switch extends Entity {

	/**
	 * status of switch
	 */
	private boolean triggered;

	/**
	 * initialize with location and status
	 * @param x
	 * @param y
	 */
	public Switch(int x, int y) {
		super(x, y);
		triggered = false;
	}

	/**
	 * override canCoexist() in Entity
	 */
	@Override
	public boolean canCoexist() {
		return true;
	}

	/**
	 * get switch status
	 * @return
	 */
	public boolean getTriggeredStatus() {
		return triggered;
	}

	/**
	 * set switch status
	 * @param status
	 */
	public void setTriggeredStatus(boolean status) {
		triggered = status;
	}

}
