package sudoku.game;

import java.io.File;

public class SudokuGame {
	private final String PATH;
	public SudokuGame(String[] args) {
		/* assume that a "games" dir exists in the project dir.
		 * let the user pick any sudoku from this directory (non-recursive)
		 */
		final File[] OPTIONS = Sudoku.listFilesForFolder(Sudoku.GAMES_FOLDER);
		// request puzzle
		System.out.println("You've initiated this program in interactive "
				+ "mode.\nPlease pick the desired puzzle by entering "
				+ "the file's index:");
		for(int i = 0; i < OPTIONS.length; i++) {
			final String OPTION = OPTIONS[i].toString();
			final int REDUNDANT_ENDS_INDEX = OPTION.lastIndexOf(Sudoku.getSep());
			final String READABLE = OPTION.substring(REDUNDANT_ENDS_INDEX + 1);
			System.out.println("\t" + (i + 1) + ") \t" + READABLE);
		}
		final int PUZZLE_INDEX = 
				Sudoku.requestInt("the file's index", 1, OPTIONS.length) - 1;
		// set up using chosen file
		this.PATH = OPTIONS[PUZZLE_INDEX].toString();
	}
	
	public String getPath() {
		return PATH;
	}
}
