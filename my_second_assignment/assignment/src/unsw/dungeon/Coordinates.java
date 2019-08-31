package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Coordinates {
	/**
	 * x property
	 */
	private IntegerProperty x;
	/**
	 * y property
	 */
	private IntegerProperty y;
	/**
	 * initialize coordinate with property
	 * @param x
	 * @param y
	 */
	public Coordinates(int x, int y) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
	}
	/**
	 * get x
	 * @return
	 */
	public int getX() {
		return x.get();
	}
	/**
	 * set x
	 * @param x
	 */
	public void setX(int x) {
		this.x = new SimpleIntegerProperty(x);
	}
	/**
	 * get y
	 * @return
	 */
	public int getY() {
		return y.get();
	}
	/**
	 * set y
	 * @param y
	 */
	public void setY(int y) {
		this.y = new SimpleIntegerProperty(y);
	}
	
	/**
	 * x property
	 * @return
	 */
	public IntegerProperty x() {
		return x;
	}
	
	/**
	 * y property
	 * @return
	 */
	public IntegerProperty y() {
		return y;
	}
	/**
	 * decide whether 2 coordinates are adjacent
	 * @param coordinates
	 * @return
	 */
	public boolean adjacentTo(Coordinates coordinates) {
		return (Math.abs(this.x().doubleValue() - coordinates.x().doubleValue()) == 1
				|| Math.abs(this.y().doubleValue() - coordinates.y().doubleValue()) == 1)
				&& (this.x().doubleValue() - coordinates.x().doubleValue() == 0
						|| this.y().doubleValue() - coordinates.y().doubleValue() == 0);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}
		Coordinates c = (Coordinates) obj;
		if (c.x().get() == this.x().get() && c.y().get() == this.y().get()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + "]";
	}

}
