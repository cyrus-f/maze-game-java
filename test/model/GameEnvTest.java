package model;

import org.junit.*;

/**
 * a class which tests the GameEnv
 */
public class GameEnvTest {
    /**
     * a GameEnv to be tested
     */
    private GameEnv gameEnv;

    /**
     * run before every test to initialise the GameEnv
     * with a char array of maze001
     */
    @Before
    public void setUp() {
        char[][] testArray = {
                {'#','#','#','#','#','#','#'},
                {'#','S','#',' ',' ',' ','#'},
                {'#',' ','#','#','#',' ','#'},
                {'#',' ','#',' ',' ',' ','#'},
                {'#',' ','#',' ','#',' ','#'},
                {'#',' ',' ',' ','#','E','#'},
                {'#','#','#','#','#','#','#'},
        };
        this.gameEnv = new GameEnv(testArray);
    }

    /**
     * checks that the getter and setters for
     * the mazeRepr function correctly
     */
    @Test
    public void getSetMazeReprTest() {
        char[][] diffArray = {{'#'}, {'#'}};
        this.gameEnv.setMazeRepr(diffArray);
        Assert.assertEquals(diffArray,
                this.gameEnv.getMazeRepr());
    }

    /**
     * checks the correct number of rows is returned
     */
    @Test
    public void getNoRowsTest() {
        Assert.assertEquals(7,
                this.gameEnv.getNoRows());
    }

    /**
     * checks the correct number of columns is returned
     */
    @Test
    public void getNoColsTest() {
        Assert.assertEquals(7,
                this.gameEnv.getNoCols());
    }

    /**
     * checks the correct exit row is returned
     */
    @Test
    public void getExitRowTest() {
        Assert.assertEquals(5,
                this.gameEnv.getExitRow());
    }

    /**
     * checks the correct exit column is returned
     */
    @Test
    public void getExitColTest() {
        Assert.assertEquals(5,
                this.gameEnv.getExitCol());
    }

    /**
     * checks the correct initial state is returned
     * corresponding to the startpoint character
     * on the map
     */
    @Test
    public void getInitStateTest() {
        Assert.assertEquals(new GameState(1,1),
                this.gameEnv.getInitState());
    }

    /**
     * checks that the correct gamestate position is returned when
     * a downward action is taken in a valid state
     */
    @Test
    public void performActionDownTest() {
        Assert.assertEquals(new GameState(2,1),
                this.gameEnv.performAction(new GameState(1,1),
                        GameEnv.DOWN));
    }

    /**
     * checks that the correct gamestate position is returned when
     * an upward action is taken in a valid state
     */
    @Test
    public void performActionUpTest() {
        Assert.assertEquals(new GameState(4,3),
                this.gameEnv.performAction(
                        new GameState(5,3), GameEnv.UP));
    }

    /**
     * checks that the correct gamestate position is returned when
     * a left action is taken in a valid state
     */
    @Test
    public void performActionLeftTest() {
        Assert.assertEquals(new GameState(5,2),
                this.gameEnv.performAction(
                        new GameState(5,3), GameEnv.LEFT));
    }

    /**
     * checks that the correct gamestate position is returned when
     * a right action is taken in a valid state
     */
    @Test
    public void performActionRightTest() {
        Assert.assertEquals(new GameState(5,3),
                this.gameEnv.performAction(
                        new GameState(5,2), GameEnv.RIGHT));
    }

    /**
     * checks that the same state is returned when
     * trying to perform an action which will result in collision with
     * a wall
     */
    @Test
    public void performActionCollisionTest() {
        Assert.assertEquals(new GameState(1,1),
                this.gameEnv.performAction(new GameState(1,1), GameEnv.UP));
    }

    /**
     * checks that the same state is returned when the action taken
     * is not a valid character from the action list
     */
    @Test
    public void performActionInvalidTest() {
        Assert.assertEquals(new GameState(1,1),
                this.gameEnv.performAction(new GameState(1,1), 'g'));
    }

    /**
     * checks that gamestates with the player position overlapping with
     * a wall is not valid
     */
    @Test
    public void isValidWallTest() {
        Assert.assertFalse(this.gameEnv.isValid(
                new GameState(0,1)));
    }

    /**
     * checks that gamestates with the player position
     * on a path tile are valid
     */
    @Test
    public void isValidPathTest() {
        Assert.assertTrue(this.gameEnv.isValid(
                new GameState(1,1)));
    }

    /**
     * checks that an out bounds state is invalid
     */
    @Test
    public void isValidOutOfBoundsTest() {
        Assert.assertFalse(this.gameEnv.isValid(
                new GameState(10,10)));
    }

    /**
     * checks that it is invalid to take an action in
     * a state which will result in going out of bounds
     */
    @Test
    public void isValidOutOfBoundsActionTest() {
        Assert.assertFalse(this.gameEnv.isValid(
                new GameState(0,0), GameEnv.LEFT));
    }

    /**
     * checks that state action pairs which result in collision
     * with a wall are not valid
     */
    @Test
    public void isValidCollisionTest() {
        Assert.assertFalse(this.gameEnv.isValid(
                new GameState(1,1), GameEnv.UP));
    }

    /**
     * checks that state action pairs which do not result in
     * collision with a wall are not valid
     */
    @Test
    public void isValidNoCollisionTest() {
        Assert.assertTrue(this.gameEnv.isValid(
                new GameState(1,1), GameEnv.DOWN));
    }

    /**
     * checks that states which have player position
     * at the exit are solved
     */
    @Test
    public void isSolvedTrueTest() {
        Assert.assertTrue(this.gameEnv.isSolved(
                new GameState(5, 5)));
    }

    /**
     * checks that states with player position not at the
     * exit are not solved
     */
    @Test
    public void isSolvedFalseTest() {
        Assert.assertFalse(this.gameEnv.isSolved(new GameState(4, 5)));
    }
}
