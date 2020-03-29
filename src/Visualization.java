import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Visualization extends JFrame {

    private int COLUMNS, ROWS, startX, startY, goalX, goalY;
    private static int cellSize = 20;


    public Visualization(List<List<Cell>> maze, ArrayList<Cell> path) {
        this.COLUMNS = maze.get(0).size();
        this.ROWS = maze.size();
        startX = path.get(0).column() * cellSize;
        startY = path.get(0).row() * cellSize;
        goalX = path.get(path.size()-1).column()* cellSize;
        goalY = path.get(path.size()-1).row() * cellSize;


        JPanel pathPanel = new JPanel() {

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(6));
                setBackground(Color.WHITE);

                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        if (maze.get(i).get(j).isTopWall())
                            g2.drawLine(cellSize * j, cellSize * i, cellSize * (j + 1), cellSize * i);
                        if (j + 1 < COLUMNS) {
                            if (maze.get(i).get(j + 1).isLeftWall())
                                g2.drawLine(cellSize * (j + 1), cellSize * i, cellSize * (j + 1), cellSize * (i + 1));
                        }
                        if (maze.get(i).get(j).isLeftWall())
                            g2.drawLine(cellSize * j, cellSize * i, cellSize * j, cellSize * (i + 1));
                        if (i + 1 < ROWS) {
                            if (maze.get(i + 1).get(j).isTopWall())
                                g2.drawLine(cellSize * j, cellSize * (i + 1), cellSize * (j + 1), cellSize * (i + 1));
                        }
                    }
                }
                g2.drawLine(0, cellSize * ROWS, cellSize * COLUMNS, cellSize * ROWS);
                g2.drawLine(cellSize * COLUMNS, 0, cellSize * COLUMNS, cellSize * ROWS);


                Stroke stroke = new BasicStroke(12f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
                g2.setStroke(stroke);
                g2.setColor(Color.BLUE);

                int x = startX + cellSize / 2, y = startY + cellSize / 2;


                for (int i = 0; i < path.size() - 1; ++i) {
                    switch (getDirection(path.get(i), path.get(i + 1))) {
                        case NORTH:
                            g2.drawLine(x, y, x, y - cellSize);
                            y = y - cellSize;
                            break;
                        case EAST:
                            g2.drawLine(x, y, x + cellSize, y);
                            x = x + cellSize;
                            break;
                        case WEST:
                            g2.drawLine(x, y, x - cellSize, y);
                            x = x - cellSize;
                            break;
                        case SOUTH:
                            g2.drawLine(x, y, x, y + cellSize);
                            y = y + cellSize;
                            break;
                    }

                }

                g2.setColor(Color.GREEN);
                g2.fillRect(startX + cellSize / 4 - 1, startY + cellSize / 4 - 1, 2 * cellSize / 3, 2 * cellSize / 3);
                g2.setColor(Color.RED);
                g2.fillRect(goalX + cellSize / 4 - 1, goalY + cellSize / 4 - 1, 2 * cellSize / 3, 2 * cellSize / 3);
            }
        };

        this.add(pathPanel);

        this.setSize(new Dimension(COLUMNS * cellSize + 18, ROWS * cellSize + 40));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Labirynt");
        this.setResizable(false);
        setLocationByPlatform(true);
        this.setVisible(true);

    }


    public Direction getDirection(Cell currentCell, Cell nextCell) {

        if(nextCell.row() - currentCell.row() == 1) return Direction.SOUTH;
        if(nextCell.row() - currentCell.row() == -1) return Direction.NORTH;
        if(nextCell.column() - currentCell.column() == 1) return Direction.EAST;
        else return Direction.WEST;


    }
}