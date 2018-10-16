/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * The <code>StartUpScene</code> class displays the initial startup menu when the user
 * first opens up the program. Serves as the introduction of the game.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class StartUpScene implements Initializable {
	@FXML
	private ImageView startGameText;
	@FXML
	private ImageView startupImage;
	
	private Timer startTimer;
	private Timer imageTimer;
	private static int cycleSelection = 0;
	private static int currentFrame = 0;;
	
	@SuppressWarnings("unused")
	private static final int NUM_IMAGES = 4;
	private static final String FRAME_1_PATH = "resources/images/menu/menu_startup_background_f.png";
	private static final String FRAME_2_PATH = "resources/images/menu/menu_startup_background_f1.png";
	private static final String FRAME_3_PATH = "resources/images/menu/menu_startup_background_f2.png";
	private static final String FRAME_4_PATH = "resources/images/menu/menu_startup_background_f3.png";
	
	/**
	 * Prepares the scene by loading up the music player along with its necessary buttons, methods,
	 * and functionality.
	 * @param arg0 Unused.
	 * @param arg1 Unused.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		startTimer = new Timer();
		startTimer.schedule(new TimerTask() {
        	/**
        	 * Runs a Timer/TimerTask which changes the <code>StartupScene</code> start game text
        	 * every 1 second.
        	 */
        	@Override
        	public void run() {
        		switch(cycleSelection) {
        		case 0:
        			cycleSelection = 1;
        			startGameText.setVisible(true);
        			break;
        		case 1:
        			cycleSelection = 0;
        			startGameText.setVisible(false);
        			break;
        		default:
        			cycleSelection = 1;
        			startGameText.setVisible(true);
        		}
        	}
        }, 0L, 750L);
		
		imageTimer = new Timer();
		imageTimer.schedule(new TimerTask() {
        	/**
        	 * Runs a Timer/TimerTask which changes the <code>SignUpScene</code> main ImageView
        	 * every 1.5 seconds. Also starts up with an initial delay of 1 second when first running.
        	 */
        	@Override
        	public void run() {
        		switch(currentFrame) {
        		case 0:
        			currentFrame = 1;
        			startupImage.setImage(new Image(new File(FRAME_1_PATH).toURI().toString()));
        			break;
        		case 1:
        			currentFrame = 2;
        			startupImage.setImage(new Image(new File(FRAME_2_PATH).toURI().toString()));
        			break;
        		case 2:
        			currentFrame = 3;
        			startupImage.setImage(new Image(new File(FRAME_3_PATH).toURI().toString()));
        			break;
        		case 3:
        			currentFrame = 0;
        			startupImage.setImage(new Image(new File(FRAME_4_PATH).toURI().toString()));
        			break;
        		default:
        			startupImage.setImage(new Image(new File(FRAME_3_PATH)
        					.toURI().toString()));
        		}
        	}
        }, 0L, 1250L);
	}
	
	/**
	 * Loads up the <code>SignInScene</code>.
	 */
	@FXML
	private void start() {
		playMenuClickSound();
		startTimer.purge();
		startTimer.cancel();
		imageTimer.purge();
		imageTimer.cancel();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Sign In", ViewNavigator.SIGN_IN_SCENE);
	}
	
	/**
	 * Loads up the <code>OptionsScene</code>.
	 */
	@FXML
	private void option() {
		startTimer.purge();
		startTimer.cancel();
		imageTimer.purge();
		imageTimer.cancel();
        ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Options", ViewNavigator.OPTION_SCENE);
	}
	
	/**
	 * Loads up the <code>CreditsScene</code>.
	 */
	@FXML
	private void credit() {
		startTimer.purge();
		startTimer.cancel();
		imageTimer.purge();
		imageTimer.cancel();
        ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Credits", ViewNavigator.CREDITS_SCENE);
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