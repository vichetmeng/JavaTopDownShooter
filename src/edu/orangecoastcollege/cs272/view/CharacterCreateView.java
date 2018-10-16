/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import edu.orangecoastcollege.cs272.controller.Controller;
import edu.orangecoastcollege.cs272.model.CharacterClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * The <code>CharacterCreateView</code> class allows the <code>User</code> to
 * create a new <code>Character</code>.
 *
 * @author Vu Nguyen
 * @version 1.0
 */
public class CharacterCreateView implements Initializable {
	private Controller controller = Controller.getInstance();

	private int avaibleStat = 5;
	private int counter = 0;
	@FXML
	private Label requiredNameWarningLabel;
	@FXML
	private Label classNameLabel;
	@FXML
	private Label unusedStatPointsWarningLabel;
	@FXML
	private AnchorPane mainContainer;
	@FXML
	private TextField characterNameTF;
	@FXML
	private TextField powerTF;
	@FXML
	private TextField defenceTF;
	@FXML
	private TextField speedTF;
	@FXML
	private TextField avaibleStatTF;
	@FXML
	private ImageView imageView;
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

	@FXML
	private Button addPow;
	@FXML
	private Button subPow;
	@FXML
	private Button addDef;
	@FXML
	private Button subDef;
	@FXML
	private Button addSpeed;
	@FXML
	private Button subSpeed;

	private ArrayList<String> characterImage = new ArrayList<>();

	private Alert exitAlert;
	private static final String ALERT_DIALOG_LOGOUT_PROMPT = "Are you sure you want to logout?";
	@SuppressWarnings("unused")
	private static final String ALERT_DIALOG_EXIT_PROMPT = "Are you sure you want to exit?";
	private Optional<ButtonType> result;

	/**
	 * Prepares the scene by loading up the music player along with its
	 * necessary buttons, methods, and functionality.
	 * 
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

		addSpeed.setOnAction(myHandler);
		subSpeed.setOnAction(myHandler);
		addPow.setOnAction(myHandler);
		subPow.setOnAction(myHandler);
		addDef.setOnAction(myHandler);
		subDef.setOnAction(myHandler);

		characterImage.add(MainView.PURPLE_MAGE_PATH);
		characterImage.add(MainView.BLUE_MAGE_PATH);

		exitAlert = new Alert(AlertType.CONFIRMATION);
		exitAlert.initOwner(controller.getStage());
		
		BackgroundImage BI = new BackgroundImage(
				new Image(new File(MainView.ROCK_BG).toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		mainContainer.setBackground(new Background(BI));
	}
	
	/**
	 * Allows the <code>User</code> to change their current image.
	 */
	@FXML
	public void switchImageToRight() {
		if (counter == 0) {
			imageView.setImage(new Image(new File(characterImage.get(1)).toURI().toString()));
			
			classNameLabel.setText("Blue Mage");
			counter++;
		} else {
			imageView.setImage(new Image(new File(characterImage.get(0)).toURI().toString()));
			classNameLabel.setText("Purple Mage");
			counter--;
		}
	}

	/**
	 * Allows the <code>User</code> to change their current image.
	 */
	@FXML
	public Object switchImageToLeft() {
		if (counter == 0) {
			imageView.setImage(new Image(new File(characterImage.get(1)).toURI().toString()));
			classNameLabel.setText("Blue Mage");
			counter++;
		} else {
			imageView.setImage(new Image(new File(characterImage.get(0)).toURI().toString()));
			
			classNameLabel.setText("Purple Mage");
			counter--;
		}
		return this;
	}

	/**
	 * Adds points to the specified stat.
	 * 
	 * @param buttonClickedId The ID of the specific stats button.
	 */
	public void add(String buttonClickedId) {
		if (avaibleStat > 0) {
			switch (buttonClickedId) {
			case "addPow":
				int powStat = Integer.parseInt(powerTF.getText());
				powStat++;
				powerTF.setText(String.valueOf(powStat));
				break;
			case "addDef":
				int defStat = Integer.parseInt(defenceTF.getText());
				defStat++;
				defenceTF.setText(String.valueOf(defStat));
				break;
			case "addSpeed":
				int speedStat = Integer.parseInt(speedTF.getText());
				speedStat++;
				speedTF.setText(String.valueOf(speedStat));
				break;
			default:
				break;
			}

			avaibleStat--;
			avaibleStatTF.setText(String.valueOf(avaibleStat));
		}
	}

	/**
	 * Removes points to the specified stat.
	 * 
	 * @param buttonClickedId The ID of the specific stats button.
	 */
	public void substact(String buttonClickedId) {
		if (avaibleStat < 5) {
			switch (buttonClickedId) {
			case "subPow":
				int powStat = Integer.parseInt(powerTF.getText());
				if (powStat > 5) {
					powStat--;
					avaibleStat++;
				}
				powerTF.setText(String.valueOf(powStat));
				break;
			case "subDef":
				int defStat = Integer.parseInt(defenceTF.getText());
				if (defStat > 5) {
					defStat--;
					avaibleStat++;
				}
				defenceTF.setText(String.valueOf(defStat));
				break;
			case "subSpeed":
				int speedStat = Integer.parseInt(speedTF.getText());
				if (speedStat > 5) {
					speedStat--;
					avaibleStat++;
				}
				speedTF.setText(String.valueOf(speedStat));
				break;
			default:
				break;
			}

			avaibleStatTF.setText(String.valueOf(avaibleStat));
		}
	}

	@FXML
	public final void logout() {
		exitAlert.setTitle(MainView.PROGRAM_NAME + ": Logout Confirmation");
		exitAlert.setHeaderText(ALERT_DIALOG_LOGOUT_PROMPT);
		result = exitAlert.showAndWait();
		if (result.get() == ButtonType.OK)
			controller.logout();
		else
			exitAlert.close();
	}

	/**
	 * Loads up the <code>MainMenuScene</code>.
	 */
	@FXML
	public final void loadMainMenu() {
		ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.MAIN_MENU_SCENE);
	}

	/**
	 * Creates the <code>Character</code>.
	 */
	@FXML
	public void create() {
		CharacterClass cClass = CharacterClass.UNDEFINED;
		if (counter == 0)
			cClass = CharacterClass.BLUE_MAGE;
		else
			cClass = CharacterClass.PURPLE_MAGE;

		if (avaibleStat == 0 && !characterNameTF.getText().trim().equals("")) {
			if (controller.createNewCharacter(characterNameTF.getText(), Integer.parseInt(powerTF.getText()),
					Integer.parseInt(defenceTF.getText()), Integer.parseInt(speedTF.getText()), cClass)) {
				ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.MAIN_MENU_SCENE);
			}
		} else {
			unusedStatPointsWarningLabel.setVisible(avaibleStat != 0);
			requiredNameWarningLabel.setVisible(characterNameTF.getText().trim().equals(""));
		}
	}

	/**
	 * Allows the <code>User</code> to go back.
	 */
	@FXML
	public void back() {
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + "Character Select", ViewNavigator.CHARACTER_SELECT_SCENE);
	}

	final private EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {
		/**
		 * Handles the button clicks.
		 */
		@Override
		public void handle(ActionEvent event) {
			Button buttonClicked = (Button) event.getSource();
			String buttonClickedId = buttonClicked.getId();
			if (buttonClickedId.substring(0, 3).equalsIgnoreCase("add"))
				add(buttonClickedId);
			else
				substact(buttonClickedId);

		}

	};

	/**
	 * It logs the user out and bring them back to the sign in screen.
	 */
	public Object logOut() {
		controller.logout();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + " : Sign In", ViewNavigator.SIGN_IN_SCENE);
		return this;
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
	 * Adjusts the music player's current volume output according to the volume
	 * slider and status.
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