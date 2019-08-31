package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.NPC.King;
import unsw.dungeon.NPC.Princess;
import unsw.dungeon.enemy.Bomber;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.enemy.Goblin;
import unsw.dungeon.goals.BoulderGoal;
import unsw.dungeon.goals.CompositeGoal;
import unsw.dungeon.goals.EnemyGoal;
import unsw.dungeon.goals.ExitGoal;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.goals.PrincessGoal;
import unsw.dungeon.goals.TreasureGoal;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.items.key.Key;
import unsw.dungeon.items.sword.Sword;
import unsw.dungeon.items.treasure.Treasure;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.moveable.strategies.LargeMoveStrategy;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.Player;
import unsw.dungeon.player.states.InvincibilityPotionState;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

	private JSONObject json;

	/**
	 * the constructor of dungeonLoader, set the json file it needs to read
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public DungeonLoader(String filename) throws FileNotFoundException {
		json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
	}

	/**
	 * Parses the JSON to create a dungeon, parse goals to further loading goals
	 * 
	 * @return initial empty dungeon with height and width
	 */
	public Dungeon load() {
		int width = json.getInt("width");
		int height = json.getInt("height");

		Dungeon dungeon = new Dungeon(width, height);

		JSONArray jsonEntities = json.getJSONArray("entities");

		for (int i = 0; i < jsonEntities.length(); i++) {
			loadEntity(dungeon, jsonEntities.getJSONObject(i));
		}

		JSONObject jsonGoals = json.getJSONObject("goal-condition");

		Goal goals = loadGoal(jsonGoals, dungeon);
		dungeon.setGoal(goals);

		dungeon.checkStatus();
		// decomposite(goals);
		return dungeon;
	}

	/*
	 * private void decomposite(Goal goals) { if (goals instanceof CompositeGoal) {
	 * System.out.println(goals.getName()); for (Goal g : ((CompositeGoal)
	 * goals).getGoals()) { decomposite(g); } } else {
	 * System.out.println(goals.getName()); } }
	 */

	/**
	 * load the goals in json file, generate like tree structure by composite patter
	 * of goals.
	 * 
	 * in that way, dungeon can deal with the goal anytime without load it over time
	 * 
	 * @param json
	 * @param dungeon
	 * @return
	 */
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
			case "princess":
				goals = new PrincessGoal(dungeon, goal);
				break;
			}
		}
		return goals;
	}

	/**
	 * load the entities, parse it in different onLoad function
	 * 
	 * @param dungeon
	 * @param json
	 */
	private void loadEntity(Dungeon dungeon, JSONObject json) {
		String type = json.getString("type");
		int x = json.getInt("x");
		int y = json.getInt("y");
		int delay = -1;
		if (json.has("delay")) {
			delay = json.getInt("delay");
		}
		int id;

		Entity entity = null;
		switch (type) {
		case "player":
			Player player = new Player(dungeon, x, y, new StrongMoveStrategy());
			dungeon.setPlayer(player);
			onLoad(player);
			entity = player;
			break;

		case "wall":
			Wall wall = new Wall(x, y);
			onLoad(wall);
			entity = wall;
			break;

		case "door":
			id = json.getInt("id");
			Door door = new Door(x, y, id);
			onLoad(door);
			entity = door;
			break;

		case "exit":
			Exit exit = new Exit(x, y);
			onLoad(exit);
			entity = exit;
			break;

		case "enemy":
			Enemy enemy = new Enemy(dungeon, x, y, new WeakMoveStrategy());
			onLoad(enemy);
			entity = enemy;
			break;

		case "bomber":
			Bomber bomber = new Bomber(dungeon, x, y, new WeakMoveStrategy());
			onLoad(bomber);
			entity = bomber;
			break;

		case "princess":
			Princess princess = new Princess(dungeon, x, y, new WeakMoveStrategy());
			onLoad(princess);
			entity = princess;
			break;

		case "boulder":
			Boulder boulder = new Boulder(dungeon, x, y, new LargeMoveStrategy());
			onLoad(boulder);
			entity = boulder;
			break;

		case "switch":
			Switch s = new Switch(x, y);
			onLoad(s);
			entity = s;
			break;

		case "treasure":
			Treasure treasure = new Treasure(dungeon, x, y, 1);
			onLoad(treasure);
			entity = treasure;
			break;

		case "sword":
			Sword sword = new Sword(dungeon, x, y);
			onLoad(sword);
			entity = sword;
			break;

		case "goblin":
			Goblin goblin = new Goblin(dungeon, x, y, new WeakMoveStrategy());
			onLoad(goblin);
			entity = goblin;
			break;

		case "key":
			id = json.getInt("id");
			Key key = new Key(dungeon, x, y, id);
			onLoad(key);
			entity = key;
			break;

		case "bomb":
			Bomb bomb = new Bomb(dungeon, x, y);
			onLoad(bomb);
			entity = bomb;
			break;

		case "invincibility":
			InvincibilityPotionState potion = new InvincibilityPotionState(dungeon, x, y);
			onLoad(potion);
			entity = potion;
			break;

		case "magma":
			Magma magma = new Magma(x, y);
			onLoad(magma);
			entity = magma;
			break;
		case "king":
			King king = new King(dungeon, x, y);
			onLoad(king);
			entity = king;
			break;

		}
		if (delay > 0) {
			dungeon.addSpawn(entity, delay);
		} else {
			dungeon.addEntity(entity);
		}
	}

	/**
	 * to load player entity
	 * 
	 * @param player
	 */
	public abstract void onLoad(Entity player);

	/**
	 * to load wall entity
	 * 
	 * @param wall
	 */
	public abstract void onLoad(Wall wall);

	/**
	 * to load door entity
	 * 
	 * @param door
	 */
	public abstract void onLoad(Door door);

	/**
	 * to load exit entity
	 * 
	 * @param exit
	 */
	public abstract void onLoad(Exit exit);

	/**
	 * to load enemy entity
	 * 
	 * @param enemy
	 */
	public abstract void onLoad(Enemy enemy);

	/**
	 * to load boulder entity
	 * 
	 * @param boulder
	 */
	public abstract void onLoad(Boulder boulder);

	/**
	 * to load switch entity
	 * 
	 * @param s
	 */
	public abstract void onLoad(Switch s);

	/**
	 * to load treasure entity
	 * 
	 * @param treasure
	 */
	public abstract void onLoad(Treasure treasure);

	/**
	 * to load sword entity
	 * 
	 * @param sword
	 */
	public abstract void onLoad(Sword sword);

	/**
	 * to load bomb entity
	 * 
	 * @param bomb
	 */
	public abstract void onLoad(Bomb bomb);

	/**
	 * to load princess entity
	 * 
	 * @param princess
	 */
	public abstract void onLoad(Princess princess);

	/**
	 * to load potion entity
	 * 
	 * @param potion
	 */
	public abstract void onLoad(InvincibilityPotionState potion);

	/**
	 * to load key entity
	 * 
	 * @param key
	 */
	public abstract void onLoad(Key key);

	/**
	 * to load bomber entity
	 * 
	 * @param bomber
	 */
	public abstract void onLoad(Bomber bomber);

	/**
	 * to load goblin entity
	 * 
	 * @param goblin
	 */
	public abstract void onLoad(Goblin goblin);

	/**
	 * to load magma entity
	 * 
	 * @param magma
	 */
	public abstract void onLoad(Magma magma);

	/**
	 * to load king entity
	 * 
	 * @param king
	 */
	public abstract void onLoad(King king);

}
