package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Switch;

public class BoulderGoal implements Goal {

	private String goal;
	private Dungeon dungeon;
	private boolean complete;
	private BooleanProperty status;

	/**
	 * initialize the goal for boulder, set states to be false
	 * @param dungeon
	 * @param goal
	 */
	public BoulderGoal(Dungeon dungeon, String goal) {
		this.dungeon = dungeon;
		this.goal = goal;
		this.complete = false;
		this.status = new SimpleBooleanProperty(false);
	}

	/**
	 * not allowed to add any child
	 */
	@Override
	public boolean add(Goal child) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not allowed to add any child
	 */
	@Override
	public boolean remove(Goal child) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * get the name of the goal, which will be "boulder"
	 * @return the name of goal
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return goal;
	}
	
	/**
	 * get the complete state of current goal
	 * @return true if complete, false if not complete yet
	 */
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return this.complete;
	}
	
	/**
	 * get the status of current goal, eg.true if complete
	 * @return true if complete, false if not complete yet
	 */
	@Override
	public BooleanProperty getProperty() {
		// TODO Auto-generated method stub
		return this.status;
	}

	/**
	 * update current state of goal, if all the boulder is on the trigger
	 * set the complete to true and status to be true
	 */
	@Override
	public void update() {

		boolean allTriggered = true;
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Switch && !((Switch) e).getTriggeredStatus()) {
				allTriggered = false;
			}
		}
		if (allTriggered) {
			if (!complete)
				System.out.println("boulder goal completed");
			this.complete = true;
			this.status.set(true);
		} else {
			if (complete)
				System.out.println("boulder goal not completed");
			this.complete = false;
			this.status.set(false);
		}

	}

}
