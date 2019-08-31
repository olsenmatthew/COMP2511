package unsw.dungeon.items.bomb;

/**
 * {@link LongLitFuse} implements {@link BombState}
 */
public class ShortLitFuse implements BombState {

	/**
	 * instance of bomb whos state we will manage
	 */
	private Bomb bomb;

	/**
	 * @param bomb
	 */
	public ShortLitFuse(Bomb bomb) {
		this.bomb = bomb;
	}

	/*
	 * update state of bomb: can change to ExplodingState
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS * .25) {
			changeToExplodingState();
		}
	}

	/*
	 * can't change to unlit fuse
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToUnlitFuse()
	 */
	@Override
	public void changeToUnlitFuse() {
		// TODO Auto-generated method stub
	}

	/*
	 * can't change to long lit fuse
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToLongLitFuse()
	 */
	@Override
	public void changeToLongLitFuse() {
		// TODO Auto-generated method stub
	}

	/*
	 * can't change to medium lit fuse
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToMediumLitFuse()
	 */
	@Override
	public void changeToMediumLitFuse() {
		// Do Nothing (Can't go to {@link MediumLitFuse})
	}

	/*
	 * already short lit fuse
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToShortLitFuse()
	 */
	@Override
	public void changeToShortLitFuse() {
		// Do Nothing (Same State)
	}

	/*
	 * transition to exploding state
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToExplodingState()
	 */
	@Override
	public void changeToExplodingState() {
		this.bomb.setBombState(new Exploding(this.bomb));
	}

}
