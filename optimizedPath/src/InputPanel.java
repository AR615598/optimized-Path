
/**
 * This class will contain all the actual inputs like the buttons 
 * sliders and toggles, The pannel itelf is divided into three 
 * segments the topmost segment is used for getting data pertaining 
 * to the grid itself, the segment right below it will commands. 
 * And the final section will be a text box used to provide feedback.
 */
import javax.swing.border.Border;
import java.awt.GridBagConstraints;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.Color;
import java.time.LocalDateTime;
import java.awt.event.*;

public class InputPanel extends javax.swing.JPanel implements ActionListener, ChangeListener {
    // variables
    Display root;
    JTextArea logs;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm.ss");
    private boolean destBool = false;
    private MapInput.MapSlider mapDim;
    private MapInput.MapButton runButt;
    private MapInput.MapButton resetButt;
    private MapInput.MapButton endPt;
    private MapInput.MapToggle toggy;

    /**
     * Constructor for the input Panel. uses a Display parameter so all the
     * containers can
     * easily comunicate with each other. It also has a string for the title
     * 
     * @param root
     * @param title
     */
    public InputPanel(Display root, String title) {
        // JPanel constructor
        super();
        // invalid, without a main container this panel loses its functionality
        if (root == null) {
            this.remove(this);
            throw new IllegalArgumentException();
        }

        this.root = root;
        this.setLayout(null);

        // border
        Border blackBord = BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.cyan),
                title);
        this.setBorder(blackBord);

        // Bunch of components
        MapInput inputs = new MapInput("Paramerters", 3);
        MapInput commands = new MapInput("Commands", 2);
        JPanel textBox = new JPanel();

        mapDim = inputs.new MapSlider("Map Dimensions", 1, 10);
        runButt = inputs.new MapButton("RUN");
        resetButt = inputs.new MapButton("RESET");
        endPt = inputs.new MapButton("Select Destination");
        toggy = inputs.new MapToggle("Select Holes");
        logs = new JTextArea();

        // adding action and change listeners to the components.
        mapDim.addChangeListener(this);
        runButt.addActionListener(this);
        resetButt.addActionListener(this);
        endPt.addActionListener(this);
        toggy.addActionListener(this);

        // formating the text box
        logs.setLineWrap(true);
        logs.setEditable(false);
        LocalDateTime now = LocalDateTime.now();

        // introduction
        logs.append(dtf.format(now) + ": Application Loaded\n");

        // border
        Border bord = BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.cyan), "Log");
        this.setBorder(blackBord);
        textBox.setBorder(bord);
        textBox.setLayout(new GridLayout());

        // adding to the Panels
        commands.add(runButt);
        commands.add(resetButt);
        inputs.add(mapDim);
        inputs.add(endPt);
        inputs.add(toggy);
        textBox.add(logs);

        this.add(inputs);
        this.add(commands);
        this.add(textBox);

        // formatting onto the main Panel
        inputs.setBounds(20, 25, (int) (210), (int) (300));
        commands.setBounds(20, 345, 210, 150);
        textBox.setBounds(20, 520, 210, 100);

        this.setVisible(true);

    }

    /**
     * pushes the string into the text box and prints
     * it into the GUI. null is not an error case and will
     * clear the box.
     * 
     * @param str
     */
    public void pushLog(String str) {
        // gets time
        LocalDateTime now = LocalDateTime.now();
        // clears box
        logs.setText(null);
        // appends
        logs.append(dtf.format(now) + ": " + str);
    }

    /**
     * This function should not be manually evoked
     * but will be evoked whenever the corresponding
     * JComponent is used. This will have four cases
     * 
     * case 1) the run button was pressed
     * in this case the method will begin collecting data
     * create a new grid with the corresponding data
     * and run the distance method.
     * 
     * case 2) the reset button was pressed
     * in this case the entire grid will reset to its
     * default just like how it was when
     * orignally ran
     * 
     * case 3) the new destination button was
     * pressed.
     * in this Case the the next button on the grid
     * will become the new destination
     * 
     * case 4) the hole toggle has been swapped
     * in this case the test box ends with a
     * true every button you press on the grid will
     * become a hole until the toggle is swapped back
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // gets the componet that evoked the method
        Object evoker = e.getSource();
        // checks if it is case 1
        if (evoker.equals(runButt)) {
            // case if the dest has not been provided yet then exits
            if (root.getGrid().getDest() == null) {
                pushLog("Please provide a dest");
            } else {
                // begins creating Grid object and inputting data to run the distance method
                ArrayList<Dimension> holes = root.getGrid().getHoles();
                Grid funny = new Grid(root.getGrid().getDest(), mapDim.getValue());

                // inputing all the holes
                for (int i = 0; i < holes.size(); i++) {
                    // Adding all the holes to the Logical Grid
                    funny.setObsticle((int) (holes.get(i).getWidth() - 1),
                            (int) (holes.get(i).getHeight()) - 1);
                }
                pushLog(funny.toString());
                // Coloring the cells so the user can view the path
                // begin at the fiish and follow the prev pointer until
                // prev is null whuch is the origin.
                ArrayList<JButton> buttArr = root.getGrid().buttonArray;
                Cell curr = funny.currGrid[funny.dest.getX() - 1][funny.dest.getY() - 1];
                while (curr != null) {                  
                    // begin coloring from dest to orign
                    // given that the buttArr is an arraylist
                    // we need a function to covert the
                    // Cartesisan coordinates to a singular value
                    buttArr.get(((curr.getX()) * mapDim.getValue()) + (curr.getY())).setBackground(Color.MAGENTA);
                    curr = curr.getPrev();
                    
                }

            }
        }
        // checking for case 2
        if (evoker.equals(resetButt)) {
            pushLog("A fresh map");
            // remove old grid
            root.remove(root.getGrid());
            // creating and formatting new grid
            GridBagConstraints GBC = new GridBagConstraints();
            JGrid datum = new JGrid(root, mapDim.getValue());
            GBC.gridx = 0;
            GBC.gridy = 0;
            GBC.gridwidth = 2;
            GBC.gridheight = 3;
            GBC.ipadx = (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH);
            GBC.ipady = (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH);
            GBC.anchor = GridBagConstraints.FIRST_LINE_START;
            GBC.weightx = 0;
            GBC.weighty = 0;

            root.add(datum, GBC);
            // connecting
            root.newGrid(datum);
            root.revalidate();
        }
        // checks for the third case
        if (evoker.equals(endPt)) {
            pushLog("New destination can be selected, if created the old destination will be overwritten \n");
            // prepares grid
            destBool = true;
            // defaults the toggle to false
            // cant be the destination and a hole at the same time
            toggy.setSelected(false);
        }
        // checks for the fourth case
        if (evoker.equals(toggy)) {
            pushLog("Begin selectng holes in the map.\n" + toggy.isSelected());
        }

    }

    /**
     * Listener solely for the slider and changing the grid size
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        // gets the slider
        JSlider curr = (JSlider) e.getSource();

        // waits for the slider to stop since it compares 3 numbers to verify
        // what value it was changed to for verification.
        if (!curr.getValueIsAdjusting()) {
            // pushes string
            pushLog("Size changed to: " + (curr.getValue()));
            // removes old grid
            root.remove(root.getGrid());

            // creates and formats new grid
            GridBagConstraints GBC = new GridBagConstraints();
            JGrid datum = new JGrid(root, curr.getValue());
            GBC.gridx = 0;
            GBC.gridy = 0;
            GBC.gridwidth = 2;
            GBC.gridheight = 3;
            GBC.ipadx = (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH);
            GBC.ipady = (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH);
            GBC.anchor = GridBagConstraints.FIRST_LINE_START;
            GBC.weightx = 0;
            GBC.weighty = 0;

            // adds to main container
            root.add(datum, GBC);
            // connects to all other containers
            root.newGrid(datum);
            root.revalidate();
        }
    }

    /**
     * getter to find whether the the destination boolean is set to true
     * to allow the next grid press be the new destination.
     * 
     * @return Boolean
     */
    public boolean getDestBool() {
        return destBool;
    }

    /**
     * Setter for the destination boolean if needed to be interuped or
     * once a new destination has been selected
     * 
     * @param state
     */
    public void setDestBool(Boolean state) {
        destBool = state;
    }

    public boolean toggleBool(){
        return toggy.isSelected();
    }

}
