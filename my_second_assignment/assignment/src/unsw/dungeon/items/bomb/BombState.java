package unsw.dungeon.items.bomb;

/**
 * Manages the state of the bomb
 */
public interface BombState {
	/**
	 * update state of bomb
	 */
	public void update();

	/**
	 * transition to unlit fuse bomb state
	 */
	public void changeToUnlitFuse();

	/**
	 * transition to long lit fuse state
	 */
	public void changeToLongLitFuse();

	/**
	 * transition to medium lit fuse state
	 */
	public void changeToMediumLitFuse();

	/**
	 * transition to short lit fuse state
	 */
	public void changeToShortLitFuse();

	/**
	 * transition to exploding state
	 */
	public void changeToExplodingState();
}
