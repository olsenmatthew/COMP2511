package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SpeakController {

    @FXML
    private Button okButton;

    @FXML
    private Text text;
    
    private Stage stage;

    /**
     * initialize the stage
     * @param stage
     */
    public SpeakController(Stage stage) {
    	this.stage = stage;

    }
    
    /**
     * initialize the text and position of text field and button
     */
    @FXML
	public void initialize() {
    	text.setText("Hi, young hero");
    	okButton.setText("Hi");
    	okButton.setAlignment(Pos.CENTER);
    	text.setTextAlignment(TextAlignment.CENTER);
    	text.setX(200);
    	text.setY(59);
    }
    /**
     * handle the button, change the text in text field and button
     * relocation them also
     * @param event
     */
    @FXML
    void handleOKButton(ActionEvent event) {
    	if(okButton.getText().equals("Hi")) {
    		text.setText("I have a request, my daughter, the princess, \n"
    				+ "is stacking in that dungeon, can you get her out?");
    		okButton.setText("it's my pleasure");
    		okButton.setAlignment(Pos.CENTER);
        	text.setTextAlignment(TextAlignment.CENTER);
        	text.setX(100);
    	}else
    	if(okButton.getText().equals("it's my pleasure")) {
    		text.setText("If you help her out, \n"
    				+ "I will let you merry her");
    		okButton.setText("wow yeah!");
    		okButton.setAlignment(Pos.CENTER);
        	text.setTextAlignment(TextAlignment.CENTER);
        	text.setX(200);
    	}else
    	if(okButton.getText().equals("wow yeah!")) {
    		text.setText("Be careful, it's dangerous inside, just let you know, \n"
    				+ "press E to pick up item, press F to use item, press S to switch item, \n"
    				+ "press D to drop item, press R to rescue the princess. Good luck!");
    		okButton.setText("got it");
    		okButton.setAlignment(Pos.CENTER);
        	text.setTextAlignment(TextAlignment.CENTER);
        	text.setX(10);
    	}else
    	if(okButton.getText().equals("got it")) {
    		stage.close();
    	}
    	
    }


}
