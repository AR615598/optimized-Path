
/**
 * This Class is the main container For the entire GUI. 
 * this class deploys a JPanel that will contain the entire GUI 
 * this GUI is divided into two segments, the left segment will 
 * the grid and the right side will be a bunch of components 
 * used to take inputs and run the distance function.
 */

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Display extends javax.swing.JFrame {
    static final double Y = 700;
    static final double X = 950;
    static final int HEAD_HEIGHT = 50;
    static final int BORDER_WIDTH = 8;
    private JGrid datum;
    private InputPanel inputs;

    // constructor
    public Display() {
        // Creating Top-Level Container (JFrame)
        super("Optimized Path");
        // Make it so when the frame is closed the program exits
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Sets size
        this.setSize((int) X, (int) Y);
        // manual layout
        this.setLayout(new GridBagLayout());
        this.setResizable(false);
        GridBagConstraints GBC = new GridBagConstraints();

        // Initalizes the grid Panel
        datum = new JGrid(this, 5);

        // formats the grid
        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.gridwidth = 2;
        GBC.gridheight = 3;
        GBC.ipadx = (int) (Y - HEAD_HEIGHT - BORDER_WIDTH);
        GBC.ipady = (int) (Y - HEAD_HEIGHT - BORDER_WIDTH);
        GBC.anchor = GridBagConstraints.FIRST_LINE_START;
        GBC.weightx = 0;
        GBC.weighty = 0;

        // adds the grid to the main container
        this.add(datum, GBC);

        // input area
        // Initalizes the input Panel
        inputs = new InputPanel(this, "Inputs");

        // formats the input panel onto the main container
        GBC.gridx = 3;
        GBC.gridy = 0;
        GBC.gridwidth = 3;
        GBC.gridheight = 3;
        GBC.ipadx = (int) (X - Y);
        GBC.ipady = (int) (Y - HEAD_HEIGHT - BORDER_WIDTH);
        GBC.anchor = GridBagConstraints.FIRST_LINE_END;
        GBC.weightx = 0;
        GBC.weighty = 0;
        this.add(inputs, GBC);

        // sets datum to visable
        datum.setVisible(true);
        // sets the input panel to visable
        inputs.setVisible(true);
    }

    /**
     * getter for the Grid panel, used to communicate
     * between panels.
     * 
     * @return Grid Panel
     */
    public JGrid getGrid() {
        return datum;
    }

    /**
     * getter for the input panel, used to communicate
     * between panels.
     * 
     * @return input Panel
     */
    public InputPanel getInputs() {
        return inputs;
    }

    /**
     * sets a JGrid object to the main JGrid object
     * connected to the all the other panels
     * 
     * @param altered
     */
    public void newGrid(JGrid altered) {
        this.datum = altered;
    }

}
