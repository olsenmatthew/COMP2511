/**
 *
 */
package unsw.automata;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Conway's Game of Life on a 10x10 grid.
 *
 * @author Robert Clifton-Everest
 *
 */
public class GameOfLife {

    private BooleanProperty[][] cells;

    public GameOfLife() {
        cells = new BooleanProperty[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cells[x][y] = new SimpleBooleanProperty();
            }
        }
    }

    public void ensureAlive(int x, int y) {
        cells[x][y].set(true);
    }

    public void ensureDead(int x, int y) {
        cells[x][y].set(true);
    }

    public boolean isAlive(int x, int y) {
        return cells[x][y].get();
    }

    public void tick() {
        boolean[][] nextGen = new boolean[10][10];

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                int liveNeighbours = neighboursAlive(x,y);
                if (isAlive(x,y)) {
                    if (liveNeighbours == 2 || liveNeighbours == 3)
                        nextGen[x][y] = true;
                    else
                        nextGen[x][y] = false;
                } else {
                    if (liveNeighbours == 3)
                        nextGen[x][y] = true;
                    else
                        nextGen[x][y] = false;
                }
            }
        }

        // Can't just reassign like this. Have to update properties.
//        cells = nextGen;

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cells[x][y].set(nextGen[x][y]);
            }
        }
    }

    public BooleanProperty cellProperty(int x, int y) {
        return cells[x][y];
    }

    private int neighboursAlive(int x, int y) {
        int alive = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;

                if (isAlive(Math.floorMod(x+i,10), Math.floorMod(y+j, 10))) {
                    alive++;
                }
            }
        }
        return alive;
    }

}
