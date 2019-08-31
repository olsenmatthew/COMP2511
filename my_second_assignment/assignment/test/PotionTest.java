import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.Player;
import unsw.dungeon.player.states.InvincibilityPotionState;

public class PotionTest {

	@Test
	public void testPlayerPickUpPotion() {
		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 1, 1));
		player.pickup();
		assertEquals(true, player.isPoweredUp());
	}

	@Test
	public void testInvinciblePlayerSurviveBomb() {
		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 1, 1));
		dungeon.addEntity(new Bomb(dungeon, 2, 1));
		player.pickup();
		player.moveRight();
		player.pickup();
		player.use();
		assertNotEquals(player, null);
		try {
			Thread.sleep((long) (Bomb.START_SECONDS * 1050));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNotEquals(player, null);
	}

	@Test
	public void testInvinciblePlayerKillEnemy() {

		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 1, 1));
		Enemy enemy = new Enemy(dungeon, 2, 1, new WeakMoveStrategy());
		dungeon.addEntity(enemy);

		player.pickup();
		player.use();
		player.moveRight();

		assertNotEquals(player, null);
		int i = 0;
		for (Entity e : dungeon.getEntities(player.getCoordinates())) {
			if (enemy.getClass().isInstance(e)) {
				i++;
			}
		}
		assertEquals(i, 0);

	}

	@Test
	public void testPowerUpReturnToNormalState() {

		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 1, 1));
		player.pickup();

		assertEquals(true, player.isPoweredUp());
		try {
			Thread.sleep((long) (InvincibilityPotionState.START_SECONDS * 1050));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(false, player.isPoweredUp());

	}

	@Test
	public void testMultiplePowerUpTime() {

		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 1, 1));
		player.pickup();

		assertEquals(true, player.isPoweredUp());
		try {
			Thread.sleep((long) (InvincibilityPotionState.START_SECONDS * 1050 / 2));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		 * Half the potion time has run out, test the player is still powered up
		 * 
		 * Now player picks up new potion, test half+1 second of the time to test if the
		 * new potion is being used.
		 */
		assertEquals(true, player.isPoweredUp());
		dungeon.addEntity(new InvincibilityPotionState(dungeon, 1, 1));
		player.pickup();
		assertEquals(true, player.isPoweredUp());
		/*
		 * New potion has been picked up, test that the player is powered up for more
		 * than the first potion's time limit
		 */
		try {
			Thread.sleep((long) ((InvincibilityPotionState.START_SECONDS * 1000 / 2)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(true, player.isPoweredUp());
		/*
		 * Test that the newest potion now runs out of time & that the potion times were
		 * not summed.
		 */
		try {
			Thread.sleep((long) (InvincibilityPotionState.START_SECONDS * 1050 / 2));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(false, player.isPoweredUp());

	}

}
