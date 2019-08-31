package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController {
	@FXML
	private Button playButton;
	@FXML
	private Button boulderButton;
	@FXML
	private Button advanceButton;
	@FXML
	private Button lavaWorldButton;
	@FXML
	private Button bombsAwayButton;
	@FXML
	private Button exitButton;
	@FXML
	private Pane pane;

	private Stage stage;
	private int level;

	/**
	 * initialize the stage, set the current level for playing to be 1
	 * 
	 * @param stage
	 */
	public MenuController(Stage stage) {
		this.stage = stage;
		this.stage.setFullScreen(true);
		this.level = 1;
	}

	/**
	 * calls nextLevel when pressed PlayButton
	 * 
	 * @throws IOException
	 */
	@FXML
	public void handlePlayButton() throws IOException {
		nextLevel();
	}

	/**
	 * load level1-4 .json file, add a listener of controller state, if state
	 * changes, change the stage, eg.gameover(to gameover menu), win(to gameover
	 * menu), or increment level then next level(still in dungeon controller).
	 * 
	 * @throws IOException
	 */
	private void nextLevel() throws IOException {
		System.out.println(pane);
		String levelName = "level_" + level + ".json";
		stage.setTitle("Dungeon");

		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(levelName);

		DungeonController controller = dungeonLoader.loadController();
		controller.getState().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("You win!")) {
					try {
						if (level < 5) {
							level++;
							nextLevel();
						} else {
							endGame(newValue);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (newValue.equals("You dead!")) {
					try {
						endGame(newValue);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (newValue.equals("retry")) {
					try {
						nextLevel();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (newValue.equals("restart")) {
					try {
						restart();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (newValue.equals("beg")) {
					try {
						beg();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
		gameLoader.setController(controller);
		Parent root = gameLoader.load();
		Scene scene = new Scene(root);
		root.requestFocus();
		setFullscreen(scene);
		stage.show();
	}

	/**
	 * access level_2 map directly
	 * 
	 * @throws IOException
	 */
	@FXML
	public void handleBoulderButton() throws IOException {
		loadDungeon("level_2.json");
	}

	@FXML
	public void handleLavaWorldButton() throws IOException {
		loadDungeon("lava_world.json");
	}

	/**
	 * access level_3 map directly
	 * 
	 * @throws IOException
	 */
	@FXML
	public void handleAdvanceButton() throws IOException {
		loadDungeon("rescue_mission.json");
	}

	/**
	 * access level_4 map directly
	 * 
	 * @throws IOException
	 */
	@FXML
	public void handleBombsAwayButton() throws IOException {
		loadDungeon("bombs_away.json");
	}

	/**
	 * close the application
	 */
	@FXML
	public void handleExitButton() {
		System.exit(0);
	}

	/**
	 * go to gameover menu according to the state, parse the state to the gameover
	 * controller
	 * 
	 * @param state
	 * @throws IOException
	 */
	public void endGame(String state) throws IOException {
		try {
			stage.setTitle("Game Over");
			FXMLLoader endLoader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
			GameOverController endcontroller = new GameOverController(state, stage);
			endLoader.setController(endcontroller);
			Parent root = endLoader.load();
			Scene scene = new Scene(root);
			root.requestFocus();
			setFullscreen(scene);
			endcontroller.setBackground();
			stage.show();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * set the background image for menu
	 */
	public void setBackground() {
		File file = new File(System.getProperty("user.dir").toString() + "/images/bomber_wallpaper.png");
		BackgroundImage background = new BackgroundImage(new Image(file.toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background b = new Background(background);
		pane.setBackground(b);
	}

	/**
	 * return to the main memu
	 * 
	 * @throws IOException
	 */
	public void restart() throws IOException {
		stage.setTitle("Main menu");
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		MenuController menuController = new MenuController(stage);
		menuLoader.setController(menuController);

		Parent root = menuLoader.load();
		Scene scene = new Scene(root);
		root.requestFocus();
		setFullscreen(scene);
		menuController.setBackground();
		stage.show();
	}

	/**
	 * pops up a window, create a new stage to do this will not effect any normal
	 * game thread
	 * 
	 * @throws IOException
	 */
	public void beg() throws IOException {
		Stage st = new Stage();
		st.setTitle("An old king have something to say");
		FXMLLoader speakLoader = new FXMLLoader(getClass().getResource("king.fxml"));
		SpeakController speakController = new SpeakController(st);
		speakLoader.setController(speakController);

		Parent root = speakLoader.load();
		Scene scene = new Scene(root);
		st.toFront();
		st.setScene(scene);
		setFullscreen(stage.getScene());
		stage.show();
		st.show();
	}

	/**
	 * load specific map, different with nextLevel(), they will go to gameOver menu
	 * after finish or fail
	 * 
	 * @param jsonPath
	 */
	private void loadDungeon(String jsonPath) {
		try {
			stage.setTitle("Dungeon");

			DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(jsonPath);

			DungeonController controller = dungeonLoader.loadController();
			controller.getState().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.equals("You win!")) {
						try {
							endGame(newValue);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (newValue.equals("You dead!")) {
						try {
							endGame(newValue);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (newValue.equals("retry")) {
						loadDungeon(jsonPath);
					} else if (newValue.equals("restart")) {
						try {
							restart();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (newValue.equals("beg")) {
						try {
							beg();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			});
			FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
			gameLoader.setController(controller);
			Parent root = gameLoader.load();
			Scene scene = new Scene(root);
			root.requestFocus();
			setFullscreen(scene);
			stage.show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * set full screen
	 * @param scene
	 */
	private void setFullscreen(Scene scene) {
		stage.setFullScreen(true);
		stage.setScene(scene);
		stage.setFullScreen(true);
	}

}
