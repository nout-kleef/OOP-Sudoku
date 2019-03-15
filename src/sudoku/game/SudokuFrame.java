package sudoku.game;

import javax.swing.JFrame;

public class SudokuFrame extends JFrame {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	protected static final boolean DEBUG = true;
	
	private final GameGrid game;
	
	protected SudokuFrame(final String filename, final int gameType) {
		setSize(WIDTH, HEIGHT);
		setTitle(filename);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		switch (gameType) {
		case 2:
			game = new XGameGrid(filename);
			break;
		default:
			game = new RGameGrid(filename);
		}
		
	}
}
