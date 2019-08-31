package unsw.dungeon.moveable.strategies;

import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;

public class WeakMoveStrategy implements Movable {


	@Override
	public void moveUp(Dungeon dungeon, Destructible movable) {
		if (movable.getY() > 0) {
			if (dungeon.canCoexist(movable.getX(), movable.getY() - 1)) {
				movable.setY(movable.getY() - 1);
				dungeon.updateMovement();
			}
		}
	}

	@Override
	public void moveDown(Dungeon dungeon, Destructible movable) {
		if (movable.getY() < dungeon.getHeight() - 1)
			if (dungeon.canCoexist(movable.getX(), movable.getY() + 1)) {
				movable.setY(movable.getY() + 1);
				dungeon.updateMovement();
			}
	}

	@Override
	public void moveLeft(Dungeon dungeon, Destructible movable) {
		if (movable.getX() > 0) {
			if (dungeon.canCoexist(movable.getX() - 1, movable.getY())) {
				movable.setX(movable.getX() - 1);
				dungeon.updateMovement();
			}
		}
	}

	@Override
	public void moveRight(Dungeon dungeon, Destructible movable) {
		if (movable.getX() < dungeon.getWidth() - 1) {
			if (dungeon.canCoexist(movable.getX() + 1, movable.getY())) {
				movable.setX(movable.getX() + 1);
				dungeon.updateMovement();
			}
		}
	}

}
