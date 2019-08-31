package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;

public interface Goal {
	/**
	 * get the complete state of current goal
	 * @return true if complete, false if not complete yet
	 */
	public boolean isComplete();

	/**
	 * allows the composite goal to add children
	 * @param child, individual goal
	 * @return true if can remove, false if can not remove
	 */
	public boolean add(Goal child);

	/**
	 * allows the composite goal to remove children
	 * @param child, individual goal
	 * @return true if can remove, false if can not remove
	 */
	public boolean remove(Goal child);
	/**
	 * get the name of the goal
	 * @return the name of goal
	 */
	public String getName();

	/**
	 * let the child class update their status/complete state
	 */
	public void update();
	/**
	 * get the status of current goal
	 * @return true or false
	 */
	public BooleanProperty getProperty();

}
