
public class Solver {
	final static boolean DEBUG = true;
	
	/**
	 * <h2>Backtracking algorithm v2.0</h2>
	 * Attempts the lowest legal value for a cell and then moves to the next.<br>
	 * Returns to a previous cell when a mistake has been made.
	 * 
	 * <h2>pseudocode</h2>
	 * starting at the top left corner, for each cell:
	 * 		if end reached return true
	 * 		(else) if cell is clue move to next cell
	 * 		else
	 * 			while value is not allowed and value < 9
	 * I)			increment value and retry
	 * 			if legal value found move to next cell
	 * 			else move to previous non-clue cell
	 * 				if back at first cell and value = 9 return false
	 * 				else continue from I onwards
	 * 
	 * @param game: GameGrid that we will attempt to solve (pass by reference)
	 * @return true if a solution is found
	 */
	public static boolean solve(GameGrid game) {
		// to ease movement I implemented a wrapper
		final CellIndexWrapper pointer = 
				new CellIndexWrapper(0, 0, GameGrid.GRID_DIM);
		boolean endReached = false;
		boolean movingForward = true; // == "not backtracking"
		while(!endReached) {
			final int ROW = pointer.getRow();
			final int COL = pointer.getCol();
			/*if(ROW == MAX_INDEX && ROW == MAX_INDEX) {
				endReached = true;
			} else */if(game.grid[ROW][COL].isInitial()) {
				/* this cell contains a clue, so we should move away
				 * in the current direction
				 */
				if(!pointer.move(movingForward)) {
					// can't move AND COL == 0? no solution.
					return COL != 0;
				}
				/* at this point we've either moved or returned,
				 * meaning we're ready for the next iteration of the
				 * while loop
				 */
			} else {
				final int START = game.grid[ROW][COL].getValue() + 1;
				boolean legalValueFound = false;
				for(int val = START; val <= GameGrid.GRID_DIM; val++) {
					if(game.setField(ROW, COL, val)) {
						if(DEBUG) {
							System.out.printf("grid after updating row %s "
									+ "and column %s\n", ROW, COL);
							System.out.println(game);
						}
						legalValueFound = true;
						break;
					}
				}
				/* if we were unable to find a legal value, we must backtrack
				 * and reset this non-clue cell to its empty value
				 */
				movingForward = legalValueFound;
				if(!legalValueFound)
					game.grid[ROW][COL].setValue(GameGrid.EMPTY_VAL);
				if(!pointer.move(movingForward)) {
					// can't move AND COL == 0? no solution.
					return COL != 0;
				}
			}
		}
		// end reached i.e. solution found
		return true;
	}
}
