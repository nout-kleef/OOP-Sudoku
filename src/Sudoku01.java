import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;

public class Sudoku01 {
	final static File GAMES_FOLDER = new File(System.getProperty("user.dir") +
			"\\games\\");
	final static String[] MENU_OPTIONS = 
		{
				"Set field",
				"Clear field",
				"Print game",
				"Solve sudoku",
				"Exit"
		};
	
    /**
     * use the console to show the game menu to the user
     * @param max: amount of options
     */
    public static void printMenu() {
        System.out.print("\n- - - - - - - - - -\n");
        for(int i = 0; i < MENU_OPTIONS.length; i++) {
        	System.out.println((i + 1) + ") " + MENU_OPTIONS[i]);
        }
        System.out.println("Select an action [1-" + MENU_OPTIONS.length + "]: ");
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
    public static int gameLoop() {
    	Sudoku01.printMenu();
    	return Sudoku01.requestInt("your chosen menu option", 1, MENU_OPTIONS.length);
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
    
    /**
     * set/clear a cell<br />
     * request a row, column (and value) from the user until valid arguments are given
     * @param game: the GameGrid instance
     * @param clear: clears the cell if true
     */
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
			/* NOTE: calling GameGrid(Field[][]) delegates deep-copying
			 * to that constructor, so we don't worry about that here
			 */
			GameGrid solutionGame = new GameGrid(game);
			if(Solver.solve(solutionGame)) {
				System.out.println("\nA solution was found: ");
				System.out.println(solutionGame);
			} else {
				System.out.println("No solution was found for this sudoku.\n"
						+ "Please try again using a different sudoku grid.");
			}
			return false;
		case 5:
			System.out.println("Exiting..");
			return true;
		default:
			return false;
		}
    }
    
    private static File[] listFilesForFolder(final File folder) {
    	final File[] FILE_LIST = folder.listFiles();
    	ArrayList<File> results = new ArrayList<File>();
    	for(int i = 0; i < FILE_LIST.length; i++) {
    		final File current = FILE_LIST[i];
    		if(current.isDirectory())
    			continue; // disregard nested directories
    		else
    			results.add(current);
    	}
    	return results.toArray(new File[0]);
    }
    
    public static void main(String[] args) {
    	java.util.Objects.requireNonNull(args);
    	if(args.length == 0)
    		throw new IllegalArgumentException("no args specified");
    	final String PATH;
    	if(args[0].equals("--interactive")) {
    		/* assume that a "games" dir exists in the project dir.
    		 * let the user pick any sudoku from this directory (non-recursive)
    		 */
    		final File[] OPTIONS = listFilesForFolder(GAMES_FOLDER);
    		// request puzzle
    		System.out.println("You've initiated this program in interactive "
    				+ "mode.\nPlease pick the desired puzzle by entering "
    				+ "the file's index:");
    		for(int i = 0; i < OPTIONS.length; i++) {
    			final String OPTION = OPTIONS[i].toString();
    			final int REDUNDANT_ENDS_INDEX = OPTION.lastIndexOf('\\');
    			final String READABLE = OPTION.substring(REDUNDANT_ENDS_INDEX + 1);
    			System.out.println("\t" + (i + 1) + ") \t" + READABLE);
    		}
    		final int PUZZLE_INDEX = 
    				requestInt("the file's index", 1, OPTIONS.length) - 1;
    		// set up using chosen file
    		PATH = OPTIONS[PUZZLE_INDEX].toString();
    	} else {
    		/* assume args[0] is the path for the sudoku file
	    	 * use this as the path to the file holding the int[][] grid
	    	 * NOTE - PATH should be an absolute path that works for the file 
	    	 * system on which this class is located.
	    	 * unfortunately it's not possible to allow for spaces in this path,
	    	 * as this would split up the path into multiple args elements
	    	 * example: C:\\Users\\[user]\\folder\\file.sd
	    	 */
	    	PATH = args[0];
    	}
    	// initialise game
    	GameGrid game = new GameGrid(PATH);
    	System.out.println(game);
    			
        boolean userExit = false;
        while(!userExit) {
        	final int userChoice = gameLoop(); // hardcoded min and max. tolerated since the menu itself is also hardcoded
        	// handle the choice the user made
        	userExit = Sudoku01.handleChoice(userChoice, game);
        }
    }
}
