// Enemy.java

import java.util.Random;
import ansi_terminal.*;

/** Enemy is a Character with random movement
 * @author Tyler Martzall, John
 */
public class Enemy extends Character {
    private String name;
    private int damage;
    private int protection;
    private static Random rng;
    private boolean battleActive;

    /** Constructor sets needed Entity info and basic Enemy info
     * @param name Name of the Enemy
     * @param display Char used to represent the Enemy on the map
     * @param row Position.row
     * @param col Position.col
     * @param hp Total HP of the Enemy
     * @param damage Damage the Enemy will deal in battle
     * @param protection Damage the Enemy will resist in battle
     */
    public Enemy(String name, char display,int row, int col, int hp, int damage, int protection) {
    	super(row, col, display, Color.RED, hp);
        this.name = name;
        this.damage = damage;
        this.protection = protection;
        this.battleActive = false;
        rng = new Random();
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getProtection() {
        return protection;
    }

    @Override
    public String getName() {
        return name;
    }

    /** Set battleActive
     * battleActive determines whether the Enemy will move or not
     */
    public void setBattleActive() {
        battleActive = true;
    }

    /** Randomly move the Enemy in one direction
     * @param room Currently displayed Room
     */
    public void walk(Room room) {
        // if a battle is active with this enemy, they DONT walk right after
        if (battleActive) {
            battleActive = false;
            return;
        }

        // loop forever until we move correctly
        while (true) {
            int choice = rng.nextInt(4);
            switch (choice) {
                case 0:
                    if (move(0, 1, room)) return;
                    break;
                case 1:
                    if (move(0, -1, room)) return;
                    break;
                case 2:
                    if (move(1, 0, room)) return;
                    break;
                case 3:
                    if (move(-1, 0, room)) return;
                    break;
            }
        }
    }
}


