import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Objects;

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
        Objects.requireNonNull(msg);

        while(true) {
            System.out.print("Please provide " + msg + ": ");
            int input = parseInput();
            if (input >= min && input <= max) return input;
            else {
                System.out.println("Invalid input. Must be between " + min + " and " + max);
            }
        }
    }

    private static void setField(int[][] grid, boolean clear) {
    	final int max = grid.length;
    	final String range = "(1-" + max + ")";
    	final int i = Sudoku01.requestInt("a row number " + range, 1, max) - 1; // -1 for 0-indexing
    	final int j = Sudoku01.requestInt("a column number " + range, 1, max) - 1; // -1 for 0-indexing
    	int val;
    	if(clear) {
    		val = 0;
    	} else {
    		boolean valid = true;
    		do {
    			val = Sudoku01.requestInt("the desired value for the cell in row: " +
    							(i + 1) + ", column: " + (j + 1) + " " + range, 1, max);
    			valid = Sudoku01.isValid(j, i, val, grid);
    			if(!valid)
    				System.out.println("Illegal value for this cell. Please choose a different value.");
    		} while(!valid);
    	}
    	System.out.println("Setting cell's value to " + val + "..");
    	grid[i][j] = val;
    	Sudoku01.printGrid(grid);
    }
    
    public static void printGrid(int[][] grid) {
    	// formatting
    	final String horizontalCellPadding = " ";
    	final String horizontalBlockPadding = "  "; // added on top of a horizontalCellPadding
    	final String verticalCellPadding = "";
    	final String verticalBlockPadding = "\n";
    	// first row: 9 4 0  1 0 2  0 5 8
    	final int blockSize = (int) Math.sqrt(grid.length); // for the hardcoded grid this will be 3
    	for(int i = 0; i < grid.length; i++) {
    		int[] row = grid[i];
    		if(i != 0) {
    			// not first row
    			// block formatting
    			if(i % blockSize == 0) {
    				System.out.print(verticalBlockPadding);
    			}
    			// cell/row formatting
    			System.out.print(verticalCellPadding);
    		}
    		// print current row
    		Sudoku01.printRow(row, blockSize, horizontalBlockPadding, horizontalCellPadding);
    	}
    } 
    
    private static void printRow(int[] row, int blockSize, String horizontalBlockPadding, String horizontalCellPadding) {
    	for(int j = 0; j < row.length; j++) {
			int cell = row[j];
			// cell formatting
			if(j == 0) {
				// first iteration
				System.out.print(cell);
			} else {
				// block formatting
    			if(j % blockSize == 0) {
    				System.out.print(horizontalBlockPadding);
    			}
    			// cell formatting
    			System.out.print(horizontalCellPadding + cell);
    			// row formatting
    			if(j == row.length - 1) {
    				// last iteration
    				System.out.print("\n");
    			}
			}
		}
    }

    /**
     * handle the choice the user makes when prompted by the main game loop
     * @param userChoice: 1,2..n, n the last menu option
     * @return whether the program/game should exit
     */
    private static boolean handleChoice(int userChoice, int[][] grid) {
    	switch (userChoice) {
		case 1:
			Sudoku01.setField(grid, false);
			return false;
		case 2:
			Sudoku01.setField(grid, true);
			return false;
		case 3:
			Sudoku01.printGrid(grid);
			return false;
		case 4:
			System.out.println("Exiting..");
			return true;
		default:
			return false;
		}
    }
    
    public static void main(String[] args) {
        int[][] grid = {
            {9,4,0,1,0,2,0,5,8},
            {6,0,0,0,5,0,0,0,4},
            {0,0,2,4,0,3,1,0,0},
            {0,2,0,0,0,0,0,6,0},
            {5,0,8,0,2,0,4,0,1},
            {0,6,0,0,0,0,0,8,0},
            {0,0,1,6,0,8,7,0,0},
            {7,0,0,0,4,0,0,0,3},
            {4,3,0,5,0,9,0,1,2}
        };
//        int[][] grid = { // 4x4 block sudoku for tests
//                {9,4,0,1,0,2,0,5,8,1,1,1,1,1,1,1},
//                {6,0,0,0,5,0,0,0,4,1,1,1,1,1,1,1},
//                {0,0,2,4,0,3,1,0,0,1,1,1,1,1,1,1},
//                {0,2,0,0,0,0,0,6,0,1,1,1,1,1,1,1},
//                {5,0,8,0,2,0,4,0,1,1,1,1,1,1,1,1},
//                {0,6,0,0,0,0,0,8,0,1,1,1,1,1,1,1},
//                {0,0,1,6,0,8,7,0,0,1,1,1,1,1,1,1},
//                {7,0,0,0,4,0,0,0,3,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1},
//                {4,3,0,5,0,9,0,1,2,1,1,1,1,1,1,1}
//            };

        Sudoku01.printGrid(grid);

        boolean userExit = false;
        while(!userExit) {
        	final int userChoice = gameLoop(1, 4); // hardcoded min and max. tolerated since the menu itself is also hardcoded
        	// handle the choice the user made
        	userExit = Sudoku01.handleChoice(userChoice, grid);
        }
    }
}
