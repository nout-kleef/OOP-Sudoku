import org.junit.Test;
import org.junit.Before;

public class GameGridBasicTest {
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
        GameGrid game = new GameGrid(grid);
    }

    @Test
    public void getFieldTest() {
       GameGrid game = new GameGrid(grid);
       int result = game.getField(0,0);
    }

    @Test
    public void setFieldTest() {
       GameGrid game = new GameGrid(grid);
       game.setField(0,0,1);
    }

    @Test
    public void clearFieldTest() {
       GameGrid game = new GameGrid(grid);
       game.clearField(0,0);
    }

    @Test
    public void toStringTest() {
        GameGrid game = new GameGrid(grid);
        String result = game.toString();
    }
}
