/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.orangecoastcollege.cs272.controller.Controller;
import edu.orangecoastcollege.cs272.model.Item;
import edu.orangecoastcollege.cs272.model.Player;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * The <code>InventoryScreen</code> class displays the current <code>Inventory</code> to the <code>Player</code>,
 * where they may then use <code>Item</code>s as needed and change the music.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public class InventoryScreen implements Initializable {
	private Controller controller = Controller.getInstance();
	private Player player = controller.getPlayer();
	private Rectangle HPBar;
	private Rectangle MPBar;
	private Label hpLabel = new Label(String.valueOf((int)controller.getPlayer().getMaxHP()) + "/" + String.valueOf((int)controller.getPlayer().getMaxHP()));
	private Label mpLabel = new Label(String.valueOf((int)controller.getPlayer().getMaxMP()) + "/" + String.valueOf((int)controller.getPlayer().getMaxMP()));;

	@FXML
	private AnchorPane mainContainer;
	@FXML
	private ImageView equipedArmor;
	@FXML
	private ImageView equipedWeapon;

	@FXML
	private ImageView slot1;
	@FXML
	private ImageView slot2;
	@FXML
	private ImageView slot3;
	@FXML
	private ImageView slot4;
	@FXML
	private ImageView slot5;
	@FXML
	private ImageView slot6;
	@FXML
	private ImageView slot7;
	@FXML
	private ImageView slot8;
	@FXML
	private ImageView slot9;
	@FXML
	private ImageView slot10;
	@FXML
	private ImageView slot11;
	@FXML
	private ImageView slot12;
	
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

	private ArrayList<ImageView> inventorySlots;

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
		
		inventorySlots = new ArrayList<>(12);
		insertImageViewsIntoArray();
		for (int i = 0; i < 12; i++) {
			inventorySlots.get(i).setOnMouseClicked(myHandler);
		}
		BackgroundImage BI = new BackgroundImage(
				new Image(new File(MainView.MENU_BACKGROUND_IMAGE_PATH).toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		mainContainer.setBackground(new Background(BI));
		initializeViewsToModel();

	}

	private void initializeViewsToModel() {
		// HPBar setup
		HPBar = new Rectangle(GameScene.MAX_BAR_WIDTH * (player.getCurrentHP() / player.getMaxHP()),
				GameScene.BAR_HEIGHT, Color.RED);
		HPBar.relocate(20, 30);
		String currHPStr = String.valueOf((int)controller.getPlayer().getCurrentHP());
		String maxHPStr = String.valueOf((int)controller.getPlayer().getMaxHP());
		// HPLabel setup
		hpLabel = new Label(currHPStr + "/" + maxHPStr);
		hpLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
		hpLabel.setTextFill(Color.RED);
		hpLabel.relocate(15, 10);
		// hpLabel.setBorder(new Border(new BorderStroke(, arg1, arg2, arg3)));
		String currMPStr = String.valueOf(controller.getPlayer().getCurrentMP());
		String maxMPStr = String.valueOf(controller.getPlayer().getMaxMP());
		// MPLabel setup
		mpLabel = new Label(currMPStr + "/" + maxMPStr);
		mpLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
		mpLabel.setTextFill(Color.BLUE);
		mpLabel.relocate(15, 40);
		// MPBar setup
		MPBar = new Rectangle(GameScene.MAX_BAR_WIDTH * (player.getCurrentMP() / player.getMaxMP()),
				GameScene.BAR_HEIGHT, Color.BLUE);
		MPBar.relocate(20, 60);
		mainContainer.getChildren().addAll(HPBar, MPBar, hpLabel, mpLabel);

		List<Item> itemsList = controller.getCurrentPlayerItems();
		int numberOfPlayerItem = itemsList.size();
		for (int i = 0; i < numberOfPlayerItem && i < 12; i++) {
			inventorySlots.get(i).setImage(itemsList.get(i).getImage());
		}
		for (int i = numberOfPlayerItem; i < inventorySlots.size(); i++) {
			inventorySlots.get(i).setImage(new Image(new File(MainView.INVENTORY_SLOT_BORDER_PATH).toURI().toString()));
		}
		System.out.println(controller.getPlayer().getArmor());
		equipedArmor.setImage(controller.getPlayer().getArmor().getImage());
		equipedWeapon.setImage(controller.getPlayer().getWeapon().getImage());
	}
	
	private void synchronizeViewsToModel() {
		String currHPStr = String.valueOf((int)player.getCurrentHP());
		String maxHPStr = String.valueOf((int)player.getMaxHP());
		// HPLabel setup
		hpLabel.setText(currHPStr + "/" + maxHPStr);
		
		String currMPStr = String.valueOf(player.getCurrentMP());
		String maxMPStr = String.valueOf(player.getMaxMP());
		// MPLabel setup
		mpLabel.setText(currMPStr + "/" + maxMPStr);
		
		HPBar.setWidth(GameScene.MAX_BAR_WIDTH * (player.getCurrentHP() / player.getMaxHP()));
		MPBar.setWidth(GameScene.MAX_BAR_WIDTH * (player.getCurrentMP() / player.getMaxMP()));
		List<Item> itemsList = controller.getCurrentPlayerItems();
		int numberOfPlayerItem = itemsList.size();
		for (int i = 0; i < numberOfPlayerItem && i < 12; i++) {
			inventorySlots.get(i).setImage(itemsList.get(i).getImage());
		}
		for (int i = numberOfPlayerItem; i < inventorySlots.size(); i++) {
			inventorySlots.get(i).setImage(new Image(new File(MainView.INVENTORY_SLOT_BORDER_PATH).toURI().toString()));
		}
	}

	/**
	 * Returns to the current game (level).
	 */
	@FXML
	void back() {
		ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.currentGameScene);
		ViewNavigator.currentGameScene.unpauseGame();
	}
	
	/**
	 * Saves the current progress.
	 */
	@FXML
	void save() {
		controller.saveGame();
	}
	
	/**
	 * Adds the images of the <code>Item</code> into the slots.
	 */
	private void insertImageViewsIntoArray() {
		inventorySlots.add(slot1);
		inventorySlots.add(slot2);
		inventorySlots.add(slot3);
		inventorySlots.add(slot4);
		inventorySlots.add(slot5);
		inventorySlots.add(slot6);
		inventorySlots.add(slot7);
		inventorySlots.add(slot8);
		inventorySlots.add(slot9);
		inventorySlots.add(slot10);
		inventorySlots.add(slot11);
		inventorySlots.add(slot12);
	}

	final private EventHandler<MouseEvent> myHandler = new EventHandler<MouseEvent>() {
		/**
		 * Sets up the needed MouseEvent handler for the <code>Inventory</code>, where each individual
		 * <code>Item</code> slot has their respective actions performed if clicked.
		 */
		@Override
		public void handle(MouseEvent event) {
			ImageView slotSelected = (ImageView) event.getSource();
			int slotIndex;
			int size = controller.getCurrentPlayerItems().size();
			for (slotIndex = 0; slotIndex < 12; slotIndex++) {

				if (slotSelected == inventorySlots.get(slotIndex)) {
					if (slotIndex < size) {
						slotSelected
								.setImage(new Image(new File(MainView.INVENTORY_SLOT_BORDER_PATH).toURI().toString()));
						controller.playerUsedItem(slotIndex);
						synchronizeViewsToModel();
					}
					break;
				}
			}
		}
	};
	
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