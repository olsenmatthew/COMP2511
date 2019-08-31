package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Exit;
import unsw.dungeon.NPC.Princess;

public class PrincessGoal implements Goal {
	private String goal;
	private Dungeon dungeon;
	private boolean complete;
	private BooleanProperty status;
	/**
	 * initialize the goal for princess, set states to be false
	 * @param dungeon
	 * @param goal
	 */
	public PrincessGoal(Dungeon dungeon, String goal) {
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
	 * get the name of the goal, which will be "princess"
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
	public BooleanProperty getProperty() {
		return this.status;
	}
	/**
	 * update current state of goal, if the princess is with player
	 * and player is near a exit,
	 * set the complete to true and status to be true
	 */
	@Override
	public void update() {
		boolean rescue = false;
		boolean escape = false;
		if(dungeon.getPlayerCoordinates()==null)return;
		for (Entity e : dungeon.getAdjacentEntities(dungeon.getPlayerCoordinates())) {
			if (e instanceof Princess) {
				rescue = true;
			}
			if(e instanceof Exit) {
				escape = false;
			}
		}
		for(Entity e: dungeon.getEntities(dungeon.getPlayerCoordinates())) {
			if (e instanceof Princess) {
				rescue = true;
			}
			if(e instanceof Exit) {
				escape = true;
			}
		}
		if (rescue && escape) {
			System.out.println("Princess goal completed");
			this.complete = true;
			this.status.set(true);
		} else {
			if (this.complete == true)
				System.out.println("Princess goal not completed");
			this.complete = false;
			this.status.set(false);
		}
	}
}
