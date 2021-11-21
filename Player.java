// Player.java

import ansi_terminal.*;

public class Player extends Character {
    private Inventory items;
    private String name;

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

    public Inventory getInventory() {
        return items;
    }
    
    public void setInventory(Inventory newInventory) {
    	items = newInventory;
    }
}

