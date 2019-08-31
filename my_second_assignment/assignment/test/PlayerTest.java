import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Coordinates;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.Switch;
import unsw.dungeon.Wall;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.items.treasure.Treasure;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.player.Player;

import unsw.dungeon.player.states.InvincibilityPotionState;
public class PlayerTest {
	@Test
	public void testLeftMovement() {
		
		
		Dungeon dungeon = new Dungeon(3, 3);
		Player player = new Player(dungeon, 1, 0, new StrongMoveStrategy());
		dungeon.setPlayer(player);

		assertEquals(new Coordinates(1, 0), player.getCoordinates());
		player.moveLeft();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());

		for (int i = 0; i < 5; i++) {
			assertEquals(new Coordinates(0, 0), player.getCoordinates());
			player.moveLeft();
		}
		
		
		player.setX(1);
		player.setY(0);
		assertEquals(new Coordinates(1, 0), player.getCoordinates());
		
		
		Wall wall = new Wall(0,0);
		dungeon.addEntity(wall);
		player.moveLeft();
		assertEquals(new Coordinates(1, 0), player.getCoordinates());
		
		dungeon.removeEntity(wall);
		player.setX(1);
		player.setY(0);
		
		Switch sw = new Switch(0,0);
		dungeon.addEntity(sw);
		player.moveLeft();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		dungeon.removeEntity(sw);
		player.setX(1);
		player.setY(0);
		
		Exit exit = new Exit(0,0);
		dungeon.addEntity(exit);
		player.moveLeft();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		dungeon.removeEntity(exit);
		player.setX(1);
		player.setY(0);
		
		Door door = new Door(0,0,0);
		dungeon.addEntity(door);
		player.moveLeft();
		assertEquals(new Coordinates(1, 0), player.getCoordinates());
		door.open();
		player.moveLeft();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		
		dungeon.removeEntity(door);
		player.setX(1);
		player.setY(0);
		
		Enemy enemy = new Enemy(dungeon, 0, 0, new StrongMoveStrategy());
		dungeon.addEntity(enemy);
		player.moveLeft();
		assertEquals(null,dungeon.getPlayer());
		
		
	}
	@Test
	public void testRightMoveMent() {
		
		//move right
		Dungeon dungeon = new Dungeon(3, 3);
		Player player = new Player(dungeon, 0, 0, new StrongMoveStrategy());
		dungeon.setPlayer(player);

		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		player.moveRight();
		assertEquals(new Coordinates(1, 0), player.getCoordinates());

		for (int i = 0; i < 5; i++) {
			player.moveRight();
		}
		assertEquals(new Coordinates(2, 0), player.getCoordinates());
		
		player.setX(0);
		player.setY(0);
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		
		Wall wall = new Wall(1,0);
		dungeon.addEntity(wall);
		player.moveRight();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		dungeon.removeEntity(wall);
		player.setX(0);
		player.setY(0);
		
		Switch sw = new Switch(1,0);
		dungeon.addEntity(sw);
		player.moveRight();
		assertEquals(new Coordinates(1, 0), player.getCoordinates());
		
		dungeon.removeEntity(sw);
		player.setX(0);
		player.setY(0);
		
		Exit exit = new Exit(1,0);
		dungeon.addEntity(exit);
		player.moveRight();
		assertEquals(new Coordinates(1, 0), player.getCoordinates());
		
		dungeon.removeEntity(exit);
		player.setX(0);
		player.setY(0);
		
		Door door = new Door(1,0,0);
		dungeon.addEntity(door);
		player.moveRight();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		door.open();
		player.moveRight();
		assertEquals(new Coordinates(1, 0), player.getCoordinates());
		
		
		dungeon.removeEntity(door);
		player.setX(0);
		player.setY(0);
		
		Enemy enemy = new Enemy(dungeon, 1, 0, new StrongMoveStrategy());
		dungeon.addEntity(enemy);
		player.moveRight();
		assertEquals(null,dungeon.getPlayer());
	}
	
	@Test
	public void testUpMoveMent() {
		
		//move up
		Dungeon dungeon = new Dungeon(3, 3);
		Player player = new Player(dungeon, 0, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);

		assertEquals(new Coordinates(0, 1), player.getCoordinates());
		player.moveUp();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());

		for (int i = 0; i < 5; i++) {
			assertEquals(new Coordinates(0, 0), player.getCoordinates());
			player.moveUp();
		}
		player.setX(0);
		player.setY(1);
		assertEquals(new Coordinates(0, 1), player.getCoordinates());
		
		
		Wall wall = new Wall(0,0);
		dungeon.addEntity(wall);
		player.moveUp();
		assertEquals(new Coordinates(0, 1), player.getCoordinates());
		
		dungeon.removeEntity(wall);
		player.setX(0);
		player.setY(1);
		
		Switch sw = new Switch(0,0);
		dungeon.addEntity(sw);
		player.moveUp();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		dungeon.removeEntity(sw);
		player.setX(0);
		player.setY(1);
		
		Exit exit = new Exit(0,0);
		dungeon.addEntity(exit);
		player.moveUp();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		dungeon.removeEntity(exit);
		player.setX(0);
		player.setY(1);
		
		Door door = new Door(0,0,0);
		dungeon.addEntity(door);
		player.moveUp();
		assertEquals(new Coordinates(0, 1), player.getCoordinates());
		door.open();
		player.moveUp();
		assertEquals(new Coordinates(0, 0), player.getCoordinates());
		
		
		dungeon.removeEntity(door);
		player.setX(0);
		player.setY(1);
		
		Enemy enemy = new Enemy(dungeon, 0, 0, new StrongMoveStrategy());
		dungeon.addEntity(enemy);
		player.moveUp();
		assertEquals(null,dungeon.getPlayer());
		
	}
	
	@Test
	public void testDownMoveMent() {
		
		//move down
		Dungeon dungeon = new Dungeon(3, 3);
		Player player = new Player(dungeon, 2, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);

		assertEquals(new Coordinates(2, 1), player.getCoordinates());
		player.moveDown();
		assertEquals(new Coordinates(2, 2), player.getCoordinates());

		for (int i = 0; i < 5; i++) {
			assertEquals(new Coordinates(2, 2), player.getCoordinates());
			player.moveDown();
		}
		player.setX(2);
		player.setY(1);
		assertEquals(new Coordinates(2, 1), player.getCoordinates());
		
		
		Wall wall = new Wall(2,2);
		dungeon.addEntity(wall);
		player.moveDown();
		assertEquals(new Coordinates(2, 1), player.getCoordinates());
		
		dungeon.removeEntity(wall);
		player.setX(2);
		player.setY(1);
		
		Switch sw = new Switch(2,2);
		dungeon.addEntity(sw);
		player.moveDown();
		assertEquals(new Coordinates(2, 2), player.getCoordinates());
		
		dungeon.removeEntity(sw);
		player.setX(2);
		player.setY(1);
		
		Exit exit = new Exit(2,2);
		dungeon.addEntity(exit);
		player.moveDown();
		assertEquals(new Coordinates(2, 2), player.getCoordinates());
		
		dungeon.removeEntity(exit);
		player.setX(2);
		player.setY(1);
		
		Door door = new Door(2,2,0);
		dungeon.addEntity(door);
		player.moveDown();
		assertEquals(new Coordinates(2, 1), player.getCoordinates());
		door.open();
		player.moveDown();
		assertEquals(new Coordinates(2, 2), player.getCoordinates());
		
		
		dungeon.removeEntity(door);
		player.setX(2);
		player.setY(1);
		
		Enemy enemy = new Enemy(dungeon, 2, 2, new StrongMoveStrategy());
		dungeon.addEntity(enemy);
		player.moveDown();
		assertEquals(null,dungeon.getPlayer());
		
	}
	@Test
	public void testPickUp() {
		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		Treasure treasure = new Treasure(dungeon,1, 1, 1);
		dungeon.addEntity(treasure);
		player.pickup();
		assertFalse(dungeon.getEntities(1, 1).contains(treasure));
	}
	@Test
	public void testDrop() {
		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 1, 1, new StrongMoveStrategy());
		dungeon.setPlayer(player);
		Treasure treasure = new Treasure(dungeon,1, 1, 1);
		dungeon.addEntity(treasure);
		player.pickup();
		assertFalse(dungeon.getEntities(1, 1).contains(treasure));
		player.drop();
		assertTrue(dungeon.getEntities(1, 1).contains(treasure));
	}
	
	
	
	

	
	
	
	
	
	
	
}
