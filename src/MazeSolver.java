/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam and Kate Little
 * @version 03/10/2023
 * edited 3/31/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }


    // Starting from the end cell, backtracks through
    // The parents to determine the solution
    // Returns an arraylist of MazeCells to visit in order
    public ArrayList<MazeCell> getSolution() {
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        MazeCell end = maze.getEndCell();
        solution.add(end);
        MazeCell currentParent = end.getParent();
        // While the starting cell hasn't been reached
        // Backtracks through all the parents
        while (!currentParent.equals(maze.getStartCell())){
            solution.add(0, currentParent);
            currentParent = currentParent.getParent();
        }
        solution.add(0, maze.getStartCell());
        return solution;
    }

    // Performs a Depth-First Search to solve the Maze
    // Returns an ArrayList of MazeCells in order from the start to end cell
    public ArrayList<MazeCell> solveMazeDFS() {
        Stack<MazeCell> toBeVisited = new Stack<MazeCell>();
        MazeCell current = maze.getStartCell();
        recursiveNeighborCheck(current, toBeVisited);
        return getSolution();
    }

    // For an individual cell, add all valid neighbors to stack and recurse until
    // It has reached the end of maze
    // Used for DFS
    public void recursiveNeighborCheck(MazeCell current, Stack<MazeCell> toBeVisited){
        // Sets isExplored to true for the cell currently being checked
        current.setExplored(true);

        // Base case
        // If code has reached the end, stop recursion
        if (current.equals(maze.getEndCell())){
            return;
        }

        // NORTH
        addNeighbor(current, -1, 0, toBeVisited);

        // EAST
        addNeighbor(current, 0, 1, toBeVisited);

        // SOUTH
        addNeighbor(current, 1, 0, toBeVisited);

        // WEST
        addNeighbor(current, 0, -1, toBeVisited);

        // Recursive case
        recursiveNeighborCheck(toBeVisited.pop(), toBeVisited);
    }

    // For DFS
    // Adds the neighbor to notVisited stack if it is a valid cell
    // Also sets each neighbor's parent + sets them to already explored
    public void addNeighbor(MazeCell current, int rowChange, int colChange, Stack<MazeCell> toBeVisited){
        if (maze.isValidCell(current.getRow() + rowChange, current.getCol() + colChange)){
            toBeVisited.add(maze.getCell(current.getRow() + rowChange, current.getCol() +colChange));
            toBeVisited.peek().setParent(current);
            toBeVisited.peek().setExplored(true);
        }
    }

    // Performs a Breadth-First Search to solve the Maze
    // Returns an ArrayList of MazeCells in order from the start to end cell
    public ArrayList<MazeCell> solveMazeBFS() {
        Queue<MazeCell> toBeVisited = new LinkedList<MazeCell>();
        MazeCell current = maze.getStartCell();
        current.setExplored(true);
        recursiveNeighborCheck(current, toBeVisited);

        return getSolution();
    }

    // For an individual cell, add all valid neighbors to queue and recurse until
    // It has reached the end of maze
    // Used for BFS
    public void recursiveNeighborCheck(MazeCell current, Queue<MazeCell> toBeVisited){
        // Base case
        // If code has reached the end of maze, stop recursion
        if (current.equals(maze.getEndCell())){
            return;
        }

        // NORTH
        addNeighbor(current, -1, 0, toBeVisited);

        // EAST
        addNeighbor(current, 0, 1, toBeVisited);

        // SOUTH
        addNeighbor(current, 1, 0, toBeVisited);

        // WEST
        addNeighbor(current, 0, -1, toBeVisited);

        // Recursive case with the front element of the toBeVisited queue
        recursiveNeighborCheck(toBeVisited.remove(), toBeVisited);
    }

    // For BFS
    // Adds the neighbor to notVisited stack if it is a valid cell
    // Also sets each neighbor's parent + sets them to already explored
    public void addNeighbor(MazeCell current, int rowChange, int colChange, Queue<MazeCell> toBeVisited){
        if (maze.isValidCell(current.getRow() + rowChange, current.getCol() + colChange)){
            MazeCell temp = maze.getCell(current.getRow() + rowChange, current.getCol() +colChange);
            toBeVisited.add(temp);
            temp.setParent(current);
            temp.setExplored(true);
        }
    }

    public static void main(String[] args) {
        // Creates the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Creates the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solves the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solves the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
