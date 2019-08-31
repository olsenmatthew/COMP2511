import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.items.bag.Bag;
import unsw.dungeon.items.key.Key;

public class DoorTest {
	@Test
	public void testKeyunLockDoor() {
		Dungeon dungeon = new Dungeon(5, 5);
		Bag bag = new Bag(dungeon, 1, 1);
		Key key1 = new Key(dungeon, 1, 1, 1);
		bag.addItem(key1);
		Door door1 = new Door(2, 1, 1);
		dungeon.addEntity(door1);
		Door door2 = new Door(1, 2, 2);
		dungeon.addEntity(door2);
		Door door3 = new Door(1, 1, 3);
		dungeon.addEntity(door3);

		key1.use();

		assertTrue(door1.isOpen().get());
		assertFalse(door2.isOpen().get());
		assertFalse(door3.isOpen().get());

		assertTrue(key1.remove());

		Key key2 = new Key(dungeon, 1, 1, 2);
		bag.addItem(key2);

		key2.use();

		assertTrue(door1.isOpen().get());
		assertTrue(door2.isOpen().get());
		assertFalse(door3.isOpen().get());

		assertTrue(door1.canCoexist());
		assertTrue(door2.canCoexist());
		assertFalse(door3.canCoexist());

	}
}
