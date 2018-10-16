/**	
 * CS A272		
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import edu.orangecoastcollege.cs272.controller.Controller;
import edu.orangecoastcollege.cs272.model.Weapon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * The <code>WeaponCodexDetailsScene</code> class displays specific details in regards to a desired
 * <code>Weapon</code>, chosen from the <code>ListView</code> in the prior menu <code>WeaponCodexDetailsListScene</code>.
 * Whether it be rarity level, the description of the <code>Weapon</code>, or even its base stats,
 * it is displayed for the user to see.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class WeaponCodexDetailsScene implements Initializable {
	private static final Controller controller = Controller.getInstance();

	@FXML
	private Label weaponNameLabel;
	@FXML
	private ImageView weaponImageImageView;
	@FXML
	private Label levelLabel;
	@FXML
	private ImageView levelImageView;
	@FXML
	private Label ratingLabel;
	@FXML
	private Label worthLabel;
	@FXML
	private TextArea descriptionTextArea;
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

	private Weapon weapon;

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
        
        weapon = controller.getSelectedCodexWeapon();

		if (weapon != null) {
			weaponNameLabel.setText(weapon.getName());
			weaponImageImageView.setImage(weapon.getImage());
            
            levelLabel.setText("Rarity Level: " + String.valueOf(weapon.getRarity()));
            if (weapon.getRarity() >= 5.0)
                levelImageView.setImage(new Image(new File(MainView.FIVE_STAR_PATH).toURI().toString()));
            else if (weapon.getRarity() < 5.0 && weapon.getRarity() >= 4.0)
                levelImageView.setImage(new Image(new File(MainView.FOUR_STAR_PATH).toURI().toString()));
            else if (weapon.getRarity() < 4.0 && weapon.getRarity() >= 3.0)
                levelImageView.setImage(new Image(new File(MainView.THREE_STAR_PATH).toURI().toString()));
            else if (weapon.getRarity() < 3.0 && weapon.getRarity() >= 2.0)
                levelImageView.setImage(new Image(new File(MainView.TWO_STAR_PATH).toURI().toString()));
            else
                levelImageView.setImage(new Image(new File(MainView.ONE_STAR_PATH).toURI().toString()));
            
            ratingLabel.setText("Weapon Attack: " + String.valueOf(weapon.getAttackPoints()));
            worthLabel.setText("Worth: " + String.valueOf(weapon.getWorth()) + " gold");
            descriptionTextArea.setText(weapon.getDescription());
		} else
			weaponNameLabel.setText("An unknown error has occurred.");
	}
	
	/**
	 * Loads up the <code>MainMenuScene</code>.
	 */
	@FXML
	public final void returnToMainMenu() {
		ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.MAIN_MENU_SCENE);
	}
	
	/**
	 * Loads up the <code>CodexScene</code>.
	 */
	@FXML
	public final void returnToCodex() {
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Weapons", ViewNavigator.WEAPON_CODEX_LIST_SCENE);
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
			volumeSlider.setValue(0.0);
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_OFF_PATH).toURI().toString()));
		} else {
			MainView.backgroundMusicPlayer.setMute(false);
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
				/**
				 * Allows auto-looping of the Heroic Desire song once it ends.
				 */
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
				/**
				 * Allows auto-looping of the Grimheart song once it ends.
				 */
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
				/**
				 * Allows auto-looping of the Greetings song once it ends.
				 */
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

		if (MainView.backgroundMusicPlayer.isMute() && value > 0.0) {
			MainView.backgroundMusicPlayer.setMute(false);
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_ON_PATH).toURI().toString()));
		}
	}
}