import java.util.*;

public class Maze {

    private int rows, columns;
    private List<List<Cell>> maze;
    private Cell startCell;
    private Cell goalCell;


    // Konsstruktor gdy losowo wybieramy poczatek i konec
    public Maze(int rows, int columns) {
        this.rows = rows; this.columns = columns;
        maze = new ArrayList<>(rows);

        for (int i = 0; i < rows; i++) {
            maze.add(new ArrayList<>(columns));
            for(int k = 0; k < columns; ++k)
                maze.get(i).add(new Cell(i, k));
        }
        generateStartAndGoal();
    }

    // Konstruktor gdy sami wybieramy poczatek i koniec
    public Maze(int rows, int columns, int startRow, int startColumn, int goalRow, int goalColumn) {
        this.rows = rows; this.columns = columns;
        maze = new ArrayList<>(rows);

        for (int i = 0; i < rows; i++) {
            maze.add(new ArrayList<>(columns));
            for(int k = 0; k < columns; ++k)
                maze.get(i).add(new Cell(i, k));
        }
        generateStartAndGoal(startRow, startColumn, goalRow, goalColumn);
    }

    /**
     *  Generuje labirynt. Uzuwa algorytmu DFS do odwiedzenia kazdej komorki.
     *  Usuwa sciany jezeli sciezka istnieje lub je zostawia
     */
    public void generateMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell start = maze.get(0).get(0);

        start.markAsVisited(); // Oznaczam poczatkowa komorke jako odwiedzona, aby nie przechodzic po niej 2-gi raz
        stack.push(start);     // Dodawanie przebytej sciezki do stosu

        while (!stack.empty()) {
            Cell current = stack.peek(); // Pobieramy ostatnio dodana sciezke
            Cell nextCell = getAdjacentUnvisited(current);

            if(nextCell != null) {
                nextCell.markAsVisited();
                removeWall(current, nextCell);
                stack.push(nextCell);
            }
            else
                stack.pop();
        }
    }

    // Zwraca losowo wybrana komorke sasiadujaca, ktora nie zostala odwiedzona
    private Cell getAdjacentUnvisited(Cell current) {
        Direction[] directions = Direction.values();
        Collections.shuffle(Arrays.asList(directions));
        for (Direction d : directions){
            if(isInBounds(d, current.row(), current.column())){
                Cell next = maze.get(d.row() + current.row()).get(d.column() + current.column());
                Boolean isVisited = next.isVisited();
                if (!isVisited)
                    return next;
            }
        }
        return null;
    }

    // Sprawdza czy komorka w wybranym kieruneku zmmiesci sie w granicach labiryntu
    private boolean isInBounds(Direction direction, int currentRow, int currentColumn) {

        int nextRow = direction.row() + currentRow;
        int nextColumn = direction.column() + currentColumn;
        return (nextRow > -1 && nextColumn > -1 && nextRow < rows && nextColumn < columns);
    }

    // Usuwa sciane w wybranym kierunku
    private void removeWall(Cell current, Cell next) {

        if (current.row() - next.row() == 1)
            current.toggleTopWall();

         else if (current.row() - next.row() == -1)
            next.toggleTopWall();

         else if (current.column() - next.column() == 1)
            current.toggleLeftWall();

         else
            next.toggleLeftWall();

    }

    // Ustawia punkt poczatkowy i koncowy
    private void generateStartAndGoal(int startRow, int startColumn, int goalRow, int goalColumn) {
        startCell = maze.get(startRow).get(startColumn);
        goalCell = maze.get(goalRow).get(goalColumn);
        goalCell.setAsGoalPoint();
    }

    // Losowo wybiera punkt poczatkowy i koncowy
    private void generateStartAndGoal() {
        Random random = new Random();
        int startRow = random.nextInt(rows);
        int startCol = random.nextInt(columns);
        int goalRow = random.nextInt(rows);
        int goalCol = random.nextInt(columns);

        boolean different = false;

        while (!different) {
            if ((goalRow != startRow) || (goalCol != startCol)) {
                different = true;
            } else {
                goalRow = random.nextInt(rows);
                goalCol = random.nextInt(columns);
            }
        }
        generateStartAndGoal(startRow, startCol, goalRow, goalCol);
    }

    public List<List<Cell>> getMaze() { return maze; }
    public Cell getStartCell() { return startCell; }
    public Cell getGoalCell() { return goalCell;}
}




