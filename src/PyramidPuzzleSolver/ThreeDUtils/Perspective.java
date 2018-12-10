package PyramidPuzzleSolver.ThreeDUtils;

public interface Perspective {
    boolean isOutOfBounds(int[][][] field, int x, int y, int z);
    int getNode(int[][][] field, int x, int y, int z);
    void setNode(int[][][] field, int x, int y, int z, int value);
}
