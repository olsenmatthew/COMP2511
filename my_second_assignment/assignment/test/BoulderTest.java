import static org.junit.Assert.assertEquals;

import org.junit.Test;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.items.key.Key;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.moveable.strategies.LargeMoveStrategy;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.Player;

public class BoulderTest {

	@Test
	public void testBoulderMovable() {
		Dungeon dungeon = new Dungeon(3, 3);
		Player p = new Player(dungeon, 0, 1, new StrongMoveStrategy());
		dungeon.setPlayer(p);
		Boulder b = new Boulder(dungeon, 1, 1, new LargeMoveStrategy());
		dungeon.addEntity(b);
		p.moveRight();
		assertEquals(b.getCoordinates(), new Coordinates(2, 1));
		p.moveRight();
		assertEquals(b.getCoordinates(), new Coordinates(2, 1));
		p.moveDown();
		p.moveRight();
		p.moveUp();
		assertEquals(b.getCoordinates(), new Coordinates(2, 0));
		p.moveUp();
		assertEquals(b.getCoordinates(), new Coordinates(2, 0));
	}

	@Test
	public void testBoulderBlockEnemy() {
		Dungeon dungeon = new Dungeon(3, 3);
		Boulder b = new Boulder(dungeon, 1, 1, new LargeMoveStrategy());
		dungeon.addEntity(b);
		Enemy e = new Enemy(dungeon, 1, 0, new WeakMoveStrategy());
		dungeon.addEntity(e);
		e.moveDown();
		assertEquals(b.getCoordinates(), new Coordinates(1, 1));
	}

	@Test
	public void testBoulderBlockBoulder() {
		Dungeon dungeon = new Dungeon(4, 4);
		Boulder b1 = new Boulder(dungeon, 1, 1, new LargeMoveStrategy());
		Boulder b2 = new Boulder(dungeon, 0, 1, new LargeMoveStrategy());
		Boulder b3 = new Boulder(dungeon, 1, 0, new LargeMoveStrategy());
		dungeon.addEntity(b1);
		dungeon.addEntity(b2);
		dungeon.addEntity(b3);
		b2.moveRight();
		assertEquals(b2.getCoordinates(), new Coordinates(0, 1));
		assertEquals(b1.getCoordinates(), new Coordinates(1, 1));
		b3.moveDown();
		assertEquals(b3.getCoordinates(), new Coordinates(1, 0));
		assertEquals(b1.getCoordinates(), new Coordinates(1, 1));
	}

	@Test
	public void testBoulderBeDestroyed() {
		Dungeon dungeon = new Dungeon(3, 3);
		Bomb bomb = new Bomb(dungeon, 2, 2);
		Boulder boulder1 = new Boulder(dungeon, 2, 3, new LargeMoveStrategy());
		Boulder boulder2 = new Boulder(dungeon, 3, 2, new LargeMoveStrategy());

		dungeon.addEntity(bomb);
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
			if (e instanceof Boulder) {
				count++;
			}
		}
		assertEquals(count, 0);
	}

	@Test
	public void testBoulderToBigToMoveThroughDoor() {
		Dungeon dungeon = new Dungeon(5, 5);
		Player p = new Player(dungeon, 0, 1, new StrongMoveStrategy());
		Boulder b = new Boulder(dungeon, 1, 1, new LargeMoveStrategy());
		Door d = new Door(2, 1, 0);
		Key k = new Key(dungeon, 2, 1, 0);
		dungeon.setPlayer(p);
		dungeon.addEntity(b);
		dungeon.addEntity(d);
		dungeon.addEntity(k);
		k.use();
		p.moveRight();
		assertEquals(b.getCoordinates(), new Coordinates(1, 1));
	}
}
