import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Wall;
import unsw.dungeon.enemy.Goblin;
import unsw.dungeon.items.treasure.Treasure;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.moveable.strategies.LargeMoveStrategy;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.Player;
import unsw.dungeon.player.states.InvincibilityPotionState;

public class GoblinTest {
	@Test
	public void testGoblinLeftMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Goblin goblin = new Goblin(dungeon, 1, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(1, 0), goblin.getCoordinates());
		goblin.moveLeft();
		assertEquals(new Coordinates(0, 0), goblin.getCoordinates());

		for (int i = 0; i < 5; i++) {
			assertEquals(new Coordinates(0, 0), goblin.getCoordinates());
			goblin.moveLeft();
		}
	}

	@Test
	public void testGoblinRightMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Goblin goblin = new Goblin(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), goblin.getCoordinates());
		goblin.moveRight();
		assertEquals(new Coordinates(1, 0), goblin.getCoordinates());

		for (int i = 0; i < 5; i++) {
			goblin.moveRight();
		}
		assertEquals(new Coordinates(2, 0), goblin.getCoordinates());
	}

	@Test
	public void testGoblinUpMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Goblin goblin = new Goblin(dungeon, 0, 1, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 1), goblin.getCoordinates());
		goblin.moveUp();
		assertEquals(new Coordinates(0, 0), goblin.getCoordinates());

		for (int i = 0; i < 5; i++) {
			goblin.moveUp();
		}
		assertEquals(new Coordinates(0, 0), goblin.getCoordinates());
	}

	@Test
	public void testGoblinDownMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Goblin goblin = new Goblin(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), goblin.getCoordinates());
		goblin.moveDown();
		assertEquals(new Coordinates(0, 1), goblin.getCoordinates());

		for (int i = 0; i < 5; i++) {
			goblin.moveDown();
		}
		assertEquals(new Coordinates(0, 2), goblin.getCoordinates());
	}

	@Test
	public void testGoblinMovementRestriction() {
		Dungeon dungeon = new Dungeon(5, 5);
		Goblin goblin = new Goblin(dungeon, 3, 3, new WeakMoveStrategy());
		Wall wall1 = new Wall(3, 4);
		Wall wall2 = new Wall(3, 2);
		Boulder boulder = new Boulder(dungeon, 4, 3, new LargeMoveStrategy());
		Door door = new Door(2, 3, 0);

		dungeon.addEntity(goblin);
		dungeon.addEntity(wall1);
		dungeon.addEntity(wall2);
		dungeon.addEntity(boulder);
		dungeon.addEntity(door);

		/*
		 * goblin can't move through wall
		 */
		goblin.moveUp();
		assertEquals(new Coordinates(3, 3), goblin.getCoordinates());
		goblin.moveDown();
		assertEquals(new Coordinates(3, 3), goblin.getCoordinates());
		/*
		 * goblin can't move to coordinates with boulder
		 */
		goblin.moveRight();
		assertEquals(new Coordinates(3, 3), goblin.getCoordinates());
		/*
		 * goblin can't move to coordinates with door
		 */
		goblin.moveLeft();
		assertEquals(new Coordinates(3, 3), goblin.getCoordinates());

	}

	@Test
	public void testGoblinNoPathsToPlayer() {
		Dungeon dungeon = new Dungeon(1, 5);
		Goblin goblin = new Goblin(dungeon, 0, 0, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 4, new StrongMoveStrategy());
		Door door = new Door(0, 3, 0);

		dungeon.addEntity(goblin);
		dungeon.setPlayer(player);
		dungeon.addEntity(door);

		Thread t = new Thread(goblin);
		t.start();

		try {
			Thread.sleep(Goblin.MOVEMENT_DELAY * 2);
		} catch (Exception e) {
			System.err.println(e);
		}

		assertEquals(new Coordinates(0, 0), goblin.getCoordinates());

	}

	@Test
	public void testGoblinMoveAlongPathToTreasure() {
		Dungeon dungeon = new Dungeon(5, 5);
		Goblin goblin = new Goblin(dungeon, 0, 0, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 0, new StrongMoveStrategy());
		Treasure treasure = new Treasure(dungeon, 0, 4, 5);

		dungeon.addEntity(goblin);
		dungeon.addEntity(treasure);
		dungeon.setPlayer(player);

		Thread t = new Thread(goblin);
		t.start();
		/*
		 * three entities since player doesn't die when goblin is on same square
		 */
		assertEquals(3, dungeon.getEntities().size());

		try {
			Thread.sleep(Goblin.MOVEMENT_DELAY * 10);
		} catch (Exception e) {
			System.err.println(e);
		}

		assertNotEquals(new Coordinates(0, 0), goblin.getCoordinates());
		/*
		 * two entities now since the goblin grabbed the treasure
		 */
		assertEquals(2, dungeon.getEntities().size());

	}

	@Test
	public void testGoblinRunAwayFromInvinciblePlayer() {
		Dungeon dungeon = new Dungeon(5, 5);
		Goblin goblin = new Goblin(dungeon, 1, 1, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 0, new StrongMoveStrategy());

		dungeon.addEntity(goblin);
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 0, 0));

		Thread t = new Thread(goblin);
		t.start();
		/*
		 * player becomes invincible
		 */
		player.pickup();
		player.use();

		try {
			/*
			 * Allow time for goblin to make 4 moves
			 */
			Thread.sleep(Goblin.MOVEMENT_DELAY * 4);
		} catch (Exception e) {
			System.err.println(e);
		}
		/*
		 * Test that the goblin did not move to any of the squares closer to the
		 * invincible player
		 */
		assertNotEquals(new Coordinates(1, 1), goblin.getCoordinates());
		assertNotEquals(new Coordinates(0, 1), goblin.getCoordinates());
		assertNotEquals(new Coordinates(1, 0), goblin.getCoordinates());
		assertNotEquals(new Coordinates(0, 0), goblin.getCoordinates());

	}

}
