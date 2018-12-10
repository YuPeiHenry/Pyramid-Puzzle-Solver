package PyramidPuzzleSolver.ThreeDUtils;

import static PyramidPuzzleSolver.ThreeDimSolver.BASE_MINUS_ONE;

/**
 * Represents the pyramid using a view from the top-right corner of bottom-most layer, giving 8 layers.
 * Uses x, y, z co-ordinates.
 */
public class InverseSlice implements Perspective {

    @Override
    public boolean isOutOfBounds(int[][][] field, int x, int y, int z) {
        return x < 0 || y < 0 || x + y > z;
    }

    @Override
    public int getNode(int[][][] field, int x, int y, int z) {
        if (z < field.length) {
            int actualZ = z - x - y;
            return field[actualZ][y][BASE_MINUS_ONE - actualZ - x];
        } else if (z + x + y > 8) {
            return 99;
        } else {
            int actualZ = 8 - z - x - y;
            return field[actualZ][z - BASE_MINUS_ONE + y][y];
        }
    }

    @Override
    public void setNode(int[][][] field, int x, int y, int z, int value) {
        if (z < field.length) {
            int actualZ = z - x - y;
            field[actualZ][y][BASE_MINUS_ONE - actualZ - x] = value;
        } else {
            int actualZ = 8 - z - x - y;
            field[actualZ][z - BASE_MINUS_ONE + y][y] = value;
        }
    }
}
