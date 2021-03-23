import java.awt.*;

/**
 * Class to create a square component in the Grid
 */
public class Square extends javax.swing.JComponent{
    java.awt.Color fill;
    int i;
    int j;

    /**
     * Creates a Square with a white fill.
     */
    public Square(){ fill = Color.WHITE; }

    public Square(int x, int y){
        i = x;
        j = y;
        fill = Color.WHITE;
    }

    /**
     * Set the fill color.
     * @param color The desired fill color
     */
    public void setFill(java.awt.Color color) {
        fill = color;
        repaint();
    }

    public Color getFill(){
        return fill;
    }

    public int getI(){
        return i;
    }

    public int getJ(){
        return j;
    }

    /**
     * Render the component
     * @param g The Graphics object to draw the component
     */
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        int size = 10;
        g.setColor(fill);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(0,0, size, size);
    }

}
