package unsw.dungeon.items.bomb;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.items.Item;

/**
 * {@link Bomb} extends {@link Bomb}, {@link Bomb} is a leaf item in
 * {@link Item}'s composite pattern
 */
public class Bomb extends Item {
	/**
	 * milliseconds in second
	 */
	public static final int SECOND = 1000;
	/**
	 * starting seconds for bombs when unlit
	 */
	public static final double START_SECONDS = 2.5;
	/**
	 * state of bomb
	 */
	private BombState bombState;
	/**
	 * string representation of bomb state
	 */
	private StringProperty bombStateProperty;
	/**
	 * time for bomb to count down when lit
	 */
	private Timer timer;
	/**
	 * number of seconds left in bomb before exploding
	 */
	private double secondsLeft;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Bomb(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
		this.bombState = new UnlitFuse(this);
		this.bombStateProperty = new SimpleStringProperty("UnlitFuse");
		this.timer = new Timer();
		this.secondsLeft = Bomb.START_SECONDS;
	}

	/**
	 * @param bombState
	 */
	public void setBombState(BombState bombState) {
		this.bombState = bombState;
	}

	/*
	 * start count down(); (light bomb)
	 * 
	 * @see unsw.dungeon.items.Item#use()
	 * 
	 * @return true if secondsLeft > 0
	 */
	@Override
	public boolean use() {
		countdown();
		return this.secondsLeft > 0;
	}

	/**
	 * @return seconds left before bomb explodes, double
	 */
	public double getSecondsLeft() {
		return this.secondsLeft;
	}

	/**
	 * start count down and run at a timer at a fixed schedule
	 */
	private void countdown() {
		int period = 500;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timerManager();
			}
		}, 0, period);
	}

	/**
	 * decrement seconds left, update bomb state, update bomb state property, cancel
	 * time if second left is less than or equal to 0
	 */
	private void timerManager() {
		this.secondsLeft -= 0.5; // Bomb.START_SECONDS * .25;
		this.bombState.update();
		updateBombStateProperty();
		if (this.secondsLeft <= 0) {
			timer.cancel();
		}
		this.bombState.update();
		updateBombStateProperty();
	}

	/*
	 * @see unsw.dungeon.items.Item#remove()
	 * 
	 * @return true if secondsLeft is less than or equal to 0, false otherwise
	 */
	@Override
	public boolean remove() {
		return secondsLeft <= 0;
	}

	/**
	 * change the bombStateProperty accordingly base upon the state of the bomb
	 */
	public void updateBombStateProperty() {
		if (bombState instanceof UnlitFuse) {
			bombStateProperty.set("UnlitFuse");
		} else if (bombState instanceof ShortLitFuse) {
			bombStateProperty.set("ShortLitFuse");
		} else if (bombState instanceof MediumLitFuse) {
			bombStateProperty.set("MediumLitFuse");
		} else if (bombState instanceof LongLitFuse) {
			bombStateProperty.set("LongLitFuse");
		} else {
			bombStateProperty.set("Exploding");
		}
	}

	/**
	 * @return bombStateProperty
	 */
	public StringProperty getBombStateProperty() {
		return this.bombStateProperty;
	}
}
