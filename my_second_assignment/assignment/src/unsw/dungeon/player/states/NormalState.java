package unsw.dungeon.player.states;

import unsw.dungeon.player.Player;

/**
 * @author Matthew Olsen
 *
 */
public class NormalState implements PlayerState {

	/**
	 * 
	 */
	private Player player;

	/**
	 * @param player
	 */
	public NormalState(Player player) {
		this.player = player;
	}

	/*
	 * update, change to powered up state if player is powered up
	 * 
	 * @see unsw.dungeon.player.states.PlayerState#update()
	 */
	@Override
	public void update() {
		if (this.player.isPoweredUp()) {
			changeToPoweredUpState();
		}
	}

	/*
	 * already normal state, do nothing
	 * 
	 * @see unsw.dungeon.player.states.PlayerState#changeToNormalState()
	 */
	@Override
	public void changeToNormalState() {

	}

	/*
	 * activate player state
	 * 
	 * @see unsw.dungeon.player.states.PlayerState#changeToPoweredUpState()
	 */
	@Override
	public void changeToPoweredUpState() {
		player.getPlayerState().activate(player);
	}

	/*
	 * update state when activated
	 * 
	 * @see
	 * unsw.dungeon.player.states.PlayerState#activate(unsw.dungeon.player.Player)
	 */
	@Override
	public void activate(Player player) {
		update();

	}

}
