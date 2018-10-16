/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The <code>ViewNavigator</code> class allows the program to load and traverse between multiple different
 * scenes in the program.
 *
 * @author Vincent Nguyen
 * @author Vu Nguyen
 * @author Vichet Meng
 * @version 1.0
 */
public final class ViewNavigator {
	/**
	 * Name of the scene where users may sign in.
	 */
	public static final String SIGN_UP_SCENE = "SignUpScene.fxml";
	/**
	 * Name of the scene where users may sign up.
	 */
	public static final String SIGN_IN_SCENE = "SignInScene.fxml";
	/**
	 * Name of the scene where the main options/menu is displayed.
	 */
	public static final String MAIN_MENU_SCENE = "MainMenuScene.fxml";
	/**
	 * Name of the scene where the codex menu is displayed.
	 */
	public static final String CODEX_MENU_SCENE = "CodexMenuScene.fxml";
	/**
	 * Name of the scene where users may view a codex of all the <code>Enemy</code> types.
	 */
	public static final String ENEMY_CODEX_LIST_SCENE = "EnemyCodexListScene.fxml";
	/**
	 * Name of the scene where users a specific description of the <code>Enemy</code> is displayed.
	 */
	public static final String ENEMY_CODEX_DETAILS_SCENE = "EnemyCodexDetailsScene.fxml";
	/**
	 * Name of the scene where users may view a codex of all the <code>Armor</code> types.
	 */
	public static final String ARMOR_CODEX_LIST_SCENE = "ArmorCodexListScene.fxml";
	/**
	 * Name of the scene where users a specific description of the <code>Armor</code> is displayed.
	 */
	public static final String ARMOR_CODEX_DETAILS_SCENE = "ArmorCodexDetailsScene.fxml";
	/**
	 * Name of the scene where users may view a codex of all the <code>Weapon</code> types.
	 */
	public static final String WEAPON_CODEX_LIST_SCENE = "WeaponCodexListScene.fxml";
	/**
	 * Name of the scene where users a specific description of the <code>Weapon</code> is displayed.
	 */
	public static final String WEAPON_CODEX_DETAILS_SCENE = "WeaponCodexDetailsScene.fxml";
	/**
	 * Name of the scene where users may view the creators and credits.
	 */
	public static final String CREDITS_SCENE = "CreditsScene.fxml";
	/**
	 * Name of the scene which first loads in the program.
	 */
    public static final String STARTUP_SCENE = "StartUpScene.fxml";
	/**
	 * Name of the scene where users may change various program options.
	 */
    public static final String OPTION_SCENE = "OptionView.fxml";
	/**
	 * Name of the scene where users may select a desired <code>Character</code>.
	 */
    public static final String CHARACTER_SELECT_SCENE = "CharacterSelectView.fxml";
	/**
	 * Name of the scene where users may create a new <code>Character</code>.
	 */
    public static final String CHARACTER_CREATE_SCENE = "CharacterCreateView.fxml";
	/**
	 * Name of the scene where users may view their current <code>Item</code> inventory.
	 */
    public static final String INVENTORY_SCENE = "InventoryScreen.fxml";
	/**
	 * Name of the scene where users may view their current <code>Equipment</code> inventory.
	 */
    public static final String EQUIPMENT_SCENE = "";
	/**
	 * Name of the scene that displays when user characters die.
	 */
    public static final String DEFEATED_SCENE = "DefeatedScene.fxml";
	/**
	 * Name of the scene that displays when user characters wins.
	 */
    public static final String VICTORY_SCENE = "VictoryScene.fxml";
	/**
	 * Name of the scene that displays the high-scores board.
	 */
    public static final String SCOREBOARD_SCENE = "ScoreboardScene.fxml";
	/**
	 * Name of the scene that displays menu with the codex, scoreboard, and credits.
	 */
    public static final String RESOURCE_MENU_SCENE = "ResourcesMenuScene.fxml";
    /**
     * Loads up the <code>PauseScene</code>.
     */
    public static final String PAUSE_SCENE = "PauseScene.fxml";
    
    /**
     * The current main/primary stage.
     */
	public static Stage mainStage;
	
	/**
	 * The <code>GameScene</code>, (game level).
	 */
	public static GameScene currentGameScene;

	/**
	 * Sets the current main stage to the specified stage.
	 * @param stage The stage to set.
	 */
	public static void setStage(final Stage stage) {
		mainStage = stage;
	}

	/**
	 * Given an FXML scene along with a title, a new scene is set and displayed.
	 * @param title The title to set.
	 * @param sceneFXML The FXML scene to load.
	 */
	public static void loadScene(final String title, final String sceneFXML) {
		try {
			mainStage.setTitle(title);
			final Scene scene = new Scene(FXMLLoader.load(ViewNavigator.class.getResource(sceneFXML)));
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Given an FXML scene along with a title, a new scene is set and displayed.
	 * @param title The title to set.
	 * @param sceneFXML The FXML scene to load.
	 */
	public static void loadScene(final String title, final GameScene gameScene) {
 		currentGameScene = gameScene;
 		mainStage.setTitle(title);
		mainStage.setScene(currentGameScene.getMainScene());
		mainStage.show();
	}
}