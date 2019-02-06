
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
		this.grid[row][column] = val;
	}
}
