package unsw.dungeon.NPC;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.player.Player;

public class King extends Destructible implements Runnable {

	public static int MOVEMENT_DELAY = 50;

	private BooleanProperty sad;

	/**
	 * create a King at position (x,y)
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public King(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
		this.sad = new SimpleBooleanProperty(true);
	}


	/**
	 * make the Y position of king +1, make king moves down
	 */
	public void moveDown() {
		this.y().set(y().get()+1);
	}

	/**
	 * calls aKingBegging() method in dungeon class, then set sad to false, then move
	 * @postCondition sad = false
	 */
	public void happy() {
		getDungeon().aKingBegging();
		sad.set(false);
		goAway();
	}
	
	/**
	 * return the state of sad variable
	 * @return BooleanProperty, the state of variable sad
	 */
	public BooleanProperty getFeeling() {
		return sad;
	}


	/**
	 * wait the player, then call the function happy()
	 */
	@Override
	public void run() {
		while(true) {
			if(!getDungeon().getEntities(this.getCoordinates(),Player.class).isEmpty()) {
				happy();
				break;
			}
		}
	}
	/**
	 * straight move down, then disappear
	 */
	private void goAway() {
		while (!sad.get()) {
			try {
				Thread.sleep(MOVEMENT_DELAY);
				moveDown();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			if(this.y().get() == 32) {
				this.hide();
				getDungeon().removeEntity(this);
				break;
			}
		}
	}



}
