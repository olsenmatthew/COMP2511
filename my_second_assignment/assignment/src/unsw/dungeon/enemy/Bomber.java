package unsw.dungeon.enemy;

import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Coordinates;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.strategy.ChaseStrategy;
import unsw.dungeon.enemy.strategy.RunAwayStrategy;
import unsw.dungeon.items.bag.Bag;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.moveable.strategies.Movable;

/**
 * 
 * {@link Bomber} extends {@link Destructible} implements {@link Runnable},
 * {@link WeakMortalEnemy}
 * 
 * Carries bombs towards player if it has enough bombs. Plants bombs if adjacent
 * to player. Runs away if minBombs requirement is not met. Automatically spans
 * bombs given enough time.
 */
public class Bomber extends Destructible implements Runnable, WeakMortalEnemy {
	/**
	 * depth of search
	 */
	private static int RANGE = 50;
	/**
	 * movement delay in milliseconds
	 */
	public static int MOVEMENT_DELAY = 200;
	/**
	 * bomber's number of steps before ready to spawn bomb
	 */
	private static int BOMB_SPAWN = 10;
	/**
	 * bomber movement strategy
	 */
	private Movable movementStrategy;
	/**
	 * is bomber alive
	 */
	private BooleanProperty alive;
	/**
	 * bomber's bag
	 */
	private Bag bag;
	/**
	 * number of steps the bomber has taken
	 */
	private int step;
	/**
	 * should bomber spawn new bomb
	 */
	private BooleanProperty spawnBomb;
	/**
	 * minimum number of bombs before bomber goes on the offensive
	 */
	private int minBombs;

	/**
	 * 
	 * Create a enemy positioned in square (x,y)
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param strategy
	 */
	public Bomber(Dungeon dungeon, int x, int y, Movable strategy) {
		super(dungeon, x, y);
		this.movementStrategy = strategy;
		this.alive = new SimpleBooleanProperty(true);
		this.bag = new Bag(dungeon, x, y);
		Random r = new Random();
		this.minBombs = (r.nextInt() % 4) + 1;
		this.step = 0;
		this.spawnBomb = new SimpleBooleanProperty(false);
	}

	/**
	 * move up with given movement strategy
	 */
	public void moveUp() {
		movementStrategy.moveUp(getDungeon(), this);
	}

	/**
	 * move down with given movement strategy
	 */
	public void moveDown() {
		movementStrategy.moveDown(getDungeon(), this);
	}

	/**
	 * move left with given movement strategy
	 */
	public void moveLeft() {
		movementStrategy.moveLeft(getDungeon(), this);
	}

	/**
	 * move right with given movement strategy
	 */
	public void moveRight() {
		movementStrategy.moveRight(getDungeon(), this);
	}

	/**
	 * @param bomb, add bomb to bag
	 */
	public void addBomb(Bomb bomb) {
		this.bag.addItem(bomb);
	}

	/**
	 * use next bomb in bag
	 */
	private void use() {
		this.bag.use();
	}

	/**
	 * drop next bomb from bag if exists
	 */
	private void drop() {
		Entity bomb = (Entity) this.bag.removeItem();
		if (bomb != null) {
			bomb.setX(getX());
			bomb.setY(getY());
			getDungeon().addEntity(bomb);
		}
	}

	/**
	 * Utilize variable search strategies in order to find paths towards the goal
	 * destination, get the next coordinates in the path.
	 * 
	 * @return destination Coordinates of next move in path to destination, null if
	 *         no path found
	 */
	private Coordinates getNextMove() {
		Coordinates result = null;
		Coordinates targetCoordinates = getDungeon().getPlayerCoordinates();
		if (targetCoordinates != null) {
			if (getDungeon().isPlayerPoweredUp() || bombCount() < getMinBombs()) {
				RunAwayStrategy runStrategy = new RunAwayStrategy(getCoordinates(), targetCoordinates, Bomber.RANGE,
						getDungeon());
				if (runStrategy.search()) {
					result = runStrategy.getNext();
				}
			} else {
				if (this.getCoordinates().adjacentTo(targetCoordinates)) {
					use();
					drop();
				}
				ChaseStrategy chaseStrategy = new ChaseStrategy(getCoordinates(), targetCoordinates, Bomber.RANGE,
						getDungeon());
				if (chaseStrategy.search()) {
					result = chaseStrategy.getNext();
				}
			}
		}

		return result;
	}

	/**
	 * move in the direction of the next coordinate (left, right, up, or down)
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
	 * while alive, make next move and spawn bomb occasionally
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (getAlive().get()) {
			try {
				Thread.sleep(MOVEMENT_DELAY);
				if (alive.get()) {
					makeNextMove();
					if (getStep() % Bomber.BOMB_SPAWN == 0) {
						spawnBomb.set(true);
					} else {
						spawnBomb.set(false);
					}
					incrementStep();
				}
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

	/**
	 * @return true if should spawn new bomb, false otherwise
	 */
	public BooleanProperty spawnBomb() {
		return spawnBomb;
	}

	/*
	 * @see unsw.dungeon.WeakMortal#getAlive()
	 * 
	 * @return true if alive, false if otherwise
	 */
	@Override
	public BooleanProperty getAlive() {
		return alive;
	}

	/**
	 * @return number of bombs, int
	 */
	private int bombCount() {
		return bag.countType(Bomb.class);
	}

	/**
	 * @return step, int
	 */
	private int getStep() {
		return this.step;
	}

	/**
	 * increment step variable
	 */
	private void incrementStep() {
		this.step++;
	}

	/*
	 * updates bag's Y coordinates as well
	 * 
	 * @see unsw.dungeon.Entity#setY(int)
	 */
	@Override
	public void setY(int y) {
		this.bag.setY(y);
		super.setY(y);
	}

	/*
	 * updates bag's X coordinates as well
	 * 
	 * @see unsw.dungeon.Entity#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.bag.setX(x);
		super.setX(x);
	}

	/**
	 * @return minBombs, result should be less than or equal to 5 and greater than 0
	 */
	private int getMinBombs() {
		return this.minBombs;
	}

}
