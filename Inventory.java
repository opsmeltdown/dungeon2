// Inventory.java
// allows for storing some number of items for the player

import java.util.ArrayList;
import java.util.Scanner;

import ansi_terminal.*;

/**
 * A list of items, a max width and equipped armor/weapon
 * @author Tyler Martzall, John
 */
public class Inventory {
    // the actual list of items
    private ArrayList<Item> items;

    // which item is equipped, if any
    private Item equippedArmor;
    private Item equippedWeapon;

    // the max weight limit for the player here
    private int maxWeight;

    /** Constructor sets max weight
     * @param maxWeight Max weight of items in list
     */
    public Inventory(int maxWeight) {
        items = new ArrayList<Item>();
        this.maxWeight = maxWeight;
    }

    /** Adds an item to the list
     * @param item Item to add
     * @return True if the item was added, false if it goes over maxWeight
     */
    public boolean add(Item item) {
        if ((item.getWeight() + totalWeight()) > maxWeight) {
            return false;
        } else {
            items.add(item);
            return true;
        }
    }

    /** Add an item to the Inventory and equip it
     * @param item Item to add and equip
     */
    public void addAndEquip(Item item) {
        items.add(item);

        if (item.getType() == ItemType.Weapon) {
            equippedWeapon = item;
        } else if (item.getType() == ItemType.Armor) {
            equippedArmor = item;
        }
    }

    /** Get equipped weapon
     * @return equipped weapon
     */
    public Item getEquippedWeapon() {
        return equippedWeapon;
    }

    /** Get equipped armor
     * @return equipped armor
     */
    public Item getEquippedArmor() {
        return equippedArmor;
    }

    /** Get weight of all items in the list
     * @return Total weight
     */
    public int totalWeight() {
        int total = 0;
        for (Item i : items) {
            total += i.getWeight();
        }
        return total;
    }

    /** Output items in the Item list
     * @param filter Filter Items by type and only display those
     * @return Total number of items displayed
     */
    private int print(ItemType filter) {
        // clear the terminal so we print over all else
        Terminal.clear();

        // print a heading row
        // the numbers and junk are to make it print in nice columns
        Terminal.setForeground(Color.RED);
        System.out.printf("%-4s %-40s %-8s %-8s %-8s\n\r", "No.", "Name", "Weight", "Value", "Strength");
        Terminal.reset();

        // print each item out
        int num = 0;
        for (Item i : items) {
            if (filter == null || i.getType() == filter) {
                System.out.printf("%-4d %-40s %-8s %-8s %-8s", num + 1, i.getName(), i.getWeight(), i.getValue(), i.getStrength());

                // tell them if this thing is equipped
                if (i == equippedArmor) {
                    System.out.print(" (equipped armor)");
                } else if (i == equippedWeapon) {
                    System.out.print(" (equipped weapon)");
                }
                System.out.print("\n\r");

                num++;
            }
        }

        return num;
    }

    /** Pause for player input
     */
    public void pressAnyKey() {
        System.out.printf("\n\rPress any key to return...\n\r");
        Terminal.getKey();
    }

    /** Print all of the items in the list
     */
    public void print() {
        print(null);
        pressAnyKey();
    }

    /** Drop an item from the inventory
     * @return Item that was dropped
     */
    public Item drop() {
        Item toDrop = pickItem(null);
        if (toDrop != null) {
            // if we're dropping our equipped stuff, remove those too!
            if (equippedWeapon == toDrop) {
                equippedWeapon = null;
            } else if (equippedArmor == toDrop) {
                equippedArmor = null;
            }

            items.remove(toDrop);
        }

        if (toDrop != null) {
            System.out.print("You dropped the " + toDrop.getName() + "...\n\r");
        } else {
            System.out.print("You dropped nothing...\n\r");
        }

        pressAnyKey();
        return toDrop;
    }

    /** Equip an item of a specific type
     * @param type ItemType to equip
     * @return Item that was equipped
     */
    private Item equip(ItemType type) {
        Item thing = pickItem(type);
        if (thing != null) {
            System.out.print("You equipped the " + thing.getName() + "\n\r");
        } else {
            System.out.print("You equipped nothing...\n\r");
        }
        pressAnyKey();
        return thing;
    }

    /** Equip a weapon
     */
    public void equipWeapon() {
        equippedWeapon = equip(ItemType.Weapon);
    }

    /** Equip armor
     */
    public void equipArmor() {
        equippedArmor = equip(ItemType.Armor);
    }

    // a method which allows users to choose an item
    // this is private - only called by drop and equip
    /** Print Items of the type passed, and allow Player to choose one
     * @param filter ItemType to filter by (print only this type)
     * @return Selected Item
     */
    private Item pickItem(ItemType filter) {
        // print all the matching items
        int options = print(filter);

        if (options == 0) {
            System.out.print("You have no appropriate items!\n\r");
            return null;
        }

        // give them a cancel option as well
        System.out.print((options + 1) + "    None\n\r");

        // get their choice, only allowing ints in the correct range
        int choice = 0;
        do {
            String entry = Terminal.getLine("Select an item: ");
            try {
                choice = Integer.parseInt(entry) - 1;
            } catch (NumberFormatException e) {
                choice = -1;
            }
        } while (choice < 0 || choice > options);

        // go through and skip items until we reach this one
        int realIndex = 0;
        for (Item i : items) {
            if (filter == null || i.getType() == filter) {
                if (choice == 0) {
                    break;
                }
                choice--;
            }
            realIndex++;
        }

        // return the thing they chose
        if (realIndex < 0 || realIndex >= items.size()) {
            return null;
        } else {
            return items.get(realIndex);
        }
    }
    
    /** Get Item list
     * @return Item list
     */
    public ArrayList<Item> getItems() {
    	return items;
    }
}

