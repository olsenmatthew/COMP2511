package unsw.dungeon.items.bomb;

/**
 * {@link LongLitFuse} implements {@link BombState}
 */
public class LongLitFuse implements BombState {

	/**
	 * instance of bomb who's state we will manage
	 */
	private Bomb bomb;

	/**
	 * @param bomb
	 */
	public LongLitFuse(Bomb bomb) {
		this.bomb = bomb;
	}

	/*
	 * update state of bomb: can change to changeToExplodingState(),
	 * changeToShortLitFuse(), changeToMediumLitFuse()
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#update()
	 */
	@Override
	public void update() {
		if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS * .25) {
			changeToExplodingState();
		} else if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS * .5) {
			changeToShortLitFuse();
		} else if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS * .75) {
			changeToMediumLitFuse();
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
	 * already long lit fuse
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToLongLitFuse()
	 */
	@Override
	public void changeToLongLitFuse() {
		// TODO Auto-generated method stub
	}

	/*
	 * transition to medium lit fuse state
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToMediumLitFuse()
	 */
	@Override
	public void changeToMediumLitFuse() {
		this.bomb.setBombState(new MediumLitFuse(this.bomb));
	}

	/*
	 * transition to short lit fuse state
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToShortLitFuse()
	 */
	@Override
	public void changeToShortLitFuse() {
		this.bomb.setBombState(new ShortLitFuse(this.bomb));
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
