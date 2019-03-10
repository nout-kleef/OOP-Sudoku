package sudoku.tests;

import org.junit.Test;

import sudoku.game.XGameGrid;

import org.junit.Before;

public class XGameGridBasicTest {
    private int[][] grid;

    @Before
    public void setUp() {
        grid = new int[][]{
            {9,4,0,1,0,2,0,5,8},
            {6,0,0,0,5,0,0,0,4},
            {0,0,2,4,0,3,1,0,0},
            {0,2,0,0,0,0,0,6,0},
            {5,0,8,0,2,0,4,0,1},
            {0,6,0,0,0,0,0,8,0},
            {0,0,1,6,0,8,7,0,0},
            {7,0,0,0,4,0,0,0,3},
            {4,3,0,5,0,9,0,1,2}
        };
    }

    @Test
    public void gameGridCtorTest() {
        XGameGrid game = new XGameGrid(grid);
    }

    @Test
    public void gameGridCpyCtorTest() {
        XGameGrid game = new XGameGrid(grid);
        XGameGrid gameCpy = new XGameGrid(game);
    }

    @Test
    public void isInitialTest() {
       XGameGrid game = new XGameGrid(grid);
       boolean result = game.isInitial(0,0);
    }

    @Test
    public void getFieldTest() {
       XGameGrid game = new XGameGrid(grid);
       int result = game.getField(0,0);
    }

    @Test
    public void setFieldTest() {
       XGameGrid game = new XGameGrid(grid);
       game.setField(0,0,1);
    }

    @Test
    public void clearFieldTest() {
       XGameGrid game = new XGameGrid(grid);
       game.clearField(0,0);
    }

    @Test
    public void toStringTest() {
        XGameGrid game = new XGameGrid(grid);
        String result = game.toString();
    }
}
