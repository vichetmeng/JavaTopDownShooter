/**
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import edu.orangecoastcollege.cs272.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * The <code>PauseScene</code> is when the <code>Player</code> pauses, where various options
 * and choices/actions are available.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class PauseScene {
	private static final Controller controller = Controller.getInstance();
	
	@FXML
	private ImageView playerPortait;
	
	@FXML
	private void inventory() {
    	playMenuClickSound();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Inventory", ViewNavigator.INVENTORY_SCENE);
	}
	
	@FXML
	private void setting() {
    	playMenuClickSound();
		OptionView.previousScene = ViewNavigator.PAUSE_SCENE;
		OptionView.previousTitle = "Player Menu";
		
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Options", ViewNavigator.OPTION_SCENE);
	}
	
	@FXML
	private void back() {
    	playMenuClickSound();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.currentGameScene);
		ViewNavigator.currentGameScene.unpauseGame();
	}
	
	@FXML
	private void save() {
    	playMenuClickSound();
		controller.saveGame();
	}
	
	/**
	 * Loads up and plays the mouse-clicking sound.
	 */
	@FXML
	public final void playMenuClickSound() {
		MainView.clickSoundPlayer.play();
		MainView.clickSoundPlayer.seek(Duration.ZERO);
	}
}