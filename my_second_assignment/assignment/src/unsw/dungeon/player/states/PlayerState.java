package unsw.dungeon.player.states;

import unsw.dungeon.player.Player;

/**
 * @author Matthew Olsen
 *
 */
public interface PlayerState {

	/**
	 * update states
	 */
	public void update();

	/**
	 * change to normal state
	 */
	public void changeToNormalState();

	/**
	 * change to powered up state based on player's state
	 */
	public void changeToPoweredUpState();

	/**
	 * @param player
	 */
	public void activate(Player player);

}
