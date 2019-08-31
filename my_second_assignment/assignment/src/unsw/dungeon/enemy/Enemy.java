package unsw.dungeon.enemy;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Coordinates;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.enemy.strategy.ChaseStrategy;
import unsw.dungeon.enemy.strategy.RunAwayStrategy;
import unsw.dungeon.moveable.strategies.Movable;

/**
 * {@link Enemy} extends {@link Destructible} implements {@link Runnable},
 * {@link WeakMortalEnemy}
 */
public class Enemy extends Destructible implements Runnable, WeakMortalEnemy {
	/**
	 * depth of search bound
	 */
	private static int RANGE = 25;
	/**
	 * delay in milliseconds for movement
	 */
	public static int MOVEMENT_DELAY = 350;
	/**
	 * movement strategy
	 */
	private Movable movementStrategy;
	/**
	 * whether enemy is alive
	 */
	private BooleanProperty alive;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param strategy
	 */
	public Enemy(Dungeon dungeon, int x, int y, Movable strategy) {
		super(dungeon, x, y);
		this.movementStrategy = strategy;
		this.alive = new SimpleBooleanProperty(true);
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
	 * use search strategies to find path to destination from current coordinates
	 * 
	 * @return coordinates of next move along path to destination from current
	 *         coordinates, null if no path is found
	 */
	private Coordinates getNextMove() {
		Coordinates result = null;
		Coordinates playerCoordinates = getDungeon().getPlayerCoordinates();
		if (playerCoordinates != null) {
			if (getDungeon().isPlayerPoweredUp()) {
				RunAwayStrategy runStrategy = new RunAwayStrategy(getCoordinates(), playerCoordinates, Enemy.RANGE,
						getDungeon());
				if (runStrategy.search()) {
					result = runStrategy.getNext();
				}
			} else {
				ChaseStrategy chaseStrategy = new ChaseStrategy(getCoordinates(), playerCoordinates, Enemy.RANGE,
						getDungeon());
				if (chaseStrategy.search()) {
					result = chaseStrategy.getNext();
				}
			}
		}

		return result;
	}

	/**
	 * move in direction of next coordinates towards destination
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
	 * while alive, make next move
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
	 * set alive to false
	 * 
	 * @see unsw.dungeon.WeakMortal#kill()
	 */
	@Override
	public void kill() {
		this.alive.set(false);
	}

	/*
	 * @return true if enemy is alive, false otherwise
	 * 
	 * @see unsw.dungeon.WeakMortal#getAlive()
	 */
	@Override
	public BooleanProperty getAlive() {
		return alive;
	}

}
