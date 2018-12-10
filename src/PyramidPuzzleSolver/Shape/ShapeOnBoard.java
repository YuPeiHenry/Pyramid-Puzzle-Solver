package PyramidPuzzleSolver.Shape;

import java.awt.Color;

/**
 * Represents a shape that can be placed isOn the game board.
 */
public class ShapeOnBoard {
    private final Color color;
    private final int numOrients;
    private final int numBlocks;
    private final boolean isXyFlipped;
    //[numBlocks][numBlocks * 2]
    private final int[][] layouts;

    public ShapeOnBoard(int red, int green, int blue, int numOrients, int numBlocks, boolean isXyFlipped,
                        int[][] layouts) {
        color = new Color(red, green, blue);
        this.numOrients = numOrients;
        this.numBlocks = numBlocks;
        this.isXyFlipped = isXyFlipped;
        this.layouts = layouts;
    }

    public Color getColor() {
        return color;
    }

    public int getNumOrients() {
        return numOrients;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public boolean getIsXyFlippable() {
        return isXyFlipped;
    }

    public int getBlockOffset(int position, int block) {
        return layouts[position][block];
    }

}