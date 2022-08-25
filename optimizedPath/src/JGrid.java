/**
 * this Class will contain a visual represntaion of the grid. 
 * the grid will be dynamically alltered, whenever the size slider 
 * is changes immediatly will the grid. Likewise with the rolles each 
 * of the buttons serve. Each with a distict color to represent 
 * their roles.
 */

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.event.*;

public class JGrid extends javax.swing.JPanel implements ActionListener {
    ArrayList<JButton> buttonArray = new ArrayList<>();
    private ArrayList<Dimension> holes = new ArrayList<>();
    private Cell dest;
    private JButton destButton;
    private int X;
    private Display root;

    // find int xCord = (int)(((i-1)/Math.sqrt(X))+1);
    // find int yCord = (int)(((i-1)%Math.sqrt(X))+1);

    /**
     * constructor 
     * @param root
     * @param size
     */
    public JGrid(Display root, int size) {
        super();
        //variables 
        X = size * size;
        this.root = root;

        // new container 
        JPanel buttonPanel = new JPanel();
        //formatting 
        this.setSize((int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH),
                (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH));

        //formatting         
        buttonPanel.setSize((int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH - 30),
                (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH - 30));

        // sets gridlayout to the appropiate rows and column
        buttonPanel.setLayout(new GridLayout((int) Math.sqrt(X), (int) Math.sqrt(X)));
        this.setLayout(null);

        //border 
        Border blackBord = BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.cyan),
                "map");
        this.setBorder(blackBord);

        //origin button (1,1)
        JButton orig = new JButton();
        //listener 
        orig.addActionListener(this);
        //allows color
        orig.setOpaque(true);
        orig.setBackground(Color.green);
        // adds to array and panel
        buttonPanel.add(orig);
        buttonArray.add(orig);

        // loops for the rest of the buttons 
        for (int i = 1; i < X; i++) {
            JButton curr = new JButton();
            curr.addActionListener(this);
            curr.setOpaque(true);
            buttonPanel.add(curr);
            buttonArray.add(curr);
        }
        //formatting 
        this.add(buttonPanel);
        buttonPanel.setBounds(20, 20, (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH - 40),
                (int) (Display.Y - Display.HEAD_HEIGHT - Display.BORDER_WIDTH - 40));
    }

    /**
     * method that should not be manually evoked, 
     * this method will be evoked by the corresponding 
     * caller
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton caller = (JButton) e.getSource();
        String cord = "(" + (int) (((buttonArray.indexOf(caller)) / Math.sqrt(X)) + 1) + ","
                + (int) (((buttonArray.indexOf(caller)) % Math.sqrt(X)) + 1) + ")";

        if (root.getInputs().getDestBool()) {
            root.getInputs().pushLog("New dest:" + cord);
            root.getInputs().setDestBool(false);
            caller.setBackground(Color.RED);
            if (dest != null) {
                destButton.setBackground(null);
            }
            dest = new Cell((int) (((buttonArray.indexOf(caller)) / Math.sqrt(X)) + 1),
                    (int) (((buttonArray.indexOf(caller)) % Math.sqrt(X)) + 1));
            destButton = caller;
        } else if (root.getInputs().toggleBool()) {
            root.getInputs().pushLog("New hole:" + cord);
            caller.setBackground(Color.BLACK);
            holes.add(new Dimension((int) (((buttonArray.indexOf(caller)) / Math.sqrt(X)) + 1),
                    (int) (((buttonArray.indexOf(caller)) % Math.sqrt(X)) + 1)));

        } else
            root.getInputs().pushLog("GridButton " + cord + " was pressed");

    }

    /**
     * returns the Destination cell
     */
    public Cell getDest() {
        return dest;
    }

    /**
     * returns the List of holes 
     * @return
     */
    public ArrayList<Dimension> getHoles() {
        
        return holes;
    }

    
}
