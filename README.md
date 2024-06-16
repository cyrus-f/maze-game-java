# Maze Game/Solver Development in Java
Description: Developed an interactive maze game using Java that supports input of any arbitrary maze encoded in .txt format through file handling.

# Features:
Terminal-Based Game: Users can navigate through the maze in a terminal-based interface. <br>
GUI Visualization: Provides a visual representation of a guided solution using a GUI developed with the Java Swing Library. <br>
Algorithm Implementation: Implemented the A* search algorithm for efficient maze solving. <br>
Technologies: Java, Java Swing Library, A* Search Algorithm, File Handling, Object-Oriented Design

# How to Use
Enter the src folder and locate Launcher.java <br>
Compile this using: `javac Launcher.java` <br>
To run a playable version of a maze file simply use the file location as an argument eg: `java Launcher maze_text_files/maze001.txt` <br>
To see the solution to the maze in a GUI input GUI as the first argument, then the filepath eg: `java Launcher GUI maze_text_files/maze001.txt`

# Creating Your Own Mazes
The program supports mazes in a .txt format: see [maze_text_files/maze001.txt](maze_text_files/maze001.txt) for an example. <br>
The first line must contain the number of rows and columns separated by a space. <br>
Subsequent lines are treated as rows in the maze encode by: <br>
'#' - walls <br>
' ' - empty space to walk through <br>
'S' - the starting point of the maze <br>
'E' - the end goal of the maze <br>
