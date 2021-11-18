// Main.java
// contains the main class for running the game

import ansi_terminal.*;
import java.util.Scanner;
public class Main {
    public static void main(String args[]) {
    	Terminal.clear();
    	Terminal.rawMode();
    	
        String[] story = {
			 "The Ruination is underway. The Ruined King, Viego, has returned in an attempt to resurrect the Queen of his long lost kingdom, Isolde.",
             "The Sentinels of Light have failed, you are the final Champion who can stop him from spreading the deadly Mist throughout the entire world.",
        };
        Terminal.setForeground(Color.GREEN);
        for (int row = 0; row < story.length; row++) {
            Terminal.warpCursor(row + 1, 0);
            System.out.print(story[row]);
        }
        Terminal.warpCursor(story.length + 1, 0);
        String name = Terminal.getLine("What is your name Champion? ");
        Terminal.reset();

        // make and run the Game
        Game game = new Game(name);
        game.run();

        // put terminal back into cooked mode
        Terminal.cookedMode();
    }
}

