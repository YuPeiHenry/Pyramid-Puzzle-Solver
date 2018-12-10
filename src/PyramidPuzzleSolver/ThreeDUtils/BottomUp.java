package PyramidPuzzleSolver.ThreeDUtils;

/**
 * Represents the pyramid using bottom up view and the x, y, z co-ordinates.
 */
public class BottomUp implements Perspective {

    @Override
    public boolean isOutOfBounds(int[][][] field, int x, int y, int z) {
        return x < 0 || y < 0 || x + z >= field.length || y + z >= field.length;
    }

    @Override
    public int getNode(int[][][] field, int x, int y, int z) {
        return field[z][x][y];
    }

    @Override
    public void setNode(int[][][] field, int x, int y, int z, int value) {
        field[z][x][y] = value;
    }
}
