package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * An entity in the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

	// IntegerProperty is used so that changes to the entities position can be
	// externally observed.
	private Coordinates coordinates;
	private BooleanProperty visible;

	/**
	 * Create an entity positioned in square (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public Entity(int x, int y) {
		this.coordinates = new Coordinates(x, y);
		this.visible = new SimpleBooleanProperty(true);
	}

	/**
	 * get Coordinates
	 * @return
	 */
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	/**
	 * property of x position
	 * @return
	 */
	public IntegerProperty x() {
		return this.coordinates.x();
	}
	
	/**
	 * property of y position
	 * @return
	 */
	public IntegerProperty y() {
		return this.coordinates.y();
	}

	/**
	 * get y pos
	 * @return
	 */
	public int getY() {
		return y().get();
	}
	/**
	 * get x pos
	 * @return
	 */
	public int getX() {
		return x().get();
	}
	/**
	 * set y pos
	 * @param y
	 */
	public void setY(int y) {
		y().set(y);
	}
	/**
	 * set x pos
	 * @param x
	 */
	public void setX(int x) {
		x().set(x);
	}
	/**
	 * decide whether an entity can be coexist
	 * @return
	 */
	public boolean canCoexist() {
		return false;
	}
	/**
	 * hide the image of entity
	 */
	public void hide() {
		this.visible.set(false);
	}
	/**
	 * show the image of entity
	 */
	public void show() {
		this.visible.set(true);
	}
	/**
	 * check whether the entity is visible
	 * @return
	 */
	public BooleanProperty isVisible() {
		return this.visible;
	}

}
