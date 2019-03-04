package sudoku;

public class Ranker {
	public static float rankSudoku(GameGrid game) {
		// according to the tutorial description
		final int SOLUTIONS_COUNT = Solver.findAllSolutions(game).size();
		final float CLUE_FIELDS_COUNT = game.getClueFields();
		// multiply by 9 and div by GRID_DIM to end up with *1 for normal size
		return SOLUTIONS_COUNT * (CLUE_FIELDS_COUNT / GameGrid.GRID_DIM) *
				(9 / GameGrid.GRID_DIM);
	}
}
