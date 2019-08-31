package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

	/**
	 * set title
	 * set controller
	 * set scene
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {

		primaryStage.setTitle("Main menu");
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		MenuController menuController = new MenuController(primaryStage);
		menuLoader.setController(menuController);

		Parent root = menuLoader.load();
		Scene scene = new Scene(root);
		root.requestFocus();

		primaryStage.setScene(scene);
		menuController.setBackground();
		primaryStage.show();
	}

	/**
	 * start app
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
