
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
	public static boolean solveV2(GameGrid game) {
		final int MAX_INDEX = GameGrid.GRID_DIM - 1;
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
						legalValueFound = true;
						break;
					}
				}
				// if we were unable to find a legal value, we must backtrack
				movingForward = legalValueFound;
				if(!pointer.move(movingForward)) {
					// can't move AND COL == 0? no solution.
					return COL != 0;
				}
			}
		}
		// end reached i.e. solution found
		return true;
	}
	
	public static boolean solve(GameGrid game) {
		final int MAX_INDEX = GameGrid.GRID_DIM - 1;
		boolean backtracking = false;
		for(int row = 0; row < GameGrid.GRID_DIM;) {
			boolean incrementRow = true;
			// col incrementation is taken care of manually, not in loop header
			for(int col = 0; col < GameGrid.GRID_DIM;) {
				if(game.grid[row][col].isInitial()) {
					if(backtracking) {
						if(col == 0) {
							if(row == 0) {
								// backtracking landed us back at the beginning
								return false;
							}
							// not at first Field (yet): go back one Field
							col = MAX_INDEX;
							// row - 2, because at the end of the row loop we get +1
							row--;
							incrementRow = false;
						} else {
							col--;
						}
					} else {
						if(row == col && row == MAX_INDEX) {
							// end reached on a fixed cell: solution found
							return true;
						}
						col++;
						continue;
					}
				} else {
					backtracking = false;
				}
				/* not a set value: we can try values for this field.
				 * we shouldn't start at the empty value for this field,
				 * but rather at the current value + 1
				 * this ensures:
				 * 		1. we start with value 1 for empty fields
				 * 		2. we don't end up trying the same value multiple times
				 */
				Field current = game.grid[row][col];
				boolean valueFound = false;
				for(int val = current.getValue() + 1; val <= GameGrid.MAX_VAL; val++) {
					if(game.setField(row, col, val)) {
						if(DEBUG) {
							System.out.printf("grid after updating row %s and column %s\n", row, col);
							System.out.println(game);
						}
						// legal value found
						backtracking = false;
						valueFound = true;
						break;
					}
				}
				if(!valueFound) {
					/* backtrack:
					 * there is no need for a new nested loop
					 * because we only check values from the current value + 1
					 * onwards, we can simply move back one Field
					 * we could end up here again, so we should check if we're
					 * back at square one, and if so, terminate
					 */
					// backtracking is used to skip clue cells when going back
					backtracking = true;
					if(col == 0) {
						if(row == 0) {
							// backtracking landed us back at the beginning
							return false;
						}
						// not at first Field (yet): go back one Field
						col = MAX_INDEX;
						// row - 2, because at the end of the row loop we get +1
						row--;
						incrementRow = false;
					} else {
						col--;
					}
				} else if(row == col && row == MAX_INDEX) {
					// we've reached the end: solution found
					return true;
				} else {
					// we've found a legal value AND haven't reached the end yet
					col++;
				}
			}
			if(incrementRow)
				row++;
		}
		// TODO: implement solution flag
		return false;
	}
}
