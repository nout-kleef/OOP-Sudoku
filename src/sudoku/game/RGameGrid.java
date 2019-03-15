/**
 * 
 */
package sudoku.game;

/**
 * @author nout-kleef
 *
 */
public final class RGameGrid extends GameGrid {

	/**
	 * @param game
	 */
	public RGameGrid(GameGrid game) {
		super(game);
	}

	/**
	 * @param grid
	 */
	public RGameGrid(int[][] grid) {
		super(grid);
	}

	/**
	 * @param path
	 */
	public RGameGrid(String path) {
		super(path);
	}
	
	/**
     * check whether a value is valid in a cell
     * @param row: the row we're checking
     * @param column: the column we're checking
     * @param val: the desired value
     * @return: true if value is allowed
     */
	@Override
    protected boolean isValid(int row, int column, int val) {
    	return this.checkRow(row, val) &&
    			this.checkColumn(column, val) &&
    			this.checkSubGrid(row, column, val);
    }

}
