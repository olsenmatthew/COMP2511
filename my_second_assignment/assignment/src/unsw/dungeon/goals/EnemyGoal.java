package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.WeakMortalEnemy;

public class EnemyGoal implements Goal {

	private String goal;
	private Dungeon dungeon;
	private boolean complete;
	private BooleanProperty status;

	/**
	 * initialize the goal for enemy, set states to be false
	 * 
	 * @param dungeon
	 * @param goal
	 */
	public EnemyGoal(Dungeon dungeon, String goal) {
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
	 * get the name of the goal, which will be "enemy"
	 * 
	 * @return the name of goal
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return goal;
	}

	/**
	 * get the complete state of current goal
	 * 
	 * @return true if complete, false if not complete yet
	 */
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return this.complete;
	}

	/**
	 * get the status of current goal, eg.true if complete
	 * 
	 * @return true if complete, false if not complete yet
	 */
	public BooleanProperty getProperty() {
		return this.status;
	}

	/**
	 * update current state of goal, if all the enemy is killed set the complete to
	 * true and status to be true
	 */
	@Override
	public void update() {
		boolean hasEnemy = false;
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof WeakMortalEnemy) {
				hasEnemy = true;
			}
		}
		if (hasEnemy) {
			this.complete = false;
			this.status.set(false);
		} else {
			if (!complete)
				System.out.println("enemy goal completed");
			this.complete = true;
			this.status.set(true);
		}
	}
}
