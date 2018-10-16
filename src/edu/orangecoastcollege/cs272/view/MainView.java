/**
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.util.Optional;
import java.util.logging.LogManager;

import edu.orangecoastcollege.cs272.controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * The <code>MainView</code> class extends the <code>Application</code> class. The main purpose
 * of the <code>MainView</code> class is to provide the necessary foundations for the scenes and
 * background functionality, including calling up the <code>Controller</code> for the first time
 * to initialize the databases, setting up the music players, as well as providing the primary stage.
 *
 * @author Vincent Nguyen
 * @author Vichet Meng
 * @author Vu Nguyen
 * @version 1.0
 */
public final class MainView extends Application {
	/**
	 * The name of the program.
	 */
	public static final String PROGRAM_NAME = "Dat One Adventure Game";
	/**
	 * Title of the <code>CHaracterCreationScene</code>.
	 */
	public static final String CHAR_CREATION_SCENE_NAME = PROGRAM_NAME + " : " + "Character Creation.";
	/**
	 * As long as the user is not playing the game itself, (as in not loading <code>Level</code>, isPlaying
	 * remains false so that pressing the escape button won't load up  the exit <code>AlertDialog</code>.
	 */
	public static boolean isPlaying = true;
	private static final String MAIN_ICON_PATH = "resources/images/equip_armor/plate.png.";
	/**
	 * The menu background String path
	 */
    public static final String MENU_BACKGROUND_IMAGE_PATH = "resources/images/menu/menu_background.jpg";
	/**
	 * The rock String path
	 */
    public static final String ROCK_BG = "resources/images/background/rock.png";
	/**
	 * The purple mage image path
	 */
	public static final String PURPLE_MAGE_PATH = "resources/images/player/static-purple.png";
	/**
	 * The blue mage image path
	 */
	public static final String BLUE_MAGE_PATH = "resources/images/player/static.png";
	/**
	 * The purple mage movement image path
	 */
	public static final String PURPLE_MAGE_MOVE_PATH = "resources/images/player/move-purple.png";
	/**
	 * The blue mage movement image path
	 */
	public static final String BLUE_MAGE_MOVE_PATH = "resources/images/player/move.png";
	/**
	 * The game background image path
	 */
	public static final String GAME_BACKGROUND_PATH = "resources/images/background/grass-bg.jpg";
	/**
	 * The inventory slot image path
	 */
	public static final String INVENTORY_SLOT_BORDER_PATH = "resources/images/misc/border.jpeg";
	private static Controller controller;

    @SuppressWarnings("unused")
	private static final String[] loadingScreenTexts = new String[] {
    		"Shovelling coal into the server...",
    		"Notifying field agents. Children acquired.",
    		"Scanning your device for credit card details. Please be patient...",
    		"Dividing eternity by zero. Please be patient...",
    		"Just stalling to simulate activity...",
    		"Commencing infinite loop... (This may take some time).",
    		};

    static MediaPlayer backgroundMusicPlayer;
    static MediaPlayer clickSoundPlayer;
    static MediaPlayer typingSoundPlayer;

    static final double DEFAULT_MUSIC_LEVEL = 0.5;
    static final double DEFAULT_SOUND_LEVEL = 0.7;

    private static final String GREETINGS_MUSIC_PATH = "resources/sounds/music/greetings.mp3";
    private static final String HEROIC_DESIRE_MUSIC_PATH = "resources/sounds/music/heroic_desire.mp3";
    private static final String GRIMHEART_MUSIC_PATH = "resources/sounds/music/grimheart.mp3";

	static final Media GREETINGS_MUSIC = new Media(new File(GREETINGS_MUSIC_PATH).toURI().toString());
	static final Media HEROIC_DESIRE_MUSIC = new Media(new File(HEROIC_DESIRE_MUSIC_PATH).toURI().toString());
    static final Media GRIMHEART_MUSIC = new Media(new File(GRIMHEART_MUSIC_PATH).toURI().toString());

    static final String CLICK_MUSIC_PATH = "resources/sounds/ambient/typing.mp3";
    static final String TYPING_MUSIC_PATH = "resources/sounds/ambient/typing.mp3";

	static final String MUSIC_OFF_PATH = "resources/images/menu/menu_music_sound_off.png";
	static final String MUSIC_ON_PATH = "resources/images/menu/menu_music_sound_on.png";
	static final String SOUND_ON_PATH = "resources/images/menu/menu_sound_on.png";
	static final String SOUND_OFF_PATH = "resources/images/menu/menu_sound_off.png";

	static final String PREVIOUS_SONG_PATH = "resources/images/menu/menu_previous_song.png";
	static final String PLAY_MUSIC_PATH = "resources/images/menu/menu_play.png";
	static final String PAUSE_MUSIC_PATH = "resources/images/menu/menu_pause.png";
	static final String SKIP_SONG_PATH = "resources/images/menu/menu_skip_song.png";

	static final int FRAME_ANIM_NUM_IMAGES = 4;
	static final String FRAME_ANIM_SIGN_UP_1_PATH = "resources/images/menu/menu_sign_up_picture.png";
	static final String FRAME_ANIM_SIGN_UP_2_PATH = "resources/images/menu/menu_sign_up_picture_2.png";
	static final String FRAME_ANIM_SIGN_UP_3_PATH = "resources/images/menu/menu_sign_up_picture_3.png";
	static final String FRAME_ANIM_SIGN_UP_4_PATH = "resources/images/menu/menu_sign_up_picture_4.png";

	static final String ONE_STAR_PATH = "resources/images/codex/codex_one_star.png";
	static final String TWO_STAR_PATH = "resources/images/codex/codex_two_star.png";
	static final String THREE_STAR_PATH = "resources/images/codex/codex_three_star.png";
	static final String FOUR_STAR_PATH = "resources/images/codex/codex_four_star.png";
	static final String FIVE_STAR_PATH = "resources/images/codex/codex_five_star.png";

	static final String FIELD_REQUIRED = "Field Required";

	static final String OOPS_PATH = "resources/images/dialog/dialog_oops.png";
	static final String ERROR_DIALOG_ICON = "resources/images/dialog/dialog_icon.png";

    private Alert exitAlert;
    private static final String ALERT_DIALOG_CONFIRMATION_PROMPT = "Are you sure you want to exit? There's more bad guys to slay!";
    private Optional<ButtonType> result;

	//private GameScene gameScene;

    /**
     * Starts up the application, providing the necessary stages, icons, music players, as well
     * as initializing the <code>Controller</code>.
     * @param primaryStage The main stage in which the majority of scenes will be displayed on.
     */
	@SuppressWarnings("incomplete-switch")
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.getIcons().add(new Image(new File(MAIN_ICON_PATH).toURI().toString()));

		// Prevents the API out-dated message from being printed to the console.
		LogManager.getLogManager().reset();

		backgroundMusicPlayer = new MediaPlayer(GREETINGS_MUSIC);
		backgroundMusicPlayer.setAutoPlay(true);
		backgroundMusicPlayer.setVolume(DEFAULT_MUSIC_LEVEL);
		backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
			/**
			 * Runs a continuous loop of the current song once it ends.
			 */
		    @Override
			public void run() {
				backgroundMusicPlayer.seek(Duration.ZERO);
			}
		});

		final Media clickSound = new Media(new File(CLICK_MUSIC_PATH).toURI().toString());
		clickSoundPlayer = new MediaPlayer(clickSound);
		clickSoundPlayer.setVolume(DEFAULT_SOUND_LEVEL);

		final Media typingSound = new Media(new File(TYPING_MUSIC_PATH).toURI().toString());
		typingSoundPlayer = new MediaPlayer(typingSound);
		typingSoundPlayer.setVolume(DEFAULT_SOUND_LEVEL);

		ViewNavigator.setStage(primaryStage);
		ViewNavigator.loadScene(PROGRAM_NAME, ViewNavigator.STARTUP_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Sign In", ViewNavigator.SIGN_IN_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Join Now!", ViewNavigator.SIGN_UP_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Codex", ViewNavigator.CODEX_MENU_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Main Menu", ViewNavigator.MAIN_MENU_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Resources", ViewNavigator.RESOURCE_MENU_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Credits", ViewNavigator.CREDITS_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Scoreboard", ViewNavigator.SCOREBOARD_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Victory!", ViewNavigator.VICTORY_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Scoreboard", ViewNavigator.INVENTORY_SCENE);
		//ViewNavigator.loadScene(PROGRAM_NAME + ": Defeat (There's always next time!)", ViewNavigator.DEFEATED_SCENE);
		//ViewNavigator.loadScene(MainView.PROGRAM_NAME, new GameScene(1));

		exitAlert = new Alert(AlertType.CONFIRMATION);
		exitAlert.setTitle(PROGRAM_NAME + ": Exit Confirmation");
		exitAlert.setHeaderText(ALERT_DIALOG_CONFIRMATION_PROMPT);
		exitAlert.initOwner(primaryStage);

		Platform.setImplicitExit(true);
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (final KeyEvent event)-> {
	        switch(event.getCode()) {
	        case ESCAPE:
				if (!isPlaying)
					promptExitVerification(primaryStage, event);
				//else
					//openPauseMenu();
	        	break;
	        }
	    });
	    primaryStage.setOnCloseRequest((final WindowEvent event) -> {
	        try {
				promptExitVerification(primaryStage, event);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    });

		controller = Controller.getInstance();
		controller.setStage(primaryStage);
	}

	/**
	 * Loads up an <code>AlertDialog</code> which then prompts the user with a verification
	 * message, and lets the user confirm whether or not they want to exit the program entirely.
	 * @param stage The stage to close (or not).
	 * @param event The type of event that triggered the dialog.
	 */
	public final void promptExitVerification(final Stage stage, final Event event) {
		result = exitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			MainView.clickSoundPlayer.play();
			MainView.clickSoundPlayer.seek(Duration.ZERO);
	        //stage = (Stage) event.getSource();

			if (controller.getPlayer() != null)
				controller.getPlayer().reset();

			stage.close();
        	System.exit(0);
		} else {
			MainView.clickSoundPlayer.play();
			MainView.clickSoundPlayer.seek(Duration.ZERO);
	        event.consume();
			exitAlert.close();
		}
	}

	/**
	 * Starts up and runs the application.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		System.out.println("Launching " + PROGRAM_NAME + "!\n");
		Application.launch(args);
	}
}