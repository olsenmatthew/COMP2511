package unsw.dungeon.moveable.strategies;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.player.Player;

public class StrongMoveStrategy implements Movable {

	/**
	 * store entity which can be pushed by player
	 */
	private List<Entity> pushables;

	/**
	 * a move strategy for player
	 */
	public StrongMoveStrategy() {
		pushables = new ArrayList<Entity>();
	}

	@Override
	public void moveUp(Dungeon dungeon, Destructible movable) {
		if (movable.getY() > 0) {
			pushables = dungeon.getEntities(new Coordinates(movable.getX(), movable.getY() - 1), Boulder.class);
			if (!pushables.isEmpty()) {
				for (Entity b : pushables) {
					((Boulder) b).moveUp();
				}
			}
			if (dungeon.canCoexist(movable.getX(), movable.getY() - 1)) {
				((Player) movable).setY(movable.getY() - 1);
			}
			dungeon.updateMovement();
		}

	}

	@Override
	public void moveDown(Dungeon dungeon, Destructible movable) {
		if (movable.getY() < dungeon.getHeight() - 1) {
			pushables = dungeon.getEntities(new Coordinates(movable.getX(), movable.getY() + 1), Boulder.class);
			if (!pushables.isEmpty()) {
				for (Entity b : pushables) {
					((Boulder) b).moveDown();
				}
			}
			if (dungeon.canCoexist(movable.getX(), movable.getY() + 1)) {
				((Player) movable).setY(movable.getY() + 1);
			}
			dungeon.updateMovement();
		}

	}

	@Override
	public void moveLeft(Dungeon dungeon, Destructible movable) {
		if (movable.getX() > 0) {
			pushables = dungeon.getEntities(new Coordinates(movable.getX() - 1, movable.getY()), Boulder.class);
			if (!pushables.isEmpty()) {
				for (Entity b : pushables) {
					((Boulder) b).moveLeft();
				}
			}
			if (dungeon.canCoexist(movable.getX() - 1, movable.getY())) {
				((Player) movable).setX(movable.getX() - 1);
			}
			dungeon.updateMovement();
		}

	}

	@Override
	public void moveRight(Dungeon dungeon, Destructible movable) {
		if (movable.getX() < dungeon.getWidth() - 1) {
			pushables = dungeon.getEntities(new Coordinates(movable.getX() + 1, movable.getY()), Boulder.class);
			if (!pushables.isEmpty()) {
				for (Entity b : pushables) {
					((Boulder) b).moveRight();
				}
			}
			if (dungeon.canCoexist(movable.getX() + 1, movable.getY())) {
				((Player) movable).setX(movable.getX() + 1);
			}
			dungeon.updateMovement();
		}

	}

}
