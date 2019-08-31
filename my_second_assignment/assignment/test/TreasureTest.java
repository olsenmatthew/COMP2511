import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.items.treasure.Treasure;

public class TreasureTest {
	@Test
	public void testTreasureValue() {
		Dungeon dungeon = new Dungeon(1, 1);
		Treasure treasure1 = new Treasure(dungeon, 1, 1, 1);
		assertNotEquals(null, treasure1);
		assertEquals(1, treasure1.getValue());
		Treasure treasure2 = new Treasure(dungeon, 1, 1, 10);
		Treasure treasure3 = new Treasure(dungeon, 1, 1, 123);
		assertEquals(10, treasure2.getValue());
		assertEquals(123, treasure3.getValue());
	}

	@Test
	public void testTreasureRemoveInvalidValue() {
		Dungeon dungeon = new Dungeon(1, 1);

		Treasure treasure1 = new Treasure(dungeon, 1, 1, -1);
		Treasure treasure2 = new Treasure(dungeon, 1, 1, 0);
		Treasure treasure3 = new Treasure(dungeon, 1, 1, 1);

		assertEquals(true, treasure1.remove());
		assertEquals(true, treasure2.remove());
		assertEquals(false, treasure3.remove());
	}

}
