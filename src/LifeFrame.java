import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Main class creates the JFrame implementation of the Life game
 * @author Jacob Voyles
 * @version 3.0
 */
public class LifeFrame extends JFrame implements ActionListener{
    Random rn = new Random();
    Timer timer = new Timer(100, this);
    Grid grid;

    //Initialize global variables
    boolean isCustom = false;
    int gridSize;
    int seed;
    int genNum;
    int count = 1;
    int maxCount, elapsedTime;
    int[][] customBoard;
    volatile int[][] old_grid;
    volatile int[][] new_grid;

    JPanel gridPanel = new JPanel();
    JPanel frame = new JPanel();

    /**
     * Constructor sets up new frame
     */
    public LifeFrame(){
        super("Life Game");
    }

    /**
     * Method creates new frame and renders to screen
     */
    public void generateFrame() {
        //Create Container
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        //Left panel for options
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        //Create Instructions
        leftPanel.add(new JLabel("White boxes are empty cells \n "));
        leftPanel.add(new JLabel("Green boxes are newly birthed cells\n"));
        leftPanel.add(new JLabel("Black boxes are dead cells\n"));
        leftPanel.add(new JLabel("Run different simulations with the seed\n"));
        leftPanel.add(new JLabel("Enter number of Generations you want to run\n"));
        leftPanel.add(new JLabel("Or enter -1 for continuous run\n"));
        leftPanel.add(new JLabel("\n"));

        JTextField boardField = new JTextField(10);
        boardField.setText("19");

        //Create seed line
        JPanel seedPanel = new JPanel();
        seedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        seedPanel.add(new JLabel("Seed:"));
        JTextField seedField = new JTextField(10);
        seedField.setText("12345");
        seedPanel.add(seedField);
        JButton randomButton = new JButton("Random Seed");
        randomButton.addActionListener(e -> seedField.setText(Integer.toString(rn.nextInt(9999))));
        seedPanel.add(randomButton);
        JButton custom = new JButton("Custom Board");
        custom.addActionListener(e -> {
            timer.stop();
            if(custom.getText().equals("Custom Board")) {
                seedField.setEnabled(false);
                randomButton.setEnabled(false);
                custom.setText("Set board");
                gridSize = Integer.parseInt(boardField.getText());
                gridPanel.add(settableGrid(gridSize));
                frame.add(gridPanel);
                pack();
            }else{
                isCustom = true;
                custom.setEnabled(false);
            }


        });
        seedPanel.add(custom);
        leftPanel.add(seedPanel);

        //Line two to specify generation
        JPanel genPanel = new JPanel();
        genPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        genPanel.add(new JLabel("# of Generations"));
        JTextField genField = new JTextField(10);
        genField.setText("1");
        genPanel.add(genField);
        leftPanel.add(genPanel);

        //Line three for board size
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        boardPanel.add(new JLabel("Board Size"));

        boardPanel.add(boardField);
        leftPanel.add(boardPanel);

        //Last line for submit
        JButton submit = new JButton("Run Simulation");
        frame.add(gridPanel);
        JButton stop = new JButton("Stop");
        stop.setEnabled(false);
        stop.addActionListener(e -> {
            String text = stop.getText();
            if(text.equals("Stop")) {
                timer.stop();
                stop.setText("Continue");
                submit.setText("Reset");
            }else{
                stop.setText("Stop");
                timer.start();
            }
        });
        //Runs on submit
        submit.addActionListener(e -> {
            try {
                if(submit.getText().equals("Run Simulation")) {
                    submit.setText("Reset");
                    stop.setText("Stop");
                    stop.setEnabled(true);
                    timer.start();
                    count = 0;
                    elapsedTime = 0;
                    genNum = Integer.parseInt(genField.getText());
                    seed = Integer.parseInt(seedField.getText());
                    gridSize = Integer.parseInt(boardField.getText());
                    maxCount = genNum * 1000;
                    if (isCustom)
                        grid = new Grid(gridSize, customBoard);
                    else
                        grid = new Grid(gridSize, seed);
                    old_grid = grid.getBoardLayout();
                    gridPanel.removeAll();
                    gridPanel.add(new JLabel("Generation: " + count++));
                    gridPanel.add(grid.generateGrid(0, null, null));
                    frame.add(gridPanel);
                }else{
                    submit.setText("Run Simulation");
                    stop.setText("Stop");
                    custom.setText("Custom Board");
                    stop.setEnabled(false);
                    timer.stop();
                    count = 0;
                    elapsedTime = 0;
                    isCustom = false;
                    gridPanel.removeAll();
                    seedField.setEnabled(true);
                    randomButton.setEnabled(true);
                    custom.setEnabled(true);

                }
                pack();
            }
            catch (Exception ignored){

            }
        });
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        submitPanel.add(submit);

        submitPanel.add(stop);
        leftPanel.add(submitPanel);

        //Adds settings panel
        frame.add(leftPanel);

        //For rendering
        setContentPane(frame);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    /**
     * Main method creates new instance of life program and prints to screen
     * @param args - no args
     */
    public static void main(String[] args) {
        LifeFrame life = new LifeFrame();
        life.generateFrame();
    }

    /**
     * Once submit is hit, this code will run on Swing Timer every second
     * @param e Timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        elapsedTime += 100;
        if(maxCount > 0){ //Max count is the amount of gen * 1000
            if(elapsedTime >= maxCount || elapsedTime == 25000){
                Timer s = (Timer)e.getSource();
                s.stop(); //stops timer
            }
        }
        
        int increment = (elapsedTime) % 1000;
        if(increment == 0) {
            count++;
            grid.newGen();
            new_grid = grid.getBoardLayout();
        }
        gridPanel.removeAll();
        gridPanel.add(new JLabel("Generation: " + count));//Updates count after rendering
        gridPanel.add(grid.generateGrid(increment/100, old_grid, new_grid));

        frame.add(gridPanel);
        pack();
        repaint();
    }

    public JPanel settableGrid(int gridSize){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(gridSize,gridSize));
        panel.setPreferredSize(new Dimension(17 * gridSize, 17 * gridSize));
        customBoard = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                customBoard[i][j] = 0;
                Square square = new Square(i, j);
                square.setFill(Color.white);
                square.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        Square current_square = (Square) e.getSource();
                        int current_i = current_square.getI();
                        int current_j = current_square.getJ();
                        if(current_square.getFill().equals(Color.WHITE)){
                            customBoard[current_i][current_j] = 1;
                            current_square.setFill(Color.RED);
                        }else if (current_square.getFill().equals(Color.RED)) {
                            customBoard[current_i][current_j] = 2;
                            current_square.setFill(Color.BLACK);
                        }else if (current_square.getFill().equals(Color.BLACK)){
                            customBoard[current_i][current_j] = 0;
                            current_square.setFill(Color.WHITE);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                panel.add(square);
            }
        }

        return panel;
    }
}
