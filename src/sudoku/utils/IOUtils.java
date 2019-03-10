package sudoku.utils;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;

import sudoku.game.GameGrid;

import java.io.UncheckedIOException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class IOUtils {
	
	private static final boolean DEBUG = true;
	
	public static HashMap<String, GameGrid> loadFromFolder(String dir) {
		File path = new File(dir);
		HashMap<String, GameGrid> filesToGames = new HashMap<String, GameGrid>();
		final String[] FILES = path.list();
		try {
			Objects.requireNonNull(FILES);
		} catch(NullPointerException e) {
			// path.list() returns null if path does not point to a directory
			if(DEBUG)
				System.out.println("No directory found.");
			return filesToGames;
		}
		for(String file: FILES) {
			if(!file.endsWith(".sd") || file.contains("unsolvable"))
				continue;
			final String FILE_PATH = dir + file;
			// add the mapping
			filesToGames.put(FILE_PATH, new GameGrid(FILE_PATH));
		}
		return filesToGames;
	}

    /**
     * This function loads a Sudoku game grid from a file.
     *
     * @param gridFileName the path to a Sudoku grid data file
     * @return a two-dimensional integer array holding the data from the specified file
     *
     */
    public static int[][] loadFromFile(String gridFileName) {
        Objects.requireNonNull(gridFileName);

        Path fileName = Paths.get(gridFileName);

        if (!Files.exists(fileName))
            throw new IllegalArgumentException("Given file does not exist: " + fileName);

        int[][] grid = new int[GameGrid.GRID_DIM][GameGrid.GRID_DIM];
        
        try {     
        	Scanner in = new Scanner(fileName);         
	
	        for(int row = 0; row < GameGrid.GRID_DIM; row++) {
	            for(int column = 0; column < GameGrid.GRID_DIM; column++) {
	                if(!in.hasNextInt())
	                    throw new RuntimeException("Given Sudoku file has invalid format: " + fileName);
	
	                int value = in.nextInt();
	                if (value < 0 || value > 9)
	                    throw new RuntimeException("Given Sudoku file has invalid "
	                               + "entry at: " + column + "x" + row);
	               
	                grid[row][column] = value;
	            }
	        }
	        
	        in.close();
        
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return grid;
    }

}
