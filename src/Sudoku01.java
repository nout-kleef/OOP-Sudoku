import java.util.InputMismatchException;
import java.util.Scanner;

public class Sudoku01 {

    /**
     * Print a game menu message to the console.
     */
    public static void printMenu(int max) {
        System.out.print("\n-*-*-*-*-*-*-*-*-\n" +
                "1. Set field\n" +
                "2. Clear field\n" +
                "3. Print game\n" +
                "4. Exit\n\n" +
                "Select an action [1-" + max + "]: ");
    }   

    /**
     * Read a single integer value from the console and return it.
     * This function blocks the program's execution until a user
     * entered a value into the command line and confirmed by pressing
     * the Enter key.
     * @return The user's input as integer or -1 if the user's input was invalid.
     */
    public static int parseInput() {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        try {
            return in.nextInt();
        } catch (InputMismatchException missE) {
            in.next(); // discard invalid input
            return -1;
        }
    }
    
    /**
     * run an iteration of the main game loop
     * this method requests user input and adjusts program behaviour based upon this
     * @return whether the user chose to exit the program/game
     */
    public static int gameLoop(int min, int max) {
    	Sudoku01.printMenu(max);
    	return Sudoku01.requestInt("your chosen menu option", min, max);
    }

    /**
     * Display a dialog requesting a single integer which is returned
     * upon completion.
     *
     * The dialog is repeated in an endless loop if the given input 
     * is not an integer or not within min and max bounds.
     *
     * @param msg: a name for the requested data.
     * @param min: minium accepted integer.
     * @param max: maximum accepted integer.
     * @return The user's input as integer.
     */
    public static int requestInt(String msg, int min, int max) {
        java.util.Objects.requireNonNull(msg);

        while(true) {
            System.out.print("Please provide " + msg + ": ");
            int input = parseInput();
            if (input >= min && input <= max) return input;
            else {
                System.out.println("Invalid input. Must be between " + min + " and " + max);
            }
        }
    }

    private static void setField(GameGrid game, boolean clear) {
    	java.util.Objects.requireNonNull(game);
    	final int MAX = GameGrid.MAX_VAL;
    	final String range = "(1-" + MAX + ")";
    	final int i = Sudoku01.requestInt("a row number " + range, 1, MAX) - 1; // -1 for 0-indexing
    	final int j = Sudoku01.requestInt("a column number " + range, 1, MAX) - 1; // -1 for 0-indexing
    	int val = GameGrid.EMPTY_VAL;
    	if(clear) {
    		game.clearField(i, j);
    	} else {
    		boolean valid = true;
    		do {
    			val = Sudoku01.requestInt("the desired value for the cell in row: " +
    							(i + 1) + ", column: " + (j + 1) + " " + range, 1, MAX);
    			valid = game.setField(i, j, val);
    			if(!valid)
    				System.out.println("Illegal value for this cell. Please choose a different value.");
    		} while(!valid);
    	}
    	System.out.println("Setting cell's value to " + val + "..");
    	System.out.println(game);
    }

    /**
     * handle the choice the user makes when prompted by the main game loop
     * @param userChoice: 1,2..n, n the last menu option
     * @return whether the program/game should exit
     */
    private static boolean handleChoice(int userChoice, GameGrid game) {
    	java.util.Objects.requireNonNull(game);
    	switch (userChoice) {
		case 1:
			Sudoku01.setField(game, false);
			return false;
		case 2:
			Sudoku01.setField(game, true);
			return false;
		case 3:
			System.out.println(game);
			return false;
		case 4:
			System.out.println("Exiting..");
			return true;
		default:
			return false;
		}
    }
    
    public static void main(String[] args) {
    	java.util.Objects.requireNonNull(args);
    	if(args.length == 0)
    		throw new IllegalArgumentException();
    	// use this as the path to the file holding the int[][] grid
    	/* NOTE - PATH should be an absolute path that works for the file system
    	 * on which this class is located.
    	 * unfortunately it's not possible to allow for spaces in this path,
    	 * as this would split up the path into multiple args elements
    	 * example: C:\\Users\\[user]\\folder\\file.sd
    	 */
    	final String PATH = args[0];
    	
    	// initialise game
    	GameGrid game = new GameGrid(PATH);
    	System.out.println(game);
    			
        boolean userExit = false;
        while(!userExit) {
        	final int userChoice = gameLoop(1, 4); // hardcoded min and max. tolerated since the menu itself is also hardcoded
        	// handle the choice the user made
        	userExit = Sudoku01.handleChoice(userChoice, game);
        }
    }
}
