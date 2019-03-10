package sudoku.game;

public class Ranker {
	public static float rankSudoku(GameGrid game) {
		// according to the tutorial description
		final int SOLUTIONS_COUNT = Solver.findAllSolutions(game).size();
		final float FREE_FIELDS_COUNT = game.getFreeFields();
		// multiply by 9 and div by GRID_DIM to end up with *1 for normal size
		return (FREE_FIELDS_COUNT * 9 /
				(GameGrid.GRID_DIM * GameGrid.GRID_DIM * SOLUTIONS_COUNT));
	}
}
