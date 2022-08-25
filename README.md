# optimized-Path
Basic Java project using a swing GUI

## Description 
This program will attempt to find the shortest path possible in a square matrix, beginning from the first index to the destination index. This matrix is composed of cells and holes, the cells are the only traversable indices. In order to traverse through all indices I chose to use bredth first search, and after iterating through each cell they would be assigned a distance from the orign. The program uses a Swing GUI along with some other AWT components.

## Possible improvments 
Creating a GUI was the primary reason in creating this program, until now I have never made a GUI. But because of this I had encountered a couple of issues such as the allowing for the communication between components, the solution that is currently being used is getters connected from the root panel. This should be changed since it violates encapsulation and is overall dangerous. Instead the majority of functionality should be moved to the display class so that the two component getters are not needed. Other classes can also benefit from removing getters and setters.


## Skills used
- Swing
- AWT
- Searching Algorithms 
