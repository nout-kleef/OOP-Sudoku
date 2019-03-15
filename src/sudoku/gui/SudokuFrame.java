package sudoku.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import sudoku.game.GameGrid;
import sudoku.game.RGameGrid;
import sudoku.game.XGameGrid;

public class SudokuFrame extends JFrame {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final int DIM = GameGrid.GRID_DIM;
	public static final boolean DEBUG = true;
	
	private final GameGrid game;
	
	public SudokuFrame(final String filename, final int gameType) {
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
		setLayout(new GridLayout(DIM, DIM));
		createButtonGrid();
	}
	
	private void createButtonGrid() {
		for(int i = 0; i < DIM; i++)
			for(int j = 0; j < DIM; j++) {
				JButton button = new JButton();
				add(button);
				if(game.getField(i, j) != 0) {
					button.setText(Integer.toString(game.getField(i, j)));
					button.setEnabled(false); // disable clue field buttons
				} else {
					button.addActionListener(new SudokuFieldAction(game, i, j));
				}
			}
	}
}
