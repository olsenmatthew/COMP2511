import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.goals.BoulderGoal;
import unsw.dungeon.goals.CompositeGoal;
import unsw.dungeon.goals.EnemyGoal;
import unsw.dungeon.goals.ExitGoal;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.goals.TreasureGoal;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.player.Player;

public class GoalTest {

	private JSONObject json;
	private String s = "";

	@Test
	public void testLoadGoal() throws JSONException, FileNotFoundException {
		json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + "advanced.json")));
		int width = json.getInt("width");
		int height = json.getInt("height");

		Dungeon dungeon = new Dungeon(width, height);
		JSONObject jsonGoals = json.getJSONObject("goal-condition");
		Goal goals = loadGoal(jsonGoals, dungeon);
		assertEquals(decomposite(goals), "ANDenemiestreasure");

		s = "";
		json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + "boulders.json")));
		width = json.getInt("width");
		height = json.getInt("height");

		dungeon = new Dungeon(width, height);
		jsonGoals = json.getJSONObject("goal-condition");
		goals = loadGoal(jsonGoals, dungeon);
		assertEquals(decomposite(goals), "boulders");

		s = "";
		json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + "maze.json")));
		width = json.getInt("width");
		height = json.getInt("height");

		dungeon = new Dungeon(width, height);
		jsonGoals = json.getJSONObject("goal-condition");
		goals = loadGoal(jsonGoals, dungeon);
		assertEquals(decomposite(goals), "exit");

	}

	@Test
	public void testGoalComplete() throws JSONException, FileNotFoundException {
		json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + "advanced.json")));
		int width = json.getInt("width");
		int height = json.getInt("height");

		Dungeon dungeon = new Dungeon(width, height);
		JSONObject jsonGoals = json.getJSONObject("goal-condition");
		Goal goals = loadGoal(jsonGoals, dungeon);
		dungeon.setGoal(goals);
		dungeon.trackGoal();
		assertEquals(goals.isComplete(), true);

		Exit e = new Exit(1, 1);
		dungeon.addEntity(e);
		Player p = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(p);
		dungeon.trackGoal();
		assertEquals(goals.isComplete(), true);
	}

	private String decomposite(Goal goals) {
		if (goals instanceof CompositeGoal) {
			System.out.println(goals.getName());
			s += goals.getName();
			for (Goal g : ((CompositeGoal) goals).getGoals()) {
				decomposite(g);
			}
		} else {
			System.out.println(goals.getName());
			s += goals.getName();
		}
		return s;
	}

	private Goal loadGoal(JSONObject json, Dungeon dungeon) {
		Goal goals = null;
		if (json.getString("goal").equals("AND")) {
			goals = new CompositeGoal(dungeon, "AND");
			JSONArray subgoals = json.getJSONArray("subgoals");
			for (int i = 0; i < subgoals.length(); i++) {
				JSONObject subgoal = subgoals.getJSONObject(i);
				goals.add(loadGoal(subgoal, dungeon));
			}
		} else if (json.getString("goal").equals("OR")) {
			goals = new CompositeGoal(dungeon, "OR");
			JSONArray subgoals = json.getJSONArray("subgoals");
			for (int i = 0; i < subgoals.length(); i++) {
				JSONObject subgoal = subgoals.getJSONObject(i);
				goals.add(loadGoal(subgoal, dungeon));
			}
		} else {
			String goal = json.getString("goal");
			switch (goal) {
			case "exit":
				goals = new ExitGoal(dungeon, goal);
				break;
			case "enemies":
				goals = new EnemyGoal(dungeon, goal);
				break;
			case "boulders":
				goals = new BoulderGoal(dungeon, goal);
				break;
			case "treasure":
				goals = new TreasureGoal(dungeon, goal);
				break;
			}
		}
		return goals;
	}
}
