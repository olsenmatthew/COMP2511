package unsw.dungeon.items.bomb;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.WeakMortal;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.player.Player;

/**
 * {@link Exploding} implements {@link BombState}
 */
public class Exploding implements BombState {

	/**
	 * instance of bomb whose state we will manage
	 */
	private Bomb bomb;

	/**
	 * @param bomb
	 */
	public Exploding(Bomb bomb) {
		this.bomb = bomb;
		this.bomb.show();
	}

	/*
	 * update state of bomb, when there are 0 seconds left explode this bomb
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#update()
	 */
	@Override
	public void update() {
		if (this.bomb.getSecondsLeft() <= 0) {
			explode();
		}
	}

	/*
	 * can't transition back to to unlit fuse state
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToUnlitFuse()
	 */
	@Override
	public void changeToUnlitFuse() {
		// TODO Auto-generated method stub
	}

	/*
	 * can't transition back to to long lit fuse state
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToLongLitFuse()
	 */
	@Override
	public void changeToLongLitFuse() {
		// TODO Auto-generated method stub
	}

	/*
	 * can't transition back to to medium lit fuse state
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToMediumLitFuse()
	 */
	@Override
	public void changeToMediumLitFuse() {
		// TODO Auto-generated method stub
	}

	/*
	 * can't transition back to to short lit fuse state
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToShortLitFuse()
	 */
	@Override
	public void changeToShortLitFuse() {
		// TODO Auto-generated method stub
	}

	/*
	 * do nothing, we are already exploding
	 * 
	 * @see unsw.dungeon.items.bomb.BombState#changeToExplodingState()
	 */
	@Override
	public void changeToExplodingState() {
		// TODO Auto-generated method stub
	}

	/**
	 * explode bomb, hide bomb, init list of classes which we can destroy, remove
	 * enemy from this coordinates and adjacent
	 */
	private void explode() {
		bomb.hide();
		List<Class<?>> canDestroy = new ArrayList<Class<?>>();
		canDestroy.add(Player.class);
		canDestroy.add(Boulder.class);
		canDestroy.add(WeakMortal.class);
		bomb.getDungeon().removeCoordinatesAndAdjacentEntities(bomb.getCoordinates(), canDestroy);
		bomb.getDungeon().removeEntity(bomb);
	}

}
