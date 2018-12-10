package PyramidPuzzleSolver.Shape;

/**
 * Represents a shape that can be placed on the game board.
 */
public class ShapeOnBoard {
    public int R, G, B;
    public int numOrients;
    public int numBlocks;
    public boolean XYswap;
    //[numBlocks][numBlocks * 2]
    public int[][] layouts = null;
    public ShapeOnBoard(int R, int G, int B, int numOrients, int numBlocks, boolean XYswap, int[][] layouts) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.numOrients = numOrients;
        this.numBlocks = numBlocks;
        this.XYswap = XYswap;
        this.layouts = layouts;
    }
}