package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import unsw.dungeon.goals.CompositeGoal;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.player.Player;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 * 
 */
public class DungeonController {

	@FXML
	private Pane pane;

	@FXML
	private GridPane squares;

	@FXML
	private TreeView<String> goalView;

	@FXML
	private VBox itemsView;

	@FXML
	private Button restartBtn;

	@FXML
	private Button retryBtn;

	private List<ImageView> initialEntities;

	private Player player;

	private Dungeon dungeon;

	private StringProperty state;

	/**
	 * the constructor for dungeon fxml controller, make the private variables to be
	 * new empty one
	 * 
	 * @param dungeon
	 * @param initialEntities
	 */
	public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
		this.dungeon = dungeon;
		this.player = dungeon.getPlayer();
		this.initialEntities = new ArrayList<>(initialEntities);
		this.state = new SimpleStringProperty();
	}

	/**
	 * initialize the state, crate empty grounds inside the map's size, according to
	 * the logic of goals, create Tree for goals to dungeon, set a listener for
	 * dungeon's state to let the application change stage at any time
	 */
	@FXML
	public void initialize() {
		state.set("playing");
		Image ground = new Image("/dirt_0_new.png");

		// Add the ground first so it is below all other entities
		for (int x = 0; x < dungeon.getWidth(); x++) {
			for (int y = 0; y < dungeon.getHeight(); y++) {
				if (x < dungeon.getWidth() * .55 && x > dungeon.getWidth() * .45 && y < dungeon.getHeight() * .55
						&& y > dungeon.getHeight() * .45) {
					System.out.println("{\"type\":\"boulder\", \"x\":" + x + ",\"y\":" + y + "},");

				}
				ImageView image = new ImageView(ground);
				squares.add(image, x, y);
			}
		}

		CheckBoxTreeItem<String> goalsRoot = new CheckBoxTreeItem<String>("Goals");
		goalsRoot.getChildren().add(decompositeDungeonGoal(dungeon.getGoal()));
		goalsRoot.setIndependent(true);
		goalsRoot.setExpanded(true);
		goalsRoot.selectedProperty().bind(dungeon.getGoal().getProperty());
		goalView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
		goalView.setRoot(goalsRoot);
		goalView.getSelectionModel().selectedItemProperty().addListener((c, oldValue, newValue) -> {
			Platform.runLater(() -> goalView.getSelectionModel().clearSelection());
		});

		initializeItems();

		for (ImageView entity : initialEntities) {
			squares.getChildren().add(entity);
		}

		dungeon.getState().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals("playing")) {
					state.set(newValue);
				}
			}
		});
	}

	/**
	 * handle the key press event, up,down,right,left to control the movement, D
	 * calls drop, E calls pick, F calls use, S calls switchItem R calls rescue
	 * 
	 * @param event
	 */
	@FXML
	public void handleKeyPress(KeyEvent event) {
		switch (event.getCode()) {
		case UP:
			player.moveUp();
			break;
		case DOWN:
			player.moveDown();
			break;
		case LEFT:
			player.moveLeft();
			break;
		case RIGHT:
			player.moveRight();
			break;
		case D:
			player.drop();
			break;
		case E:
			player.pickup();
			break;
		case F:
			player.use();
			break;
		case S:
			player.switchItem();
			break;
		case R:
			player.rescue();
			break;
		default:
			break;
		}
	}

	@FXML
	public void handleUselessKey(KeyEvent event) {
		handleKeyPress(event);
	}

	/**
	 * for the list view for the goals, create the tree of checkboxes, to let player
	 * track current goal status, bind the checkboxes with goals in dungeon in both
	 * way
	 * 
	 * @param goals
	 * @return
	 */
	private CheckBoxTreeItem<String> decompositeDungeonGoal(Goal goals) {
		CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>(goals.getName());
		root.setExpanded(true);
		root.setIndependent(true);
		root.selectedProperty().bindBidirectional(goals.getProperty());
		if (goals instanceof CompositeGoal) {
			for (Goal g : ((CompositeGoal) goals).getGoals()) {
				root.getChildren().add(decompositeDungeonGoal(g));
			}
		}
		return root;
	}

	/**
	 * return the state of controller
	 * 
	 * @return
	 */
	public StringProperty getState() {
		return state;
	}

	/**
	 * set the state of controller to be restart
	 * 
	 * @param event
	 */
	@FXML
	void handleRestart(ActionEvent event) {
		cleanUpEndGameEntities();
		state.set("restart");
	}

	/**
	 * set the state of controller to be retry
	 * 
	 * @param event
	 */
	@FXML
	void handleRetry(ActionEvent event) {
		cleanUpEndGameEntities();
		state.set("retry");
	}

	private void cleanUpEndGameEntities() {
		for (Entity e : dungeon.getEntities(WeakMortal.class)) {
			((WeakMortal) e).kill();
		}
		for (Entity e : dungeon.getEntities()) {
			if (!(e instanceof Player)) {
				e.setX(0);
				e.setY(0);
			}
		}
	}

	/**
	 * create a list view of items, show the item player currently have in the top
	 * right of the UI
	 */
	private void initializeItems() {
		player.getItems().addListener((MapChangeListener<String, Integer>) change -> {
			Platform.runLater(() -> {
				List<Node> remove = new ArrayList<Node>();
				for (Node n : itemsView.getChildren()) {
					remove.add(n);
				}
				itemsView.getChildren().removeAll(remove);
				for (String key : player.getItems().keySet()) {
					String content = "Item: " + key + " " + player.getItems().get(key);
					Text text = new Text(content);
					text.setId(key);
					itemsView.getChildren().add(text);
					itemsView.setAlignment(Pos.TOP_LEFT);
					if (text.getId().equals(player.getSelected().get())) {
						text.setFill(Color.RED);
					}
				}
			});
		});

		player.getSelected().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(() -> {
					for (Node n : itemsView.getChildren()) {
						if (n.getId().equals(newValue)) {
							((Text) n).setFill(Color.RED);
						} else {
							((Text) n).setFill(Color.BLACK);
						}
					}
				});
			}
		});
	}

}
