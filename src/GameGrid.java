import java.util.Objects;

public class GameGrid {
	// protected to allow for a quick copy before solving
	protected final Field[][] grid;
	// Constants for coordinate boundaries and Sudoku numbers
	public static final int GRID_DIM = 9;
	public static final int SUBGRID_DIM = (int) Math.sqrt(GRID_DIM);
	public static final int MAX_VAL = GRID_DIM;
	public static final int MIN_VAL = 1;
	public static final int EMPTY_VAL = 0;
	// formatting
	private static final String HORIZONTAL_CELL_PADDING = " ";
	private static final String HORIZONTAL_BLOCK_PADDING = "  ";
	private static final String VERTICAL_CELL_PADDING = "";
	private static final String VERTICAL_BLOCK_PADDING = "\n";
	
	public GameGrid(GameGrid game) {
		Objects.requireNonNull(game);
		// ensuring that altering game.grid does not alter this.grid i.e. deep-copy
		this.grid = deepCopyGrid(game.grid);
	}
	
	/**
	 * construct new GameGrid from an existing twodimensional array
	 * @param : twodimensional array of Field instances
	 */
	public GameGrid(int[][] grid) {
		Objects.requireNonNull(grid);
		// ensuring that altering grid does not alter this.grid
		this.grid = initialiseGrid(grid);
	}
	
	private static Field[][] deepCopyGrid(Field[][] grid) {
		Field[][] newGrid = new Field[GRID_DIM][GRID_DIM];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				newGrid[i][j] = new Field(
						grid[i][j].getValue(),
						grid[i][j].isInitial());
			}
		}
		return newGrid;
	}
	
	/**
	 * construct new GameGrid from a file
	 * @param path: absolute path where sudoku file is located
	 */
	public GameGrid(String path) {
		Objects.requireNonNull(path);
		final Field[][] FIELD_GRID = initialiseGrid(IOUtils.loadFromFile(path));
		this.grid = FIELD_GRID;
	}
	
	/**
	 * converts int[][] into Field[][], taking care of initial/clue values
	 * @param grid: the original int[][] grid
	 * @return the Field[][] representation
	 */
	private static Field[][] initialiseGrid(int[][] grid) {
		Field[][] newGrid = new Field[GRID_DIM][GRID_DIM];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				final int VAL = grid[i][j];
				newGrid[i][j] = VAL == EMPTY_VAL ? 
						new Field() : new Field(VAL, true);
			}
		}
		return newGrid;
	}
	
	/**
	 * for exception purposes. p should always we between 0 (inclusive) and GameGrid.GRID_DIM (exclusive)
	 * @param p: value we're checking
	 * @return: true if p is out of bounds
	 */
	private boolean invalidParameter(int p, boolean zeroIndexed) {
		final int NEW_P = zeroIndexed ? p : p - 1;
		return NEW_P < 0 || NEW_P >= GameGrid.GRID_DIM;
	}
	
	/**
	 * generic getter
	 * @param row: cell's row
	 * @param column: cell's column
	 * @return: the value in the grid
	 */
	public int getField(int row, int column) {
		if(invalidParameter(row, true) || invalidParameter(column, true))
			throw new IllegalArgumentException("row/column out of legal bounds");
		return this.grid[row][column].getValue();
	}
	
	/**
	 * set a certain cell to a new value, provided this is a valid update
	 * @param row: row of the cell to be set
	 * @param column: column of the cell to be set
	 * @param val: the desired value
	 * @return: true if the cell was updated
	 */
	public boolean setField(int row, int column, int val) {
		if(invalidParameter(row, true) || invalidParameter(column, true) || invalidParameter(val, false))
			throw new IllegalArgumentException("row/column/value out of legal bounds");
		/* proceed to update if val is valid in this position
		 * AND we're not updating a clue field
		 */
		if(this.isValid(row, column, val) && !isInitial(row, column)) {
			this.grid[row][column].setValue(val);
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
		if(invalidParameter(row, true) || invalidParameter(column, true))
			throw new IllegalArgumentException("row/column out of legal bounds");
		
		this.grid[row][column].setValue(GameGrid.EMPTY_VAL);
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
    
    public boolean isInitial(int row, int column) {
    	if(invalidParameter(row, true) || invalidParameter(column, true))
			throw new IllegalArgumentException("row/column out of legal bounds");
    	return grid[row][column].isInitial();
    }
	
	/**
     * iterates over Field[] haystack to locate needle
     * @param needle: search value
     * @param haystack: array of Field instances
     * @return index of needle if it exists, -1 otherwise
     */
    private static int indexOf(int needle, Field[] haystack) {
    	Objects.requireNonNull(haystack);
    	for(int i = 0; i < haystack.length; i++) {
    		if(haystack[i].getValue() == needle)
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
    	Field[] rowRepresentation = new Field[this.grid.length];
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
    	final int startRow = row - row % SUBGRID_DIM; // 0, 3, 6 etc
    	final int startCol = column - column % SUBGRID_DIM;
    	for(int i = 0; i < SUBGRID_DIM; i++) {
    		for(int j = 0; j < SUBGRID_DIM; j++) {
    			if(this.grid[startRow + i][startCol + j].getValue() == val)
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
