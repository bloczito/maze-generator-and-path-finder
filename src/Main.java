import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {



        int rows = 36, columns = 64;
        Maze maze = new Maze(rows, columns);
//        Maze maze = new Maze(rows, columns, 0,0,rows-1,columns-1);
        maze.generateMaze();

        List<List<Cell>> m = maze.getMaze();

        PathFinder pathFinder = new PathFinder(m, maze.getStartCell());
        pathFinder.findPath();
        ArrayList<Cell> path = pathFinder.getPath();

        Visualization v = new Visualization(m, path);


    }
}
