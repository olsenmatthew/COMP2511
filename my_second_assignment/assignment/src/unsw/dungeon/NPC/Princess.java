package unsw.dungeon.NPC;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Coordinates;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.WeakMortal;
import unsw.dungeon.enemy.strategy.ChaseStrategy;
import unsw.dungeon.moveable.strategies.Movable;

public class Princess extends Destructible implements Runnable, WeakMortal {

	private boolean saved;
	private static int RANGE = 10;
	public static int MOVEMENT_DELAY = 100;

	private Movable movementStrategy;
	private BooleanProperty alive;

	/**
	 * constructor of princess, with movement strategy and position (x,y)
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param strategy
	 */
	public Princess(Dungeon dungeon, int x, int y, Movable strategy) {
		super(dungeon, x, y);
		this.movementStrategy = strategy;
		this.saved = false;
		this.alive = new SimpleBooleanProperty(true);
	}

	/**
	 * princess moves up
	 */
	public void moveUp() {
		movementStrategy.moveUp(getDungeon(), this);
	}

	/**
	 * princess moves down
	 */
	public void moveDown() {
		movementStrategy.moveDown(getDungeon(), this);
	}

	/**
	 * princess moves left
	 */
	public void moveLeft() {
		movementStrategy.moveLeft(getDungeon(), this);
	}

	/**
	 * princess moves right
	 */
	public void moveRight() {
		movementStrategy.moveRight(getDungeon(), this);
	}

	/**
	 * make variable saved become true
	 * @postCondition saved = true
	 */
	public void savePrincess() {
		this.saved = true;
	}

	/**
	 * return the saving state
	 * @return saved, the state of variable saved
	 */
	public Boolean safe() {
		return this.saved;
	}

	/**
	 * find the next move princess goes
	 * @return Coordinates
	 */
	private Coordinates getNextMove() {
		Coordinates result = null;
		Coordinates playerCoordinates = getDungeon().getPlayerCoordinates();
		ChaseStrategy chaseStrategy = new ChaseStrategy(getCoordinates(), playerCoordinates, RANGE, getDungeon());
		if (chaseStrategy.search()) {
			result = chaseStrategy.getNext();
		}
		return result;
	}

	/**
	 * do the next move
	 */
	private void makeNextMove() {
		Coordinates next = getNextMove();
		if (next != null && !next.equals(getDungeon().getPlayerCoordinates())) {
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

	/**
	 * if princess is safe, let her move
	 */
	@Override
	public void run() {
		while (alive.get()) {
			try {
				Thread.sleep(MOVEMENT_DELAY);
				if (safe()) {
					makeNextMove();
				}
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * kill the princess
	 * @postCondition alive=false
	 */
	@Override
	public void kill() {
		alive.set(false);
	}

	/**
	 * return the living state of princess
	 * @return BooleanProperty, the state of variable alive
	 */
	@Override
	public BooleanProperty getAlive() {
		return alive;
	}
}
