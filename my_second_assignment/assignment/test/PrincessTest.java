import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.NPC.Princess;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.Player;
import unsw.dungeon.player.states.InvincibilityPotionState;

public class PrincessTest {
	@Test
	public void testprincessLeftMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Princess princess = new Princess(dungeon, 1, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(1, 0), princess.getCoordinates());
		princess.moveLeft();
		assertEquals(new Coordinates(0, 0), princess.getCoordinates());

		for (int i = 0; i < 5; i++) {
			assertEquals(new Coordinates(0, 0), princess.getCoordinates());
			princess.moveLeft();
		}
	}

	@Test
	public void testprincessRightMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Princess princess = new Princess(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), princess.getCoordinates());
		princess.moveRight();
		assertEquals(new Coordinates(1, 0), princess.getCoordinates());

		for (int i = 0; i < 5; i++) {
			princess.moveRight();
		}
		assertEquals(new Coordinates(2, 0), princess.getCoordinates());
	}

	@Test
	public void testprincessUpMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Princess princess = new Princess(dungeon, 0, 1, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 1), princess.getCoordinates());
		princess.moveUp();
		assertEquals(new Coordinates(0, 0), princess.getCoordinates());

		for (int i = 0; i < 5; i++) {
			princess.moveUp();
		}
		assertEquals(new Coordinates(0, 0), princess.getCoordinates());
	}

	@Test
	public void testprincessDownMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Princess princess = new Princess(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), princess.getCoordinates());
		princess.moveDown();
		assertEquals(new Coordinates(0, 1), princess.getCoordinates());

		for (int i = 0; i < 5; i++) {
			princess.moveDown();
		}
		assertEquals(new Coordinates(0, 2), princess.getCoordinates());
	}
	@Test
	public void testprincessGetKill() {
		Dungeon dungeon = new Dungeon(3, 3);
		Princess princess = new Princess(dungeon, 0, 0, new WeakMoveStrategy());
		dungeon.addEntity(princess);
		assertTrue(princess.getAlive().get());
		Enemy e = new Enemy(dungeon,0,1,new WeakMoveStrategy());
		dungeon.addEntity(e);
		e.moveUp();
		assertFalse(princess.getAlive().get());
	}

}
