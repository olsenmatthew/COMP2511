import static org.junit.Assert.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Switch;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.moveable.strategies.LargeMoveStrategy;
import unsw.dungeon.moveable.strategies.StrongMoveStrategy;
import unsw.dungeon.moveable.strategies.WeakMoveStrategy;
import unsw.dungeon.player.*;

public class SwitchTest {
	
	@Test
	public void testSwitchCanCoexist() {
		Dungeon dungeon = new Dungeon(2, 2);
		Switch s = new Switch(0,0);
		Player p = new Player(dungeon, 1,0,new StrongMoveStrategy());
		dungeon.setPlayer(p);
		p.moveLeft();
		assertEquals(p.getCoordinates(), s.getCoordinates());
		Enemy e = new Enemy(dungeon, 0,1, new WeakMoveStrategy());
		e.moveUp();
		assertEquals(e.getCoordinates(), s.getCoordinates());
		p.moveRight();
		e.moveRight();
		Boulder b = new Boulder(dungeon, 0,1,new LargeMoveStrategy());
		b.moveUp();
		assertEquals(b.getCoordinates(), s.getCoordinates());
	}
	
	@Test
	public void testBoulderTriggerSwitch() {
		Dungeon dungeon = new Dungeon(2, 2);
		Switch s = new Switch(0,0);
		dungeon.addEntity(s);
		
		Boulder b1 = new Boulder(dungeon, 0,1,new LargeMoveStrategy());
		dungeon.addEntity(b1);
		dungeon.checkStatus();
		assertEquals(s.getTriggeredStatus(), false);
		
		Boulder b2 = new Boulder(dungeon, 0,0,new LargeMoveStrategy());
		dungeon.addEntity(b2);
		dungeon.checkStatus();
		assertEquals(s.getTriggeredStatus(), true);
		
		dungeon.removeEntity(b1);
		dungeon.removeEntity(b2);
		
		Player p = new Player(dungeon, 1,0,new StrongMoveStrategy());
		dungeon.setPlayer(p);
		dungeon.checkStatus();
		assertEquals(s.getTriggeredStatus(), false);
		
		Enemy e = new Enemy(dungeon, 0,1, new WeakMoveStrategy());
		dungeon.addEntity(e);
		dungeon.checkStatus();
		assertEquals(s.getTriggeredStatus(), false);
	}
}
