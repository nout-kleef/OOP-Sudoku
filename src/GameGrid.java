import java.util.Objects;

public class GameGrid {
	private final int[][] grid;
	// Constants for coordinate boundaries and Sudoku numbers
	public static final int GRID_DIM = 9;
	public static final int SUBGRID_DIM = (int) Math.sqrt(GRID_DIM);
	public static final int MAX_VAL = GRID_DIM;
	public static final int MIN_VAL = 1;
	public static final int EMPTY_VAL = 0;
	// formatting
	private static final String HORIZONTAL_CELL_PADDING = " ";
	private static final String HORIZONTAL_BLOCK_PADDING = "  "; // added on top of a horizontalCellPadding
	private static final String VERTICAL_CELL_PADDING = "";
	private static final String VERTICAL_BLOCK_PADDING = "\n";
	
	/**
	 * construct new GameGrid from an existing twodimensional array
	 * @param grid: twodimensional array of integers
	 */
	public GameGrid(int[][] grid) {
		Objects.requireNonNull(grid);
		this.grid = grid;
	}
	
	/**
	 * construct new GameGrid from a file
	 * @param path: absolute path where sudoku file is located
	 */
	public GameGrid(String path) {
		Objects.requireNonNull(path);
		this.grid = IOUtils.loadFromFile(path);
	}
	
	/**
	 * for exception purposes. p should always we between 0 (inclusive) and GameGrid.GRID_DIM (exclusive)
	 * @param p: value we're checking
	 * @return: true if p is out of bounds
	 */
	private boolean invalidParameter(int p) {
		return p < 0 || p >= GameGrid.GRID_DIM;
	}
	
	/**
	 * generic getter
	 * @param row: cell's row
	 * @param column: cell's column
	 * @return: the value in the grid
	 */
	public int getField(int row, int column) {
		if(invalidParameter(row) || invalidParameter(column))
			throw new IllegalArgumentException("row/column out of legal bounds");
		return this.grid[row][column];
	}
	
	/**
	 * set a certain cell to a new value, provided this is a valid update
	 * @param row: row of the cell to be set
	 * @param column: column of the cell to be set
	 * @param val: the desired value
	 * @return: true if the cell was updated
	 */
	public boolean setField(int row, int column, int val) {
		if(invalidParameter(row) || invalidParameter(column) || invalidParameter(val))
			throw new IllegalArgumentException("row/column/value out of legal bounds");
		
		if(this.isValid(row, column, val)) {
			this.grid[row][column] = val;
			return true;
		}
		return false;
	}
	
	/**
	 * reset a certain cell
	 * @param row: row of the cell to be reset
	 * @param column: column of the cell to be reset
	 */
	public void clearField(int row, int column) {
		if(invalidParameter(row) || invalidParameter(column))
			throw new IllegalArgumentException("row/column out of legal bounds");
		
		this.grid[row][column] = GameGrid.EMPTY_VAL;
	}
	
	/**
	 * @return: string representation of the GameGrid instance
	 */
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
    
	/**
	 * convert the ith row into a string
	 * @param i: row's index
	 * @return: string representation of the specified row
	 */
    private String rowToString(int i) {
    	final int[] ROW = this.grid[i];
    	String representation = "";
    	for(int j = 0; j < ROW.length; j++) {
			int cell = ROW[j];
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
    			representation += GameGrid.HORIZONTAL_CELL_PADDING + cell;
    			// row formatting
    			if(j == ROW.length - 1) {
    				// last iteration
    				representation += "\n";
    			}
			}
		}
    	return representation;
    }
	
	/**
     * iterates over haystack to locate needle
     * @param needle: search value
     * @param haystack: array of values
     * @return index of needle if it exists, -1 otherwise
     */
    private static int indexOf(int needle, int[] haystack) {
    	Objects.requireNonNull(haystack);
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
    
    /**
     * check whether a value is valid in a cell
     * @param row: the row we're checking
     * @param column: the column we're checking
     * @param val: the desired value
     * @return: true if value is allowed
     */
    private boolean isValid(int row, int column, int val) {
    	return this.checkRow(row, val) &&
    			this.checkColumn(column, val) &&
    			this.checkSubGrid(row, column, val);
    }
}
