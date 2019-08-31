import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Wall;
import unsw.dungeon.enemy.Bomber;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.moveable.strategies.LargeMoveStrategy;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.Player;
import unsw.dungeon.player.states.InvincibilityPotionState;

public class BomberTest {
	@Test
	public void testBomberLeftMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Bomber bomber = new Bomber(dungeon, 1, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(1, 0), bomber.getCoordinates());
		bomber.moveLeft();
		assertEquals(new Coordinates(0, 0), bomber.getCoordinates());

		for (int i = 0; i < 5; i++) {
			assertEquals(new Coordinates(0, 0), bomber.getCoordinates());
			bomber.moveLeft();
		}
	}

	@Test
	public void testBomberRightMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Bomber bomber = new Bomber(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), bomber.getCoordinates());
		bomber.moveRight();
		assertEquals(new Coordinates(1, 0), bomber.getCoordinates());

		for (int i = 0; i < 5; i++) {
			bomber.moveRight();
		}
		assertEquals(new Coordinates(2, 0), bomber.getCoordinates());
	}

	@Test
	public void testBomberUpMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Bomber bomber = new Bomber(dungeon, 0, 1, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 1), bomber.getCoordinates());
		bomber.moveUp();
		assertEquals(new Coordinates(0, 0), bomber.getCoordinates());

		for (int i = 0; i < 5; i++) {
			bomber.moveUp();
		}
		assertEquals(new Coordinates(0, 0), bomber.getCoordinates());
	}

	@Test
	public void testBomberDownMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Bomber bomber = new Bomber(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), bomber.getCoordinates());
		bomber.moveDown();
		assertEquals(new Coordinates(0, 1), bomber.getCoordinates());

		for (int i = 0; i < 5; i++) {
			bomber.moveDown();
		}
		assertEquals(new Coordinates(0, 2), bomber.getCoordinates());
	}

	@Test
	public void testBomberMovementRestriction() {
		Dungeon dungeon = new Dungeon(5, 5);
		Bomber bomber = new Bomber(dungeon, 3, 3, new WeakMoveStrategy());
		Wall wall1 = new Wall(3, 4);
		Wall wall2 = new Wall(3, 2);
		Boulder boulder = new Boulder(dungeon, 4, 3, new LargeMoveStrategy());
		Door door = new Door(2, 3, 0);

		dungeon.addEntity(bomber);
		dungeon.addEntity(wall1);
		dungeon.addEntity(wall2);
		dungeon.addEntity(boulder);
		dungeon.addEntity(door);

		/*
		 * bomber can't move through wall
		 */
		bomber.moveUp();
		assertEquals(new Coordinates(3, 3), bomber.getCoordinates());
		bomber.moveDown();
		assertEquals(new Coordinates(3, 3), bomber.getCoordinates());
		/*
		 * bomber can't move to coordinates with boulder
		 */
		bomber.moveRight();
		assertEquals(new Coordinates(3, 3), bomber.getCoordinates());
		/*
		 * bomber can't move to coordinates with door
		 */
		bomber.moveLeft();
		assertEquals(new Coordinates(3, 3), bomber.getCoordinates());

	}

	@Test
	public void testBomberNoPathsToPlayer() {
		Dungeon dungeon = new Dungeon(1, 5);
		Bomber bomber = new Bomber(dungeon, 0, 0, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 4, new StrongMoveStrategy());
		Door door = new Door(0, 3, 0);

		dungeon.addEntity(bomber);
		dungeon.setPlayer(player);
		dungeon.addEntity(door);

		Thread t = new Thread(bomber);
		t.start();

		try {
			Thread.sleep(Bomber.MOVEMENT_DELAY * 2);
		} catch (Exception e) {
			System.err.println(e);
		}

		assertEquals(new Coordinates(0, 0), bomber.getCoordinates());

	}

	@Test
	public void testBomberMoveAlongPathToPlayer() {
		Dungeon dungeon = new Dungeon(5, 5);
		Bomber bomber = new Bomber(dungeon, 0, 0, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 4, new StrongMoveStrategy());

		dungeon.addEntity(bomber);
		dungeon.setPlayer(player);

		Thread t = new Thread(bomber);
		t.start();

		try {
			Thread.sleep(Bomber.MOVEMENT_DELAY * 2);
		} catch (Exception e) {
			System.err.println(e);
		}

		assertNotEquals(new Coordinates(0, 0), bomber.getCoordinates());

	}

	@Test
	public void testBomberRunAwayFromInvinciblePlayer() {
		Dungeon dungeon = new Dungeon(5, 5);
		Bomber bomber = new Bomber(dungeon, 1, 1, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 0, new StrongMoveStrategy());

		dungeon.addEntity(bomber);
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 0, 0));

		Thread t = new Thread(bomber);
		t.start();
		/*
		 * player becomes invincible
		 */
		player.pickup();
		player.use();

		try {
			/*
			 * Allow time for bomber to make 4 moves
			 */
			Thread.sleep(Bomber.MOVEMENT_DELAY * 4);
		} catch (Exception e) {
			System.err.println(e);
		}
		/*
		 * Test that the bomber did not move to any of the squares closer to the
		 * invincible player
		 */
		assertNotEquals(new Coordinates(1, 1), bomber.getCoordinates());
		assertNotEquals(new Coordinates(0, 1), bomber.getCoordinates());
		assertNotEquals(new Coordinates(1, 0), bomber.getCoordinates());
		assertNotEquals(new Coordinates(0, 0), bomber.getCoordinates());

	}

}
