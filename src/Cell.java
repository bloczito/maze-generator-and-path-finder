public class Cell {

    private boolean visited;
    private boolean topWall;
    private boolean leftWall;
    private boolean isGoalPoint;
    private int rowNo, columnNo;

    public Cell(int row, int column) {

        rowNo = row; columnNo = column;
        visited = false;
        topWall = true;
        leftWall = true;
        isGoalPoint = false;

    }

    public boolean isVisited() { return visited; }

    public boolean isTopWall() {return topWall;}

    public boolean isLeftWall() {return leftWall;}

    public boolean isGoal() {return isGoalPoint;}

    public int row() {return rowNo;}

    public int column() {return columnNo;}

    public void markAsVisited() { visited = true; }

    public void markAsUnvisited() {
        visited = false;
    }

    public void toggleTopWall() {
        topWall = false;
    }

    public void toggleLeftWall() { leftWall = false; }

    public void setAsGoalPoint(){
        isGoalPoint = true;
    }

}
