package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.NPC.King;
import unsw.dungeon.NPC.Princess;
import unsw.dungeon.enemy.Bomber;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.enemy.Goblin;
import unsw.dungeon.items.bomb.Bomb;
import unsw.dungeon.items.key.Key;
import unsw.dungeon.items.sword.Sword;
import unsw.dungeon.items.treasure.Treasure;
import unsw.dungeon.moveable.Boulder;
import unsw.dungeon.player.Player;
import unsw.dungeon.player.states.InvincibilityPotionState;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

	private List<ImageView> entities;

	// Images
	private Image playerImage;
	private Image wallImage;

	private Image exitImage;
	private Image enemyImage;

	private Image boulderImage;
	private Image switchImage;
	private Image treasureImage;
	private Image swordImage;
	private Image doorImage;
	private Image keyImage;
	private Image openDoorImage;
	private Image unlitBombImage;
	private Image princessImage;
	private Image longBombImage;
	private Image mediumBombImage;
	private Image shortBombImage;
	private Image explodeBombImage;
	private Image potionImage;
	private Image waveImage;
	private Image bomberImage;
//	private Image rockMonsterImage;
//	private Image swordThiefImage;
	private Image goblinImage;
	private Image magmaImage;
	private Image kingImage;

	/**
	 * set private variable link with image, create empty list of entities
	 * 
	 * @param filename, the file name of loader loads
	 * @throws FileNotFoundException
	 */
	public DungeonControllerLoader(String filename) throws FileNotFoundException {
		super(filename);
		entities = new ArrayList<>();
		playerImage = new Image("/human_new.png");
		wallImage = new Image("/brick_brown_0.png");

		exitImage = new Image("/exit.png");
		enemyImage = new Image("/deep_elf_master_archer.png");

		boulderImage = new Image("/boulder.png");
		switchImage = new Image("/pressure_plate.png");

		treasureImage = new Image("/gold_pile.png");
		swordImage = new Image("/greatsword_1_new.png");
		doorImage = new Image("/closed_door.png");
		keyImage = new Image("/key.png");
		openDoorImage = new Image("/open_door.png");
		unlitBombImage = new Image("bomb_unlit.png");
		princessImage = new Image("/princess.png");
		longBombImage = new Image("bomb_lit_1.png");
		mediumBombImage = new Image("bomb_lit_2.png");
		shortBombImage = new Image("bomb_lit_3.png");
		explodeBombImage = new Image("bomb_lit_4.png");
		potionImage = new Image("brilliant_blue_new.png");
		waveImage = new Image("/wave.png");
		bomberImage = new Image("skeleton_bomber.png");
//		rockMonsterImage = new Image("rock_monster.png");
//		swordThiefImage = new Image("sword_thief.png");
		goblinImage = new Image("goblin.png");
		magmaImage = new Image("magma.png");
		kingImage = new Image("king.png");
	}

	/**
	 * load the player entity, create variable imageview, then calls addEntity() to
	 * deal with the bind, calls the method useEffect to add effect while player
	 * using item
	 */
	@Override
	public void onLoad(Entity player) {
		ImageView view = new ImageView(playerImage);
		addEntity(player, view);
		useEffect(player, view);
	}

	/**
	 * load the wall entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(Wall wall) {
		ImageView view = new ImageView(wallImage);
		addEntity(wall, view);
	}

	/**
	 * load the magma entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(Magma magma) {
		ImageView view = new ImageView(magmaImage);
		addEntity(magma, view);
	}

	/**
	 * load the exit entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(Exit exit) {
		ImageView view = new ImageView(exitImage);
		addEntity(exit, view);
	}

	/**
	 * load the enemy entity, create variable imageview, then calls addEntity() to
	 * deal with the bind, then create a thread for enemy and make it start, also
	 * create a listener to make the thread stop
	 */
	@Override
	public void onLoad(Enemy enemy) {
		ImageView view = new ImageView(enemyImage);
		addEntity(enemy, view);

		Thread thread = new Thread(enemy);
		thread.start();
		enemy.getAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					thread.interrupt();
				}

			}
		});
	}

	/**
	 * load the goblin entity, create variable imageview, then calls addEntity() to
	 * deal with the bind, then create a thread for goblin and make it start, also
	 * create a listener to make the thread stop
	 */
	@Override
	public void onLoad(Goblin goblin) {
		ImageView view = new ImageView(goblinImage);
		addEntity(goblin, view);

		Thread thread = new Thread(goblin);
		thread.start();
		goblin.getAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					thread.interrupt();
				}

			}
		});
	}

	/**
	 * load the princess entity, create variable imageview, then calls addEntity()
	 * to deal with the bind, then create a thread for princess and make it start,
	 * also create a listener to make the thread stop
	 */
	@Override
	public void onLoad(Princess princess) {
		ImageView view = new ImageView(princessImage);
		addEntity(princess, view);

		Thread thread = new Thread(princess);
		thread.start();

		princess.getAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					thread.interrupt();
				}
			}
		});

	}

	/**
	 * load the bomber entity, create variable imageview, then calls addEntity() to
	 * deal with the bind, give the bomber a bomb and set the condition to let it
	 * fires the bomb, then create a thread for bomb and make it start, also create
	 * a listener to make the thread stop
	 */
	@Override
	public void onLoad(Bomber bomber) {
		ImageView view = new ImageView(bomberImage);
		addEntity(bomber, view);

		Thread thread = new Thread(bomber);
		thread.start();

		List<Bomb> inventory = new ArrayList<Bomb>();
		for (int i = 0; i < 10; i++) {
			Bomb bomb = new Bomb(bomber.getDungeon(), bomber.getX(), bomber.getY());
			onLoad(bomb);
			inventory.add(bomb);
			bomber.getDungeon().removeEntity(bomb);
		}

		bomber.getAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					thread.interrupt();
				}
			}
		});

		bomber.spawnBomb().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue && bomber.getAlive().get()) {
					Bomb bomb = null;
					for (Bomb b : inventory) {
						if (b != null && b.getSecondsLeft() == Bomb.START_SECONDS) {
							bomb = b;
							break;
						}
					}
					if (bomb != null) {
						bomber.addBomb(bomb);
					}
				}
			}
		});
	}

	/**
	 * load the boulder entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(Boulder boulder) {
		ImageView view = new ImageView(boulderImage);
		addEntity(boulder, view);
	}

	/**
	 * load the switch entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(Switch s) {
		ImageView view = new ImageView(switchImage);
		addEntity(s, view);
	}

	/**
	 * load the treasure entity, create variable imageview, then calls addEntity()
	 * to deal with the bind,
	 */
	@Override
	public void onLoad(Treasure treasure) {
		ImageView view = new ImageView(treasureImage);
		addEntity(treasure, view);
	}

	/**
	 * load the sword entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(Sword sword) {
		ImageView view = new ImageView(swordImage);
		addEntity(sword, view);
	}

	/**
	 * load the door entity, create variable imageview, then calls trackState() to
	 * track its state for open/close, then add it to entities
	 */
	@Override
	public void onLoad(Door door) {
		ImageView view_close = new ImageView(doorImage);
		trackState(door, view_close);
		entities.add(view_close);
	}

	/**
	 * load the key entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(Key key) {
		ImageView view = new ImageView(keyImage);
		addEntity(key, view);
	}

	/**
	 * load the bomb entity, create variable imageview, then calls addEntity() to
	 * deal with the bind, and calls trackState() to track the state for the bomb
	 */
	@Override
	public void onLoad(Bomb bomb) {
		ImageView view = new ImageView(unlitBombImage);
		addEntity(bomb, view);
		trackState(bomb, view);
	}

	/**
	 * load the potion entity, create variable imageview, then calls addEntity() to
	 * deal with the bind,
	 */
	@Override
	public void onLoad(InvincibilityPotionState potion) {
		ImageView view = new ImageView(potionImage);
		addEntity(potion, view);
	}

	/**
	 * load the king entity, create variable imageview, then calls addEntity() to
	 * deal with the bind, then create a thread for king and make it start, also
	 * create a listener to make the thread stop
	 */
	@Override
	public void onLoad(King king) {
		ImageView view = new ImageView(kingImage);
		addEntity(king, view);
		Thread thread = new Thread(king);
		thread.start();
		king.getFeeling().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					thread.interrupt();
				}

			}
		});
	}

	/**
	 * add the entity to variable entities, then calls trackPosition to bind the
	 * image with the position of entity
	 * 
	 * @param entity, different entity
	 * @param view, imageview for the entity
	 */
	private void addEntity(Entity entity, ImageView view) {
		trackPosition(entity, view);
		entities.add(view);
	}

	/**
	 * Set a node in a GridPane to have its position track the position of an entity
	 * in the dungeon.
	 *
	 * By connecting the model with the view in this way, the model requires no
	 * knowledge of the view and changes to the position of entities in the model
	 * will automatically be reflected in the view.
	 * 
	 * @param entity
	 * @param node
	 */
	private void trackPosition(Entity entity, Node node) {
		GridPane.setColumnIndex(node, entity.getX());
		GridPane.setRowIndex(node, entity.getY());
		entity.x().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				GridPane.setColumnIndex(node, newValue.intValue());
			}
		});
		entity.y().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				GridPane.setRowIndex(node, newValue.intValue());
			}
		});
		entity.isVisible().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				node.setVisible(newValue.booleanValue());
			}

		});

	}

	/**
	 * Set a node in a GridPane to have its state track the state of an entity in
	 * the dungeon.
	 *
	 * By connecting the model with the view in this way, the model requires no
	 * knowledge of the view and changes to the position of entities in the model
	 * will automatically be reflected in the view.
	 * 
	 * @param entity
	 * @param node
	 */
	private void trackState(Entity entity, Node node) {
		GridPane.setColumnIndex(node, entity.getX());
		GridPane.setRowIndex(node, entity.getY());
		if (entity instanceof Door) {
			((Door) entity).isOpen().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						((ImageView) node).setImage(openDoorImage);
					}
				}
			});
		} else {
			((Bomb) entity).getBombStateProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.compareTo("ShortLitFuse") == 0) {
						((ImageView) node).setImage(shortBombImage);
					} else if (newValue.compareTo("MediumLitFuse") == 0) {
						((ImageView) node).setImage(mediumBombImage);
					} else if (newValue.compareTo("LongLitFuse") == 0) {
						((ImageView) node).setImage(longBombImage);
					} else if (newValue.compareTo("Exploding") == 0) {
						((ImageView) node).setImage(explodeBombImage);
					}
				}
			});
		}
	}

	/**
	 * add the listener for player entity to have a special effect when player calls
	 * use()
	 * 
	 * so that player have no knowledge with frontend, it will deal with the image
	 * switch itself
	 * 
	 * @param entity
	 * @param node
	 */
	private void useEffect(Entity entity, Node node) {
		((Player) entity).getUsingState().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					((ImageView) node).setImage(waveImage);

					TimerTask task = new TimerTask() {
						public void run() {
							((ImageView) node).setImage(playerImage);
						}
					};
					Timer timer = new Timer("Timer");

					long delay = 100;
					timer.schedule(task, delay);
				}
			}
		});
	}

	/**
	 * Create a controller that can be attached to the DungeonView with all the
	 * loaded entities.
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public DungeonController loadController() throws FileNotFoundException {
		return new DungeonController(load(), entities);
	}

}
