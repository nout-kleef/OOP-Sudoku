package sudoku.game;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.io.File;

public class Sudoku07 {
	
	private static final String DEFAULT_PATH = 
			"games" + System.getProperty("file.separator") + "sudoku0.sd";

	public static void main(String[] args) {
		// get path to game file
		System.out.println("working dir: " + System.getProperty("user.dir"));
		String path;
		do {
			System.out.println("Requesting path from user..");
			path = askForPath();
		} while (path == null);
		// initiate
		System.out.println("Path specified correctly. Initiating..");
		// TODO: handle different types of games
		final int gameType = 1;
		JFrame frame = new SudokuFrame(path, gameType);
		frame.setVisible(true);
	}
	
	/**
	 * use a JOptionPane to request a path string
	 * IMPORTANT: user is expected to provide a <b>relative</b> path<br>
	 * for example: "games\\sudoku0.sd"
	 * @return null on failure, path String on success
	 */
	private static String askForPath() {
		String path = JOptionPane.showInputDialog("Provide path to a sudoku file.");
		if(path == null) {
			System.out.println("null path");
			if(SudokuFrame.DEBUG) {
				System.out.println("Proceeding using default path instead.");
				path = DEFAULT_PATH;
				if(checkPath(path) != null) return path;
			}
		} else {
			if(checkPath(path) != null) return path;
		}
		return null;
	}

	private static String checkPath(final String path) {
		final File game = new File(path);
		System.out.println("path entered: " + path);
		if(!game.exists()) 
			System.out.println("User provided path to non-existant location.");
		else if(game.isDirectory()) 
			System.out.println("User provided path to a directory.");
		else return path;
		return null;
	}
	
}
