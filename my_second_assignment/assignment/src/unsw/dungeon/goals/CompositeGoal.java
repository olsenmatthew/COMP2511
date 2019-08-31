package unsw.dungeon.goals;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;

public class CompositeGoal implements Goal {

	private List<Goal> goals;
	private Dungeon dungeon;
	private String logic;
	private BooleanProperty status;
	
	/**
	 * initialize the goal for goals, set state to be false,
	 * generate list of goals, is a composite pattern
	 * @param dungeon
	 * @param goal
	 */
	public CompositeGoal(Dungeon dungeon, String logic) {
		this.dungeon = dungeon;
		this.logic = logic;
		this.goals = new ArrayList<Goal>();
		this.status = new SimpleBooleanProperty(false);
	}
	/**
	 * add a child to goals
	 * @param child, any individual goal
	 * @return true, will successfully add
	 */
	@Override
	public boolean add(Goal child) {
		goals.add(child);
		return true;
	}

	/**
	 * remove a child to goals
	 * @param child, any individual goal
	 * @return false, can not remove this
	 */
	@Override
	public boolean remove(Goal child) {
		goals.remove(child);
		return false;
	}

	/**
	 * get the names of the goals, which will be some goal and/or some goal etc.
	 * @return the name of logic
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return logic;
	}

	/**
	 * get the goals
	 * @return list of goal
	 */
	public List<Goal> getGoals() {
		return goals;
	}

	/**
	 * basic logic to let complete to be true or false, deal with
	 * "and" "or" logic
	 * @return true if complete, false if not complete yet
	 */
	@Override
	public boolean isComplete() {
		if (logic == "AND") {
			for (Goal g : goals) {
				if (!g.isComplete())
					return false;
			}
			return true;
		} else {
			for (Goal g : goals) {
				if (g.isComplete())
					return true;
			}
			return false;
		}
	}

	/**
	 * get the status of current goals, eg.true if complete
	 * @return true if complete, false if not complete yet
	 */
	@Override
	public BooleanProperty getProperty() {
		// TODO Auto-generated method stub
		return this.status;
	}
	/**
	 * update current state of goals, if all the child goal is complete
	 * set status to be true
	 */
	@Override
	public void update() {
		for (Goal g : goals) {
			g.update();
		}
		if (isComplete()) {
			this.status.set(true);
		}else {
			this.status.set(false);
		}
	}
	/**
	 * get the dungeon variable
	 * @return dungeon currently playing
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}
	

}
