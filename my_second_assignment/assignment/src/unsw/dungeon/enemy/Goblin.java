package unsw.dungeon.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Coordinates;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.strategy.GreedyStrategy;
import unsw.dungeon.enemy.strategy.RunAwayStrategy;
import unsw.dungeon.items.bag.Bag;
import unsw.dungeon.items.treasure.Treasure;
import unsw.dungeon.moveable.strategies.Movable;

/**
 * {@link Goblin} extends {@link Destructible} implements {@link Runnable},
 * {@link WeakMortalEnemy}
 */
public class Goblin extends Destructible implements Runnable, WeakMortalEnemy {
	/**
	 * depth of search
	 */
	private static int RANGE = 25;
	/**
	 * delay in milliseconds between moves
	 */
	public static int MOVEMENT_DELAY = 200;
	/**
	 * goblin's movement strategy
	 */
	private Movable movementStrategy;
	/**
	 * true if goblin is alive, false otherwise
	 */
	private BooleanProperty alive;
	/**
	 * goblin's bag
	 */
	private Bag bag;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param strategy
	 */
	public Goblin(Dungeon dungeon, int x, int y, Movable strategy) {
		super(dungeon, x, y);
		this.movementStrategy = strategy;
		this.alive = new SimpleBooleanProperty(true);
		this.bag = new Bag(dungeon, x, y);
	}

	/**
	 * pickup treasures from dungeon of type treasure, then add treasures to bag
	 */
	private void pickup() {
		List<Entity> treasures = getDungeon().getEntities(getCoordinates(), Treasure.class);
		for (Entity e : treasures) {
			bag.addItem((Treasure) e);
		}
	}

	/**
	 * remove all treasures from bag and add to dungeon
	 */
	private void emptyBag() {
		Entity treasure = (Entity) this.bag.removeItem();
		while (treasure != null) {
			treasure.setX(getX());
			treasure.setY(getY());
			getDungeon().addEntity(treasure);
			treasure = (Entity) this.bag.removeItem();
		}
	}

	/**
	 * use movement strategy to move up
	 */
	public void moveUp() {
		movementStrategy.moveUp(getDungeon(), this);
	}

	/**
	 * use movement strategy to move down
	 */
	public void moveDown() {
		movementStrategy.moveDown(getDungeon(), this);
	}

	/**
	 * use movement strategy to move left
	 */
	public void moveLeft() {
		movementStrategy.moveLeft(getDungeon(), this);
	}

	/**
	 * use movement strategy to move right
	 */
	public void moveRight() {
		movementStrategy.moveRight(getDungeon(), this);
	}

	/**
	 * 
	 * use search strategies to find paths to nearest treasures or furthest path
	 * from player
	 * 
	 * @return coordinates of next move, or null if no move is to be made
	 */
	private Coordinates getNextMove() {
		Coordinates result = null;
		Coordinates targetCoordinates = getDungeon().getPlayerCoordinates();

		List<Entity> treasures = getDungeon().getEntities(Treasure.class);

		if (targetCoordinates != null) {
			Random r = new Random();
			if (getDungeon().isPlayerPoweredUp() || treasures.isEmpty() || r.nextInt() % 5 == 0) {
				RunAwayStrategy runStrategy = new RunAwayStrategy(getCoordinates(), targetCoordinates, Goblin.RANGE,
						getDungeon());
				if (runStrategy != null && runStrategy.search()) {
					result = runStrategy.getNext();
				}
			} else {

				List<Coordinates> options = new ArrayList<Coordinates>();
				for (Entity e : treasures) {
					options.add(e.getCoordinates());
					if (getCoordinates().equals(e.getCoordinates())) {
						pickup();
					}
				}
				GreedyStrategy greedyStrategy = new GreedyStrategy(getCoordinates(), options, Goblin.RANGE,
						getDungeon());
				if (greedyStrategy.search()) {
					result = greedyStrategy.getNext();
				}
			}
		}

		return result;
	}

	/**
	 * move (left, right, up, or down) to the next coordinate from current
	 * coordinates
	 */
	private void makeNextMove() {
		Coordinates next = getNextMove();
		if (next != null) {
			if (next.getX() > getCoordinates().getX()) {
				moveRight();
			} else if (next.getX() < getCoordinates().getX()) {
				moveLeft();
			} else if (next.getY() > getCoordinates().getY()) {
				moveDown();
			} else if (next.getY() < getCoordinates().getY()) {
				moveUp();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (getAlive().get()) {
			try {
				Thread.sleep(MOVEMENT_DELAY);
				makeNextMove();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}

	/*
	 * @see unsw.dungeon.WeakMortal#kill()
	 * 
	 * set alive to false and empty bag
	 */
	@Override
	public void kill() {
		this.alive.set(false);
		emptyBag();
	}

	/*
	 * @see unsw.dungeon.WeakMortal#getAlive()
	 * 
	 * @return true if alive, false otherwise
	 */
	@Override
	public BooleanProperty getAlive() {
		return alive;
	}

	/**
	 * @return true if bag contains treasure, false otherwise
	 */
	public boolean hasTreasure() {
		return bag.getValue() > 0;
	}

}
