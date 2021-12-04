// Game.java
// contains logic for running the Game

import java.util.ArrayList;
import ansi_terminal.*;

public class Game {
	private World world;
    private Room room;
    private Player player;
    private Position portalPosition;
    private ArrayList<Box> boxes;
    private ArrayList<Enemy> enemies;
    private String name;

    public Game(String newName) {
    	name = newName;
    	world = new World();
    	room = world.getCurrentRoom();
        player = new Player(room.getPlayerStart(), name, 50);
        boxes = room.getBoxes();
        enemies = room.getEnemies();
        portalPosition = room.getPortalPosition();
    }
    
    private void newRoom() {
    	world.nextRoom();
    	room = world.getCurrentRoom();
    	player = new Player(room.getPlayerStart(), name, 50);
        boxes = room.getBoxes();
        enemies = room.getEnemies();
        portalPosition = room.getPortalPosition();
        redrawMapAndHelp();
    }

    // prints a help menu to the left of the map
    private void showHelp() {
        String[] cmds = {player.getName(),
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

    // right under the map we keep a line for status messages
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

    // code for when the player tries to pickup an item
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

    // code for when the player tries to drop an item
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

    // handle the key which was read - return false if we quit the game
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

    // this is called when we need to redraw the room and help menu
    // this happens after going into a menu like for choosing items
    private void redrawMapAndHelp() {
        room.draw();
        showHelp();
    }

    // returns a Box if the player is on it -- otherwise null
    private Box checkForBox() {
        Position playerLocation = player.getPosition();

        for (Box box : boxes) {
            if (playerLocation.equals(box.getPosition())) {
                return box;
            }
        }

        return null;
    }
    
    private boolean checkForPortal() {
    	Position playerLocation = player.getPosition();
 
    	if (portalPosition == null) {
    		return false;
    	}
    	return playerLocation.equals(portalPosition);
    }

    // check for battles and return false if player has died
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
            return player.fight(opponent, room, enemies);
        }

        return true;
    }

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
	    //checking if final boss is alive or dead, ending the game if hes dead
	    if (world.getRoomNum() == 2) {
		boolean alive = false;
	    	for (Enemy enemy : enemies) {
			if (enemy.getName().equals("Viego")) {
				alive = true;
			}
		}
		if (alive = false){
			setStatus("You have beaten the Ruined King! :\n\r");
			playing = false;
		}
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
    
    private void save() {
		try {
			int roomNum = world.getRoomNum();
			SaveLoad.save(roomNum, player, boxes, enemies);
			setStatus("Game Saved!");
		} catch(Exception e) {
			System.out.println(e);
		}
    }
    
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

