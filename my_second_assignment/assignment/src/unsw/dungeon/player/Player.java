package unsw.dungeon.player;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.NPC.Princess;
import unsw.dungeon.items.Item;
import unsw.dungeon.items.bag.Bag;
import unsw.dungeon.moveable.strategies.Movable;
import unsw.dungeon.player.states.NormalState;
import unsw.dungeon.player.states.PlayerState;

/**
 * The {@link Player} extends {@link Destructible}
 */
public class Player extends Destructible {

	/**
	 * player's bag
	 */
	private Bag bag;
	/**
	 * player's state
	 */
	private PlayerState playerState;
	/**
	 * player's movement strategy
	 */
	private Movable movementStrategy;
	/**
	 * true if player is using an item in bag, false otherwise
	 */
	private BooleanProperty using;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param strategy
	 */
	public Player(Dungeon dungeon, int x, int y, Movable strategy) {
		super(dungeon, x, y);
		this.bag = new Bag(getDungeon(), x, y);
		this.playerState = new NormalState(this);
		this.movementStrategy = strategy;
		this.using = new SimpleBooleanProperty(false);
	}

	/**
	 * pickup {@link Item}s and {@link PlayerState}s from dungeon
	 * 
	 * add items to bag
	 * 
	 * change player state accordingly base on player state
	 */
	public void pickup() {
		List<Entity> items = getDungeon().getEntities(getCoordinates(), Item.class);
		for (Entity i : items) {
			this.bag.addItem((Item) i);
		}
		List<Entity> powerUps = getDungeon().getEntities(getCoordinates(), PlayerState.class);
		for (Entity i : powerUps) {
			setPlayerState((PlayerState) i);
			getDungeon().removeEntity(i);
			break;
		}
		getDungeon().trackGoal();
	}

	/**
	 * drop the selected item onto the square the player resides on
	 */
	public void drop() {
		Entity item = (Entity) this.bag.removeItem();
		if (item != null) {
			getDungeon().addEntity(item);
		}
		getDungeon().trackGoal();
	}

	/**
	 * switch selected item in bag
	 */
	public void switchItem() {
		this.bag.switchItem();
	}

	/**
	 * use selected item in bag
	 */
	public void use() {
		if (this.bag.use())
			using.set(true);
		getDungeon().trackGoal();
		using.set(false);
	}

	/**
	 * rescue princess if she is adjacent to the player
	 */
	public void rescue() {
		for (Entity e : getDungeon().getAdjacentEntities(getCoordinates())) {
			if (e instanceof Princess) {
				((Princess) e).savePrincess();
			}
		}
	}

	/**
	 * @return getValue of the bag
	 */
	public int getValue() {
		return bag.getValue();
	}

	/**
	 * use movement strategy to move up
	 */
	public void moveUp() {
		movementStrategy.moveUp(getDungeon(), this);
	}

	/**
	 * use movementStrategy to move down
	 */
	public void moveDown() {
		movementStrategy.moveDown(getDungeon(), this);
	}

	/**
	 * use movement strategy to move left
	 */
	public void moveLeft() {
		movementStrategy.moveLeft(getDungeon(), this);
	}

	/**
	 * use movement strategy to move right
	 */
	public void moveRight() {
		movementStrategy.moveRight(getDungeon(), this);
	}

	/*
	 * set Y coordinates to equal player's Y coordinates
	 * 
	 * @see unsw.dungeon.Entity#setY(int)
	 */
	@Override
	public void setY(int y) {
		this.bag.setY(y);
		super.setY(y);
	}

	/*
	 * set X coordinates to equal player's X coordinates
	 * 
	 * @see unsw.dungeon.Entity#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.bag.setX(x);
		super.setX(x);
	}

	/**
	 * @param playerState
	 * 
	 *                    set player state and activate player state
	 */
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
		this.playerState.activate(this);
	}

	/**
	 * @return player state
	 */
	public PlayerState getPlayerState() {
		return this.playerState;
	}

	/**
	 * @return true if player is not in normal state, false otherwise
	 */
	public boolean isPoweredUp() {
		return !NormalState.class.isInstance(playerState);
	}

	/**
	 * @return true if player is using an item, false otherwise
	 */
	public BooleanProperty getUsingState() {
		return using;
	}

	/**
	 * @return map of bag's items type and count
	 */
	public MapProperty<String, Integer> getItems() {
		return bag.getItems();
	}

	/**
	 * @return selected item type in bag
	 */
	public StringProperty getSelected() {
		return bag.getSelected();
	}

}
