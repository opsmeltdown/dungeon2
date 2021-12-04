// Item.java

/**
 * An Item has a type, name, weight, value and strength
 * @author Tyler Martzall, LLC, John
 */
public class Item {
    // what sort of item it is
    private ItemType type;

    // the name of the item as shown to the user
    private String name;

    // how much it weighs (player can only carry so much)
    private int weight;

    // how much the item is worth for buying/selling
    private int value;

    // the item's strength - this differs based on the type
    // for a weapon, it's damage
    // for armor, it's protection
    private int strength;

    /** Constructor that sets basic Item info
     * @param name Name of the item
     * @param weight Weight of the item, how much space it takes up in the inventory
     * @param value Value of the item
     * @param strength Strength of the item (damage or resistance for weapon or armor)
     * @param type Weapon, Armor or Other
     */
    public Item(String name, int weight, int value, int strength, ItemType type) {
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.strength = strength;
    }

    /** Get item weight
     * @return Weight of item (how much space in inventory to take up)
     */
    public int getWeight() {
        return weight;
    }

    /** Get value of item
     * @return Item value
     */
    public int getValue() {
        return value;
    }

    /** Get item strength
     * @return Strength of item
     */
    public int getStrength() {
        return strength;
    }

    /** Get item name
     * @return Item Name
     */
    public String getName() {
        return name;
    }

    /** Get ItemType
     * @return ItemType
     */
    public ItemType getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " " + weight + " " + value + " " + strength;
    }
}

