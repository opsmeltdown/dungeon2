// Main.java
// contains the main class for running the game

import ansi_terminal.*;
import java.util.Scanner;
public class Main {
    public static void main(String args[]) {
	String name;
	Scanner input = new Scanner(System.in);
        // put termain in raw mode
        
	System.out.print("The Ruination is underway. The Ruined King, Viego, has returned in an attempt to resurrect the Queen of his long lost kingdom, Isolde.\n\r");
	System.out.print("The Sentinels of Light have failed, you are the final Champion who can stop him from spreading the deadly Mist throughout the entire world.\n\r");
	System.out.print("What is your name Champion?\n\r");
	name = input.nextLine();
	Terminal.rawMode();
        // make and run the Game
        Game game = new Game(name);
        game.run();

        // put terminal back into cooked mode
        Terminal.cookedMode();
    }
}

