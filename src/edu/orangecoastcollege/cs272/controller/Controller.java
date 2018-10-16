/**
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import edu.orangecoastcollege.cs272.model.Armor;
import edu.orangecoastcollege.cs272.model.CharacterClass;
import edu.orangecoastcollege.cs272.model.DBModel;
import edu.orangecoastcollege.cs272.model.Enemy;
import edu.orangecoastcollege.cs272.model.Item;
import edu.orangecoastcollege.cs272.model.Player;
import edu.orangecoastcollege.cs272.model.Potion;
import edu.orangecoastcollege.cs272.model.PotionType;
import edu.orangecoastcollege.cs272.model.Projectile;
import edu.orangecoastcollege.cs272.model.Role;
import edu.orangecoastcollege.cs272.model.Score;
import edu.orangecoastcollege.cs272.model.Skill;
import edu.orangecoastcollege.cs272.model.SkillType;
import edu.orangecoastcollege.cs272.model.User;
import edu.orangecoastcollege.cs272.model.Weapon;
import edu.orangecoastcollege.cs272.view.GameScene;
import edu.orangecoastcollege.cs272.view.MainView;
import edu.orangecoastcollege.cs272.view.ViewNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The <code>Controller</code> class keep track of all game object, database table and scene.
 * 
 * @author Vincent Nguyen
 * @author Vichet Meng
 * @author Vu Nguyen
 * @version 1.0
 */
public final class Controller {
	private static Controller theOne;
	private Stage mPrimaryStage;

	/*********************************
	 * ALL *INSERT NAME HERE* LISTS
	 **********************************/

	private ObservableList<User> mAllUsersList;
	private ObservableList<Weapon> mAllWeaponsList;
	private ObservableList<Armor> mAllArmorList;
	private ObservableList<Item> mAllItemsList;
	private ObservableList<Enemy> mAllEnemyList;
	private ObservableList<Player> mAllCharacterList;
	private ObservableList<Score> mAllScores;
	private List<Item> mCurrentPlayerItem;

	/*********************************
	 * GameScene STUFF
	 **********************************/

	public static final double WINDOW_WIDTH = 1000;
	public static final double WINDOW_HEIGHT = WINDOW_WIDTH * 9.0 / 16.0;
	public static final int ENEMY_COUNT = 3;
	public static final String PLAYER_SHOT_NAME = "Player Shot";
	public static final String ENEMY_SHOT_NAME = "Enemy Shot";

	private Player mCurrentPlayer;
	private List<Enemy> mEnemysList;
	private List<Projectile> mAllProjectiles;
	private int mCurrentLevel;

	private static final String TROLL_PATH = "resources/images/enemy/troll_move.png";
	private static final String PROJECTILE_PATH = "resources/images/player/attack/projectile.png";

	/*********************************
	 * CSV FILE STRINGS/PATHS
	 **********************************/

	private static final String WEAPONS_DATA_FILE = "resources/game_data/weapons_lite.csv";
	private static final String ARMOR_DATA_FILE = "resources/game_data/armor_lite.csv";
	private static final String ITEMS_DATA_FILE = "resources/game_data/item_lite.csv";
	private static final String ENEMY_DATA_FILE = "resources/game_data/enemy_lite.csv";
	private static final String CHARACTER_CLASS_DATA_FILE = "resources/game_data/character_class.csv";
	private static final String SKIPPING_CSV_ROW = "Skipping bad CSV row: ";

	/*********************************
	 * DATABASE TABLES
	 **********************************/

	private DBModel mUserDB;
	private DBModel mWeaponsDB;
	private DBModel mArmorDB;
	private DBModel mItemsDB;
	private DBModel mEnemyDB;
	private DBModel mScoreDB;
	private DBModel mPlayerItemsDB;
	private DBModel mCharactersDB;
	private DBModel mCharacterClassDB;
	private DBModel mSaveDB;
	private static final String DB_NAME = "adventure_game.db";

	private static final String INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
	private static final String INTEGER = "INTEGER";
	private static final String REAL = "REAL";
	private static final String TEXT = "TEXT";
	@SuppressWarnings("unused")
	private static final String BLOB = "BLOB";

	private static final String CHARACTER_TABLE_NAME = "player_table";
	private static final String[] CHARACTER_FIELD_NAMES = { "character_id", "name", "class_id", "weapon_id", "armor_id",
			"power", "defense", "speed", "score" };
	private static final String[] CHARACTER_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "INTEGER", "INTEGER",
			"INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER" };

	private static final String CHARACTER_INVENTORY_TABLE_NAME = "inventory_table";
	private static final String[] CHARACTER_INVENTORY_FIELD_NAME = { "player_id", "item_id" };
	private static final String[] CHARACTER_INVENTORY_FIELD_TYPES = { "INTEGER", "INTEGER" };

	private static final String CHARACTER_CLASS_TABLE_NAME = "character_class";
	private static final String[] CHARACTER_CLASS_FIELD_NAME = { "class_id", "name", "image_path" };
	private static final String[] CHARACTER_CLASS_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT" };

	private static final String USER_TABLE_NAME = "user";
	private static final String[] USER_FIELD_NAMES = { "id", "username", "role", "password", "character_id" };
	private static final String[] USER_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT", "INTEGER" };

	private static final String WEAPON_TABLE_NAME = "weapons";
	private static final String[] WEAPON_FIELD_NAMES = { "weapon_id", "name", "description", "worth", "rarity_value",
			"attack_points", "image_uri" };
	private static final String[] WEAPON_FIELD_TYPES = { INTEGER_PRIMARY_KEY, TEXT, TEXT, INTEGER, INTEGER, INTEGER,
			TEXT };

	private static final String ARMOR_TABLE_NAME = "armor";
	private static final String[] ARMOR_FIELD_NAMES = { "armor_id", "name", "description", "worth", "rarity_value",
			"armor_rating", "image_uri" };
	private static final String[] ARMOR_FIELD_TYPES = { INTEGER_PRIMARY_KEY, TEXT, TEXT, INTEGER, INTEGER, INTEGER,
			TEXT };

	private static final String ITEM_TABLE_NAME = "items";
	private static final String[] ITEM_FIELD_NAMES = { "item_id", "name", "description", "weight", "power", "usable",
			"image_path" };
	private static final String[] ITEM_FIELD_TYPES = { INTEGER_PRIMARY_KEY, TEXT, TEXT, INTEGER, INTEGER, INTEGER,
			TEXT };

	private static final String SAVE_TABLE_NAME = "saves";
	private static final String[] SAVE_FIELD_NAMES = { "player_id", "lvl_id" };
	private static final String[] SAVE_FIELD_TYPES = { INTEGER_PRIMARY_KEY, INTEGER };

	private static final String SCORE_TABLE_NAME = "scores";
	private static final String[] SCORE_FIELD_NAMES = { "score_id", "name", "score_points", "time" };
	private static final String[] SCORE_FIELD_TYPES = { INTEGER_PRIMARY_KEY, TEXT, INTEGER, REAL };

	private static final String ENEMY_TABLE_NAME = "enemies";
	private static final String[] ENEMY_FIELD_NAMES = { "id", "name", "description", "attack", "defense", "speed",
			"max_hp", "move_image_path", "move_wounded_image_path", "death_image_path", "codex_image", "level" };
	private static final String[] ENEMY_FIELD_TYPES = { INTEGER_PRIMARY_KEY, TEXT, TEXT, REAL, REAL, REAL, REAL, TEXT,
			TEXT, TEXT, TEXT, INTEGER };

	@SuppressWarnings("unused")
	private static final String USER_PLAYER_NAME = "user_player";
	@SuppressWarnings("unused")
	private static final String[] USER_PLAYER_FIELD_NAMES = { "user_id", "player_id" };
	@SuppressWarnings("unused")
	private static final String[] USER_PLAYER_FIELD_TYPES = { INTEGER, INTEGER };

	@SuppressWarnings("unused")
	private static final String PLAYER_ARMOR_TABLE_NAME = "player_armor";
	@SuppressWarnings("unused")
	private static final String[] PLAYER_ARMOR_FIELD_NAMES = { "player_id", "armor_id" };
	@SuppressWarnings("unused")
	private static final String[] PLAYER_ARMOR_FIELD_TYPES = { INTEGER, INTEGER };

	@SuppressWarnings("unused")
	private static final String PLAYER_WEAPON_TABLE_NAME = "player_weapon";
	@SuppressWarnings("unused")
	private static final String[] PLAYER_WEAPON_FIELD_NAMES = { "player_id", "weapon_id" };
	@SuppressWarnings("unused")
	private static final String[] PLAYER_WEAPON_FIELD_TYPES = { INTEGER, INTEGER };

	/*********************************
	 * SIGN IN STRINGS INITIALIZATION
	 **********************************/

	/**
	 * If the <code>User</code> enters valid information and is able to sign
	 * in/sign up.
	 */
	public static final String SUCCESS = "SUCCESS";
	/**
	 * If the <code>User</code> enters information where both the password and
	 * username is invalid.
	 */
	public static final String COMBINATION_INCORRECT = "Invalid username/password combination.";
	/**
	 * If the <code>User</code> enters an incorrect password but a valid
	 * username.
	 */
	public static final String PASSWORD_INCORRECT = "Entered password is incorrect. Please try again.";

	private User mCurrentUser;
	private static final Role DEFAULT_ROLE = Role.STANDARD;

	/*********************************
	 * SIGN UP STRINGS INITIALIZATION
	 **********************************/

	public static final String USER_EXISTS = "Entered username already exists. Please enter another one.";
	public static final String ACCOUNT_CREATION_FAIL = "Account creation failed. Please try again.";
	public static final String USERNAME_DOES_NOT_MEET_CRITERIA = "Entered username does not meet the following criteria. Please try again.";
	public static final String PASSWORD_DOES_NOT_MEET_CRITERIA = "Entered password does not meet the following criteria. Please try again.";
	public static final String PASSWORDS_DO_NOT_MATCH = "Entered passwords do not match. Please try again.";
	public static final String PASSWORD_SAME_AS_USER = "Entered password cannot be the same as the username. Please try again.";

	/*********************************
	 * CODEX STRINGS/PATHS
	 **********************************/

	private Enemy mSelectedCodexEnemy;
	private Armor mSelectedCodexArmor;
	private Weapon mSelectedCodexWeapon;

	/*********************************
	 * CHARACTER STRINGS
	 **********************************/

	public static final String EMPTY_CHARACTER_ID = "-1";
	public static final String BLUE_MAGE_CLASS_NAME = "Blue Mage";
	public static final String PURPLE_MAGE_CLASS_NAME = "Purple Mage";
	public static final String DEFAULT_WEAPON_ID = "1";

	/*********************************
	 * CONTROLLER INITIALIZATION
	 **********************************/

	private boolean initialize = false;

	private Controller() {
	}

	/**
	 * On the first initial call, the <code>Controller</code> initializes all
	 * databases and loads up all necessary background data/services before
	 * being having its reference returned.
	 * 
	 * @return A reference to the singleton <code>Controller</code>.
	 */
	public static synchronized Controller getInstance() {
		if (theOne == null) {
			theOne = new Controller();
			try {
				System.out.println("Loading User Database...");
				theOne.mAllUsersList = FXCollections.observableArrayList();
				theOne.mUserDB = new DBModel(DB_NAME, USER_TABLE_NAME, USER_FIELD_NAMES, USER_FIELD_TYPES);
				// theOne.mUserDB.deleteAllRecords();
				final List<List<String>> userResultList = theOne.mUserDB.getAllRecords();

				int id;
				String username;
				Role role;
				String playerId;
				final Iterator<List<String>> userResultListIT = userResultList.iterator();
				List<String> user;

				while (userResultListIT.hasNext()) {
					user = userResultListIT.next();

					id = Integer.parseInt(user.get(0));
					username = user.get(1);
					role = Role.valueOf(user.get(2));
					playerId = user.get(4);
					theOne.mAllUsersList.add(new User(id, username, playerId, role));
				}
				theOne.mAllUsersList.forEach(System.out::println);

				System.out.println("\nLoading Weapon Database...");
				theOne.mWeaponsDB = new DBModel(DB_NAME, WEAPON_TABLE_NAME, WEAPON_FIELD_NAMES, WEAPON_FIELD_TYPES);
				if (theOne.initialize) {
					theOne.mWeaponsDB.deleteAllRecords();
					theOne.initializeWeaponsDBFromFile();
				}
				theOne.mAllWeaponsList = FXCollections.observableArrayList();

				final List<List<String>> weaponResultList = theOne.mWeaponsDB.getAllRecords();
				final Iterator<List<String>> weaponResultListIT = weaponResultList.iterator();
				List<String> weapon;

				int weaponID;
				String weaponName;
				String weaponDescription;
				int weaponWorth;
				int weaponRarity;
				int attackPoints;
				Image weaponImage;

				while (weaponResultListIT.hasNext()) {
					weapon = weaponResultListIT.next();

					weaponID = Integer.parseInt(weapon.get(0));
					weaponName = weapon.get(1);
					weaponDescription = weapon.get(2);
					weaponWorth = Integer.parseInt(weapon.get(3));
					weaponRarity = Integer.parseInt(weapon.get(4));
					attackPoints = Integer.parseInt(weapon.get(5));
					weaponImage = new Image(new File(weapon.get(6)).toURI().toString());
					theOne.mAllWeaponsList.add(new Weapon(weaponID, weaponName, weaponDescription, weaponWorth,
							weaponRarity, attackPoints, weaponImage));
				}
				theOne.mAllWeaponsList.forEach(System.out::println);

				System.out.println("\nLoading Armor Database...");
				theOne.mArmorDB = new DBModel(DB_NAME, ARMOR_TABLE_NAME, ARMOR_FIELD_NAMES, ARMOR_FIELD_TYPES);
				if (theOne.initialize) {
					theOne.mArmorDB.deleteAllRecords();
					theOne.initializeArmorDBFromFile();
				}
				theOne.mAllArmorList = FXCollections.observableArrayList();

				final List<List<String>> armorResultList = theOne.mArmorDB.getAllRecords();
				final Iterator<List<String>> armorResultListIT = armorResultList.iterator();
				List<String> armor;

				int armorID;
				String armorName;
				String armorDescription;
				int armorWorth;
				int armorRarity;
				int armorRating;
				Image armorImage;

				while (armorResultListIT.hasNext()) {
					armor = armorResultListIT.next();

					armorID = Integer.parseInt(armor.get(0));
					armorName = armor.get(1);
					armorDescription = armor.get(2);
					armorWorth = Integer.parseInt(armor.get(3));
					armorRarity = Integer.parseInt(armor.get(4));
					armorRating = Integer.parseInt(armor.get(5));
					armorImage = new Image(new File(armor.get(6)).toURI().toString());
					theOne.mAllArmorList.add(new Armor(armorID, armorName, armorDescription, armorWorth, armorRarity,
							armorRating, armorImage));
				}
				theOne.mAllArmorList.forEach(System.out::println);

				System.out.println("\nLoading Items Database...");
				theOne.mItemsDB = new DBModel(DB_NAME, ITEM_TABLE_NAME, ITEM_FIELD_NAMES, ITEM_FIELD_TYPES);

				if (theOne.initialize) {
					theOne.mItemsDB.deleteAllRecords();
					theOne.initializeItemsDBFromFile();
				}
				theOne.mAllItemsList = FXCollections.observableArrayList();

				final List<List<String>> itemResultList = theOne.mItemsDB.getAllRecords();
				final Iterator<List<String>> itemResultListIT = itemResultList.iterator();
				List<String> item;

				String itemID;
				String itemName;
				String itemDescription;
				double itemWeight;
				PotionType itemType;
				int itemPower;
				boolean itemUsability;
				String itemImagePath;

				while (itemResultListIT.hasNext()) {
					item = itemResultListIT.next();

					itemID = String.valueOf(item.get(0));
					itemName = item.get(1);
					itemDescription = item.get(2);
					itemWeight = Double.parseDouble(item.get(3));
					itemPower = Integer.parseInt(item.get(4));
					itemType = itemID.equals("1") ? PotionType.HP_Potion : PotionType.MP_Potion;
					itemUsability = item.get(5).equals("1") ? true : false;
					itemImagePath = item.get(6);
					theOne.mAllItemsList.add(new Potion(itemID, itemName, itemDescription, itemWeight, itemType,
							itemPower, itemUsability, itemImagePath));
				}
				theOne.mAllItemsList.forEach(System.out::println);

				System.out.println("\nLoading Classes Into Database");
				theOne.mCharacterClassDB = new DBModel(DB_NAME, CHARACTER_CLASS_TABLE_NAME, CHARACTER_CLASS_FIELD_NAME,
						CHARACTER_CLASS_FIELD_TYPES);
				theOne.initializeClassDBFromFile();

				theOne.mSaveDB = new DBModel(DB_NAME, SAVE_TABLE_NAME, SAVE_FIELD_NAMES, SAVE_FIELD_TYPES);
				theOne.mPlayerItemsDB = new DBModel(DB_NAME, CHARACTER_INVENTORY_TABLE_NAME,
						CHARACTER_INVENTORY_FIELD_NAME, CHARACTER_INVENTORY_FIELD_TYPES);

				System.out.println("\nLoading Players In Database");
				theOne.mCharactersDB = new DBModel(DB_NAME, CHARACTER_TABLE_NAME, CHARACTER_FIELD_NAMES,
						CHARACTER_FIELD_TYPES);
				List<List<String>> allCharacters = theOne.mCharactersDB.getAllRecords();
				theOne.mAllCharacterList = FXCollections.observableArrayList();
				for (List<String> rows : allCharacters) {
					List<Item> items = new ArrayList<>();
					Weapon playerWeapon = null;
					Armor playerArmor = null;
					CharacterClass cClass = rows.get(2) == "1" ? CharacterClass.BLUE_MAGE : CharacterClass.PURPLE_MAGE;
					List<List<String>> playerItems = theOne.mPlayerItemsDB.getRecord(rows.get(0));
					for (List<String> playerItemRelation : playerItems) {
						String itemId = playerItemRelation.get(1);
						for (Item i : theOne.mAllItemsList) {
							Potion p = (Potion) i;
							if (p.getId().equals(itemId)) {
								items.add(p);
							}
						}
						// Potion p = new Potion(currentItemInfo.get(0),
						// currentItemInfo.get(1), currentItemInfo.get(2),
						// Integer.parseInt(currentItemInfo.get(3)),
						// currentItemInfo.get(0).equals("1") ?
						// PotionType.HP_Potion : PotionType.MP_Potion,
						// Integer.parseInt(currentItemInfo.get(4)),
						// currentItemInfo.get(5).equals("1") ? true : false,
						// currentItemInfo.get(6));
						// items.add(p);
					}
					for (Weapon currentWeapon : theOne.mAllWeaponsList) {
						System.out.println("Weapon id is");
						if (String.valueOf(currentWeapon.getID()).equals(rows.get(3)))
							playerWeapon = currentWeapon;
					}
					for (Armor currentArmor : theOne.mAllArmorList) {
						if (String.valueOf(currentArmor.getID()).equals(rows.get(4)))
							playerArmor = currentArmor;
					}
					Player newPlayer = new Player(rows.get(0), rows.get(1), Integer.parseInt(rows.get(5)),
							Integer.parseInt(rows.get(6)), Integer.parseInt(rows.get(7)), playerWeapon, playerArmor,
							Integer.parseInt(rows.get(8)), cClass, cClass == CharacterClass.BLUE_MAGE
									? MainView.BLUE_MAGE_MOVE_PATH : MainView.PURPLE_MAGE_MOVE_PATH,
							items);
					// System.out.println(newPlayer);
					theOne.mAllCharacterList.add(newPlayer);
				}

				theOne.mAllCharacterList.forEach(System.out::println);

				System.out.println("\nLoading Enemy Database...");
				theOne.mAllEnemyList = FXCollections.observableArrayList();
				theOne.mEnemyDB = new DBModel(DB_NAME, ENEMY_TABLE_NAME, ENEMY_FIELD_NAMES, ENEMY_FIELD_TYPES);
				if (theOne.initialize) {
					theOne.mEnemyDB.deleteAllRecords();
					theOne.initializeEnemyDBFromFile();
				}
				final List<List<String>> enemyResultList = theOne.mEnemyDB.getAllRecords();

				int enemyID;
				String enemyName;
				String enemyDescription;
				double enemyAttack;
				double enemyDefense;
				double enemyMovementSpeed;
				double enemyMaxHP;
				Image enemyMoveImage;
				Image enemyWoundedImage;
				Image enemyDeathImage;
				Image enemyCodexImage;
				int enemyLevel;

				final Iterator<List<String>> enemyResultListIT = enemyResultList.iterator();
				List<String> enemy;

				while (enemyResultListIT.hasNext()) {
					enemy = enemyResultListIT.next();

					enemyID = Integer.parseInt(enemy.get(0));
					enemyName = enemy.get(1);
					enemyDescription = enemy.get(2);
					enemyAttack = Double.parseDouble(enemy.get(3));
					enemyDefense = Double.parseDouble(enemy.get(4));
					enemyMovementSpeed = Double.parseDouble(enemy.get(5));
					enemyMaxHP = Double.parseDouble(enemy.get(6));
					enemyMoveImage = new Image(new File(enemy.get(7)).toURI().toString());
					enemyWoundedImage = new Image(new File(enemy.get(8)).toURI().toString());
					enemyDeathImage = new Image(new File(enemy.get(9)).toURI().toString());
					enemyCodexImage = new Image(new File(enemy.get(10)).toURI().toString());
					enemyLevel = Integer.parseInt(enemy.get(11));
					theOne.mAllEnemyList.add(new Enemy(enemyID, enemyName, enemyDescription, enemyAttack, enemyDefense,
							enemyMovementSpeed, enemyMaxHP, enemyMoveImage, enemyWoundedImage, enemyDeathImage,
							enemyCodexImage, enemyLevel));
				}
				theOne.mAllEnemyList.forEach(System.out::println);

				System.out.println("\nLoading Scores Database...");
				theOne.mAllScores = FXCollections.observableArrayList();
				theOne.mScoreDB = new DBModel(DB_NAME, SCORE_TABLE_NAME, SCORE_FIELD_NAMES, SCORE_FIELD_TYPES);
				theOne.mScoreDB.deleteAllRecords();
				final List<List<String>> scoreResultList = theOne.mScoreDB.getAllRecords();

				int scoreID;
				String scoreName;
				int rawScore;
				double scoreTime;

				final Iterator<List<String>> scoreRecordIT = scoreResultList.iterator();
				List<String> score;

				while (enemyResultListIT.hasNext()) {
					score = scoreRecordIT.next();

					scoreID = Integer.parseInt(score.get(0));
					scoreName = score.get(1);
					rawScore = Integer.parseInt(score.get(2));
					scoreTime = Integer.parseInt(score.get(3));

					theOne.mAllScores.add(new Score(scoreID, scoreName, rawScore, scoreTime));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			theOne.mEnemysList = new ArrayList<Enemy>();
			theOne.mAllProjectiles = new ArrayList<>();
			theOne.loadNewLevel(1);

			System.out.println("\nFinished Loading up all the necessary resources!");
		}
		return theOne;
	}

	/*********************************
	 * MAIN PREPARATIONS
	 **********************************/

	private final int initializeWeaponsDBFromFile() throws SQLException {
		if (theOne.mWeaponsDB.getRecordCount() > 0)
			return 0;

		int recordsCreated = 0;
		Scanner cin = null;
		try {
			cin = new Scanner(new File(WEAPONS_DATA_FILE));
			cin.nextLine();

			String[] data;
			String[] values;

			while (cin.hasNextLine()) {
				data = cin.nextLine().split(",");

				if (data.length != WEAPON_FIELD_NAMES.length) {
					System.err.println("Weapons - " + SKIPPING_CSV_ROW + data[0]);
					continue;
				}

				values = new String[WEAPON_FIELD_NAMES.length];
				values[0] = data[0];
				values[1] = data[1];
				values[2] = data[2].replaceAll("'", "''");
				values[3] = data[3];
				values[4] = data[4];
				values[5] = data[5];
				values[6] = (data[6]);
				// System.out.println(Arrays.toString(values));
				theOne.mWeaponsDB.createRecord(WEAPON_FIELD_NAMES, values);
				// theOne.mWeaponsDB.createRecord(Arrays.copyOfRange(WEAPON_FIELD_NAMES,
				// 1,
				// WEAPON_FIELD_NAMES.length), values);

				// System.out.println("Controller Line 352: " +
				// Arrays.toString(values));
				recordsCreated++;
			}

			cin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (cin != null)
				cin.close();
		}
		return recordsCreated;
	}

	private final int initializeArmorDBFromFile() throws SQLException {
		if (theOne.mArmorDB.getRecordCount() > 0)
			return 0;

		int recordsCreated = 0;
		Scanner cin = null;
		try {
			cin = new Scanner(new File(ARMOR_DATA_FILE));
			cin.nextLine();

			String[] data;
			String[] values;

			while (cin.hasNextLine()) {
				data = cin.nextLine().split(",");

				if (data.length != ARMOR_FIELD_NAMES.length) {
					System.err.println("Armor - " + SKIPPING_CSV_ROW + data[0]);
					continue;
				}

				values = new String[ARMOR_FIELD_NAMES.length];
				values[0] = data[0];
				values[1] = data[1];
				values[2] = data[2].replaceAll("'", "''");
				values[3] = data[3];
				values[4] = data[4];
				values[5] = data[5];
				values[6] = data[6];
				// System.out.println(Arrays.toString(values));
				theOne.mArmorDB.createRecord(ARMOR_FIELD_NAMES, values);
				// theOne.mArmorDB.createRecord(Arrays.copyOfRange(ARMOR_FIELD_NAMES,
				// 1,
				// ARMOR_FIELD_NAMES.length), values);

				// System.out.println("Controller Line 391: " +
				// Arrays.toString(values));
				recordsCreated++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (cin != null)
				cin.close();
		}
		return recordsCreated;
	}

	private final int initializeClassDBFromFile() {
		int recordsCreated = 0;
		Scanner cin = null;
		try {
			if (theOne.mCharacterClassDB.getRecordCount() > 0)
				return 0;
			try {
				cin = new Scanner(new File(CHARACTER_CLASS_DATA_FILE));
				cin.nextLine();

				String[] data;
				String[] values;

				while (cin.hasNextLine()) {
					data = cin.nextLine().split(",");

					if (data.length != CHARACTER_CLASS_FIELD_NAME.length) {
						System.err.println("Skippng bad CSV row: " + data[0]);
						continue;
					}

					values = new String[CHARACTER_CLASS_FIELD_NAME.length - 1];
					// values[0] = data[0];
					for (int i = 0; i < CHARACTER_CLASS_FIELD_NAME.length - 1; i++) {
						values[i] = data[i + 1];
					}
					// System.out.println(values);
					theOne.mCharacterClassDB.createRecord(
							Arrays.copyOfRange(CHARACTER_CLASS_FIELD_NAME, 1, CHARACTER_CLASS_FIELD_NAME.length),
							values);

					// System.out.println("Controller Line 352: " +
					// Arrays.toString(values));
					recordsCreated++;
				}

				cin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return 0;
			} finally {
				if (cin != null)
					cin.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return recordsCreated;
	}

	private final int initializeItemsDBFromFile() {

		int recordsCreated = 0;
		Scanner cin = null;
		try {
			if (theOne.mItemsDB.getRecordCount() > 0)
				return 0;
			try {
				cin = new Scanner(new File(ITEMS_DATA_FILE));
				cin.nextLine();

				String[] data;
				String[] values;

				while (cin.hasNextLine()) {
					data = cin.nextLine().split(",");

					if (data.length != ITEM_FIELD_NAMES.length) {
						System.err.println("Skippng bad CSV row: " + data[0]);
						continue;
					}

					values = new String[ITEM_FIELD_NAMES.length - 1];
					// values[0] = data[0];
					for (int i = 0; i < ITEM_FIELD_NAMES.length - 1; i++) {
						values[i] = data[i + 1];
					}
					theOne.mItemsDB.createRecord(Arrays.copyOfRange(ITEM_FIELD_NAMES, 1, ITEM_FIELD_NAMES.length),
							values);

					// System.out.println("Controller Line : " +
					// Arrays.toString(values));
					recordsCreated++;
				}

				cin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return 0;
			} finally {
				if (cin != null)
					cin.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return recordsCreated;
	}

	private final int initializeEnemyDBFromFile() throws SQLException {
		if (theOne.mEnemyDB.getRecordCount() > 0)
			return 0;

		int recordsCreated = 0;
		Scanner cin = null;
		try {
			cin = new Scanner(new File(ENEMY_DATA_FILE));
			cin.nextLine();

			String[] data;
			String[] values;

			while (cin.hasNextLine()) {
				data = cin.nextLine().split(",");

				if (data.length != ENEMY_FIELD_NAMES.length) {
					System.err.println("Enemies - " + SKIPPING_CSV_ROW + data[0]);
					continue;
				}

				values = new String[ENEMY_FIELD_NAMES.length];
				values[0] = data[0];
				values[1] = data[1];
				values[2] = data[2];
				values[3] = data[3];
				values[4] = data[4];
				values[5] = data[5];
				values[6] = data[6];
				values[7] = data[7];
				values[8] = data[8];
				values[9] = data[9];
				values[10] = data[10];
				values[11] = data[11];
				theOne.mEnemyDB.createRecord(ENEMY_FIELD_NAMES, values);

				// System.out.println("Controller Line : " +
				// Arrays.toString(values));
				recordsCreated++;
			}

			cin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (cin != null)
				cin.close();
		}
		return recordsCreated;
	}

	/**
	 * Sets the current primary stage to the assigned.
	 * 
	 * @param stage
	 *            The stage to be assigned.
	 */
	public final void setStage(final Stage stage) {
		theOne.mPrimaryStage = stage;
	}

	/**
	 * Returns a reference to the currently assigned primary stage.
	 * 
	 * @return The currently assigned primary stage.
	 */
	public final Stage getStage() {
		return theOne.mPrimaryStage;
	}

	/*********************************
	 * SIGN IN
	 **********************************/

	/**
	 * Check and return true or false if the imputed username is valid
	 * @param username user input
	 * @return a boolean depending on the input
	 */
	public final boolean isValidUsername(final String username) {
		return username.matches("[a-zA-Z0-9 @._']{4,20}$*");
	}

	/**
	 * Check and return true or false if the imputed password is valid
	 * @param password user inputed password
	 * @return a boolean depending on the input
	 */
	public final boolean isValidPassword(final String password) {
		return password.matches("^(?=.*[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9 @._']{8,20}$*");
	}

	/**
	 * Return a string depend on what the user input
	 * @param u account userName
	 * @param p account password
	 * @return a string
	 */
	public final String signIn(final String u, final String p) {
		final String username = u.trim();
		final String password = p.trim();

		if (isValidUsername(username) && isValidPassword(password)) {
			final Iterator<User> userIT = mAllUsersList.iterator();
			User user;

			while (userIT.hasNext()) {
				user = (User) userIT.next();

				if (user.getUserName().equalsIgnoreCase(username)) {
					try {
						List<List<String>> userResultList = theOne.mUserDB.getRecord(String.valueOf(user.getId()));
						final String storedPassword = userResultList.get(0).get(3);

						if (password.equals(storedPassword)) {
							// Signed in
							theOne.mCurrentUser = user;
							String currentCharacterId = user.getPlayerId();
							for (Player player : mAllCharacterList) {
								System.out.println("current player Id " + player.getPlayerId());
								if (player.getPlayerId().equals(currentCharacterId)) {
									mCurrentPlayer = player;
									break;
								}
							}
							List<List<String>> saveListResult = mSaveDB.getRecord(currentCharacterId);
							if (saveListResult.isEmpty()) {
								// No save
								// loadNewLevel(1);
								mCurrentLevel = 1;
							} else {
								// There is save, so load from the DB.
								mCurrentLevel = Integer.parseInt(saveListResult.get(0).get(1));
								// loadNewLevel(mCurrentLevel);
							}

							return SUCCESS;
						} else
							return PASSWORD_INCORRECT;
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return COMBINATION_INCORRECT;
	}

	/*********************************
	 * SIGN UP
	 **********************************/

	/**
	 * Return a string of input error depend on what the user input
	 * @param u user input account userName
	 * @param p user input account password
	 * @param cp user input account re-entered password
	 * @return a string depending on what the user input
	 */
	public final String signUp(final String u, final String p, final String cp) {
		final String username = u.trim();
		final String password = p.trim();
		final String confirmPassword = cp.trim();

		if (!isValidUsername(username))
			return USERNAME_DOES_NOT_MEET_CRITERIA;
		if (!isValidPassword(password))
			return PASSWORD_DOES_NOT_MEET_CRITERIA;
		if (!confirmPassword.equals(password))
			return PASSWORDS_DO_NOT_MATCH;
		if (password.equalsIgnoreCase(username))
			return PASSWORD_SAME_AS_USER;

		final Iterator<User> userIT = mAllUsersList.iterator();
		User user;

		while (userIT.hasNext()) {
			user = (User) userIT.next();
			if (user.getUserName().equalsIgnoreCase(username))
				return USER_EXISTS;
		}

		try {
			final int id = theOne.mUserDB.createRecord(Arrays.copyOfRange(USER_FIELD_NAMES, 1, USER_FIELD_NAMES.length),
					new String[] { username, DEFAULT_ROLE.toString(), password, EMPTY_CHARACTER_ID });
			final User currentUser = new User(id, username, EMPTY_CHARACTER_ID, DEFAULT_ROLE);
			theOne.mAllUsersList.add(currentUser);
			theOne.mCurrentUser = currentUser;
		} catch (SQLException e) {
			e.printStackTrace();
			return ACCOUNT_CREATION_FAIL;
		}
		return SUCCESS;
	}

	/*********************************
	 * MAIN MENU
	 **********************************/

	/**
	 * this <code>logout<code> log the user out and load up the start screen
	 */
	public void logout() {
		theOne.mCurrentUser = null;
		theOne.mCurrentLevel = 0;
		theOne.mCurrentPlayer = null;
		theOne.mCurrentPlayerItem = null;
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Sign In", ViewNavigator.SIGN_IN_SCENE);
	}

	/*********************************
	 * CODEX LIST
	 **********************************/

	/**
	 * Return an ObservableList<Enemy> of off the difficulty level
	 * @param difficultyLevel the level difficulty
	 * @return ObservableList<Enemy> of off the difficulty level
	 */
	public final ObservableList<Enemy> getDistinctEnemiesByLevel(final int difficultyLevel) {
		final ObservableList<Enemy> filteredList = FXCollections.observableArrayList();
		final Iterator<Enemy> enemyIT = theOne.mAllEnemyList.iterator();
		Enemy enemy;

		while (enemyIT.hasNext()) {
			enemy = enemyIT.next();
			if (enemy.getLevel() == difficultyLevel)
				filteredList.add(enemy);
		}

		return filteredList;
	}

	/**
	 * Return an ObservableList of armor base on the rarity of off that armor
	 * @param rarity the rarity level to find
	 * @return ObservableList of armor
	 */
	public final ObservableList<Armor> getDistinctArmorByRarity(final int rarity) {
		final ObservableList<Armor> filteredList = FXCollections.observableArrayList();
		final Iterator<Armor> armorIT = theOne.mAllArmorList.iterator();
		Armor armor;

		while (armorIT.hasNext()) {
			armor = armorIT.next();
			if (armor.getRarity() == rarity)
				filteredList.add(armor);
		}

		return filteredList;
	}

	/**
	 * Return an ObservableList of waepon base on it rarity
	 * @param rarity the rarity level to find
	 * @return ObservableList of waepon
	 */
	public final ObservableList<Weapon> getDistinctWeaponByRarity(final int rarity) {
		final ObservableList<Weapon> filteredList = FXCollections.observableArrayList();
		final Iterator<Weapon> weaponIT = theOne.mAllWeaponsList.iterator();
		Weapon weapon;

		while (weaponIT.hasNext()) {
			weapon = weaponIT.next();
			if (weapon.getRarity() == rarity)
				filteredList.add(weapon);
		}

		return filteredList;
	}

	/**
	 * Populate the enemy list
	 * @param enemyList a list of enemy
	 * @param names an ObservableList of string
	 * @param list ListView of string
	 */
	public final void populateEnemyList(final List<Enemy> enemyList, final ObservableList<String> names,
			final ListView<String> list) {
		FXCollections.sort(names);
		list.setItems(names);
		list.setCellFactory(c -> new ListCell<String>() {
			private ImageView listCellImage = new ImageView();
			private Iterator<Enemy> enemyListIT;
			private Enemy enemy;

			@Override
			public void updateItem(final String name, final boolean isEmpty) {
				super.updateItem(name, isEmpty);
				if (isEmpty) {
					setText(null);
					setGraphic(null);
				} else {
					enemyListIT = enemyList.iterator();
					StringBuilder enemyToolTipSB = new StringBuilder();

					if (enemyListIT.hasNext()) {
						enemy = (Enemy) enemyListIT.next();

						setText(name);
						boolean found = false;
						while (!found) {
							if (name.equals(enemy.getName())) {
								listCellImage.setImage(enemy.getImage());
								found = true;
							} else {
								if (enemyListIT.hasNext())
									enemy = (Enemy) enemyListIT.next();
								else
									enemyListIT = enemyList.iterator();
							}
						}

						listCellImage.setImage(enemy.getCodexImage());
						listCellImage.setFitHeight(50);
						listCellImage.setFitWidth(50);
						setGraphic(listCellImage);
						setTooltip(new Tooltip(enemyToolTipSB.append(name).append("\n").append("HP: ")
								.append(enemy.getMaxHP()).append("\nAttack Points: ").append(enemy.getAttackPower())
								.append("\nBase Defense: ").append(enemy.getBaseDefense())
								.append("\nDifficulty Level: ").append(enemy.getLevel()).toString()));
					}
					enemyToolTipSB = new StringBuilder();
				}
			}
		});
	}

	/**
	 * Populate an armor list
	 * @param armorList List of armor
	 * @param names ObservableList of string
	 * @param list an ListView of string
	 */
	public final void populateArmorList(final List<Armor> armorList, final ObservableList<String> names,
			final ListView<String> list) {
		// FXCollections.sort(names);
		list.setItems(names);
		list.setCellFactory(c -> new ListCell<String>() {
			private ImageView listCellImage = new ImageView();
			private Iterator<Armor> armorListIT;
			private Armor armor;

			@Override
			public void updateItem(final String name, final boolean isEmpty) {
				super.updateItem(name, isEmpty);
				if (isEmpty) {
					setText(null);
					setGraphic(null);
				} else {
					StringBuilder armorToolTipSB = new StringBuilder();
					armorListIT = armorList.iterator();

					if (armorListIT.hasNext()) {
						armor = (Armor) armorListIT.next();

						setText(name);
						boolean found = false;
						while (!found) {
							if (name.equals(armor.getName())) {
								listCellImage.setImage(armor.getImage());
								found = true;
							} else {
								if (armorListIT.hasNext())
									armor = (Armor) armorListIT.next();
								else
									armorListIT = armorList.iterator();
							}
						}

						listCellImage.setFitHeight(50);
						listCellImage.setFitWidth(50);
						setGraphic(listCellImage);
						setTooltip(new Tooltip(armorToolTipSB.append(name).append("\n").append("Armor Rating: ")
								.append(armor.getArmorRating()).append("\nRarity Value: ").append(armor.getRarity())
								.append("\nWorth: ").append(armor.getWorth()).append(" gold\n")
								.append("Is Equippable: Yes").toString()));
					}
					armorToolTipSB = new StringBuilder();
				}
			}
		});
	}

	/**
	 * Populate an weapon List
	 * @param weaponList an List of weapon
	 * @param names ObservableList of string
	 * @param list ListView of string
	 */
	public final void populateWeaponsList(final List<Weapon> weaponList, final ObservableList<String> names,
			final ListView<String> list) {
		FXCollections.sort(names);
		list.setItems(names);
		list.setCellFactory(c -> new ListCell<String>() {
			private ImageView listCellImage = new ImageView();
			private Iterator<Weapon> weaponListIT;
			private Weapon weapon;

			@Override
			public void updateItem(final String name, final boolean isEmpty) {
				super.updateItem(name, isEmpty);
				if (isEmpty) {
					setText(null);
					setGraphic(null);
				} else {
					StringBuilder armorToolTipSB = new StringBuilder();
					weaponListIT = weaponList.iterator();

					if (weaponListIT.hasNext()) {
						weapon = (Weapon) weaponListIT.next();

						setText(name);
						boolean found = false;
						while (!found) {
							if (name.equals(weapon.getName())) {
								listCellImage.setImage(weapon.getImage());
								found = true;
							} else {
								if (weaponListIT.hasNext())
									weapon = (Weapon) weaponListIT.next();
								else
									weaponListIT = weaponList.iterator();
							}
						}

						listCellImage.setFitHeight(50);
						listCellImage.setFitWidth(50);
						setGraphic(listCellImage);
						setTooltip(new Tooltip(armorToolTipSB.append(name).append("\n").append("Armor Rating: ")
								.append(weapon.getAttackPoints()).append("\nAttack: ").append(weapon.getRarity())
								.append("\nWorth: ").append(weapon.getWorth()).append(" gold\n")
								.append("Is Equippable: Yes").toString()));
					}
					armorToolTipSB = new StringBuilder();
				}
			}
		});
	}

	/**
	 * Return the controller <code>mAllEnemyList<code>
	 * @return an List of Enemy
	 */
	public List<Enemy> getAllEnemies() {
		return theOne.mAllEnemyList;
	}

	/**
	 * Return the controller <code>mAllCodexArmor<code>
	 * @return an List of Armor
	 */
	public List<Armor> getAllCodexArmor() {
		return theOne.mAllArmorList;
	}

	/**
	 * Return the controller <code>mAllWeaponsList<code>
	 * @return an List of Weapon
	 */
	public List<Weapon> getAllCodexWeapons() {
		return theOne.mAllWeaponsList;
	}

	/**
	 * Set the selected enemy from the codex;
	 * @param enemy <code>Enemy<code>
	 */
	public final void setSelectedCodexEnemy(final Enemy enemy) {
		theOne.mSelectedCodexEnemy = new Enemy(enemy);
	}

	/**
	 * Set the selected armor from the codex;
	 * @param enemy <code>Armor<code>
	 */
	public final void setSelectedCodexArmor(final Armor armor) {
		theOne.mSelectedCodexArmor = new Armor(armor);
	}

	/**
	 * Set the selected weapon from the codex;
	 * @param enemy <code>Weapon<code>
	 */
	public final void setSelectedCodexWeapon(final Weapon weapon) {
		theOne.mSelectedCodexWeapon = new Weapon(weapon);
	}

	/*********************************
	 * CODEX DETAILS
	 **********************************/

	/**
	 * Return the selected Enemy for the codex
	 * @return an <code>Enemy<code>
	 */
	public final Enemy getSelectedCodexEnemy() {
		return new Enemy(theOne.mSelectedCodexEnemy);
	}

	/**
	 * Return the selected Armor for the codex
	 * @return an <code>Armor<code>
	 */
	public final Armor getSelectedCodexArmor() {
		return new Armor(theOne.mSelectedCodexArmor);
	}

	/**
	 * Return the selected Weapon for the codex
	 * @return an <code>Weapon<code>
	 */
	public final Weapon getSelectedCodexWeapon() {
		return new Weapon(theOne.mSelectedCodexWeapon);
	}

	/*********************************
	 * CHARACTER CREATION
	 *********************************/

	/**
	 * Return a boolean true or false if the creation of the character is successful.
	 * @param name character name
	 * @param power character power
	 * @param defense character defense
	 * @param speed character speed
	 * @param cClass character class
	 * @return a boolean true or false
	 */
	public boolean createNewCharacter(String name, int power, int defense, int speed, CharacterClass cClass) {
		String classId = "";
		switch (cClass) {
		case PURPLE_MAGE:
			classId = "1";
			break;
		case BLUE_MAGE:
			classId = "2";
			break;
		default:
		}
		try {
			// TODO: Change the default weapon and armor id
			int id = theOne.mCharactersDB.createRecord(
					Arrays.copyOfRange(CHARACTER_FIELD_NAMES, 1, CHARACTER_FIELD_NAMES.length),
					new String[] { name, classId, "1", "1", String.valueOf(power), String.valueOf(defense),
							String.valueOf(speed), "0" });
			List<List<String>> classList = mCharacterClassDB.getRecord(classId);
			List<String> classInfo = classList.get(0);
			mCurrentUser.setPlayer(String.valueOf(id));
			Player newPlayer = new Player(String.valueOf(id), name, power, defense, speed, mAllWeaponsList.get(0),
					mAllArmorList.get(0), cClass, classInfo.get(2));
			mAllCharacterList.add(newPlayer);
			mCurrentPlayer = newPlayer;
			// Update the user database.
			System.out.println(mUserDB.updateRecord(String.valueOf(mCurrentUser.getId()),
					new String[] { "character_id" }, new String[] { String.valueOf(id) }));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*********************************
	 * LEVELS (GAME)
	 **********************************/

	/**
	 * Return the Player form the controller 
	 * @return <code>Player<code>
	 */
	public Player getPlayer() {
		return theOne.mCurrentPlayer;
	}

	/**
	 * Update the player x and y
	 * @param dx position x amount of move
	 * @param dy position y amount of move
	 * @param cx offset x find in the center of player ImageView
	 * @param cy offset y find in the center of player ImageView
	 */
	public void movePlayer(double dx, double dy, double cx, double cy) {
		if (dx == 0 && dy == 0)
			return;
		double x = dx + theOne.mCurrentPlayer.getX();
		double y = dy + theOne.mCurrentPlayer.getY();

		moveTo(x, y, cx, cy);
	}

	/**
	 * Move the player to x and y position
	 * @param x player move to x position
	 * @param y player move to y position
	 * @param cx offset x find in the center of player ImageView
	 * @param cy offset y find in the center of player ImageView
	 */
	public void moveTo(double x, double y, double cx, double cy) {
		if (x - cx >= 0 && x + cx <= WINDOW_WIDTH && y - cy >= 0 && y + cy <= WINDOW_HEIGHT) {
			theOne.mCurrentPlayer.setPos(x, y);
		}
	}

	/**
	 * Update player health pool when player is hit
	 * @param attacker the one that attack the player
	 */
	public void playerGotHit(Enemy attacker) {
		theOne.mCurrentPlayer.wasHit(attacker.getAttackPower());
	}

	/**
	 * return the controller <code>mEnemysList<code>
	 * @return a List of Enemy
	 */
	public List<Enemy> getAllEnemyList() {
		return theOne.mEnemysList;
	}

	/**
	 * return the controller <code>mAllProjectiles<code>
	 * @return a List of Projectile
	 */
	public List<Projectile> getAllProjectiles() {
		return theOne.mAllProjectiles;
	}

	/**
	 * Fire a Projectile on the x and y position of the mouse when left mouse key is press
	 * @param mouseX mouse x position on the screen
	 * @param mouseY mouse y position on the screen
	 */
	public void playerShot(double mouseX, double mouseY) {
		String projectileFileStr = (theOne.mCurrentPlayer.hasEnoughMana() &&theOne.mCurrentPlayer.skillIsActive()
				&& theOne.mCurrentPlayer.useSkill().getType() == SkillType.Active
				) ? GameScene.FIRE_BALL_SHOT_PATH : GameScene.NORMAL_SHOT_PATH;

		Projectile shot = new Projectile(theOne.mCurrentPlayer, mouseX, mouseY, theOne.mCurrentPlayer.getAttackPower(),
				PLAYER_SHOT_NAME, new Image(new File(projectileFileStr).toURI().toString()));
		theOne.mAllProjectiles.add(shot);
	}

	/**
	 * Fire a Projectile at the Player x and y position
	 * @param attacker <code>Enemy<code>
	 */
	public void enemyShot(Enemy attacker) {
		Projectile shot = new Projectile(attacker, theOne.mCurrentPlayer.getX(), theOne.mCurrentPlayer.getY(),
				attacker.getAttackPower(), ENEMY_SHOT_NAME, new Image(new File(PROJECTILE_PATH).toURI().toString()));
		theOne.mAllProjectiles.add(shot);
	}

	/**
	 * Player active a skill when a skill key is press
	 */
	public void playerUsedSkill() {
		theOne.mCurrentPlayer.activateSkill();
	}

	/**
	 * Load a new Level when the player is finish with the current level
	 * @param level Level
	 */
	public void loadNewLevel(int level) {
		theOne.mCurrentLevel = level;
		if (theOne.mCurrentPlayer != null) {
			theOne.mCurrentPlayer.setX(GameScene.WINDOW_WIDTH / 2);
			theOne.mCurrentPlayer.setY(GameScene.WINDOW_HEIGHT / 2);
			mCurrentPlayerItem = theOne.mCurrentPlayer.getInventory();
		}
		theOne.mEnemysList.clear();
		theOne.mAllProjectiles.clear();

		for (int i = 0; i < level; i++) {
			Enemy e = new Enemy((Math.random() * (GameScene.WINDOW_WIDTH - 32)), 100, "Trolls",
					new Image(new File(TROLL_PATH).toURI().toString()),
					new Image(new File(TROLL_PATH).toURI().toString()));
			theOne.mEnemysList.add(e);
		}
	}

	/**
	 * return the player <code>mCurrentPlayerItem<code>
	 * @return a List of Item
	 */
	public List<Item> getCurrentPlayerItems() {
		return mCurrentPlayerItem;
	}

	/**
	 * Return a boolean depending on the success of a save
	 * @return a boolean 
	 */
	public boolean saveGame() {
		try {
			mCharactersDB.updateRecord(String.valueOf(mCurrentPlayer.getPlayerId()),
					Arrays.copyOfRange(CHARACTER_FIELD_NAMES, 1, CHARACTER_FIELD_NAMES.length),
					new String[] { mCurrentPlayer.getName(), mCurrentPlayer.getClassId(),
							String.valueOf(mCurrentPlayer.getWeapon().getID()),
							String.valueOf(mCurrentPlayer.getArmor().getID()),
							String.valueOf((int) mCurrentPlayer.getPowerPoint()),
							String.valueOf((int) mCurrentPlayer.getDefensePoint()),
							String.valueOf((int) mCurrentPlayer.getSpeedPoint()),
							String.valueOf(mCurrentPlayer.getScore()) });
			List<List<String>> playerSave = mSaveDB.getRecord(mCurrentPlayer.getPlayerId());
			// Check to see if the player saved previously
			if (playerSave.isEmpty()) {
				// First time saving, so create a new record
				System.out.println("New Save");
				System.out.println("Save id : " + mSaveDB.createRecord(SAVE_FIELD_NAMES,
						new String[] { theOne.mCurrentPlayer.getPlayerId(), String.valueOf(mCurrentLevel) }));
			} else {
				System.out.println("Updating Save");
				// Not first time saving, so update record instead
				mSaveDB.updateRecord(mCurrentPlayer.getPlayerId(), new String[] { "lvl_id" },
						new String[] { String.valueOf(mCurrentLevel) });
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Add a score record to the <code>mScoreDB<code>
	 * @param name player character name
	 * @param score player character score
	 * @param time player character time score
	 */
	public void addScore(String name, int score, double time) {
		final int id = theOne.mAllScores.size() + 1;
		theOne.mAllScores.add(new Score(id, name, score, time));
		final String[] values = { String.valueOf(id), name, String.valueOf(score), String.valueOf(time) };
		try {
			theOne.mScoreDB.createRecord(SCORE_FIELD_NAMES, values);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the controller <code>mAllScores<code>
	 * @return List of Score
	 */
	public List<Score> getAllScore() {
		return theOne.mAllScores;
	}

	/**
	 * Accessor method that return the currentLevel
	 * @return the currentLevel
	 */
	public int getCurrentLevel() {
		return mCurrentLevel;
	}

	/**
	 *Deactivate player skill
	 */
	public void playerUnusedSkill() {
		theOne.mCurrentPlayer.deActivateSkill();
	}

	public User getCurrentUser() {
		return mCurrentUser;
	}

	/**
	 * Swap the player skill
	 * @param skillType skill to swap
	 */
	public void playerSwitchedSkill(SkillType skillType) {
		theOne.mCurrentPlayer.setSkill(new Skill(10, skillType));
	}

	/**
	 * Player pick up all item at the end of the level
	 * @param itemId the item id
	 */
	public void playerPickedUpItem(String itemId) {
		try {
			if (mCurrentPlayerItem.size() < 12) {
				mPlayerItemsDB.createRecord(CHARACTER_INVENTORY_FIELD_NAME,
						new String[] { mCurrentPlayer.getPlayerId(), itemId });
				for (Item i : mAllItemsList) {
					Potion p = (Potion) i;
					if (p.getId().equals(itemId)) {
						mCurrentPlayerItem.add(p);
						mCurrentPlayer.addItem(p);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Player use an item
	 * @param index of the use item
	 */
	public void playerUsedItem(int index) {
		theOne.mCurrentPlayer.usedItem(mCurrentPlayerItem.get(index));
		theOne.mCurrentPlayerItem.remove(index);
	}
}