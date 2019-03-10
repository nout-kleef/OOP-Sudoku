package sudoku.utils;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;

import sudoku.game.GameGrid;
import sudoku.game.RGameGrid;
import sudoku.game.XGameGrid;

import java.io.UncheckedIOException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class IOUtils {
	
	public static HashMap<String, GameGrid> loadFromFolder(String dir, final int SUDOKU_TYPE) throws Exception {
		File path = new File(dir);
		HashMap<String, GameGrid> filesToGames = new HashMap<String, GameGrid>();
		
		if(!path.exists() || !path.isDirectory())
			throw new Exception("Invalid folder specified");
		
		final String[] FILES = path.list();
		for(String file : FILES) {
			if(!file.endsWith(".sd") || file.contains("unsolvable"))
				continue;
			final String FILE_PATH = dir + file;
			// add the mapping
			final GameGrid game = SUDOKU_TYPE == 2 ? new XGameGrid(FILE_PATH) : new RGameGrid(FILE_PATH);
			filesToGames.put(FILE_PATH, game);
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
