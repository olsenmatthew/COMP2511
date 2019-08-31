package unsw.dungeon.player.states;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import unsw.dungeon.Destructible;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enemy.WeakMortalEnemy;
import unsw.dungeon.player.Player;

/**
 * @author Matthew Olsen
 *
 */
public class InvincibilityPotionState extends Destructible implements PlayerState {
	/**
	 * milliseconds in second
	 */
	public static int SECOND = 1000;
	/**
	 * length of potion state
	 */
	public static int START_SECONDS = 10;
	/**
	 * instance of player who's state this will manage
	 */
	private Player player;
	/**
	 * timer to countdown to 0 from START_SECONDS
	 */
	private Timer timer;
	/**
	 * number of seconds left
	 */
	private int secondsLeft;
	/**
	 * change listener for player's location
	 */
	private ChangeListener<Number> listener;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public InvincibilityPotionState(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
		timer = new Timer();
		player = null;
		secondsLeft = 0;
	}

	/**
	 * create fixed schedule timer and timer task to count down at the given period
	 */
	private void countdown() {
		if (timer != null) {
			int period = InvincibilityPotionState.SECOND;
			try {
				timer.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						timerManager();
					}
				}, 0, period);
			} catch (IllegalStateException e) {
				System.err.println(e);
				System.err.println(e.getStackTrace()[0].getLineNumber());
				System.err.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
			}
		}
	}

	/**
	 * @param player set player's state to invincible, add listener's to player's
	 *               location
	 */
	private void startInvincibility(Player player) {
		listener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				killEnemiesOnContact();
			}
		};
		if (player != null && listener != null) {
			player.x().addListener(listener);
			player.y().addListener(listener);
		}
	}

	/**
	 * 
	 */
	private void killEnemiesOnContact() {
		for (Entity e : player.getDungeon().getEntities(player.getCoordinates(), WeakMortalEnemy.class)) {
			getDungeon().removeEntity(e);
			((WeakMortalEnemy) e).kill();
		}
	}

	/*
	 * activate powerup
	 * 
	 * @see
	 * unsw.dungeon.player.states.PlayerState#activate(unsw.dungeon.player.Player)
	 */
	@Override
	public void activate(Player player) {
		if (player != null) {
			this.player = player;
			if (this.secondsLeft <= 0) {
				this.secondsLeft = InvincibilityPotionState.START_SECONDS;
				countdown();
				startInvincibility(player);
			}
		}
	}

	/**
	 * decrement the seconds left
	 * 
	 * if seconds left is <=0 cancel and purge timer
	 * 
	 * call update if seconds left >= 0
	 */
	private void timerManager() {
		this.secondsLeft -= 1;
		if (secondsLeft <= 0 && timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
		if (secondsLeft >= 0) {
			update();
		}
	}

	/*
	 * change to normal state or new powered up state
	 * 
	 * @see unsw.dungeon.player.states.PlayerState#update()
	 */
	@Override
	public void update() {
		System.out.println(this + " " + secondsLeft);
		if (player.isPoweredUp() && !player.getPlayerState().equals(this)) {
			changeToPoweredUpState();
		} else if (secondsLeft <= 0) {
			changeToNormalState();
		}
	}

	/**
	 * remove player coordinate listeners, hide self
	 */
	private void endInvincibility() {
		try {
			if (listener != null) {
				player.x().removeListener(listener);
				player.y().removeListener(listener);
			}
			secondsLeft = 0;
			hide();
			timerManager();
		} catch (Exception e) {
			System.err.println(e);
			System.err.println(e.getStackTrace()[0].getLineNumber());
		}
	}

	/*
	 * end invincibility and then change to normal state
	 * 
	 * @see unsw.dungeon.player.states.PlayerState#changeToNormalState()
	 */
	@Override
	public void changeToNormalState() {
		endInvincibility();
		player.setPlayerState(new NormalState(player));
	}

	/*
	 * end this invincibility function and then activate player's new state
	 * 
	 * @see unsw.dungeon.player.states.PlayerState#changeToPoweredUpState()
	 */
	@Override
	public void changeToPoweredUpState() {
		endInvincibility();
		player.getPlayerState().activate(player);
	}

}
