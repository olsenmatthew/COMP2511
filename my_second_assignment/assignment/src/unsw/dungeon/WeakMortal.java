package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public interface WeakMortal {
	/**
	 * kill the princess or king
	 * @postCondition alive=false
	 */
	public void kill();

	/**
	 * return the living state of princess
	 * @return BooleanProperty, the state of variable alive
	 */
	public BooleanProperty getAlive();
}
