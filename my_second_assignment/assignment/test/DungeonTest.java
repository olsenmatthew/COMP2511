import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Wall;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.player.Player;

public class DungeonTest {
	@Test
	public void testAddEntity() {
		Dungeon dungeon = new Dungeon(10, 10);
		dungeon.addEntity(new Wall(1, 1));
		assertEquals(1, dungeon.getEntities().size());
		dungeon.addEntity(new Wall(0, 0));
		assertEquals(2, dungeon.getEntities().size());

		for (Entity e : dungeon.getEntities()) {
			assertTrue(e.isVisible().get());
		}
	}

	@Test
	public void testRemoveEntity() {
		Dungeon dungeon = new Dungeon(10, 10);
		Wall w1 = new Wall(1, 1);
		Wall w2 = new Wall(0, 0);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);
		assertEquals(dungeon.getEntities().size(), 2);
		dungeon.removeEntity(w1);
		assertEquals(dungeon.getEntities().size(), 1);
		assertFalse(w1.isVisible().get());
	}

	@Test
	public void testSpawnEntity() {
		Dungeon dungeon = new Dungeon(10, 10);
		Wall w1 = new Wall(1, 1);
		dungeon.addSpawn(w1, 1);
		assertEquals(dungeon.getEntities().size(), 0);
		try {
			Thread.sleep(1050);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(dungeon.getEntities().size(), 1);
	}

	@Test
	public void testCanCoexist() {
		Dungeon dungeon = new Dungeon(10, 10);
		Wall w1 = new Wall(1, 1);
		Wall w2 = new Wall(0, 0);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);

		assertFalse(dungeon.canCoexist(1, 1));
		assertFalse(dungeon.canCoexist(0, 0));
		assertTrue(dungeon.canCoexist(0, 1));
		assertTrue(dungeon.canCoexist(1, 0));
	}

	@Test
	public void testDungeonPlayer() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		assertEquals(player.isPoweredUp(), dungeon.isPlayerPoweredUp());
		assertEquals(1, dungeon.getEntities().size());
		assertEquals(0, dungeon.getPlayerCoordinates().getX());
		assertEquals(0, dungeon.getPlayerCoordinates().getY());
	}

}
