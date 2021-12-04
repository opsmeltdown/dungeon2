// Position.java
// represents a simple row/col position in the world

/** A row/col position in a Room
 * @author Tyler Martzall, John
 */
public class Position {
    private int row;
    private int col;

    /** Constructor with no params sets row = 0 and col = 0
     */
    public Position() {
        row = 0;
        col = 0;
    }

    /** Constructor with row/col
     * @param row Position.row
     * @param col Position.col
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {
        Position op = (Position) other;

        // they are equal when both coordinates match
        return this.row == op.row && this.col == op.col;
    }

    /** Returns whether a position is adjacent to this Position
     * @param other
     * @return
     */
    public boolean isAdjacent(Position other) {
        int rowdiff = Math.abs(this.row - other.row);
        int coldiff = Math.abs(this.col - other.col);

        if (rowdiff + coldiff < 2) {
            return true;
        } else {
            return false;
        }
    }

    /** Return Position.row
     * @return this.row
     */
    public int getRow() {
        return row;
    }

    /** Return Position.col
     * @return this.col
     */
    public int getCol() {
        return col;
    }
}

