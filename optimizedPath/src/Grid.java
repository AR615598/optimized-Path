import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is the logical grid used to do all the calcuations.
 * a grid is compsed of n cells. The grid always has one default
 * starting location at (1,1) or (0,0) in terms of arrays.
 * other components are holes/obstacles which the user cannot go
 * through so they must be avoided. and the destination cell where
 * you are trying to reach in the shortest path possible.
 */
public class Grid {
    Cell[][] currGrid;
    final Cell dest;
    private Cell origin;
    private int size;

    // defualt constructor will always default the size to 10,10
    public Grid() {
        int x = 0;
        int y = 0;
        currGrid = new Cell[10][10];
        // nested loop to create all the cells
        while (x < 10) {
            while (y < 10) {
                // assignment
                currGrid[x][y] = new Cell(x, y);
                // increment
                y++;
            }
            // increment
            x++;
            // reset
            y = 0;
        }
        dest = null;
        origin = new Cell(0, 0);
        size = 10;
    }

    /**
     * Constructo used to create the logical gird used for all the
     * calculations and to itterate through the cells. With this
     * constructo you can choose the dest and the size. This
     * constructor will throw an exception if the size is less
     * than zero or size is zero or less.
     * 
     * @param dest
     * @param size
     */
    public Grid(Cell dest, int size) {
        int x = 0;
        int y = 0;
        this.size = size;

        if (dest == null || size <= 0)
            throw new IllegalArgumentException("please enter valid values");

        currGrid = new Cell[size][size];
        // nested loop
        while (x < size) {
            while (y < size) {
                // assignment
                currGrid[x][y] = new Cell(x, y);
                // increment
                y++;
            }
            // incremnt
            x++;
            // reset for next nested loop
            y = 0;
        }
        this.origin = new Cell(1, 1);
        this.dest = dest;
    }


    /**
     * used to create a new dest. will throw a exception
     * if either ints are greater than the size of grid,
     * or less than or equal to 0. Not used since it is defaulted to
     * 0,0
     * 
     * @param x
     * @param y
     */
    public void setOrign(int x, int y) {
        if (!insideBounds(x, y)) {
            throw new IllegalArgumentException("Invalid values (0 < x,y <= gridSize)");
        }
        this.origin = currGrid[x][y];
    }

    /**
     * used to create a new dest. will throw an exception
     * if either ints are greater than the size or less
     * than or equal to zero.
     * 
     * @param x
     * @param y
     */
    public void setObsticle(int x, int y) {
        if (!insideBounds(x, y)) {
            throw new IllegalArgumentException("Invalid values (0 < x,y <= gridSize)");
        }
        currGrid[x][y] = new Obsticle(x, y);
    }

    /**
     * Checks if the coordiates are valid given the grid size
     * if it is withni the correct bounds the method will return
     * true, otherwise it will return false.
     * 
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean insideBounds(int x, int y) {
        if (x >= currGrid.length || x < 0 || y >= currGrid.length || y < 0) {
            return false;
        }
        return true;
    }

    /**
     * String representation of the majority of the grid including the calculations.
     * Only used once the
     * nessasary components are assigned.
     */
    @Override
    public String toString() {
        String str;
        if (dest != null && currGrid.length > 0) {
            if (distance() != -1) {
                str = "Dest:  (" + dest.getX() + ", " + dest.getY() + ")\n" + "Origin: (" + origin.getX() + ", " +
                        origin.getY() + ") \n" + "Size: " + currGrid.length + "x" + currGrid.length + "\n"
                        + "Steps from orgin to dest: " +
                        distance() + "\n";
            } else {
                str = "Dest:  (" + dest.getX() + ", " + dest.getY() + ")\n" + "Origin: (" + origin.getX() + ", " +
                        origin.getY() + ") \n" + "Size: " + currGrid.length + "x" + currGrid.length + "\n"
                        + "Destination unreachable" + "\n";
            }
            return str;
        } else {
            return "Size: " + currGrid.length + "x" + currGrid.length;
        }
    }

    /** method that will be called */
    public int distance() {
        return distanceHelper(origin, 0);
    }

    /**
     * Bredth first search through all the cells
     * and gives each cell its distance from the
     * origin.
     * 
     * @param curr
     * @param count
     * @return
     */
    private int distanceHelper(Cell curr, int count) {
        ArrayList<Cell> cellList = new ArrayList<>();
        ArrayList<Cell> secondaryList = new ArrayList<>();

        int min = 0;
        int max = currGrid.length - 1;
        int currX = 0;
        int currY = 0;

        // the loop breaks the moment the integers values are out of bounds for the
        // this loop will but all non-hole cells into a stack
        while (currX <= max && currY <= max) {

            // Very first case where the current cell is the first cell
            if (currX == min && currY == min) {
                if (!currGrid[currX][currY].isHole()) {
                    cellList.add(currGrid[currX][currY]);
                    currGrid[currX][currY].setDistance(0);
                    currGrid[currX][currY].setPrev(null);
                }
                currY++;
                while (currY > 0) {
                    if (!currGrid[currX][currY].isHole()) {
                        /*
                         * checks if one of its neighbors are within the cellList
                         * if it is within the Arraylist set it to its prev and add
                         * prev's distance by one. If there are multiple, compare and
                         * assign the one with the lowest distance value. If none are
                         * found set the cell to the secondary list to be checked
                         * later once all the cells have been discovered.
                         */
                        if (checkDistance(cellList, currGrid[currX][currY])) {
                            cellList.add(currGrid[currX][currY]);
                        } else
                            secondaryList.add(currGrid[currX][currY]);
                    }
                    currY--;
                    currX++;
                }
            } else
            // Very last case where the current cell is the last cell
            if (currX == max && currY == max) {
                if (!currGrid[currX][currY].isHole()) {
                    if (checkDistance(cellList, currGrid[currX][currY])) {
                        cellList.add(currGrid[currX][currY]);
                    } else
                        secondaryList.add(currGrid[currX][currY]);
                }
                currX++;
                currY++;

            } else
            // Right hand side max
            if (currY == max) {
                if (!currGrid[currX][currY].isHole()) {
                    if (checkDistance(cellList, currGrid[currX][currY])) {
                        cellList.add(currGrid[currX][currY]);
                    } else
                        secondaryList.add(currGrid[currX][currY]);
                }

                currX++;
                while (currX < max) {
                    if (!currGrid[currX][currY].isHole()) {
                        if (checkDistance(cellList, currGrid[currX][currY])) {
                            cellList.add(currGrid[currX][currY]);
                        } else
                            secondaryList.add(currGrid[currX][currY]);
                    }

                    currX++;
                    currY--;
                }
            } else
            // Left hand side max
            // add one to the right side
            if (currX == max) {
                if (!currGrid[currX][currY].isHole()) {
                    if (checkDistance(cellList, currGrid[currX][currY])) {
                        cellList.add(currGrid[currX][currY]);
                    } else
                        secondaryList.add(currGrid[currX][currY]);
                }

                currY++;
                while (currY < max) {
                    if (!currGrid[currX][currY].isHole()) {
                        if (checkDistance(cellList, currGrid[currX][currY])) {
                            cellList.add(currGrid[currX][currY]);
                        } else
                            secondaryList.add(currGrid[currX][currY]);
                    }

                    currY++;
                    currX--;
                }
            } else
            // Right hand side min
            // add one to the left side
            if (currY == min && currX != max) {
                if (!currGrid[currX][currY].isHole()) {
                    if (checkDistance(cellList, currGrid[currX][currY])) {
                        cellList.add(currGrid[currX][currY]);
                    } else
                        secondaryList.add(currGrid[currX][currY]);
                }

                currX++;
                while (currX > 0) {
                    if (!currGrid[currX][currY].isHole()) {
                        if (checkDistance(cellList, currGrid[currX][currY])) {
                            cellList.add(currGrid[currX][currY]);
                        } else
                            secondaryList.add(currGrid[currX][currY]);
                    }

                    currX--;
                    currY++;
                }
            } else
            // Left hand side min
            // add one to right side
            if (currX == min && currY != max) {
                if (!currGrid[currX][currY].isHole()) {
                    if (checkDistance(cellList, currGrid[currX][currY])) {
                        cellList.add(currGrid[currX][currY]);
                    } else
                        secondaryList.add(currGrid[currX][currY]);
                }

                currY++;
                while (currY > 0) {
                    if (!currGrid[currX][currY].isHole()) {
                        if (checkDistance(cellList, currGrid[currX][currY])) {
                            cellList.add(currGrid[currX][currY]);
                        } else
                            secondaryList.add(currGrid[currX][currY]);
                    }
                    currY--;
                    currX++;
                }
            }
        }

        while (!secondaryList.isEmpty()) {
            Iterator<Cell> iter = secondaryList.iterator();
            Integer startingSize = cellList.size();
            System.out.println("Before loop: " + startingSize);
          

            while (iter.hasNext()) {
                Cell current = iter.next();
                if (!checkDistance(cellList, current) && secondaryList.size() == 1){
                    iter.remove();
                }

                if (checkDistance(cellList, current)) {
                    cellList.add(current);
                    iter.remove();
                }
            }
            System.out.println("after loop: " + startingSize + "\n");

            if (startingSize == cellList.size()){
            System.out.println("I havce no reason to return but i did");
                break;
            }
        }
        return currGrid[dest.getX() - 1][dest.getY() - 1].getDistance();
    }

    /**
     * This method will check if one of the cells neighbors are contained in
     * the cellList ArrayList if multiple exist it will choose the one with
     * the lowest distance value. If no suitable cells are found it will return
     * false, otherwise it will assign the cell the correct prev value and
     * distance value and return true.
     * 
     * @param cellList
     * @param cell
     * @return boolean
     */
    public boolean checkDistance(ArrayList<Cell> cellList, Cell cell) {
        Cell temp = new Cell(cell.getX(), cell.getY());
        int currX;
        int plusX;
        int minusX;
        int currY;
        int plusY;
        int minusY;
        int max;

        if (cellList == null || cell == null) {
            return false;
        }

        currX = cell.getX();
        minusX = currX - 1;
        plusX = currX + 1;
        currY = cell.getY();
        minusY = currY - 1;
        plusY = currY + 1;
        max = size - 1;

        if (minusX >= 0) {
            // check for minus X and currY
            temp.setX(minusX);
            if (cellList.contains(temp)) {
                int index = cellList.indexOf(temp);

                // case for first match found
                if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }
                // case for if there is a min cell already so we have to check if
                // the new cell distance val is smaller replace the old min
                if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }

            }

            if (minusY >= 0) {
                temp.setY(minusY);
                // check for minusX and minusY
                if (cellList.contains(temp)) {
                    int index = cellList.indexOf(temp);

                    // case for first match found
                    if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                    // case for if there is a min cell already so we have to check if
                    // the new cell distance val is smaller replace the old min
                    if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                }
            }

            if (plusY <= max) {
                temp.setY(plusY);

                // check for minusX and plusY
                temp.setX(minusX);
                if (cellList.contains(temp)) {
                    int index = cellList.indexOf(temp);

                    // case for first match found
                    if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                    // case for if there is a min cell already so we have to check if
                    // the new cell distance val is smaller replace the old min
                    if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                }

            }
        }
        // reset temp
        temp.setX(currX);
        temp.setY(currY);

        if (plusX <= max) {
            temp.setX(plusX);

            // check for plusX and currY
            if (cellList.contains(temp)) {
                int index = cellList.indexOf(temp);

                // case for first match found
                if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }
                // case for if there is a min cell already so we have to check if
                // the new cell distance val is smaller replace the old min
                if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }
            }

            if (minusY >= 0) {
                temp.setY(minusY);

                // check for plusX and minusY
                if (cellList.contains(temp)) {
                    int index = cellList.indexOf(temp);

                    // case for first match found
                    if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                    // case for if there is a min cell already so we have to check if
                    // the new cell distance val is smaller replace the old min
                    if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                }
            }
            if (plusY <= max) {
                temp.setY(plusY);

                // check for plusX and plusY
                if (cellList.contains(temp)) {
                    int index = cellList.indexOf(temp);

                    // case for first match found
                    if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                    // case for if there is a min cell already so we have to check if
                    // the new cell distance val is smaller replace the old min
                    if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                        cell.setPrev(cellList.get(index));
                        cell.setDistance(cellList.get(index).getDistance() + 1);
                    }
                }
            }
        }

        // check for currX and minusY
        temp.setX(currX);
        temp.setY(minusY);
        if (minusY >= 0)
            if (cellList.contains(temp)) {
                int index = cellList.indexOf(temp);

                // case for first match found
                if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }
                // case for if there is a min cell already so we have to check if
                // the new cell distance val is smaller replace the old min
                if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }

            }

        // check for currX and plusY
        temp.setX(currX);
        temp.setY(plusY);
        if (plusY <= max)
            if (cellList.contains(temp)) {
                int index = cellList.indexOf(temp);

                // case for first match found
                if (cell.getPrev() == null && cellList.get(index).getDistance() != -1) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }
                // case for if there is a min cell already so we have to check if
                // the new cell distance val is smaller replace the old min
                if (cell.getDistance() != -1 && cell.getDistance() > cellList.get(index).getDistance()) {
                    cell.setPrev(cellList.get(index));
                    cell.setDistance(cellList.get(index).getDistance() + 1);
                }

            }
        if (cell.getPrev() != null) {
            return true;
        }
        return false;
    }
}
