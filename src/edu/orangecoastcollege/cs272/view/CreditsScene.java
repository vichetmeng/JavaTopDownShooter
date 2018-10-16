/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import edu.orangecoastcollege.cs272.controller.Controller;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * The <code>CreditsScene</code> allows the user to view the makers as well as the music artists
 * for the soundtrack used in the game.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class CreditsScene implements Initializable {
	private static final Controller controller = Controller.getInstance();

	@FXML
	private ImageView greetingsButton;
	@FXML
	private ImageView heroicDesireButton;
	@FXML
	private ImageView grimheartButton;
    @FXML
    private ImageView backgroundMusicButton;
    @FXML
    private ImageView soundButton;
    @FXML
    private Label currentlyPlayingLabel;
    @FXML
    private Label songNameLabel;
    @FXML
    private ImageView pauseMusicButton;
    @FXML
    private Slider volumeSlider;

    private static MediaPlayer greetingsMusicPlayer;
    private static MediaPlayer heroicDesireMusicPlayer;
    private static MediaPlayer grimheartMusicPlayer;

    private Alert exitAlert;
    private static final String ALERT_DIALOG_CONFIRMATION_PROMPT = "Are you sure you want to logout?";
    private Optional<ButtonType> result;

	/**
	 * Prepares the scene by loading up the music player along with its necessary buttons, methods,
	 * and functionality.
	 * @param location Unused.
	 * @param resources Unused.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        if (MainView.backgroundMusicPlayer.isMute()) {
            volumeSlider.setValue(0.0);
            backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_OFF_PATH).toURI().toString()));
        } else {
            volumeSlider.setValue(MainView.backgroundMusicPlayer.getVolume());
            backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_ON_PATH).toURI().toString()));
        }

        if (MainView.backgroundMusicPlayer.getStatus().equals(Status.PAUSED))
            pauseMusicButton.setImage(new Image(new File(MainView.PLAY_MUSIC_PATH).toURI().toString()));
        else
            pauseMusicButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));

        if (MainView.clickSoundPlayer.isMute())
            soundButton.setImage(new Image(new File(MainView.SOUND_OFF_PATH).toURI().toString()));
        else
            soundButton.setImage(new Image(new File(MainView.SOUND_ON_PATH).toURI().toString()));

        if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.GREETINGS_MUSIC)) {
        	songNameLabel.setText("Greetings");
        } else if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.HEROIC_DESIRE_MUSIC)) {
        	songNameLabel.setText("Heroic Desire");
        } else if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.GRIMHEART_MUSIC)) {
        	songNameLabel.setText("Grimheart");
        }

		exitAlert = new Alert(AlertType.CONFIRMATION);
		exitAlert.setTitle(MainView.PROGRAM_NAME + ": Logout Confirmation");
		exitAlert.setHeaderText(ALERT_DIALOG_CONFIRMATION_PROMPT);
		exitAlert.initOwner(controller.getStage());

		greetingsMusicPlayer = new MediaPlayer(MainView.GREETINGS_MUSIC);
		greetingsMusicPlayer.setVolume(MainView.DEFAULT_MUSIC_LEVEL);
		greetingsMusicPlayer.setOnEndOfMedia(new Runnable() {
			/**
			 * Sets the continuous looping of Heroic Desire when it ends.
			 */
			@Override
			public void run() {
				greetingsMusicPlayer.seek(Duration.ZERO);
			}
		});

		heroicDesireMusicPlayer = new MediaPlayer(MainView.HEROIC_DESIRE_MUSIC);
		heroicDesireMusicPlayer.setVolume(MainView.DEFAULT_MUSIC_LEVEL);
		heroicDesireMusicPlayer.setOnEndOfMedia(new Runnable() {
			/**
			 * Sets the continuous looping of Heroic Desire when it ends.
			 */
			@Override
			public void run() {
				heroicDesireMusicPlayer.seek(Duration.ZERO);
			}
		});

		grimheartMusicPlayer = new MediaPlayer(MainView.GRIMHEART_MUSIC);
		grimheartMusicPlayer.setVolume(MainView.DEFAULT_MUSIC_LEVEL);
		grimheartMusicPlayer.setOnEndOfMedia(new Runnable() {
			/**
			 * Sets the continuous looping of Grimheart when it ends.
			 */
			@Override
			public void run() {
				grimheartMusicPlayer.seek(Duration.ZERO);
			}
		});

		greetingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			/**
			 * Pauses Greetings player if playing, unpauses if it isn't.
			 */
		     @Override
		     public void handle(final MouseEvent event) {
		    	 handleMusicButtons(event);
		     }
		});
		heroicDesireButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			/**
			 * Pauses Heroic Desire player if playing, unpauses if it isn't.
			 */
		     @Override
		     public void handle(final MouseEvent event) {
		    	 handleMusicButtons(event);
		     }
		});
		grimheartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			/**
			 * Pauses Grimheart player if playing, unpauses if it isn't.
			 */
		     @Override
		     public void handle(final MouseEvent event) {
		    	 handleMusicButtons(event);
		     }
		});
	}

	/**
	 * Plays/pauses the appropriate songs when the user mouse-clicks on the buttons.
	 * @param event The mouse-click event.
	 */
    @FXML
    private void handleMusicButtons(final MouseEvent event) {
		if (MainView.backgroundMusicPlayer.getStatus().equals(Status.PLAYING)) {
			MainView.backgroundMusicPlayer.pause();
			currentlyPlayingLabel.setText("Currently Paused:");
			pauseMusicButton.setImage(new Image(new File(MainView.PLAY_MUSIC_PATH).toURI().toString()));
		}

        if (event.getSource() == greetingsButton) {
        	if (greetingsMusicPlayer.getStatus().equals(Status.PLAYING)) {
        		greetingsMusicPlayer.pause();
    			greetingsButton.setImage(new Image(new File(MainView.PLAY_MUSIC_PATH).toURI().toString()));
        	} else {
        		greetingsMusicPlayer.play();
        		greetingsButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));
        	}
        } else if (event.getSource() == heroicDesireButton) {
        	if (heroicDesireMusicPlayer.getStatus().equals(Status.PLAYING)) {
        		heroicDesireMusicPlayer.pause();
    			heroicDesireButton.setImage(new Image(new File(MainView.PLAY_MUSIC_PATH).toURI().toString()));
        	} else {
        		heroicDesireMusicPlayer.play();
        		heroicDesireButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));
        	}
        } else {
        	if (grimheartMusicPlayer.getStatus().equals(Status.PLAYING)) {
        		grimheartMusicPlayer.pause();
    			grimheartButton.setImage(new Image(new File(MainView.PLAY_MUSIC_PATH).toURI().toString()));
        	} else {
        		grimheartMusicPlayer.play();
        		grimheartButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));
        	}
        }
    }

	/**
	 * Loads up the <code>MainMenuScene</code>.
	 */
	@FXML
	public final void loadMainMenu() {
		greetingsMusicPlayer.stop();
		heroicDesireMusicPlayer.stop();
		grimheartMusicPlayer.stop();

		playMenuClickSound();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.MAIN_MENU_SCENE);
	}

	/**
	 * Loads up the <code>ResourceMenuScene</code>.
	 */
	@FXML
	public final void back() {
		greetingsMusicPlayer.stop();
		heroicDesireMusicPlayer.stop();
		grimheartMusicPlayer.stop();

		playMenuClickSound();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Resources", ViewNavigator.RESOURCE_MENU_SCENE);
	}

	/**
	 * Logs out the user if the user clicks ok in the AlertDialog, closes the dialog if not.
	 */
	@FXML
	public final void logout() {
		greetingsMusicPlayer.stop();
		heroicDesireMusicPlayer.stop();
		grimheartMusicPlayer.stop();

		result = exitAlert.showAndWait();
		if (result.get() == ButtonType.OK)
			controller.logout();
		else
			exitAlert.close();
	}

	/**
	 * Loads up and plays the mouse-clicking sound.
	 */
	@FXML
	public final void playMenuClickSound() {
		MainView.clickSoundPlayer.play();
		MainView.clickSoundPlayer.seek(Duration.ZERO);
	}

	/**
	 * Loads up and plays the typing sound.
	 */
	@FXML
	public final void playMenuTypeSound() {
		MainView.typingSoundPlayer.play();
		MainView.typingSoundPlayer.seek(Duration.ZERO);
	}

	/**
	 * Mutes (or unmutes) ambient/UI sounds, depending on the current volume.
	 */
	@FXML
	public final void changeSoundStatus() {
		if (!MainView.clickSoundPlayer.isMute()) {
			soundButton.setImage(new Image(new File(MainView.SOUND_OFF_PATH).toURI().toString()));
			MainView.clickSoundPlayer.setMute(true);
			MainView.typingSoundPlayer.setMute(true);
		} else {
			soundButton.setImage(new Image(new File(MainView.SOUND_ON_PATH).toURI().toString()));
			MainView.clickSoundPlayer.setMute(false);
			MainView.typingSoundPlayer.setMute(false);
		}
	}

	/**
	 * Mutes (or unmutes) the background music, depending on the current volume.
	 */
	@FXML
	public final void changeBackgroundSongStatus() {
		if (!MainView.backgroundMusicPlayer.isMute()) {
			MainView.backgroundMusicPlayer.setMute(true);
			greetingsMusicPlayer.setMute(true);
		    heroicDesireMusicPlayer.setMute(true);
		    grimheartMusicPlayer.setMute(true);
			volumeSlider.setValue(0.0);
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_OFF_PATH).toURI().toString()));
		} else {
			MainView.backgroundMusicPlayer.setMute(false);
			greetingsMusicPlayer.setMute(false);
		    heroicDesireMusicPlayer.setMute(false);
		    grimheartMusicPlayer.setMute(false);
			volumeSlider.setValue(MainView.backgroundMusicPlayer.getVolume());
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_ON_PATH).toURI().toString()));
		}
	}

	/**
	 * Replays the current background song player.
	 */
	@FXML
	public final void restartSong() {
		MainView.backgroundMusicPlayer.pause();
		MainView.backgroundMusicPlayer.seek(Duration.ZERO);
		MainView.backgroundMusicPlayer.play();
	}

	/**
	 * Pauses the current background song player.
	 */
	@FXML
	public final void pauseSong() {
		if (MainView.backgroundMusicPlayer.getStatus().equals(Status.PLAYING)) {
			MainView.backgroundMusicPlayer.pause();
			currentlyPlayingLabel.setText("Currently Paused:");
			pauseMusicButton.setImage(new Image(new File(MainView.PLAY_MUSIC_PATH).toURI().toString()));
		} else {
			MainView.backgroundMusicPlayer.play();
			currentlyPlayingLabel.setText("Currently Playing:");
			pauseMusicButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));
		}
	}

	/**
	 * Cycles to the next available song in the music playlist and plays it.
	 */
	@FXML
	public final void skipSong() {
		if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.GREETINGS_MUSIC)) {
			MainView.backgroundMusicPlayer.stop();
			MainView.backgroundMusicPlayer.seek(Duration.ZERO);

			songNameLabel.setText("Heroic Desire");
			MainView.backgroundMusicPlayer = new MediaPlayer(MainView.HEROIC_DESIRE_MUSIC);
			MainView.backgroundMusicPlayer.setAutoPlay(true);
			MainView.backgroundMusicPlayer.setVolume(volumeSlider.getValue());
			MainView.backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					MainView.backgroundMusicPlayer.seek(Duration.ZERO);
				}
			});
		} else if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.HEROIC_DESIRE_MUSIC)) {
			MainView.backgroundMusicPlayer.stop();
			MainView.backgroundMusicPlayer.seek(Duration.ZERO);

			songNameLabel.setText("Grimheart");
			MainView.backgroundMusicPlayer = new MediaPlayer(MainView.GRIMHEART_MUSIC);
			MainView.backgroundMusicPlayer.setAutoPlay(true);
			MainView.backgroundMusicPlayer.setVolume(volumeSlider.getValue());
			MainView.backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					MainView.backgroundMusicPlayer.seek(Duration.ZERO);
				}
			});
		} else if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.GRIMHEART_MUSIC)) {
			MainView.backgroundMusicPlayer.stop();
			MainView.backgroundMusicPlayer.seek(Duration.ZERO);

			songNameLabel.setText("Greetings");
			MainView.backgroundMusicPlayer = new MediaPlayer(MainView.GREETINGS_MUSIC);
			MainView.backgroundMusicPlayer.setAutoPlay(true);
			MainView.backgroundMusicPlayer.setVolume(volumeSlider.getValue());
			MainView.backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					MainView.backgroundMusicPlayer.seek(Duration.ZERO);
				}
			});
		}
		pauseMusicButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));
	}

	/**
	 * Adjusts the music player's current volume output according to the volume slider and status.
	 */
	@FXML
	public final void adjustVolume() {
		final double value = volumeSlider.getValue();
		MainView.backgroundMusicPlayer.setVolume(value);
		greetingsMusicPlayer.setVolume(value);
	    heroicDesireMusicPlayer.setVolume(value);
	    grimheartMusicPlayer.setVolume(value);

		if (MainView.backgroundMusicPlayer.isMute() && value > 0.0) {
			MainView.backgroundMusicPlayer.setMute(false);
			greetingsMusicPlayer.setMute(false);
		    heroicDesireMusicPlayer.setMute(false);
		    grimheartMusicPlayer.setMute(false);
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_ON_PATH).toURI().toString()));
		}
	}
}