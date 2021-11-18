// World.java

public class World {
	private Room[] rooms;
	private int currentRoom;
	
	static String[] grid1 = new String[] {
		"##################                ######################    ",
		"##              ##                ##      i           ##    ",
		"##  @           ###########       ##        *         ##    ",
		"##                       ##       ##                  ##    ",
		"##              #######  ##       ##################  ##    ",
		"##              ##   ##  ##                       ##  ##    ",
		"##################   ##  ##################       ##  ##    ",
		"                     ##                  ##       ##  ##    ",
		"                     ##   *  i           ##       ##  ##    ",
		"                     ##                  ##       ##  ##    ",
		"                     ##############  ######       ##  ##    ",
		"                                 ##  ##           ##  ##    ",
		"                                 ##  ##           ##  ##    ",
		"                       ############  ###############  ######",
		"                       ##                                 ##",
		"                       ##                                 ##",
		"    #####################                  *              ##",
		"    ##                                                    ##",
		"    ##  #################                                 ##",
		"    ##  ##             ##                                 ##",
		"    ##  ##             #################  ##################",
		"    ##  ##                            ##  ##                ",
		"    ##  ##                            ##  ##                ",
		"    ##  ##                       #######  #######           ",
		"    ##  ##                       ##            ##           ",
		"######  ####                     ##  i  *   +  ##           ",
		"##        ##                     ##            ##           ",
		"## i  *   ##                     ################           ",
		"##        ##                                                ",
		"############                                                "
	};
	static String[] grid2 = new String[] {
        "##################         ###############################  ",
        "##              ##         ##           i               ##  ",
        "##  @           #############              *            ##  ",
        "##                                                      ##  ",
        "##              ####################################    ##  ",
        "##         *    ##                                ##    ##  ",
        "##################   ######################       ##    ##  ",
        "                     ##                  ##       ##    ##  ",
        "                     ##   *  i           ##       ##    ##  ",
        "                     ##                  ##       ##    ##  ",
        "                     ##############  ######       ##    ##  ",
        "                                 ##  ##           ##    ##  ",
        "                                 ##  ##           ##    ##  ",
        "                       ############  ###############    ####",
        "                       ##                                 ##",
        "                       ##                                 ##",
        "   ######################                   *             ##",
        "   ##                                                     ##",
        "   ##   #################          *                      ##",
        "   ##   ##             ##                                 ##",
        "   ##   ##             #####################################",
        "   ##   ##      ##################                          ",
        "   ##   ##      ##              ##                          ",
        "   ##   ##      ##              ############################",
        "   ##   ##      ##     ##       ##               *        ##",
        "#####   ##########     ##       ##      i                 ##",
        "##                     ##       ##                    +   ##",
        "## i          *        ##       ##         ########       ##",
        "##                     ##                  ##    ##       ##",
        "#############################################    ###########",
	};
	static String[] grid3 = new String[] {
        "##################               #######################    ",
        "##              ##               ##      i            ##    ",
        "##  @           ###########      ##        *          ##    ",
        "##                       ##      ##                   ##    ",
        "##              #######  ##      ##   ##############  ##    ",
        "##              ##   ##  ##      ##   #####       ##  ##    ",
        "##################   ##  ##########   #####       ##  ##    ",
        "                     ##                  ##       ##  ##    ",
        "                     ##   *  i           ##       ##  ##    ",
        "                     ##                  ##       ##  ##    ",
        "                     ######################       ##  ##    ",
        "                                                  ##  ##    ",
        "                                                  ##  ##    ",
        "                       #############################  ######",
        "                       ##                                 ##",
        "                       ##    *                            ##",
        "    #####################                  *              ##",
        "    ##                                                    ##",
        "    ##  #################                                 ##",
        "    ##  ##             ##                                 ##",
        "    ##  ##             #####################################",
        "    ##  ##                                                  ",
        "    ##  ##                                                  ",
        "    ##  ##                   ####################           ",
        "    ##  ##                   ##     i        i ##           ",
        "######  ##                   ##  i  *   *      ##           ",
        "##      #######################                ##           ",
        "## i  *                            i   i       ##           ",
        "##                              i            i ##           ",
        "#################################################           ",
	};
	
	public World() {
		rooms = new Room[3];
		currentRoom = 0;
		rooms[0] = new Room(grid1, 30, 60);
		rooms[1] = new Room(grid2, 30, 60);
		rooms[2] = new Room(grid3, 30, 60);
	}

	public Room getCurrentRoom() {
		return rooms[currentRoom];
	}
	
	public void nextRoom() {
		currentRoom++;
	}
}