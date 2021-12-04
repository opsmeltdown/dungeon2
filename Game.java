// Game.java
// contains logic for running the Game

import java.util.ArrayList;
import ansi_terminal.*;

/** Orchestrates the Game
 * @author Tyler Martzall, John
 */
public class Game {
	private World world;
    private Room room;
    private Player player;
    private Position portalPosition;
    private ArrayList<Box> boxes;
    private ArrayList<Enemy> enemies;
    private String name;

    /** Constructor sets up a new Game
     * @param newName Name of the player's Character
     */
    public Game(String newName) {
    	name = newName;
    	world = new World();
    	room = world.getCurrentRoom();
        player = new Player(room.getPlayerStart(), name, 50);
        boxes = room.getBoxes();
        enemies = room.getEnemies();
        portalPosition = room.getPortalPosition();
    }
    
    /** Sets up the next Room
     */
    private void newRoom() {
    	world.nextRoom();
    	room = world.getCurrentRoom();
    	player = new Player(room.getPlayerStart(), name, 50);
        boxes = room.getBoxes();
        enemies = room.getEnemies();
        portalPosition = room.getPortalPosition();
        redrawMapAndHelp();
    }

    /** Outputs the help menu to the side of the map
     */
    private void showHelp() {
        String[] cmds = {player.getName() + ": " + player.getHealth() + " hp",
			 "Commands:",
             "---------",
             "Move: Arrow Keys",
             "Pickup an item: p",
             "Drop an item: d",
             "List items: i",
             "Equip weapon: w",
             "Equip armor: a",
             "Save: s",
             "Load: l",
             "Quit: q"
        };
        Terminal.setForeground(Color.GREEN);
        for (int row = 0; row < cmds.length; row++) {
            Terminal.warpCursor(row + 1, room.getCols());
            System.out.print(cmds[row]);
        }
        Terminal.reset();
    }

    /** Status message is printed under the map
     * @param mesg Message to put under the map
     */
    private void setStatus(String mesg) {
        // clear anything old first
        Terminal.warpCursor(room.getRows(), 0);
        for (int i = 0; i < 100; i++) {
            System.out.print(" ");
        }

        // then print the message
        Terminal.warpCursor(room.getRows(), 0);
        System.out.print(mesg);
    }

    /** Attempts to pick up Item under player
     * This can fail if there's no item or the inventory if full
     */
    private void pickup() {
        Box thing = checkForBox();
        if (thing == null) {
            setStatus("There is nothing here to pick up...");
            Terminal.pause(1.25);
        } else {
            if (player.getInventory().add(thing.getItem())) {
                setStatus("You added the " + thing.getItem().getName() + " to your inventory.");
                boxes.remove(thing);
            } else {
                setStatus("This is too large for you to add!");
            }
            Terminal.pause(1.25);
        }
    }

    /** Attempts to drop an Item
     * This can fail if there is something in the way
     */
    private void drop() {
        if (checkForBox() == null) {
            Item dropped = player.getInventory().drop();
            if (dropped != null) {
                boxes.add(new Box(player.getRow(), player.getCol(), dropped));
            }
            redrawMapAndHelp();
        } else {
            setStatus("You cannot drop something on an existing item...");
            Terminal.pause(1.25);
        }
    }

    /** Handle input
     * @param key Key pressed by player
     * @return True to continue, false if the game should be exited
     */
    private boolean handleKey(Key key) {
        switch (key) {
            case p:
                pickup();
                break;

            case i:
                player.getInventory().print();
                redrawMapAndHelp();
                break;

            case d:
                drop();
                break;

            case w:
                player.getInventory().equipWeapon();
                redrawMapAndHelp();
                break;

            case a:
                player.getInventory().equipArmor();
                redrawMapAndHelp();
                break;
            
            case s:
            	save();
            	break;

            case l:
            	load();
            	break;

            // handle movement
            case LEFT: player.move(0, -1, room);
                break;
            case RIGHT: player.move(0, 1, room);
                break;
            case UP: player.move(-1, 0, room);
                break;
            case DOWN: player.move(1, 0, room);
                break;

            // and finally the quit command
            case q:
                return false;
        }

        return true;
    }

    /** Draw map and Help
     */
    private void redrawMapAndHelp() {
        room.draw();
        showHelp();
    }

    /** Checks for a Box under the player
     * @return True if Box, false otherwise
     */
    private Box checkForBox() {
        Position playerLocation = player.getPosition();

        for (Box box : boxes) {
            if (playerLocation.equals(box.getPosition())) {
                return box;
            }
        }

        return null;
    }
    
    /** Checks for a portal under the player
     * @return True if portal, false otherwise
     */
    private boolean checkForPortal() {
    	Position playerLocation = player.getPosition();
 
    	if (portalPosition == null) {
    		return false;
    	}
    	return playerLocation.equals(portalPosition);
    }

    // check for battles and return false if player has died
    /** Checks for adjacent Enemies and does battle with them
     * @return False if the player dies, True otherwise
     */
    private boolean checkBattles() {
        Position playerLocation = player.getPosition();

        // look for an enemy that is close
        Enemy opponent = null;
        for (Enemy enemy : enemies) {
            if (playerLocation.isAdjacent(enemy.getPosition())) {
                opponent = enemy;
            }
        }

        // now do the battle
        if (opponent != null) {
            opponent.setBattleActive();
            boolean killed = player.fight(opponent, room, enemies);
            showHelp();
            return killed;
        }

        return true;
    }

    /** Game loop, draws map and help, draw entities,
     * move enemies, take input, do battle, check for entities under the player
     */
    public void run() {
        // draw these for the first time now
        redrawMapAndHelp();

        boolean playing = true;
        while (playing) {
            // draw the entities
            for (Box box : boxes) {
                box.draw();
            }
            for (Enemy enemy : enemies) {
                enemy.draw();
            }
            player.draw();

            // read a key from the user
            Terminal.warpCursor(room.getRows() + 1, 0);
            Key key = Terminal.getKey();
            playing = handleKey(key);

            // clear status by default
            setStatus("");

            // move the enemies
            for (Enemy enemy : enemies) {
                enemy.walk(room);
            }

            // check for battles
            if (checkBattles() == false) {
                setStatus("You have been killed :(\n\r");
                playing = false;
            }
            
            // check for health fountain
            Position playerPos = player.getPosition();
            char charHere = room.getStaticCharAtLocation(playerPos.getRow(), playerPos.getCol());
            if (charHere == 'H') {
            	setStatus("There is a health fountain here, you are healed to full health.");
            	player.setHealth(50);
            	showHelp();
            }

            // check if we are on a box and print what's in it
            Box thingHere = checkForBox();
            if (thingHere != null) {
                setStatus("Here you find: " + thingHere.getItem().getName());
            }

            // check if we are on a portal
            if (portalPosition != null) {
                boolean portalHere = checkForPortal();
                if (portalHere) {
                	setStatus("You find a portal. Would you like to take it? y/n");
                	Key portalKey = Terminal.getKey();
                	if (portalKey == Key.y) {
                		newRoom();
                	}
                }
            }
        }
    }
    
    /** Write out to a file
     */
    private void save() {
		try {
			int roomNum = world.getRoomNum();
			SaveLoad.save(roomNum, player, boxes, enemies);
			setStatus("Game Saved!");
		} catch(Exception e) {
			System.out.println(e);
		}
    }
    
    /** Load game state from a file
     */
    private void load() {
        try {
            int loadRoomNum = SaveLoad.loadRoomNum();
            Player loadPlayer = SaveLoad.loadPlayer();
            ArrayList<Box> loadBoxes = SaveLoad.loadBoxes();
            ArrayList<Enemy> loadEnemies = SaveLoad.loadEnemies();
            
            name = loadPlayer.getName();
            world = new World();
            world.setRoomNum(loadRoomNum);
            room = world.getCurrentRoom();
            player = loadPlayer;
            boxes = loadBoxes;
            enemies = loadEnemies;
            portalPosition = room.getPortalPosition();
            
            redrawMapAndHelp();
            setStatus("Game Loaded!");
        } catch(Exception e) {
        	System.out.println(e);
        }
    }
}

