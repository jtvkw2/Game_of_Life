import javax.swing.*;
import java.awt.*;

/**
 * Method to create JPanel Grid of Squares
 */
public class Grid extends JPanel {
    Life life;
    int[][] boardLayout;
    int gridSize;

    /**
     * Initializes a new grid
     * @param size - size of grid(size x size)
     * @param seed - sets up random seed
     */
    Grid(int size, int seed){
        gridSize = size;
        life = new Life(gridSize);
        boardLayout = life.setUp(gridSize, seed);
    }

    Grid(int size, int[][] custom){
        gridSize = size;
        life = new Life(gridSize);
        boardLayout = life.customSetUp(size, custom);
    }

    /**
     * Updates boardLayout based on old layout
     */
    public void newGen(){
        boardLayout = life.gen(gridSize);
    }

    public int[][] getBoardLayout(){
        return boardLayout;
    }

    /**
     * Method to render squares in a box layout to create a grid
     * @return updated JPanel
     */
    public JPanel generateGrid(int increment, int[][] old, int[][] curr){
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(gridSize, gridSize, 4, 4));
        grid.setPreferredSize(new Dimension(17 * gridSize, 17 * gridSize));
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                int currentNum;
                Square square = new Square();
                int oldNum = old[x][y];
                //System.out.println(curr == null);
                if(curr != null)
                    currentNum = curr[x][y];
                else
                    currentNum = old[x][y];
                square.setFill(whatColor(currentNum, oldNum, increment));
                grid.add(square);
            }
        }
        return grid;
    }

    public Color whatColor(int currentNum, int oldNum, int increment){
        switch (currentNum){
            case 1:
                if(currentNum == oldNum)
                    return Color.RED;
                else{
                    System.out.println("New Color");
                    return new Color(255 - increment*10, 0, 0);
                }
            case 2:
                if(currentNum == oldNum)
                    return Color.BLACK;
                else {
                    if (oldNum == 3){
                        System.out.println("New Color");
                        return new Color(255 - increment * 10, increment * 10, 0);
                    }
                    if(oldNum == 1){
                        return Color.BLACK;
                    }
                }
            case 3:
                if(currentNum == oldNum)
                    return Color.GREEN;
                else{
                    if(oldNum == 2){
                        System.out.println("New Color");
                        return new Color(0, 255 - increment*10, 0);
                    }else if(oldNum == 1){
                        System.out.println("New Color");
                        return new Color(255 - increment*10, 255 - increment*10, 0);
                    }
                }
            default:
                return Color.WHITE;
        }
    }

}
