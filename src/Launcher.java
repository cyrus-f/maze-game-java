import controller.Controller;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileLoader;
import model.GameEnv;
import view.TextViewer;
import view.GuiViewer;
import view.GuiPanel;
import model.Solver;

import java.io.FileNotFoundException;

/**
 * the class which launches the maze game application and
 * starts the control flow. It is the only runnable class
 * as it contains a main method.
 */
public class Launcher {
    /**
     * the main method of maze game takes arguments to
     * determine whether to run in GUI or text mode
     * and which maze file to load
     * @param args 1 or 2 strings in an array:
     *             If 1 string is provided it is the file path
     *             which will play in text mode.
     *             If 2 strings are provided the first is GUI
     *             and the second is the file path to be played
     *             in GUI mode.
     */
    public static void main(String[] args) throws FileNotFoundException,
            MazeSizeMissmatchException, MazeMalformedException, IllegalArgumentException {
        String filename = "";
        boolean isGui = false;
        if (args.length > 2) {
            System.out.println("invalid arguments");
            return;
        }
        if (args[0].equals("GUI")) {
            //gui stuff
            isGui = true;
            filename = args[1];
        } else {
            filename = args[0];
        }
        System.out.println("Oh no! Sir Wobbleton has gotten stuck in a maze!");
        Controller controller = new Controller(filename, isGui);
    }
}