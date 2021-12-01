// EnemyGenerator.java
// this class contains a static method for creating enemies randomly

import java.util.Random;
import java.util.ArrayList;
public class EnemyGenerator {
	

    public static Enemy generate(int row, int col) {
	ArrayList<Enemy> enemyBank = new ArrayList<Enemy>();
	{
		enemyBank.add(new Enemy("Ghoul", '*',row, col, 15, 5, 0));
		enemyBank.add(new Enemy("Ruined Melee Minion", '*',row, col, 15, 4, 1));
		enemyBank.add(new Enemy("Ruined Mage", '*',row, col, 10, 7, 0));
		enemyBank.add(new Enemy("Pantheon", 'P',row, col, 20, 10, 3));
		enemyBank.add(new Enemy("Kalista", 'K',row, col, 25, 12, 4));
		enemyBank.add(new Enemy("Miss Fortune",'M', row, col, 20, 11, 2));
	}

        Random randomNum = new Random();
	int randomIndex = randomNum.nextInt(enemyBank.size());
	return enemyBank.get(randomIndex);
    }
}

