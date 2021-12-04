// Entity.java
// this class represents one moveable, drawable thing in the game

import ansi_terminal.*;

/** Something to be drawn on the map with Position and able to move
 * @author Tyler Martzall, John
 */
public class Entity {
    // the location of the entity in space
    private Position position;

    // the character used to draw it
    private char display;

    // the color used for drawing
    private Color color;

    /** Constructor sets Position, display, and color
     * @param row Position.row
     * @param col Position.col
     * @param display Char used to represent the Entity
     * @param color The color of the above char
     */
    public Entity(int row, int col, char display, Color color) {
        position = new Position(row, col);
        this.display = display;
        this.color = color;
    }

    /** Move the entity to a new position
     * @param row New position.row
     * @param col New position.col
     */
    public void setPosition(int row, int col) {
        position = new Position(row, col);
    }

    /** Get position
     * @return Current position
     */
    public Position getPosition() {
        return position;
    }

    /** Get row
     * @return Current position.row
     */
    public int getRow() {
        return position.getRow();
    }
    
    /** Get col
     * @return Current position.col
     */
    public int getCol() {
        return position.getCol();
    }
    
    /** Get display char
     * @return Char used to represent this Entity
     */
    public char getDisplay() {
    	return this.display;
    }

    /** Move the entity in a Room, unless target space is a wall
     * @param rowChange How far to move in Position.row
     * @param colChange How far to move in Position.col
     * @param room Currently displayed Room
     * @return True if the Entity moved, false if Room won't allow the movement
     */
    public boolean move(int rowChange, int colChange, Room room) {
        // find new position
        int newRow = position.getRow() + rowChange;
        int newCol = position.getCol() + colChange;

        if (room.canGo(newRow, newCol)) {
            // draw a space or character where it currently is
            Terminal.warpCursor(position.getRow(), position.getCol());
            char draw = room.getStaticCharAtLocation(position.getRow(), position.getCol());
            System.out.print(draw);

            // and then move it
            position = new Position(newRow, newCol);
            return true;
        } else {
            return false;
        }
    }

    /** Output Entity display
     */
    public void draw() {
        Terminal.warpCursor(position.getRow(), position.getCol());
        Terminal.setForeground(color);
        System.out.print(display);
        Terminal.reset();
    }
}

