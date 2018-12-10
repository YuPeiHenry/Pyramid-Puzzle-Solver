package PyramidPuzzleSolver.ThreeDUtils;

import static PyramidPuzzleSolver.ThreeDimUi.TUPLE_SIZE;

/**
 * Represents the pyramid using a view from the top-left corner of bottom-most layer, giving 8 layers.
 * Uses x, y, z co-ordinates.
 */
public class Slice implements Perspective {

    @Override
    public boolean isOutOfBounds(int[][][] field, int x, int y, int z) {
        return x < 0 || y < 0 || x + y > z;
    }

    @Override
    public int getNode(int[][][] field, int x, int y, int z) {
        if (z < field.length) {
            return field[z - x - y][x][y];
        } else if (z + x + y > 8) {
            return 99;
        } else {
            return field[8 - z - x - y][z - TUPLE_SIZE + x][z - TUPLE_SIZE + y];
        }
    }

    @Override
    public void setNode(int[][][] field, int x, int y, int z, int value) {
        if (z < field.length) {
            field[z - x - y][x][y] = value;
        } else {
            field[8 - z - x - y][z - TUPLE_SIZE + x][z - TUPLE_SIZE + y] = value;
        }
    }
}
