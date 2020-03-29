import java.util.*;

public class PathFinder {

    private List<List<Cell>> maze;
    private Cell startCell;
    private ArrayList<Cell> path;
    private int ROWS, COLUMNS;


    PathFinder(List<List<Cell>> maze, Cell startCell) {
        this.maze = maze;
        this.startCell = startCell;

        path = new ArrayList<>();
        ROWS = maze.size();
        COLUMNS = maze.get(0).size();
    }

    // Algorytm szukania sciezki BFS. Dodaje punkt poczatkowy do kolejki i oznaczam jako odwiedzony.
    // Algorytm pobiera i usuwa go z kolejki. Nastepnie algorytm pobiera tablice dostepnych, nieodwiedzaonych
    // sasiadow. Dla kazdej z moozliwosci algorytm dodaje do kolejki sciezke zlozona z obecnie przebytej i
    // uzupelniona o sasiada. Nastepnie algorytm pobiera z kolejki nastepna galaz i powtarza instrukcje
    // az do osiagniecia punktu koncowego
    public void findPath() {
        markAllUnvisited();
        Queue<ArrayList<Cell>> queue = new LinkedList<>(); // Kolejka w ktorej przechowujemy odwiedzone sciezki
        Cell start = startCell;

        start.markAsVisited();
        path.add(start);
        queue.add(path);

        boolean gotGoal = false;

        while (!(gotGoal || queue.isEmpty())) {

            ArrayList<Cell> currentPath = queue.remove();
            Cell lastCell = currentPath.get(currentPath.size()-1);

            if(lastCell.isGoal()) {
                path = currentPath;
                gotGoal = true;
            } else {
                ArrayList<Cell> adj = getAllAdjacentUnvisited(lastCell);
                for (Cell cell : adj) {
                    ArrayList<Cell> previousPath = new ArrayList<>(currentPath);
                    cell.markAsVisited();
                    previousPath.add(cell);
                    queue.add(previousPath);
                }
            }
        }


    }

    // Odznacza wszystkie komorki jako nieodwiedzone
    private void markAllUnvisited() { maze.forEach(cells -> cells.forEach(Cell::markAsUnvisited)); }

    // Pobiera wszystkich dostepnych, nieodwiedzonych sasiadow
    private ArrayList<Cell> getAllAdjacentUnvisited(Cell current) {
        Direction[] directions = Direction.values();
        Collections.shuffle(Arrays.asList(directions));
        ArrayList<Cell> adj = new ArrayList<>();

        for(Direction d : directions) {
            if(isInBounds(d, current.row(), current.column())) {
                if(!isWallExist(d, current)) {
                    Cell next = maze.get(d.row() + current.row()).get(d.column() + current.column());
                    if(!next.isVisited())
                        adj.add(next);
                }
            }
        }
        return adj;
    }

    // Sprawdza czy komorka w wybraym kierunku miesci sie w granicach
    private boolean isInBounds(Direction direction, int currentRow, int currentColumn) {
        int nextRow = direction.row() + currentRow;
        int nextColumn = direction.column() + currentColumn;
        return (nextRow > -1 && nextColumn > -1 && nextRow < ROWS && nextColumn < COLUMNS);
    }

    // Sprawdza czy pomiedzy obecna a nastepnie wybrana komorka istnieje sciana
    public boolean isWallExist(Direction d, Cell current) {
        if (d == Direction.NORTH) {
            return maze.get(current.row()).get(current.column()).isTopWall();
        } else if (d == Direction.SOUTH) {
            return maze.get(current.row() + d.row()).get(current.column()).isTopWall();
        } else if (d.column() == -1) {
            return maze.get(current.row()).get(current.column()).isLeftWall();
        } else {
            return maze.get(current.row()).get(current.column() + d.column()).isLeftWall();
        }
    }

    public ArrayList<Cell> getPath() { return path; }

}