package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * a class carrying functionality to process maze text files and
 * turn them into 2d char arrays for data manipulation.
 * It implements the specifications in FileInterface.
 */
public class FileLoader implements FileInterface {
    /**
     * a list containing all legal characters which can appear in the maze text files
     */
    private final List<Character> legalCharacters = new ArrayList<>(
            Arrays.asList('#', ' ', '.', 'S', 'E'));

    /**
     *Loads a maze from the specified filename and converts it into a 2D character array.
     * This method attempts to read a maze file with the following expectations:
     *      * - The first line should contain the maze's dimensions, separated by a space
     *      (e.g., "10 15").
     *      * - Subsequent lines should provide the maze data with specific characters
     *      representing the maze elements.
     * @param filename The path to the maze file to be loaded.
     * @return A 2D character array representing the loaded maze.
     * @throws MazeMalformedException      If the maze data is not correctly formatted.
     * @throws MazeSizeMissmatchException  If the maze dimensions do not match the provided size.
     * @throws IllegalArgumentException     For other validation errors.
     * @throws FileNotFoundException        If the maze file is not found.
     */
    public char[][] load(String filename) throws MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        if (filename == null) {
            System.out.println("//!// IllegalArgumentException: null filename not allowed");
            throw new IllegalArgumentException("null filename not allowed");
        }
        // check if file exists
        File tempFile = new File(filename);
        boolean exists = tempFile.exists();
        if (!exists) {
            System.out.println("//!// FileNotFoundException: file does not exist");
            throw new FileNotFoundException();
        }

        char[][] mazeOutput = null;
        String line;
        int rows = 0;
        int cols = 0;
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                // first iteration checks the number of rows and columns
                if (i == 0) {
                    String[] dims = line.split(" ");
                    rows = Integer.parseInt(dims[0]);
                    cols = Integer.parseInt(dims[1]);
                    // check for positive odd number of rows and columns
                    if (rows % 2 == 0 || cols % 2 == 0 || rows < 0 || cols < 0) {
                        throw new MazeMalformedException("rows/column numbers are invalid");
                    }
                    mazeOutput = new char[rows][cols];
                    i++;
                    continue;
                }
                // check that the dimensions provided match the dimensions of the maze data
                if (i > rows || line.length() != cols) {
                    throw new MazeSizeMissmatchException(
                            "the maze dimensions do not match the provided size.");
                }
                // all next iterations read rows and add them to the char array
                char[] elements = new char[line.length()];
                for (int j = 0; j < line.length(); j++) {
                    char currentCharacter = line.charAt(j);
                    // check for illegal characters
                    if (!this.legalCharacters.contains(currentCharacter)) {
                        System.out.println("//!// IllegalArgumentException invalid characters");
                        throw new IllegalArgumentException("illegal characters");
                    }
                    mazeOutput[i - 1][j] = currentCharacter;

                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("//!// FileNotFoundException");
            throw new FileNotFoundException();
        } catch (NumberFormatException e) {
            System.out.println("//!// IllegalArgumentException: numbers in first row invalid");
            throw new IllegalArgumentException("illegal characters");
        }
        return mazeOutput;
    }
}
