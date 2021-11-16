import java.util.Random;			
import java.util.ArrayList;

public class ItemGenerator {		//will return a randomly generated item
	
	private static ArrayList<Item> itemBank = new ArrayList<Item>();
	static {
		itemBank.add(new Item("Blade of the Ruined King", 15, 100, 25, ItemType.Weapon));
		itemBank.add(new Item("Warmog's Armor", 20, 25, 18, ItemType.Armor));		//pool of items draws from
		itemBank.add(new Item("Tear of the Goddess", 3, 500, 0, ItemType.Other));
		itemBank.add(new Item("Infinity Edge", 13, 70, 20, ItemType.Weapon));
		itemBank.add(new Item("Force Of Nature", 10, 20, 18, ItemType.Armor));
		itemBank.add(new Item("Iron Ore", 9, 10, 0, ItemType.Other));
		itemBank.add(new Item("High Quality Wool", 2, 10, 0, ItemType.Other));
		itemBank.add(new Item("Rylai's Crystal Scepter", 12, 55, 16, ItemType.Weapon));
		itemBank.add(new Item("Randuin's Omen", 17, 35, 15, ItemType.Armor));
		itemBank.add(new Item("Doran's Ring", 1, 75, 0, ItemType.Other));
		itemBank.add(new Item("Phantom Dancer", 11, 40, 15, ItemType.Weapon));
		itemBank.add(new Item("Null Magic Mantle", 5, 15, 13, ItemType.Armor));
		itemBank.add(new Item("Wit's End", 14, 17, 12, ItemType.Weapon));
		itemBank.add(new Item("Chain Vest", 20, 50, 11, ItemType.Armor));
		itemBank.add(new Item("Health Potion", 2, 10, 0, ItemType.Other));
		itemBank.add(new Item("Steel Sword", 7, 10, 11, ItemType.Weapon));
		itemBank.add(new Item("Negatron Cloak", 4, 15, 9, ItemType.Armor));
		itemBank.add(new Item("Galeforce Bow", 20, 100, 19, ItemType.Weapon));
		itemBank.add(new Item("Thornmail", 25, 90, 23, ItemType.Armor));
		itemBank.add(new Item("Ring of the Lost Queen", 1, 500, 0, ItemType.Other));

	}
	public static Item generate() {			//gets a random index of itemBank to and sends to inventory 
		Random randomNum = new Random();
		int randomIndex = randomNum.nextInt(itemBank.size());
		return itemBank.get(randomIndex);
	}
}

