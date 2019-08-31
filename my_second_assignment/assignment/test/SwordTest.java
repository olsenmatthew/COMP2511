import static org.junit.Assert.assertEquals;

import org.junit.Test;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.items.sword.Sword;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;

public class SwordTest {
	@Test
	public void testSwordKillEnemys() {

		Dungeon dungeon = new Dungeon(2, 1);

		int x = 0;
		for (x = 0; x < 6; x++) {
			dungeon.addEntity(new Enemy(dungeon, 2, 1, new WeakMoveStrategy()));
		}

		Sword sword = new Sword(dungeon, 1, 1);
		dungeon.addEntity(sword);

		for (int i = 0; i < 5; i++) {
			/*
			 * test enemy count before player uses sword
			 */
			int enemyCount = 0;
			for (Entity e : dungeon.getEntities(new Coordinates(2, 1))) {
				if (e.getClass().equals(Enemy.class)) {
					enemyCount++;
				}
			}
			assertEquals(enemyCount, x - i);

			/*
			 * use sword, should kill exactly one enemy
			 */
			sword.use();
			/*
			 * test the the use of the sword killed exactly one enemy
			 */
			int aftermath = 0;
			for (Entity e : dungeon.getEntities(new Coordinates(2, 1))) {
				if (e.getClass().equals(Enemy.class)) {
					aftermath++;
				}
			}
			assertEquals(aftermath + 1, x - i);

		}

		/*
		 * test that the sword is out of hit and should be removed
		 */
		assertEquals(true, sword.remove());
	}

}
