
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.items.bag.Bag;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.items.key.Key;
import unsw.dungeon.items.sword.Sword;
import unsw.dungeon.items.treasure.Treasure;

public class BagTest {

	@Test
	public void testEmptyBagValue() {
		Dungeon dungeon = new Dungeon(1, 1);
		Bag bag = new Bag(dungeon, 1, 1);
		assertEquals(0, bag.getValue());
	}

	@Test
	public void testBagValueWithItems() {
		Dungeon dungeon = new Dungeon(1, 1);
		Bag bag = new Bag(dungeon, 1, 1);
		Sword sword = new Sword(dungeon, 1, 1);
		Bag bag2 = new Bag(dungeon, 1, 1);
		Key key = new Key(dungeon, 1, 1, 1);
		bag.addItem(sword);
		bag.addItem(bag2);
		bag.addItem(key);
		assertEquals(0, bag.getValue());

		Treasure treasure = new Treasure(dungeon, 1, 1, 1);
		bag.addItem(treasure);
		assertEquals(1, bag.getValue());

		Treasure treasure2 = new Treasure(dungeon, 1, 1, 49);
		bag.addItem(treasure2);
		assertEquals(50, bag.getValue());

	}

	@Test
	public void testBagValueNestedBags() {
		Dungeon dungeon = new Dungeon(1, 1);
		Bag bag = new Bag(dungeon, 1, 1);
		Bag bag2 = new Bag(dungeon, 1, 1);
		Treasure treasure = new Treasure(dungeon, 1, 1, 1);
		bag.addItem(treasure);
		assertEquals(1, bag.getValue());

		Treasure treasure2 = new Treasure(dungeon, 1, 1, 49);
		bag2.addItem(treasure2);
		assertEquals(49, bag2.getValue());
		bag.addItem(bag2);
		assertEquals(50, bag.getValue());
	}

	@Test
	public void testBagSwordCount() {
		Dungeon dungeon = new Dungeon(1, 1);
		Bag bag = new Bag(dungeon, 1, 1);
		Sword sword1 = new Sword(dungeon, 1, 1);
		Sword sword2 = new Sword(dungeon, 1, 1);
		Sword sword3 = new Sword(dungeon, 1, 1);
		assertEquals(0, bag.countType(Sword.class));
		bag.addItem(sword1);
		assertEquals(1, bag.countType(Sword.class));
		bag.addItem(sword2);
		assertEquals(1, bag.countType(Sword.class));
		bag.addItem(sword3);
		assertEquals(1, bag.countType(Sword.class));
	}

	@Test
	public void testBagKeyCount() {
		Dungeon dungeon = new Dungeon(1, 1);
		Bag bag = new Bag(dungeon, 1, 1);
		Key key1 = new Key(dungeon, 1, 1, 1);
		Key key2 = new Key(dungeon, 1, 1, 2);
		Key key3 = new Key(dungeon, 1, 1, 3);

		assertEquals(0, bag.countType(Key.class));

		bag.addItem(key1);
		assertEquals(1, bag.countType(Key.class));

		bag.addItem(key2);
		assertEquals(1, bag.countType(Key.class));

		bag.addItem(key3);
		assertEquals(1, bag.countType(Key.class));

	}

	@Test
	public void testBagAddRemoveCount() {
		Dungeon dungeon = new Dungeon(1, 1);
		Bag bag = new Bag(dungeon, 1, 1);

		for (int i = 0; i < 10; i++) {
			bag.addItem(new Bomb(dungeon, 1, 1));
		}

		assertEquals(10, bag.countType(Bomb.class));

		bag.removeItem();

		assertEquals(9, bag.countType(Bomb.class));

		Key key1 = new Key(dungeon, 1, 1, 1);
		bag.addItem(key1);

		assertEquals(9, bag.countType(Bomb.class));
		assertEquals(1, bag.countType(Key.class));

	}

	@Test
	public void testBagEmpty() {
		Dungeon dungeon = new Dungeon(1, 1);
		Bag bag = new Bag(dungeon, 1, 1);

		assertEquals(null, bag.removeItem());

		bag.addItem(null);
		assertEquals(null, bag.removeItem());
		assertEquals(true, bag.remove());

	}

}
