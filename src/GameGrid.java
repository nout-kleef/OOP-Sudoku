
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
	
	// constructor
	public GameGrid(int[][] grid) {
		java.util.Objects.requireNonNull(grid);
		this.grid = grid;
	}
	
	public GameGrid(String path) {
		java.util.Objects.requireNonNull(path);
		this.grid = IOUtils.loadFromFile(path);
	}
	
	private boolean invalidParameter(int p) {
		return p < 0 || p >= GameGrid.GRID_DIM;
	}
	
	public int getField(int row, int column) {
		if(invalidParameter(row) || invalidParameter(column))
			throw new IllegalArgumentException();
		return this.grid[row][column];
	}
	
	public boolean setField(int row, int column, int val) {
		if(invalidParameter(row) || invalidParameter(column) || invalidParameter(val))
			throw new IllegalArgumentException();
		
		if(this.isValid(row, column, val)) {
			this.grid[row][column] = val;
			return true;
		}
		return false;
	}
	
	public void clearField(int row, int column) {
		if(invalidParameter(row) || invalidParameter(column))
			throw new IllegalArgumentException();
		
		this.grid[row][column] = GameGrid.EMPTY_VAL;
	}
	
	public void print() {
		System.out.print(this.toString());
	}
	
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
    		representation += this.row(i);
    	}
    	return representation;
    } 
    
    private String row(int i) {
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
    	java.util.Objects.requireNonNull(haystack);
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
