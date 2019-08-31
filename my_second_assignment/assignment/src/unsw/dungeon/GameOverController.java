package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverController {

	@FXML
	private Button returnButton;

	@FXML
	private Pane pane;

	@FXML
	private Button exitButton;

	@FXML
	private Text text;

	private String state;
	private Stage stage;

	/**
	 * set initial state and stage, for further modifies
	 * @param state
	 * @param stage
	 */
	public GameOverController(String state, Stage stage) {
		this.state = state;
		this.stage = stage;
	}

	/**
	 * set background and the text to show at top middle
	 */
	@FXML
	public void initialize() {
		text.setText(state);
		setBackground();
	}

	/**
	 * kill all thread then exit the game
	 * @param event
	 */
	@FXML
	void handleExitButton(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * return to main menu
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void handleReturnButton(ActionEvent event) throws IOException {

		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		MenuController menucontroller = new MenuController(stage);
		menuLoader.setController(menucontroller);

		Parent root = menuLoader.load();
		Scene scene = new Scene(root);
		root.requestFocus();
		menucontroller.setBackground();
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	/**
	 * set the background image
	 */
	public void setBackground() {
		String image = "bomber_wallpaper.png";
		if (state.contains("dead")) {
			image = "sad_king.png";
		} else if (state.contains("win")) {
			image = "happy_king.png";
		}
		File file = new File(System.getProperty("user.dir").toString() + "/images/" + image);
		BackgroundImage background = new BackgroundImage(new Image(file.toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background b = new Background(background);
		pane.setBackground(b);
//		this.stage.setFullScreen(true);
	}

}
