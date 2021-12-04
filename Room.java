// Room.java
// provides code for the drawing of a room
// also provides starting locations for the player, boxes, and enemies

import java.util.ArrayList;
import ansi_terminal.*;

/** Room holds a 2D array of characters representing the room
 * @author Tyler Martzall, John
 */
public class Room {
    // the grid holds the room geometry
    private String[] grid;

    // the size of the room
    private int rows;
    private int cols;

    /** Constructor takes a grid
     * @param newGrid 2D array of characters representing room
     * @param newRows Width of room
     * @param newCols Height of room
     */
    public Room(String[] newGrid, int newRows, int newCols) {
        // this initializes the room to one specific space
        rows = newRows;
        cols = newCols;

        // the actual room geometry
        // the i cells refer to where an item should be placed at
        grid = newGrid;
    }

    /** Get starting position for player
     * @return Position of "@" character (player starting pos)
     */
    public Position getPlayerStart() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row].charAt(col) == '@') {
                    return new Position(row, col);
                }
            }
        }

        return null;
    }

    /** Create a list of Boxes on the map
     * @return List of randomized Boxes
     */
    public ArrayList<Box> getBoxes() {
        ArrayList<Box> boxes = new ArrayList<Box>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row].charAt(col) == 'i') {
                    boxes.add(new Box(row, col, ItemGenerator.generate()));
                }
            }
        }

        return boxes;
    }

    /** Create a list of Enemies on the map
     * @return List of randomized Enemies
     */
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	World world = new World();
	int roomNum = world.getRoomNum();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row].charAt(col) == '*') {
                    enemies.add(EnemyGenerator.generate(row, col, roomNum));
                }
		
            }
        }

        return enemies;
    }

    /** Get Position of "+" character
     * @return Portal position
     */
    public Position getPortalPosition() {
    	for (int row = 0; row < rows; row++) {
    		for (int col = 0; col < cols; col++) {
    			if (grid[row].charAt(col) == '+') {
    				return new Position(row, col);
    			}
    		}
    	}
    	return null;
    }

    /** Get total number of rows
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /** Get total number of columns
     * @return cols
     */
    public int getCols() {
        return cols;
    }

    /** Output map by looping over 2D array grid
     */
    public void draw() {
        Terminal.clear();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char cell = grid[row].charAt(col);
                if (cell == '#') {
                    // a unicode block symbol
                    System.out.print('\u2588');
                } else if (cell != '*' && cell != 'i' && cell != '@' && cell != 'V') {
                	System.out.print(cell);
                } else {
                    // whatever else, just draw a blank (we DONT draw starting items from map)
                    System.out.print(' ');
                }
            }

            System.out.print("\n\r");
        }
    }
    
    /** Returns the char from a grid position
     * @param row Position.row
     * @param col Position.col
     * @return char at Position
     */
    public char getStaticCharAtLocation(int row, int col) {
    	char staticChar = grid[row].charAt(col);
    	if (staticChar != '*' && staticChar != 'i' && staticChar != '@') {
    		return staticChar;
    	}
    	return ' ';
    }

    /** Returns whether a cell is walkable
     * @param row Target Position.row
     * @param col Target Position.col
     * @return True if not a wall, false if it is a wall
     */
    public boolean canGo(int row, int col) {
        return grid[row].charAt(col) != '#';
    }
}



