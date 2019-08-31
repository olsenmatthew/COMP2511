package unsw.dungeon.items.bomb;

public class UnlitFuse implements BombState {
	
	private Bomb bomb;
	
	public UnlitFuse(Bomb bomb) {
		this.bomb = bomb;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS*.25) {
			changeToExplodingState();
		} else if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS*.5) {
			changeToShortLitFuse();
		} else  if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS*.75) {
			changeToMediumLitFuse();
		} else if (this.bomb.getSecondsLeft() < Bomb.START_SECONDS) {
			changeToLongLitFuse();
		}
	}

	@Override
	public void changeToUnlitFuse() {
		// Do Nothing (Same State)
	}

	@Override
	public void changeToLongLitFuse() {
		this.bomb.setBombState(new LongLitFuse(this.bomb));
	}

	@Override
	public void changeToMediumLitFuse() {
		this.bomb.setBombState(new MediumLitFuse(this.bomb));
	}

	@Override
	public void changeToShortLitFuse() {
		this.bomb.setBombState(new ShortLitFuse(this.bomb));
	}

	@Override
	public void changeToExplodingState() {
		this.bomb.setBombState(new Exploding(this.bomb));
	}

}
