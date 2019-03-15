/**
 * 
 */
package sudoku.game;

/**
 * @author nout-kleef
 *
 */
public final class XGameGrid extends GameGrid {

	private static final String PRE_CELL_PADDING = "[";
	private static final String POST_CELL_PADDING = "]";

	/**
	 * @param game
	 */
	public XGameGrid(GameGrid game) {
		super(game);
	}

	/**
	 * @param grid
	 */
	public XGameGrid(int[][] grid) {
		super(grid);
	}

	/**
	 * @param path
	 */
	public XGameGrid(String path) {
		super(path);
	}
	
	@Override
	public String toString() {
		String representation = "";
    	for(int i = 0; i < this.grid.length; i++) {
    		if(i != 0) {
    			// not first row
    			// block formatting
    			if(i % GameGrid.SUBGRID_DIM == 0) {
    				representation += GameGrid.VERTICAL_BLOCK_PADDING;
    			}
    			// cell/row formatting
    			representation += GameGrid.VERTICAL_CELL_PADDING;
    		}
    		representation += this.rowToString(i);
    	}
    	return representation;
	}
	
	private String rowToString(int i) {
		final Field[] ROW = this.grid[i];
    	String representation = "";
    	for(int j = 0; j < ROW.length; j++) {
			Field cell = ROW[j];
			// cell formatting
			if(j == 0) {
				// first iteration
				representation += cell;
			} else {
				// block formatting
    			if(j % GameGrid.SUBGRID_DIM == 0) {
    				representation += GameGrid.HORIZONTAL_BLOCK_PADDING;
    			}
    			// cell formatting
    			final String VAL;
    			if(i == j || i + j == GameGrid.GRID_DIM - 1) {
    				// currently on a diagonal
    				VAL = PRE_CELL_PADDING + cell + POST_CELL_PADDING;
    			} else {
    				VAL = GameGrid.HORIZONTAL_CELL_PADDING + cell;
    			}
    			representation += VAL;
    			// row formatting
    			if(j == ROW.length - 1) {
    				// last iteration
    				representation += "\n";
    			}
			}
		}
    	return representation;
	}
	
	private boolean checkDiagonal(int row, int column, int val) {
		// convert into single array
		if(row == column) {
			Field[] diagonal = new Field[this.grid.length];
			for(int i = 0; i < diagonal.length; i++)
	    		diagonal[i] = this.grid[i][i];
			if(GameGrid.indexOf(val, diagonal) != -1) return false;
		}
		if(row + column == GameGrid.GRID_DIM - 1) {
			Field[] diagonal = new Field[this.grid.length];
			for(int i = 0; i < diagonal.length; i++)
	    		diagonal[i] = this.grid[i][GameGrid.GRID_DIM - 1 - i];
			if(GameGrid.indexOf(val, diagonal) != -1) return false;
		}
		return true;
	}
	
	@Override
	protected boolean isValid(int row, int column, int val) {
    	return this.checkRow(row, val) &&
    			this.checkColumn(column, val) &&
    			this.checkSubGrid(row, column, val) &&
    			this.checkDiagonal(row, column, val);
    }

}
