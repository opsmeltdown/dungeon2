// Character.java

import java.util.ArrayList;

import ansi_terminal.*;

/** Character is an entity with HP,
 * can deal damage, and can take damage
 * @author Tyler Martzall, John
 */
public abstract class Character extends Entity {
    // the characters health points
    private int hp;

    /** Constructor sets needed Entity info and HP
     * @param row Position.row
     * @param col Position.col
     * @param display Char used to show the Character on the map
     * @param color The color of the above Char
     * @param hp Total HP of the Character
     */
    public Character(int row, int col, char display, Color color, int hp) {
        super(row, col, display, color);
        this.hp = hp;
    }

    /** Get HP
     * @return current HP
     */
    public int getHealth() {
        return hp;
    }
    /** Set HP
     * @param newHp New value for HP
     */
    public void setHealth(int newHp) {
    	hp = newHp;
    }
    /** Get damage
     * @return Damage this Character will inflict
     */
    public abstract int getDamage();
    /** Get protection
     * @return Number of damage the Character will resist
     */
    public abstract int getProtection();
    /** Get name
     * @return Character's name
     */
    public abstract String getName();

    /** Do damage to another Character
     * @param other The other Character damage should be dealt to
     * @param room The Room currently displayed
     * @return Boolean if the other Character died or not
     */
    private boolean dealDamage(Character other, Room room) {
        // this character does damage to the other character
        int damageDone = getDamage() - other.getProtection();

        // prevent negative damage
        if (damageDone < 0) {
            damageDone = 0;
        }

        // actually damage them
        other.hp -= damageDone;

        // prevent negative hp
        if (other.hp < 0) {
            other.hp = 0;
        }

        // print the info on this
        Terminal.warpCursor(room.getRows(), 0);
        if (other.hp > 0) {
            System.out.print(getName() + " does " + damageDone + " damage to " + other.getName()
                + ", leaving " + other.hp + " health.\n\r");
            return false;
        } else {
            System.out.print(getName() + " does " + damageDone + " damage to " + other.getName()
                + ", killing them.\n\r");
            return true;
        }
    }

    /** Perform one round of battle between 2 Characters
     * @param other The other Character in the fight
     * @param room The currently displayed Room
     * @param enemies List of enemies in the current Room
     * @return False if this Character dies
     */
    public boolean fight(Character other, Room room, ArrayList<Enemy> enemies) {
        // do damage to them first
        boolean killed = dealDamage(other, room);
        if (killed) {
            enemies.remove(other);
        }
        System.out.printf("Press any key to return...\n\r");
        Terminal.getKey();

        // don't allow dead enemies to fight back
        if (killed) {
            return true;
        }

        // now take damage from them
        if (other.dealDamage(this, room)) {
            return false;
        }
        System.out.printf("Press any key to return...\n\r");
        Terminal.getKey();
        return true;
    }
}

