package edu.orangecoastcollege.cs272.view;

import java.io.File;	
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * The <code>OptionView</code> class allows the <code>User</code> to adjust various options including the
 * background music and sound in-game.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class OptionView implements Initializable {
	/**
	 * The name of the previous scene.
	 */
    public static String previousScene = "";
    /**
     * The name of the previous scene's title.
     */
    public static String previousTitle = "";
    
    @FXML private Slider backgroundMusic;
    @FXML private Slider soundEffect;
    @FXML private ImageView muteImage;
    

    /**
     * Mutes/unmutes the background music.
     */
    @FXML
    public void mute() {
    	playMenuClickSound();
    	if (MainView.backgroundMusicPlayer.isMute() && MainView.clickSoundPlayer.isMute()) {
    		MainView.backgroundMusicPlayer.setMute(false);
    		MainView.clickSoundPlayer.setMute(false);
    		muteImage.setImage(new Image(new File(MainView.SOUND_ON_PATH).toURI().toString()));
    	} else {
    		MainView.backgroundMusicPlayer.setMute(true);
    		MainView.clickSoundPlayer.setMute(true);
    		muteImage.setImage(new Image(new File(MainView.SOUND_OFF_PATH).toURI().toString()));
    	}
    }

    /**
     * Loads up the previous scene.
     */
    @FXML
    public void back() {
    	playMenuClickSound();
        ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Paused", ViewNavigator.PAUSE_SCENE);
    }

    /**
     * Loads up the <code>Startup</code> scene.
     */
    @FXML
    public void returnToTitle() {
    	ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.STARTUP_SCENE);
    }

    /**
     * Prepares the scene by loading up the music player along with its necessary buttons, methods,
     * and functionality.
     * @param location Unused.
     * @param resources Unused.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		backgroundMusic.setValue(MainView.backgroundMusicPlayer.getVolume());
		backgroundMusic.valueProperty().addListener(new ChangeListener<Number>()  {
			/**
			 * If a change is detected, adjust music player.
			 */
			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number value) {
				MainView.backgroundMusicPlayer.setVolume(value.doubleValue());
			}
			
		});
		
		soundEffect.setValue(MainView.clickSoundPlayer.getVolume());
		soundEffect.valueProperty().addListener(new ChangeListener<Number>()  {
			/**
			 * If a change is detected, adjust sound players.
			 */
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				MainView.clickSoundPlayer.setVolume(newValue.doubleValue());
				MainView.typingSoundPlayer.setVolume(newValue.doubleValue());
			}
			
		});
		
        if (MainView.backgroundMusicPlayer.isMute()) {
        	muteImage.setImage(new Image(new File(MainView.SOUND_OFF_PATH).toURI().toString()));
        } else {
        	muteImage.setImage(new Image(new File(MainView.SOUND_ON_PATH).toURI().toString()));
        }	
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