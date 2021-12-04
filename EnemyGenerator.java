// EnemyGenerator.java
// this class contains a static method for creating enemies randomly

import java.util.Random;
import java.util.ArrayList;

/** Creates a random instance of an Enemy
 * @author Tyler Martzall, John
 */
public class EnemyGenerator {

	/** Creates a random instance of an Enemy
	 * @param row Position.row
	 * @param col Position.col
	 * @return a random Enemy from enemyBank
	 */
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

        if (row == 26 && col == 39) {
		return new Enemy("Viego", 'V', row, col, 50, 20, 15); 
	} else {
		Random randomNum = new Random();
		int randomIndex = randomNum.nextInt(enemyBank.size());
		return enemyBank.get(randomIndex);
	}
    }
}

