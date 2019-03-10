package sudoku.utils;

import java.util.HashMap;

import sudoku.game.GameGrid;
import sudoku.game.Ranker;
import sudoku.game.Sudoku;

public class RankerRun {
	public RankerRun(String[] args, final int SUDOKU_TYPE) {
		HashMap<String, GameGrid> filesToGames;
		try {
			filesToGames = IOUtils.loadFromFolder(Sudoku.getGamesFolderString(), SUDOKU_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid folder specified. Terminating..");
			return;
		}
		System.out.println("Calculating ranks for all eligible sudokus in "
				+ "the games folder..");
		for(String file : filesToGames.keySet()) {
			final float RANK = Ranker.rankSudoku(filesToGames.get(file));
			final int SUBSTRING_INDEX = file.lastIndexOf(Sudoku.getSep());
			final String SUDOKU_NAME = file.substring(SUBSTRING_INDEX);
			System.out.printf("Sudoku: %s,\trank: %f\n", SUDOKU_NAME, RANK);
		}
	}
}
