/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import unsw.dungeon.NPC.Princess;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.player.Player;

/**
 * 
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 * 
 * @author Matthew Olsen
 *
 */
public class Dungeon {

	/**
	 * width is the width of the dungeon
	 */
	/**
	 * height is the height of the dungeon
	 */
	private int width, height;
	/**
	 * entities is a list of all of the entities in the dungeon
	 */
	private List<Entity> entities;
	/**
	 * player is the player in the dungeon
	 */
	private Player player;
	/**
	 * goals is the dungeons goals
	 */
	private Goal goals;
	/**
	 * state is the state of the dungeon in string format
	 */
	private StringProperty state;
	/**
	 * spawn is a list of all entities we will add to the dungeon later
	 */
	private Map<Integer, Entity> spawn;
	/**
	 * timer is a timer for the dungeon
	 */
	private Timer timer;
	/**
	 * seconds since start is the number of seconds since the game began
	 */
	private int secondsSinceStart;

	/**
	 * @param width
	 * @param height
	 */
	public Dungeon(int width, int height) {
		this.width = width;
		this.height = height;
		this.entities = new ArrayList<>();
		this.player = null;
		this.goals = null;
		this.state = new SimpleStringProperty("playing");
		this.spawn = new HashMap<Integer, Entity>();
		this.timer = new Timer();
		this.secondsSinceStart = 0;
		this.startTimer();
	}

	/**
	 * start dungeon's timer at scheduled rate of 1 second
	 */
	private void startTimer() {
		int period = 1000;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				spawnEntities();
			}
		}, 0, period);
	}

	/**
	 * spawn enitities if the dungeon's secondsSinceStart is greater than or equal
	 * to the entities spawn time
	 */
	protected void spawnEntities() {
		this.secondsSinceStart++;
		for (Integer i : this.spawn.keySet()) {
			if (i.intValue() <= this.secondsSinceStart) {
				Entity e = spawn.get(i);
				if ((e.canCoexist() && canCoexist(e.getX(), e.getY())) || getEntities(e.getCoordinates()).isEmpty()) {
					addEntity(e);
					spawn.remove(i);
				}
			}
		}
	}

	/**
	 * @return dungeon's width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return dungeons height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return dungeon's player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param e, entity to be spawned
	 * @param seconds, seconds after start when entity will be spawned
	 */
	public void addSpawn(Entity e, int seconds) {
		if (e != null) {
			e.hide();
			this.spawn.put(seconds, e);
		}
	}

	/**
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
		addEntity(player);
	}

	/**
	 * add entity and show entity
	 * 
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		if (entity != null) {
			entity.show();
			getEntities().add(entity);
		}
	}

	/**
	 * remove entity, kill if mortal, hide when removed
	 * 
	 * @param e
	 */
	public void removeEntity(Entity e) {
		if (e != null) {
			e.hide();
			if (e instanceof WeakMortal) {
				((WeakMortal) e).kill();
			}
			entities.remove(e);
		}

		if (getEntities(Player.class).isEmpty()) {
			endGame();
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return list of {@link Entity} sharing same given x,y positions
	 */
	public List<Entity> getEntities(int x, int y) {
		Coordinates coordinates = new Coordinates(x, y);
		return getEntities(coordinates);
	}

	/**
	 * @param coordinates
	 * @return list of {@link Entity} sharing same given {@link Coordinates}
	 */
	public List<Entity> getEntities(Coordinates coordinates) {
		List<Entity> current = new ArrayList<Entity>();
		try {
			for (Entity e : getEntities()) {
				if (e != null && e.getCoordinates().equals(coordinates)) {
					current.add(e);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println(e);
		} catch (NoSuchElementException e) {
			System.out.println(e);
		}
		return current;
	}

	/**
	 * @param coordinates
	 * @param cls
	 * @return list of {@link Entity} sharing same given {@link Coordinates} & Class
	 *         Generic
	 */
	public List<Entity> getEntities(Coordinates coordinates, Class<?> cls) {
		List<Entity> result = new ArrayList<Entity>();
		try {
			for (Entity e : getEntities(coordinates)) {
				if (e != null && cls.isInstance(e)) {
					result.add(e);
				}
			}
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param coordinates
	 * @return list of {@link Entity} sharing adjacent {@link Coordinates}
	 */
	public List<Entity> getAdjacentEntities(Coordinates coordinates) {
		if (coordinates == null)
			return null;
		List<Entity> current = new ArrayList<Entity>();
		for (Entity e : getEntities()) {
			if (e != null && e.getCoordinates().adjacentTo(coordinates)) {
				current.add(e);
			}
		}
		return current;
	}

	/**
	 * @param coordinates
	 * @param cls
	 * @return list of {@link Entity} sharing adjacent {@link Coordinates} & same
	 *         Class Generic
	 */
	public List<Entity> getAdjacentEntities(Coordinates coordinates, Class<?> cls) {
		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : getAdjacentEntities(coordinates)) {
			if (e != null && cls.isInstance(e)) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * @param cls
	 * @return list of {@link Entity} sharing same Class Generic
	 */
	public List<Entity> getEntities(Class<?> cls) {
		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : getEntities()) {
			if (e != null && cls.isInstance(e)) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * @return complete list of {@link Entity}s
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * @param x
	 * @param y
	 * @return true if there are no entities that you can't coexist with for the
	 *         given x & y, false otherwise
	 */
	public boolean canCoexist(int x, int y) {
		List<Entity> current = getEntities(x, y);
		boolean movable = true;
		for (Entity e : current) {
			if (e != null && !e.canCoexist()) {
				movable = false;
			}
		}
		return movable;
	}

	/**
	 * @return player {@link Coordinates}
	 */
	public Coordinates getPlayerCoordinates() {
		if (player != null) {
			return this.player.getCoordinates();
		}
		return null;
	}

	/**
	 * @param coordinates
	 * @param cls
	 */
	public void removeCoordinatesAndAdjacentEntities(Coordinates coordinates, List<Class<?>> cls) {
		for (Class<?> c : cls) {
			List<Entity> remove = new ArrayList<Entity>();
			for (Entity e : getAdjacentEntities(coordinates, c)) {
				remove.add(e);
			}
			for (Entity e : getEntities(coordinates, c)) {
				remove.add(e);
			}
			for (Entity e : remove) {
				removeEntity(e);
			}
		}
		trackGoal();
	}

	/**
	 * track dungeons goals and update dungeon state
	 */
	public void trackGoal() {
		if (goals != null) {
			goals.update();
			if (goals.isComplete()) {
				for (Entity e : getEntities(WeakMortal.class)) {
					((WeakMortal) e).kill();
				}
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						state.set("You win!");
					}

				});
			}
		}
	}

	/**
	 * set goals to given goal
	 * 
	 * @param goal
	 */
	public void setGoal(Goal goal) {
		this.goals = goal;
	}

	/**
	 * @return this dungeons {@link Goal}
	 */
	public Goal getGoal() {
		return this.goals;
	}

	/**
	 * check if an enemy is incoming to a player or princess, kill player/princess
	 * if true
	 * 
	 * endGame if player is killed
	 */
	private void enemyIncoming() {
		List<Entity> enemies = getEntities(getPlayerCoordinates(), Enemy.class);
		if (!enemies.isEmpty()) {
			endGame();
		}
		for (Entity princess : getEntities(Princess.class)) {
			for (Entity enemy : getEntities(princess.getCoordinates())) {
				if (enemy instanceof Enemy) {
					this.entities.remove(princess);
				}
			}
		}
	}

	/**
	 * end game
	 * 
	 * clean up by killing all {@link WeakMortal} & set all entities position to 0,0
	 * 
	 * remove player and update state of game
	 */
	public void endGame() {
		for (Entity e : getEntities(WeakMortal.class)) {
			((WeakMortal) e).kill();
		}
		for (Entity e : getEntities()) {
			if (!(e instanceof Player)) {
				e.setX(0);
				e.setY(0);
			}
		}

		entities.remove(player);
		this.player = null;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				state.set("You dead!");
			}

		});
	}

	/**
	 * check status of switches(being triggered) and boulders in game
	 */
	public void checkStatus() {
		for (Entity e : getEntities()) {
			if (e instanceof Switch) {
				if (!getEntities(new Coordinates(e.getX(), e.getY()), Boulder.class).isEmpty()) {
					((Switch) e).setTriggeredStatus(true);
				} else {
					((Switch) e).setTriggeredStatus(false);
				}
			}
		}
	}

	/**
	 * checked every time there is movement in the dungeon
	 * 
	 * checks enemies incoming, checks status, tracks goals
	 */
	public void updateMovement() {
		enemyIncoming();
		checkStatus();
		trackGoal();
	}

	/**
	 * @return true if player if powered up, false otherwise
	 */
	public boolean isPlayerPoweredUp() {
		if (player != null) {
			return player.isPoweredUp();
		}
		return false;
	}

	/*
	 * string representation of dungeon
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dungeon [width=" + width + ", height=" + height + ", entities=" + entities + ", player=" + player + "]";
	}

	/**
	 * @return state of the dungeon
	 */
	public StringProperty getState() {
		return state;
	}

	/**
	 * change state to beg, when the king needs to beg the player to help save his
	 * princess
	 */
	public void aKingBegging() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				state.set("beg");
			}
		});
	}

}
