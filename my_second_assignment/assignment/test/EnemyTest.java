import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Wall;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.moveable.strategies.LargeMoveStrategy;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.Player;
import unsw.dungeon.player.states.InvincibilityPotionState;

public class EnemyTest {
	@Test
	public void testEnemyLeftMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Enemy enemy = new Enemy(dungeon, 1, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(1, 0), enemy.getCoordinates());
		enemy.moveLeft();
		assertEquals(new Coordinates(0, 0), enemy.getCoordinates());

		for (int i = 0; i < 5; i++) {
			assertEquals(new Coordinates(0, 0), enemy.getCoordinates());
			enemy.moveLeft();
		}
	}

	@Test
	public void testEnemyRightMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Enemy enemy = new Enemy(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), enemy.getCoordinates());
		enemy.moveRight();
		assertEquals(new Coordinates(1, 0), enemy.getCoordinates());

		for (int i = 0; i < 5; i++) {
			enemy.moveRight();
		}
		assertEquals(new Coordinates(2, 0), enemy.getCoordinates());
	}

	@Test
	public void testEnemyUpMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Enemy enemy = new Enemy(dungeon, 0, 1, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 1), enemy.getCoordinates());
		enemy.moveUp();
		assertEquals(new Coordinates(0, 0), enemy.getCoordinates());

		for (int i = 0; i < 5; i++) {
			enemy.moveUp();
		}
		assertEquals(new Coordinates(0, 0), enemy.getCoordinates());
	}

	@Test
	public void testEnemyDownMovement() {
		Dungeon dungeon = new Dungeon(3, 3);
		Enemy enemy = new Enemy(dungeon, 0, 0, new WeakMoveStrategy());

		assertEquals(new Coordinates(0, 0), enemy.getCoordinates());
		enemy.moveDown();
		assertEquals(new Coordinates(0, 1), enemy.getCoordinates());

		for (int i = 0; i < 5; i++) {
			enemy.moveDown();
		}
		assertEquals(new Coordinates(0, 2), enemy.getCoordinates());
	}

	@Test
	public void testEnemyMovementRestriction() {
		Dungeon dungeon = new Dungeon(5, 5);
		Enemy enemy = new Enemy(dungeon, 3, 3, new WeakMoveStrategy());
		Wall wall1 = new Wall(3, 4);
		Wall wall2 = new Wall(3, 2);
		Boulder boulder = new Boulder(dungeon, 4, 3, new LargeMoveStrategy());
		Door door = new Door(2, 3, 0);

		dungeon.addEntity(enemy);
		dungeon.addEntity(wall1);
		dungeon.addEntity(wall2);
		dungeon.addEntity(boulder);
		dungeon.addEntity(door);

		/*
		 * enemy can't move through wall
		 */
		enemy.moveUp();
		assertEquals(new Coordinates(3, 3), enemy.getCoordinates());
		enemy.moveDown();
		assertEquals(new Coordinates(3, 3), enemy.getCoordinates());
		/*
		 * enemy can't move to coordinates with boulder
		 */
		enemy.moveRight();
		assertEquals(new Coordinates(3, 3), enemy.getCoordinates());
		/*
		 * enemy can't move to coordinates with door
		 */
		enemy.moveLeft();
		assertEquals(new Coordinates(3, 3), enemy.getCoordinates());

	}

	@Test
	public void testEnemyNoPathsToPlayer() {
		Dungeon dungeon = new Dungeon(1, 5);
		Enemy enemy = new Enemy(dungeon, 0, 0, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 4, new StrongMoveStrategy());
		Door door = new Door(0, 3, 0);

		dungeon.addEntity(enemy);
		dungeon.setPlayer(player);
		dungeon.addEntity(door);

		Thread t = new Thread(enemy);
		t.start();

		try {
			Thread.sleep(Enemy.MOVEMENT_DELAY * 2);
		} catch (Exception e) {
			System.err.println(e);
		}

		assertEquals(new Coordinates(0, 0), enemy.getCoordinates());

	}

	@Test
	public void testEnemyMoveAlongPathToPlayer() {
		Dungeon dungeon = new Dungeon(5, 5);
		Enemy enemy = new Enemy(dungeon, 0, 0, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 4, new StrongMoveStrategy());

		dungeon.addEntity(enemy);
		dungeon.setPlayer(player);

		Thread t = new Thread(enemy);
		t.start();

		try {
			Thread.sleep(Enemy.MOVEMENT_DELAY * 2);
		} catch (Exception e) {
			System.err.println(e);
		}

		assertNotEquals(new Coordinates(0, 0), enemy.getCoordinates());

	}

	@Test
	public void testEnemyRunAwayFromInvinciblePlayer() {
		Dungeon dungeon = new Dungeon(5, 5);
		Enemy enemy = new Enemy(dungeon, 1, 1, new WeakMoveStrategy());
		Player player = new Player(dungeon, 0, 0, new StrongMoveStrategy());

		dungeon.addEntity(enemy);
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 0, 0));

		Thread t = new Thread(enemy);
		t.start();
		/*
		 * player becomes invincible
		 */
		player.pickup();
		player.use();

		try {
			/*
			 * Allow time for enemy to make 4 moves
			 */
			Thread.sleep(Enemy.MOVEMENT_DELAY * 4);
		} catch (Exception e) {
			System.err.println(e);
		}
		/*
		 * Test that the enemy did not move to any of the squares closer to the
		 * invincible player
		 */
		assertNotEquals(new Coordinates(1, 1), enemy.getCoordinates());
		assertNotEquals(new Coordinates(0, 1), enemy.getCoordinates());
		assertNotEquals(new Coordinates(1, 0), enemy.getCoordinates());
		assertNotEquals(new Coordinates(0, 0), enemy.getCoordinates());

	}

}
