import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveLoad {
	static PrintWriter writer;

	public static void save(int roomNum, Player player, ArrayList<Box> boxes, ArrayList<Enemy> enemies) throws FileNotFoundException, UnsupportedEncodingException {
		// let us write to a file
		writer = new PrintWriter("save.txt", "UTF-8");
		
		// record room number, because layout is written into an array at index of room number
//		writer.print("roomNum: ");
		writer.println(roomNum);
		
//		writer.print("player name: ");
		writer.println(player.getName());
//		writer.print("player position: ");
		writePosition(player.getPosition());
//		writer.print("player health: ");
		writer.println(player.getHealth());
		Inventory inventory = player.getInventory();
		
		// write size of lists so we know how many lines to read when loading
		ArrayList<Item> items = inventory.getItems();
//		writer.print("num items: ");
		writer.println(items.size());
//		writer.print("num boxes: ");
		writer.println(boxes.size());
//		writer.print("num enemies: ");
		writer.println(enemies.size());
		
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			writeItem(item);
		}
		
		for (int i = 0; i < boxes.size(); i++) {
			Position position = boxes.get(i).getPosition();
			writePosition(position);
			Item item = boxes.get(i).getItem();
			writeItem(item);
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			Position position = enemy.getPosition();
//			writer.print("enemy position: ");
			writePosition(position);
//			writer.print("enemy name: ");
			writer.println(enemy.getName());
//			writer.print("enemy display: ");
			writer.println(enemy.getDisplay());
//			writer.print("enemy health: ");
			writer.println(enemy.getHealth());
//			writer.print("enemy damage: ");
			writer.println(enemy.getDamage());
//			writer.print("enemy protection: ");
			writer.println(enemy.getProtection());
		}
		
		// finish and save?
		writer.close();
	}
	
	// write row and column of position :w
	private static void writePosition(Position position) {
		writer.println(position.getRow());
		writer.println(position.getCol());
	}
	
	// write all of the stats of an item
	private static void writeItem(Item item) {
//		writer.print("item name: ");
		writer.println(item.getName());
//		writer.print("item weight: ");
		writer.println(item.getWeight());
//		writer.print("item value: ");
		writer.println(item.getValue());
//		writer.print("item strength: ");
		writer.println(item.getStrength());
//		writer.print("item type: ");
		writer.println(item.getType());
	}
	
	private static String getLine(int num) throws IOException {
		String line = Files.readAllLines(Paths.get("save.txt")).get(num);
		return line;
	}
	
	public static int loadRoomNum() throws IOException {
		String roomNum = getLine(0);
		return Integer.parseInt(roomNum);
	}
	
	public static Player loadPlayer() throws IOException {
		String name = getLine(1);
		String row = getLine(2);
		String col = getLine(3);
		Position position = new Position(Integer.parseInt(row), Integer.parseInt(col));
		int health = Integer.parseInt(getLine(4));
		Player player = new Player(position, name, health);
//      System.out.println(player.getName());
//      System.out.println(player.getPosition().getRow());
//      System.out.println(player.getPosition().getCol());
//      System.out.println(player.getHealth());
		
		Inventory inventory = new Inventory(100);
		int itemCount = Integer.parseInt(getLine(5));
		for (int i = 0; i < itemCount; i++) {
			// skip first 8 lines because that's where items start
			// then skip i * 5 because each item takes 5 lines
			Item item = getItem(8 + (i * 5));
			inventory.add(item);
		}
		player.setInventory(inventory);
		
		return player;
	}
	
	private static Item getItem(int line) throws IOException {
		String itemName = getLine(line);
		int weight = Integer.parseInt(getLine(line + 1));
		int value = Integer.parseInt(getLine(line + 2));
		int strength = Integer.parseInt(getLine(line + 3));
		String itemType = getLine(line + 4);
		ItemType type;
		switch (itemType) {
			case "Weapon":
				type = ItemType.Weapon;
				break;
			case "Armor":
				type = ItemType.Armor;
				break;
			case "Other":
				type = ItemType.Other;
				break;
			default:
				type = ItemType.Other;
				break;
		}
		Item item = new Item(itemName, weight, value, strength, type);
//		System.out.println(item.getName());
//		System.out.println(item.getWeight());
//		System.out.println(item.getValue());
//		System.out.println(item.getStrength());
//		System.out.println(item.getType());
		return item;
	}

	public static ArrayList<Box> loadBoxes() throws IOException {
		int itemCount = Integer.parseInt(getLine(5));
		int boxCount = Integer.parseInt(getLine(6));
		
		int boxItemsStart = 8 + (itemCount * 5);
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < boxCount; i++) {
			int start = boxItemsStart + (i * 7);
			int row = Integer.parseInt(getLine(start));
			int col = Integer.parseInt(getLine(start + 1));
			Item item = getItem(start + 2);
			Box box = new Box(row, col, item);
//			System.out.println(row);
//			System.out.println(col);
//			System.out.println(item.getName());
//			System.out.println(item.getWeight());
//			System.out.println(item.getValue());
//			System.out.println(item.getStrength());
//			System.out.println(item.getType());
			boxes.add(box);
		}
		
		return boxes;
	}
	
	public static ArrayList<Enemy> loadEnemies() throws IOException {
		int itemCount = Integer.parseInt(getLine(5));
		int boxCount = Integer.parseInt(getLine(6));
		int enemyCount = Integer.parseInt(getLine(7));
		
		// skip 8 because thats where items start
		// then skip itemCount * 5 because thats how many lines per item
		// then skip boxCount * 7 because thats how many lines boxes take
		int enemyStart = 8 + (itemCount * 5) + (boxCount * 7);
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for (int i = 0; i < enemyCount; i++) {
			int start = enemyStart + (i * 7);
			int row = Integer.parseInt(getLine(start));
			int col = Integer.parseInt(getLine(start + 1));
			String name = getLine(start + 2);
			char display = getLine(start + 3).charAt(0);
			int hp = Integer.parseInt(getLine(start + 4));
			int damage = Integer.parseInt(getLine(start + 5));
			int protection = Integer.parseInt(getLine(start + 6));
			Enemy enemy = new Enemy(name, display, row, col, hp, damage, protection);
//			System.out.println(enemy.getName());
//			System.out.println(enemy.getDisplay());
//			System.out.println(enemy.getPosition().getRow());
//			System.out.println(enemy.getPosition().getCol());
//			System.out.println(enemy.getHealth());
//			System.out.println(enemy.getDamage());
//			System.out.println(enemy.getProtection());
			enemies.add(enemy);
		}
		return enemies;
	}
}
