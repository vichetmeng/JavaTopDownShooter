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
import java.util.concurrent.ThreadLocalRandom;

import edu.orangecoastcollege.cs272.controller.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Duration;

/**
 * The <code>SignUpScene</code> class allows the <code>User</code> to register for an account
 * to gain full functionality of the game.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class SignUpScene implements Initializable {
	private static final Controller controller = Controller.getInstance();

	@FXML
	private TextField usernameTF;
	@FXML
	private PasswordField passwordPF;
	@FXML
	private PasswordField confirmPasswordPF;
	@FXML
	private Label signInLabel;
	@FXML
	private Label usernameErrorLabel;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private Label confirmPasswordErrorLabel;
	@FXML
	private ImageView signUpButton;
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
	private ImageView signUpMainImage;

	private Alert dialog;
	private Label errorResultLabel;
	private TextArea accountCriteriaTA;
	private ImageView oopsImage;

	private Timer timer;
	private int previousFrame;
	private boolean isRunning;

	/**
	 * Prepares the scene by loading up the music player along with its necessary buttons, methods,
	 * and functionality.
	 * @param arg0 Unused.
	 * @param arg1 Unused.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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

        dialog = new Alert(AlertType.ERROR);
        dialog.setTitle("Invalid Field(s) Entered");

		oopsImage = new ImageView(new Image(new File(MainView.OOPS_PATH).toURI().toString()));
		oopsImage.setFitWidth(120);
		oopsImage.setFitHeight(120);
        dialog.setGraphic(oopsImage);
        dialog.setHeaderText("");

        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
		errorResultLabel = new Label("An Unknown Error Has Occurred.");
		errorResultLabel.setAlignment(Pos.CENTER);
		errorResultLabel.setTextFill(Color.RED);
		errorResultLabel.setFont(Font.font("Calibri", FontPosture.ITALIC, Font.getDefault().getSize()));
        grid.add(errorResultLabel, 0, 0);

		accountCriteriaTA = new TextArea("Username must meet ALL of the following requirements:"
				+ "\n\t1.) The username must be at at least 4 (but no more than 20) characters long."
				+ "\n\t2.) The username may not contain any special characters aside from the following: ' ', '@', '.', '_'"
				+ "\n\t3.) The username may not start or end with spaces, (they will be removed)."
				+ "\n\t4.) The username must be new/unique. No duplicate/existing usernames will be allowed."
				+ "\n\nPassword must meet ALL of the following requirements:"
				+ "\n\t1.) The password must be at least 8 (but no more than 20) characters long."
				+ "\n\t2.) The password must contain at least 1 alphabetical character."
				+ "\n\t3.) The password must contain at least 1 number."
				+ "\n\t4.) The password may not contain any special characters aside from the following: ' ', '@', '.', '_'"
				+ "\n\t5.) The password may not start or end with spaces, (they will be removed)."
				+ "\n\t6.) The password may not be the same as the username.");
		accountCriteriaTA.setEditable(false);
		accountCriteriaTA.setFocusTraversable(false);
		accountCriteriaTA.setMinWidth(550);
		accountCriteriaTA.setMaxWidth(550);
		accountCriteriaTA.minHeight(350);
		accountCriteriaTA.setMaxHeight(350);
        grid.add(accountCriteriaTA, 0, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.initOwner(controller.getStage());

		previousFrame = 1;
		isRunning = true;

        timer = new Timer();
        timer.schedule(new TimerTask() {
        	/**
        	 * Runs a Timer/TimerTask which changes the <code>SignUpScene</code> main ImageView
        	 * every 1.5 seconds. Also starts up with an initial delay of 1 second when first running.
        	 */
        	@Override
        	public void run() {
                int randomPictureSelection;
                do {
                    randomPictureSelection = ThreadLocalRandom.current().nextInt(1,
                            MainView.FRAME_ANIM_NUM_IMAGES + 1);
                } while (randomPictureSelection == previousFrame);
        		previousFrame = randomPictureSelection;

        		switch(randomPictureSelection) {
        		case 1:
        			signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_1_PATH)
        					.toURI().toString()));
        			break;
        		case 2:
        			signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_2_PATH)
        					.toURI().toString()));
        			break;
        		case 3:
        			signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_3_PATH)
        					.toURI().toString()));
        			break;
        		case 4:
        			signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_4_PATH)
        					.toURI().toString()));
        			break;
        		default:
        			signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_1_PATH)
        					.toURI().toString()));
        		}
        	}
        }, 1000L, 1500L);
	}

	/**
	 * Either starts up or pauses (depending on the status) of the current TimerTask/Timer for the ImageView.
	 */
	@FXML
	public final void changeFrameAnimStatus() {
		if (isRunning) {
			isRunning = false;
			timer.cancel();
			timer.purge();
		} else {
			isRunning = true;
			timer = new Timer();
			timer.schedule(new TimerTask() {
	        /**
	        * Runs a Timer/TimerTask which changes the <code>SignUpScene</code> main ImageView
	        * every 1.5 seconds. Also starts up with an initial delay of 1 second when first running.
	        */
			@Override
			public void run() {
				int randomPictureSelection;
				do {
					randomPictureSelection = ThreadLocalRandom.current().nextInt(1,
							MainView.FRAME_ANIM_NUM_IMAGES + 1);
				} while (randomPictureSelection == previousFrame);
				previousFrame = randomPictureSelection;

				switch (randomPictureSelection) {
				case 1:
					signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_1_PATH)
							.toURI().toString()));
					break;
				case 2:
					signUpMainImage
							.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_2_PATH)
									.toURI().toString()));
					break;
				case 3:
					signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_3_PATH)
									.toURI().toString()));
					break;
				case 4:
					signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_4_PATH)
										.toURI().toString()));
					break;
				default:
					signUpMainImage.setImage(new Image(new File(MainView.FRAME_ANIM_SIGN_UP_1_PATH)
									.toURI().toString()));
				}
			}
		}, 0L, 1500L);
		}
	}

	/**
	 * Registers a new <code>User</code> account into the database if all fields of input
	 * are valid. If they aren't, the appropriate error messages are displayed.
	 */
	@FXML
	public final void signUp() {
	    final String username = usernameTF.getText().trim();
	    final String password = passwordPF.getText().trim();
	    final String confirmPassword = confirmPasswordPF.getText().trim();
        final String result = controller.signUp(username, password, confirmPassword);

	    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
	    	if (username.isEmpty()) {
	    		usernameErrorLabel.setText(MainView.FIELD_REQUIRED);
		    	usernameErrorLabel.setVisible(true);
	    	} else
		    	usernameErrorLabel.setVisible(false);
	    	if (password.isEmpty()) {
	    		passwordErrorLabel.setText(MainView.FIELD_REQUIRED);
		        passwordErrorLabel.setVisible(true);
	    	} else
		        passwordErrorLabel.setVisible(false);
	    	if (confirmPassword.isEmpty()) {
	    		confirmPasswordErrorLabel.setText(MainView.FIELD_REQUIRED);
		        confirmPasswordErrorLabel.setVisible(true);
	    	} else
	    		confirmPasswordErrorLabel.setVisible(false);
	        return;
	    }

        if (result.equalsIgnoreCase("SUCCESS")) {
        	if (isRunning) {
        		timer.cancel();
        		timer.purge();
        	}
            ViewNavigator.loadScene("Adventure Game", ViewNavigator.SIGN_IN_SCENE);
        } else {
    	    if (result.equals(Controller.USERNAME_DOES_NOT_MEET_CRITERIA)) {
    	    	usernameErrorLabel.setText("Invalid Username");
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(false);
		        confirmPasswordErrorLabel.setVisible(false);
    	    } else if (result.equals(Controller.PASSWORD_DOES_NOT_MEET_CRITERIA)) {
    	    	passwordErrorLabel.setText("Invalid Password");
    	        usernameErrorLabel.setVisible(false);
    	        passwordErrorLabel.setVisible(true);
		        confirmPasswordErrorLabel.setVisible(false);
    	    } else if (result.equals(Controller.PASSWORDS_DO_NOT_MATCH)) {
	    		confirmPasswordErrorLabel.setText(Controller.PASSWORDS_DO_NOT_MATCH);
    	        usernameErrorLabel.setVisible(false);
    	        passwordErrorLabel.setVisible(false);
		        confirmPasswordErrorLabel.setVisible(true);
    	    } else if (result.equals(Controller.PASSWORD_SAME_AS_USER)) {
    	    	usernameErrorLabel.setText("Same As Password");
    	    	passwordErrorLabel.setText("Same As Username");
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(true);
		        confirmPasswordErrorLabel.setVisible(false);
    	    } else if (result.equals(Controller.USER_EXISTS)) {
    	    	usernameErrorLabel.setText("Username Already Exists");
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(false);
		        confirmPasswordErrorLabel.setVisible(false);
    	    }
    	    errorResultLabel.setText(result);
    	    dialog.showAndWait();
        }
	}

	/**
	 * Loads up the <code>SignInScene</code>.
	 */
	@FXML
	public final void loadSignIn() {
    	if (isRunning) {
    		timer.cancel();
    		timer.purge();
    	}
	    ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Sign In", ViewNavigator.SIGN_IN_SCENE);
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