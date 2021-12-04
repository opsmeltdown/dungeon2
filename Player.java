// Player.java

import ansi_terminal.*;

/** Player is a Character with an Inventory
 * @author Tyler Martzall, John
 */
public class Player extends Character {
    private Inventory items;
    private String name;

    /** Constructor takes basic info for Player
     * Creates inventory and equips starter items
     * @param start Beginning Position of Player
     * @param name Name of Player
     * @param health Player's total HP
     */
    public Player(Position start, String name, int health) {
        // our starting details
        super(start.getRow(), start.getCol(), '@', Color.CYAN, health);
        this.name = name;
        // we can carry 100 pounds of items
        items = new Inventory(100);

        // give them some basic stuff to start with
        // TODO make up your own starting equipment!
        items.addAndEquip(new Item("Doran's Blade", 5, 12, 7, ItemType.Weapon));
        items.addAndEquip(new Item("Cloth Armor", 15, 20, 5, ItemType.Armor));
    }

    @Override
    public int getDamage() {
        Item weapon = items.getEquippedWeapon();
        if (weapon != null) {
            return weapon.getStrength();
        } else {
            // if we have no weapon, our fists are pretty weak...
            return 1;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getProtection() {
        Item armor = items.getEquippedArmor();
        if (armor != null) {
            return armor.getStrength();
        } else {
            // without armor, we have no protection
            return 0;
        }
    }

    /** Get Player's Inventory
     * @return Inventory
     */
    public Inventory getInventory() {
        return items;
    }
    
    /** Set Player's Inventory
     * @param newInventory Inventory value
     */
    public void setInventory(Inventory newInventory) {
    	items = newInventory;
    }
}

