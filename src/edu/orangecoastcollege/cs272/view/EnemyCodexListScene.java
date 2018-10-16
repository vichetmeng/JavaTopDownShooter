/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import edu.orangecoastcollege.cs272.controller.Controller;
import edu.orangecoastcollege.cs272.model.Enemy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * The <code>EnemyCodexListScene</code> class displays a list of all the possible <code>Enemy</code> objects that the
 * <code>Player</code> could encounter in the game. It gives a description, alongside with other information
 * including an image of what they look like, their base stats, and difficulty level.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class EnemyCodexListScene implements Initializable {
	private Controller controller = Controller.getInstance();

	@FXML
	private ListView<String> codexListView;
	@FXML
	private ComboBox<String> enemyLevelFilterComboBox;
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

	private List<Enemy> enemyList;
	private ObservableList<String> enemyNamesList;
	private ObservableList<String> filteredList;
	private ObservableList<Enemy> filteredEnemyList;
	
	private static final String SELECT_ENEMY = "All Enemy Levels";
	private static final String LEVEL_1 = "Level 1";
	private static final String LEVEL_2 = "Level 2";
	private static final String LEVEL_3 = "Level 3";
	private static final String LEVEL_4 = "Level 4";
	private static final String LEVEL_5 = "Level 5";

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

        enemyList = controller.getAllEnemies();
        
    	final Set<Enemy> duplicateClearStack = new HashSet<>();
    	duplicateClearStack.addAll(enemyList);
    	enemyList.clear();
    	enemyList.addAll(duplicateClearStack);
        enemyNamesList = FXCollections.observableArrayList();
        
    	final Iterator<Enemy> enemyListIT = enemyList.iterator();
    	Enemy enemy;
        
    	while(enemyListIT.hasNext()) {
    		enemy = (Enemy) enemyListIT.next();
        	enemyNamesList.add(enemy.getName());
        }
    	
    	Collections.sort(enemyList, new Comparator<Enemy>(){
		@Override
		public int compare(Enemy first, Enemy second) {
 		     return first.getName().compareTo(second.getName());
		}
  		});
        
        controller.populateEnemyList(enemyList, enemyNamesList, codexListView);
        
        filteredList = FXCollections.observableArrayList();
        filteredList.addAll(SELECT_ENEMY, LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4,
        		LEVEL_5);
        enemyLevelFilterComboBox.setItems(filteredList);
        enemyLevelFilterComboBox.setCellFactory(c->new ListCell<String>() {
            private ImageView levelCellImageView = new ImageView();
            @Override
            protected void updateItem(final String level, final boolean isEmpty) {
                super.updateItem(level, isEmpty);
                setText(level);
                if (isEmpty)
                    setGraphic(null);
                else {
                    setText(level);
                    if (LEVEL_1.equals(level))        	
                    	levelCellImageView.setImage(new Image(new File(MainView.ONE_STAR_PATH)
                    			.toURI().toString()));
                    else if (LEVEL_2.equals(level))        	
                    	levelCellImageView.setImage(new Image(new File(MainView.TWO_STAR_PATH)
                    			.toURI().toString()));
                    else if (LEVEL_3.equals(level))        	
                    	levelCellImageView.setImage(new Image(new File(MainView.THREE_STAR_PATH)
                    			.toURI().toString()));
                    else if (LEVEL_4.equals(level))        	
                    	levelCellImageView.setImage(new Image(new File(MainView.FOUR_STAR_PATH)
                    			.toURI().toString()));
                    else if (LEVEL_5.equals(level))
                    	levelCellImageView.setImage(new Image(new File(MainView.FIVE_STAR_PATH)
                    			.toURI().toString()));
                    levelCellImageView.setFitHeight(30);
                    levelCellImageView.setPreserveRatio(true);
                    setGraphic(levelCellImageView);
                }
            }
        });
	}

	/**
	 * Gets the specific position of the selected <code>Enemy</code> from the list and loads up the
	 * detailed enemy page.
	 */
	@FXML
	public final void displayEnemyDetails() {
		playMenuClickSound();
		final String enemyName = codexListView.getSelectionModel().getSelectedItem();
		
		if (enemyName != null) {
			final Iterator<Enemy> enemyListIT = enemyList.iterator();
			Enemy enemy;

			while (enemyListIT.hasNext()) {
				enemy = (Enemy) enemyListIT.next();
				if (enemy.getName().equals(enemyName)) {
					controller.setSelectedCodexEnemy(enemy);
					break;
				}
			}

			ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": " + enemyName, ViewNavigator.ENEMY_CODEX_DETAILS_SCENE);
		}
	}

	/**
	 * Filters out <code>Enemy</code> objects from the list according to the selected difficulty level and displays
	 * them accordingly.
	 */
	@FXML
	public final void filter() {
		playMenuClickSound();
		final String selectedLevelChoice = enemyLevelFilterComboBox.getSelectionModel().getSelectedItem();
		int level = 0;
		if (selectedLevelChoice == null || SELECT_ENEMY.equals(selectedLevelChoice)
				|| selectedLevelChoice.replaceAll("\\s", "").isEmpty()) {
	        controller.populateEnemyList(enemyList, enemyNamesList, codexListView);
			return;
		}
		else {
			if (LEVEL_1.equals(selectedLevelChoice))
				level = 1;
			else if (LEVEL_2.equals(selectedLevelChoice))
				level = 2;
			else if (LEVEL_3.equals(selectedLevelChoice))
				level = 3;
			else if (LEVEL_4.equals(selectedLevelChoice))
				level = 4;
			else
				level = 5;
			filteredEnemyList = FXCollections.observableArrayList(controller.getDistinctEnemiesByLevel(level));
		}
		
		filteredList = FXCollections.observableArrayList();
		
    	final Set<Enemy> duplicateClearStack = new HashSet<>();
    	duplicateClearStack.addAll(filteredEnemyList);
    	filteredEnemyList.clear();
    	filteredEnemyList.addAll(duplicateClearStack);
		
		if (level > 0) {
			Enemy enemy;
			final Iterator<Enemy> enemyIT = filteredEnemyList.iterator();
			while (enemyIT.hasNext()) {
				enemy = enemyIT.next();
				if (enemy.getLevel() == level)
					filteredList.add(enemy.getName());
			}
		}
		
		controller.populateEnemyList(enemyList, filteredList, codexListView);
	}
	
	/**
	 * Loads up the <code>MainMenuScene</code>.
	 */
	@FXML
	public final void loadMainMenu() {	
		playMenuClickSound();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME, ViewNavigator.MAIN_MENU_SCENE);
	}
	
	/**
	 * Loads up the <code>CodexMenuScene</code>.
	 */
	@FXML
	public final void loadCodexMenu() {
		playMenuClickSound();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Codex", ViewNavigator.CODEX_MENU_SCENE);
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