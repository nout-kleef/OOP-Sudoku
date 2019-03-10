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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param grid
	 */
	public RGameGrid(int[][] grid) {
		super(grid);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param path
	 */
	public RGameGrid(String path) {
		super(path);
		// TODO Auto-generated constructor stub
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
