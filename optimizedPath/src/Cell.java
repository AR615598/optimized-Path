/**
 * This class is used to fold the individual components in the Grid class.
 * This class with contain the Cell's X and Y coordinates as well as a flag
 * to see whether this cell has been checked before. Finally the class is able
 * to
 * track its previous node, which is usefull for tracing back the most optimal
 * path.
 */

public class Cell {
    // Class Variables
    private int x;
    private int y;
    private Cell prev = null;
    private int distance = -1;

    // constructor
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * makes a deep Copy of the referenced object.
     * the two objects will behave independently like
     * a deep copy, but by default will equal each other.
     * The only difference is that the flag will
     * default to false even if the referenced cell flag
     * is true.
     * 
     * @return Object
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Cell copy = new Cell(x, y);
        return copy;
    }

    /**
     * Compares the referenced object to the prameteized
     * object. The all values will be compared including
     * the flag.
     * 
     * @param Object: expects cell object, will return false
     *                otherwise
     * @return Boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Cell param = (Cell) obj;
        if (param.getX() == this.getX() && param.getY() == this.getY())
            return true;
        else
            return false;

    }

    /**
     * Gives a string representaion of the current Cell object
     */
    @Override
    public String toString() {
            return "(" + x + "," + y + ")";
    }

    // Getter (width) Should be replaced ngl
    public int getX() {
        return x;
    }

    // Getter (height) Should be replaced ngl
    public int getY() {
        return y;
    }

    /**
     * checks if the current Cell object is a hole. 
     * Always returns false.
     * @return
     */
    public boolean isHole(){
        return false;
    }
 
    /**
     * Setter to change the distance value
     * @param val
     */
    public void setDistance (int val){
        distance = val;
    }

    /**
     * Sets the parameter cell as the crrent cell's 
     * prev value.
     * @param cell
     */
    public void setPrev (Cell cell){
        prev = cell;
    }

    /**
     * Getter, returns the distance value
     * @return
     */
    public int getDistance (){
        return this.distance;
    }

    /**
     * Getter, returns the Prev cell, if one is never assigned 
     * it will return null.
     * @return
     */
    public Cell getPrev (){
        return this.prev;
    }
  
    /**
     * assigns the X value to newVal
     * @param newVal
     */
    public void setX(int newVal){
        this.x = newVal;
    }
 
    /**
     * assigns the Y value to newVal
     * @param newVal
     */
    public void setY(int newVal){
        this.y = newVal;
    }
}

/**
 * Hole object used by grid class to detect a hole in the
 * grid Other than that has the same exact functionality of
 * the Cell Class object.
 */
class Obsticle extends Cell {
    /**
     * Creates an obsticle object 
     * @param x
     * @param y
     */
    public Obsticle(int x, int y) {
        super(x, y);
    }
   /**
     * checks if the current Cell object is a hole. 
     * Always returns false.
     * @return
     */
    public boolean isHole(){
        return true;
    }
}
