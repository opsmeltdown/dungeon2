// Box.java
// represents a pickup-able item

import ansi_terminal.*;

/** Box stores an item in an Entity
 * @author Tyler Martzall, John
 */

public class Box extends Entity {
    // the Item that is in the box
    private Item item;

    /** Creates a box, and includes the given item
     * @param row Position.row
     * @param col Position.col
     * @param item The item to be stored at this position
     */
    public Box(int row, int col, Item item) {
        super(row, col, 'i', Color.MAGENTA);
        this.item = item;
    }

    /** Gets the Item in this Box
     * @return The Item stored in this box
     */
    public Item getItem() {
        return item;
    }
}
