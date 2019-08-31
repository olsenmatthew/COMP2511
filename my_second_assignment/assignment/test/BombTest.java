import static org.junit.Assert.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.moveable.strategies.LargeMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.player.Player;

public class BombTest {

	@Test
	public void testExplodingBombKillsPlayerCarried() {
		Dungeon dungeon = new Dungeon(1, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		Bomb bomb = new Bomb(dungeon, 1, 1);

		dungeon.setPlayer(player);
		dungeon.addEntity(bomb);

		player.pickup();
		player.use();

		try {
			Thread.sleep((long) (Bomb.START_SECONDS * Bomb.SECOND + 100));
		} catch (Exception e) {
			System.err.println(e);
		}

		int count = 0;
		for (Entity e : dungeon.getEntities(bomb.getCoordinates(), player.getClass())) {
			count++;
		}

		assertEquals(0, count);
	}

	@Test
	public void testExplodingBombSameSquare() {
		Dungeon dungeon = new Dungeon(1, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		Bomb bomb = new Bomb(dungeon, 1, 1);
		Enemy enemy = new Enemy(dungeon, 1, 1, new WeakMoveStrategy());
		Boulder boulder = new Boulder(dungeon, 1, 1, new LargeMoveStrategy());

		dungeon.setPlayer(player);
		dungeon.addEntity(bomb);
		dungeon.addEntity(enemy);
		dungeon.addEntity(boulder);

		player.pickup();
		player.use();

		try {
			Thread.sleep((long) (Bomb.START_SECONDS * Bomb.SECOND + 100));
		} catch (Exception e) {
			System.err.println(e);
		}

		int count = 0;
		for (Entity e : dungeon.getEntities(bomb.getCoordinates(), player.getClass())) {
			count++;
		}

		assertEquals(0, count);
	}

	@Test
	public void testExplodingBombAdjacentSquare() {
		Dungeon dungeon = new Dungeon(3, 3);
		Player player = new Player(dungeon, 2, 1, new StrongMoveStrategy());
		Bomb bomb = new Bomb(dungeon, 2, 2);
		Enemy enemy = new Enemy(dungeon, 1, 2, new WeakMoveStrategy());
		Boulder boulder1 = new Boulder(dungeon, 2, 3, new LargeMoveStrategy());
		Boulder boulder2 = new Boulder(dungeon, 3, 2, new LargeMoveStrategy());

		dungeon.setPlayer(player);
		dungeon.addEntity(bomb);
		dungeon.addEntity(enemy);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);

		bomb.use();

		try {
			Thread.sleep((long) (Bomb.START_SECONDS * Bomb.SECOND + 100));
		} catch (Exception e) {
			System.err.println(e);
		}

		int count = 0;
		for (Entity e : dungeon.getAdjacentEntities(bomb.getCoordinates(), player.getClass())) {
			count++;
		}
		assertEquals(0, count);

		for (Entity e : dungeon.getEntities(bomb.getCoordinates(), player.getClass())) {
			count++;
		}
		assertEquals(0, count);
	}

	@Test
	public void testBombDisappearsAfterExlosion() {
		Dungeon dungeon = new Dungeon(1, 1);

		Bomb bomb = new Bomb(dungeon, 1, 1);
		dungeon.addEntity(bomb);
		bomb.use();

		assertEquals(1, dungeon.getEntities().size());

		try {
			Thread.sleep((long) (Bomb.START_SECONDS * Bomb.SECOND + 100));
		} catch (Exception e) {
			System.err.println(e);
		}

		assertEquals(true, bomb.remove());

		assertEquals(0, dungeon.getEntities().size());

	}

	@Test
	public void testExplodingBombOutOfRange() {
		Dungeon dungeon = new Dungeon(25, 25);
		Player player = new Player(dungeon, 2, 1, new StrongMoveStrategy());
		Bomb bomb = new Bomb(dungeon, 25, 25);
		Enemy enemy = new Enemy(dungeon, 1, 2, new WeakMoveStrategy());
		Boulder boulder1 = new Boulder(dungeon, 2, 3, new LargeMoveStrategy());
		Boulder boulder2 = new Boulder(dungeon, 3, 2, new LargeMoveStrategy());

		dungeon.setPlayer(player);
		dungeon.addEntity(bomb);
		dungeon.addEntity(enemy);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);

		bomb.use();

		try {
			Thread.sleep((long) (Bomb.START_SECONDS * Bomb.SECOND + 100));
		} catch (Exception e) {
			System.err.println(e);
		}
		int count = 0;
		for (Entity e : dungeon.getEntities()) {
			count++;
		}
		assertEquals(4, count);

	}

}
