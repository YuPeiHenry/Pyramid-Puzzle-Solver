package PyramidPuzzleSolver;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.ROTATION;
import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;

/**
 * Solves a given 2D puzzle. Method broken into symmetry.
 */
public class TwoDimSolver {
    private static final int NO_SHAPE = 0;

    //i = 0 ...
    //i = 1 ..
    //i = 2 .
    public static boolean solve(int[][] field, boolean[] used) {
        int x = -1, y = -1;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j + i < field.length; j++) {
                if (field[i][j] == 0) {
                    x = i;
                    y = j;
                    j = field.length;
                    i = field.length;
                }
            }
        }
        if (x < 0) {
            return true;
        }
        for (int shape = 0; shape < SHAPES_ON_BOARD.length; shape++) {
            if (used[shape]) {
                continue;
            }
            boolean isDefinitelyViable = tryPiece(field, used, shape, x, y, 0, 1);
            if (isDefinitelyViable) {
                return true;
            }
            if (!SHAPES_ON_BOARD[shape].getIsXyFlippable()) {
                continue;
            }
            isDefinitelyViable = tryPiece(field, used, shape, x, y, 1, 0);
            if (isDefinitelyViable) {
                return true;
            }
        }
        return false;
    }

    private static boolean tryPiece(int[][] field, boolean[] used, int shape, int x, int y, int xIndex, int yIndex) {
        for (int orient = 0; orient < SHAPES_ON_BOARD[shape].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[shape].getNumBlocks(); center++) {
                if (!isWithinBoundary(field, orient, center, shape, x, y, xIndex, yIndex)) {
                    continue;
                }
                fillShape(field, orient, center, shape, x, y, xIndex, yIndex, shape + 1);
                used[shape] = true;
                if (solve(field, used)) {
                    return true;
                }
                fillShape(field, orient, center, shape, x, y, xIndex, yIndex, NO_SHAPE);
                used[shape] = false;
            }
        }
        return false;
    }

    private static boolean isWithinBoundary(int[][] field, int orient, int center, int shape, int x, int y,
                                            int xIndex, int yIndex) {
        for (int block = 0; block < SHAPES_ON_BOARD[shape].getNumBlocks(); block++) {
            int newX = x + ROTATION[orient][xIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + xIndex);
            int newY = y + ROTATION[orient][yIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + yIndex);
            if (newX < 0 || newY < 0 || newX + newY >= field.length || field[newX][newY] > 0) {
                return false;
            }
        }
        return true;
    }

    private static void fillShape(int[][] field, int orient, int center, int shape, int x, int y,
                                  int xIndex, int yIndex, int value) {
        for (int block = 0; block < SHAPES_ON_BOARD[shape].getNumBlocks(); block++) {
            int newX = x + ROTATION[orient][xIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + xIndex);
            int newY = y + ROTATION[orient][yIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + yIndex);
            field[newX][newY] = value;
        }
    }
}
