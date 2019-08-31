package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Exit;

public class ExitGoal implements Goal {

	private String goal;
	private Dungeon dungeon;
	private boolean complete;
	private BooleanProperty status;
	/**
	 * initialize the goal for exit, set states to be false
	 * @param dungeon
	 * @param goal
	 */
	public ExitGoal(Dungeon dungeon, String goal) {
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
		return false;
	}
	/**
	 * not allowed to add any child
	 */
	@Override
	public boolean remove(Goal child) {
		return false;
	}
	/**
	 * get the name of the goal, which will be "exit"
	 * @return the name of goal
	 */
	@Override
	public String getName() {
		return goal;
	}
	/**
	 * get the complete state of current goal
	 * @return true if complete, false if not complete yet
	 */
	@Override
	public boolean isComplete() {
		return this.complete;
	}
	/**
	 * get the status of current goal, eg.true if complete
	 * @return true if complete, false if not complete yet
	 */
	public BooleanProperty getProperty() {
		return this.status;
	}
	/**
	 * update current state of goal, if player is on the exit
	 * set the complete to true and status to be true
	 */
	@Override
	public void update() {
		boolean onExit = false;
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Exit) {
				if (e.getCoordinates().equals(dungeon.getPlayerCoordinates()))
					onExit = true;
			}
		}
		if (onExit) {
			if (!complete)
				System.out.println("exit goal completed");
			this.complete = true;
			this.status.set(true);
		} else {
			if (complete)
				System.out.println("exit goal not completed");
			this.complete = false;
			this.status.set(false);
		}
	}
}
