
/**
 * This Class is a custom Jpanel class used to contain other JComponents 
 * this class is used to differntiate between groups of inputs. 
 * the nested class are not heavily modified if not at all but they are helpful 
 * for organaizing and grouping.
 */

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.GridLayout;
import java.awt.Color;

public class MapInput extends javax.swing.JPanel {

    /**
     * constructor for a JPanel, will throw an exception if the numComponents are
     * zero
     * 
     * @param title
     * @param numOfComponents
     */
    public MapInput(String title, int numOfComponents) {
        // JPanel constructor
        super();
        // verifies that there will be at least one component using this panel
        if (numOfComponents > 0) {
            // layout is set up so they are in a single equally sized column,
            // numOfComponents high.
            this.setLayout(new GridLayout(numOfComponents, 1));
            // titled border
            Border blackBord = BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.cyan),
                    title);
            this.setBorder(blackBord);
            // sets visability
            this.setVisible(true);
        } else {
            // if not in use it is going to delete itself
            this.remove(this);
        }

    }

    // custom Jslider
    public class MapSlider extends javax.swing.JSlider {
        /**
         * constructor for a JSlider it takes a string as a title that it will use as a
         * title and and minimum and maximum value for the edges of the slider.
         * will throw an exception if either the min is less than zero or if
         * the min is greater than max.
         * 
         * @param title
         * @param min
         * @param max
         */
        public MapSlider(String title, int min, int max) {
            // constructor of the slider
            super(min, max);
            // verifies values
            if (min > 0 && min < max) {
                // border
                Border blackBord = BorderFactory.createTitledBorder(BorderFactory.createRaisedSoftBevelBorder(), title);
                // characteristics
                this.setBorder(blackBord);
                this.setMajorTickSpacing(1);
                this.setPaintTicks(true);
                this.setPaintLabels(true);
                this.setSnapToTicks(true);
            } else {
                // removes itself if it has invalid values or does not throw an exception itself
                this.remove(this);
            }
        }
    }

    /**
     * just a JToggle
     */
    public class MapToggle extends javax.swing.JToggleButton {
        public MapToggle(String title) {
            super(title);
        }
    }

    /**
     * just a JButton
     */
    public class MapButton extends javax.swing.JButton {
        public MapButton(String title) {
            super(title);
        }
    }

}
