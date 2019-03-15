package sudoku.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import sudoku.game.GameGrid;

public class SudokuFieldAction implements ActionListener {

	private final GameGrid game;
	private final int row;
	private final int col;
	
	public SudokuFieldAction(final GameGrid game, final int row, final int col) {
		this.game = game;
		this.row = row;
		this.col = col;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if(!(source instanceof JButton)) return;
		System.out.println("button pressed");
		JButton button = (JButton) source;
		int val = getNewValue();
		if(val == -1) {
			System.out.println("button input cancelled");
		} else if(val == 0) {
			// reset this field and button
			game.clearField(row, col);
			button.setText("");
		} else {
			// update to desired value
			while (!game.setField(row, col, val)) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + Integer.toString(val));
				val = getNewValue();
				if(val == -1) {
					System.out.println("button input cancelled");
					return;
				}
			}
			button.setText(Integer.toString(val));
		}
	}
	
	private int getNewValue() {
		int val;
		do {
			String input = JOptionPane.showInputDialog("desired value for this cell:");
			if(input == null) return -1;
			try {
				val = Integer.parseInt(input);
				if(val < 0 || val > 9) val = -1;
			} catch (NumberFormatException e) {
				val = -1;
			}
		} while(val == -1);
		return val;
	}

}
