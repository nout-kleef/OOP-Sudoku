
public class GameGrid {
	private final int[][] grid;
	// Constants for coordinate boundaries and Sudoku numbers
	public static final int GRID_DIM = 9;
	public static final int SUBGRID_DIM = (int) Math.sqrt(GRID_DIM);
	public static final int MAX_VAL = GRID_DIM;
	public static final int MIN_VAL = 1;
	public static final int EMPTY_VAL = 0;
	// constructor
	public GameGrid(int[][] grid) {
		this.grid = grid;
	}
	
	public int getField(int row, int column) {
		return this.grid[row][column];
	}
	
	public void setField(int row, int column, int val) {
		// TODO: validate val
		if(this.isValid(row, column, val))
		this.grid[row][column] = val;
	}
	
	/**
     * iterates over haystack to locate needle
     * @param needle: search value
     * @param haystack: array of values
     * @return index of needle if it exists, -1 otherwise
     */
    private static int indexOf(int needle, int[] haystack) {
    	for(int i = 0; i < haystack.length; i++) {
    		if(haystack[i] == needle)
    			return i;
    	}
    	return -1;
    }
    
    /**
     * @param row: row of cell
     * @param val: value of cell
     * @return whether the row is valid
     */
    private boolean checkRow(int row, int val) {
    	return GameGrid.indexOf(val, this.grid[row]) == -1;
    }
    
    /**
     * @param column: column of cell
     * @param val: value of cell
     * @return whether the column is valid
     */
    private boolean checkColumn(int column, int val) {
    	// convert into single array
    	int[] rowRepresentation = new int[this.grid.length];
    	for(int i = 0; i < rowRepresentation.length; i++)
    		rowRepresentation[i] = this.grid[i][column];
    	// check this array
    	return GameGrid.indexOf(val, rowRepresentation) == -1;
    }
    
    /**
     * checking only the subgrid to find any potential violation
     * @param column: column of cell
     * @param row: row of cell
     * @param val: value of cell
     * @param grid: entire "sudoku"
     * @return whether the subgrid is valid
     */
    private boolean checkSubGrid(int row, int column, int val) {
    	final int subGridSize = (int) Math.sqrt(this.grid.length); // 2, 3, 4 etc
    	final int startRow = row - row % subGridSize; // 0, 3, 6 etc
    	final int startCol = column - column % subGridSize;
    	for(int i = 0; i < subGridSize; i++) {
    		for(int j = 0; j < subGridSize; j++) {
    			if(this.grid[startRow + i][startCol + j] == val)
    				return false;
    		}
    	}
    	// no violation found
    	return true;
    }
    
    private boolean isValid(int row, int column, int val) {
    	return this.checkRow(row, val) &&
    			this.checkColumn(column, val) &&
    			this.checkSubGrid(row, column, val);
    }
}
