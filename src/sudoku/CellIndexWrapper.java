package sudoku;
/**
 * exists to move around cells more easily
 * @author noutk
 *
 */
public class CellIndexWrapper {
	private int row;
	private int col;
	private final int SIZE;
	
	public CellIndexWrapper(int row, int col, int gridSize) {
		this.row = row;
		this.col = col;
		this.SIZE = gridSize;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public boolean move(boolean forward) {
		// convert to single index
		int i = row * SIZE + col;
		// move in desired direction
		i = forward ? i + 1 : i - 1;
		if(i < 0 || i >= SIZE * SIZE) {
			// out of bounds
			return false;
		} else {
			// convert back to coordinates and update
			col = i % SIZE;
			row = i / SIZE;
		}
		return true;
	}
}
