package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import org.junit.*;


import java.io.FileNotFoundException;

/**
 * a class which tests the FileLoader class
 */
public class FileLoaderTest {
    /**
     * an instance of FileLoader to test
     */
    private FileLoader fl;

    /**
     * run before every test to initialise the FileLoader
     */
    @Before
    public void setUp() {
        this.fl = new FileLoader();
    }

    /**
     * tests the first provided maze text file which is valid in all criteria.
     * This should not throw an exception.
     */
    @Test
    public void loadNoExceptionsTest() {
        char[][] maze;
        try {
            maze = this.fl.load("src/maze_text_files/maze001.txt");
            // this is a valid maze hence it should not throw exceptions
        } catch (MazeMalformedException | MazeSizeMissmatchException | FileNotFoundException e) {
            throw new AssertionError();
        }
    }

    /**
     * tests an invalid maze with dimensions 6,7. Dimensions must be odd
     * numbers so this should throw a MazeMalformedException.
     */
    @Test
    public void loadInvalidMaze1Test() {
        char[][] maze;
        try {
            maze = this.fl.load("src/maze_text_files/invalidMaze1.txt");
        } catch (MazeMalformedException e) {
            return;
        } catch (Exception e) {
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    /**
     * tests a maze which has an invalid character appear in the maze. this should throw an
     * IllegalArgumentException.
     */
    @Test
    public void loadInvalidMaze2Test() {
        char[][] maze;
        try {
            maze = this.fl.load("src/maze_text_files/invalidMaze2.txt");
        } catch (IllegalArgumentException e) {
            return;
        } catch (Exception e) {
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    /**
     * attempts to load a maze where there is a row which exceeds the provided
     * row size. This should throw a MazeSizeMissMatchException.
     */
    @Test
    public void loadInvalidMaze3Test() {
        char[][] maze;
        try {
            maze = this.fl.load("src/maze_text_files/invalidMaze3.txt");
        } catch (MazeSizeMissmatchException e) {
            return;
        } catch (Exception e) {
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    /**
     * attempts to load a maze file which does not exist. this should throw a
     * FileNotFoundException.
     */
    @Test
    public void loadMissingFileTest() {
        char[][] maze;
        try {
            maze = this.fl.load(
                    "src/maze_text_files/bigScaryNonExistentMaze.txt");
        } catch (FileNotFoundException e) {
            return;
        } catch (Exception e) {
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    /**
     * attempts to load a file with a filename parameter of null.
     * this should throw an IllegalArgumentException
     */
    @Test
    public void loadNullFileNameTest() {
        char[][] maze;
        try {
            maze = this.fl.load(null);
        } catch (IllegalArgumentException e) {
            return;
        } catch (Exception e) {
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    /**
     * tests that maze file 1 is correctly parsed into its array form.
     * does this by manually comparing them.
     */
    @Test
    public void loadCorrectMapText() {
        char[][] maze = new char[0][];
        try {
            maze = this.fl.load("src/maze_text_files/maze001.txt");
            // this is a valid maze hence it should not throw exceptions
        } catch (Exception e) {
        }
        char[][] testArray = {
                {'#','#','#','#','#','#','#'},
                {'#','S','#',' ',' ',' ','#'},
                {'#',' ','#','#','#',' ','#'},
                {'#',' ','#',' ',' ',' ','#'},
                {'#',' ','#',' ','#',' ','#'},
                {'#',' ',' ',' ','#','E','#'},
                {'#','#','#','#','#','#','#'},
        };
        Assert.assertEquals(maze, testArray);
    }
}