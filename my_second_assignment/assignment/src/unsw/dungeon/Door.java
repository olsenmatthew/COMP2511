package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {
	/**
	 * id of door
	 */
	private int id;
	/**
	 * status of door
	 */
	private BooleanProperty open;

	/**
	 * initialize witch location, id and status
	 * @param x
	 * @param y
	 * @param id
	 */
	public Door(int x, int y, int id) {
		super(x, y);
		this.id = id;
		this.open = new SimpleBooleanProperty(false);
	}
	/** 
	 * override canCoexist() in Entity
	 */
	public boolean canCoexist() {
		return isOpen().get();
	}
	/**
	 * set the status to open
	 */
	public void open() {
		this.open.set(true);
	}

	/**
	 * get id
	 * @return
	 */
	public int getID() {
		return this.id;
	}
	/**
	 * get property of status
	 * @return
	 */
	public BooleanProperty isOpen() {
		return this.open;
	}
}
