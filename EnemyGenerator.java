// EnemyGenerator.java
// this class contains a static method for creating enemies randomly

import java.util.Random;
import java.util.ArrayList;
public class EnemyGenerator {
	

    public static Enemy generate(int row, int col, String type) {
	ArrayList<Enemy> enemyBank = new ArrayList<Enemy>();
	{
		enemyBank.add(new Enemy("Ghoul", '*',row, col, 15, 5, 3));
		enemyBank.add(new Enemy("Ruined Melee Minion", '*',row, col, 15, 4, 5));
		enemyBank.add(new Enemy("Ruined Mage", '*',row, col, 10, 7, 3));
		enemyBank.add(new Enemy("Pantheon", 'P',row, col, 20, 10, 7));
		enemyBank.add(new Enemy("Kalista", 'K',row, col, 25, 12, 8));
		enemyBank.add(new Enemy("Miss Fortune",'M', row, col, 20, 11, 5));
	}

        Random randomNum = new Random();
	int randomIndex = randomNum.nextInt(enemyBank.size());
	if (type.equals("Viego")) {
		return new Enemy("Viego", 'V', row, col, 50, 20, 11);
	}
	return enemyBank.get(randomIndex);
    }
}

