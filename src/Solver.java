
public class Solver {
	public static boolean solve(GameGrid game) {
		final int MAX_INDEX = GameGrid.GRID_DIM - 1;
		for(int row = 0; row < GameGrid.GRID_DIM; row++) {
			// col incrementation is taken care of manually, not in loop header
			for(int col = 0; col < GameGrid.GRID_DIM;) {
				Field current = game.grid[row][col];
				if(current.isInitial()) {
					if(row == col && row == MAX_INDEX) {
						// end reached on a fixed cell: solution found
						return true;
					}
					col++;
					continue;
				}
				/* not a set value: we can try values for this field.
				 * we shouldn't start at the empty value for this field,
				 * but rather at the current value + 1
				 * this ensures:
				 * 		1. we start with value 1 for empty fields
				 * 		2. we don't end up trying the same value multiple times
				 */
				boolean valueFound = false;
				for(int val = current.getValue() + 1; val < GameGrid.MAX_VAL; val++) {
					if(game.setField(row, col, val)) {
						// DEBUG
						System.out.println(game);
						// legal value found
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
					if(col == 0) {
						if(row == 0) {
							// backtracking landed us back at the beginning
							return false;
						}
						// not at first Field (yet): go back one Field
						col = MAX_INDEX;
						// row - 2, because at the end of the row loop we get +1
						row -= 2;
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
		}
		// TODO: implement solution flag
		return false;
	}
}
