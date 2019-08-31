package unsw.dungeon.moveable.strategies;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.Bomber;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.enemy.Goblin;

public class LargeMoveStrategy implements Movable {

	/**
	 * store entity in the direction of movement
	 */
	private List<Entity> largeItemBlock;

	/**
	 * create a list that store entity in the direction of movement
	 */
	public LargeMoveStrategy() {
		largeItemBlock = new ArrayList<Entity>();
	}

	@Override
	public void moveUp(Dungeon dungeon, Destructible movable) {
		Boolean isBlock = false;
		if (movable.getY() > 0) {
			largeItemBlock = dungeon.getEntities(new Coordinates(movable.getX(), movable.getY() - 1));
			for (Entity e: largeItemBlock) {
				if (e instanceof Enemy || e instanceof Bomber || e instanceof Goblin) {
					isBlock = true;
				}
			}
			if (!isBlock && dungeon.canCoexist(movable.getX(), movable.getY() - 1)) {
				movable.setY(movable.getY() - 1);
				dungeon.updateMovement();
			}
		}
	}

	@Override
	public void moveDown(Dungeon dungeon, Destructible movable) {
		Boolean isBlock = false;
		if (movable.getY() < dungeon.getHeight() - 1) {
			//largeItemBlock = dungeon.getEntities(new Coordinates(movable.getX(), movable.getY() + 1), Door.class);
			largeItemBlock = dungeon.getEntities(new Coordinates(movable.getX(), movable.getY() + 1));
			for (Entity e: largeItemBlock) {
				if (e instanceof Enemy || e instanceof Bomber || e instanceof Goblin) {
					isBlock = true;
				}
			}
			if (!isBlock && dungeon.canCoexist(movable.getX(), movable.getY() + 1)) {
				movable.setY(movable.getY() + 1);
				dungeon.updateMovement();
			}
		}
	}

	@Override
	public void moveLeft(Dungeon dungeon, Destructible movable) {
		Boolean isBlock = false;
		if (movable.getX() > 0) {
			//largeItemBlock = dungeon.getEntities(new Coordinates(movable.getX() - 1, movable.getY()), Door.class);
			largeItemBlock = dungeon.getEntities(new Coordinates(movable.getX()-1, movable.getY()));
			for (Entity e: largeItemBlock) {
				if (e instanceof Enemy || e instanceof Bomber || e instanceof Goblin) {
					isBlock = true;
				}
			}
			if (!isBlock && dungeon.canCoexist(movable.getX() - 1, movable.getY())) {
				movable.setX(movable.getX() - 1);
				dungeon.updateMovement();
			}
		}
	}

	@Override
	public void moveRight(Dungeon dungeon, Destructible movable) {
		Boolean isBlock = false;
		if (movable.getX() < dungeon.getWidth() - 1) {
			//largeItemBlock = dungeon.getEntities(new Coordinates(movable.getX() + 1, movable.getY()), Door.class);
			largeItemBlock = dungeon.getEntities(new Coordinates(movable.getX()+1, movable.getY()));
			for (Entity e: largeItemBlock) {
				if (e instanceof Enemy || e instanceof Bomber || e instanceof Goblin) {
					isBlock = true;
				}
			}
			if (!isBlock && dungeon.canCoexist(movable.getX() + 1, movable.getY())) {
				movable.setX(movable.getX() + 1);
				dungeon.updateMovement();
			}
		}

	}

}
