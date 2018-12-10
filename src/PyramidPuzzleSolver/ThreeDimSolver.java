package PyramidPuzzleSolver;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.ROTATION;
import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;

import PyramidPuzzleSolver.ThreeDUtils.InverseSlice;
import PyramidPuzzleSolver.ThreeDUtils.Perspective;
import PyramidPuzzleSolver.ThreeDUtils.Slice;
import PyramidPuzzleSolver.ThreeDUtils.BottomUp;

/**
 * Solves a given 3D puzzle. Method broken into 3 perspectives and symmetry.
 */
public class ThreeDimSolver {
    public static final int BASE_MINUS_ONE = ThreeDimUi.PYRAMID_BASE_SIZE - 1;
    private static final int NO_SHAPE = 0;

    //i = 2 ...
    //      ...
    //      ...

    //i = 1 ..
    //      ..

    //i = 0 .

    //k =   012
    //j = 0 ...
    //j = 1 ...

    /**
     * Tries to fit a specific piece at a specific position.
     * There are 3 ways to fit a piece:
     * Lying flat (BottomUp perspective)
     * Standing, facing the top left of the board (Slice perspective)
     * Standing, facing the top right of the board (InvertedSlice perspective)
     */
    public static boolean pieceConstraintSolve(int[][][] field, boolean[] used, int shape, int x, int y, int z) {
        int sliceX = (x + y <= BASE_MINUS_ONE - z) ? x : x - y;
        int sliceY = (x + y <= BASE_MINUS_ONE - z) ? y : y - x;
        int sliceZ = x + y + z;
        int invertedSliceX = (x + (BASE_MINUS_ONE - y) <= BASE_MINUS_ONE) ? (BASE_MINUS_ONE - y) - z : (BASE_MINUS_ONE - y) - z - x;
        int invertedSliceY = (x + (BASE_MINUS_ONE - y) <= BASE_MINUS_ONE) ? x : x - (BASE_MINUS_ONE - y);
        int invertedSliceZ = x + (BASE_MINUS_ONE - y);
        return pieceConstraintSolveFlat(field, used, x, y, z, shape) ||
                pieceConstraintSolveSlice(field, used, sliceX, sliceY, sliceZ, shape) ||
                pieceConstraintSolveInverseSlice(field, used, invertedSliceX, invertedSliceY, invertedSliceZ, shape);
    }

    private static boolean pieceConstraintSolveFlat(int[][][] field, boolean[] used, int x, int y, int z, int shape) {
        return pieceConstraintSolveGivenPerspective(new BottomUp(), field, used, x, y, z, shape);
    }

    private static boolean pieceConstraintSolveSlice(int[][][] field, boolean[] used, int x, int y, int z, int shape) {
        return pieceConstraintSolveGivenPerspective(new Slice(), field, used, x, y, z, shape);
    }

    private static boolean pieceConstraintSolveInverseSlice(int[][][] field, boolean[] used, int x, int y, int z, int shape) {
        return pieceConstraintSolveGivenPerspective(new InverseSlice(), field, used, x, y, z, shape);
    }

    private static boolean pieceConstraintSolveGivenPerspective(Perspective perspective, int[][][] field, boolean[] used, int x,
                                                                int y, int z, int shape) {
        if (used[shape]) {
            return false;
        }
        boolean isDefinitelyViable = tryPiece(perspective, field, used, x, y, z, shape, 0, 1);
        if (isDefinitelyViable) {
            return true;
        }
        if (!SHAPES_ON_BOARD[shape].getIsXyFlippable()) {
            return false;
        }
        isDefinitelyViable = tryPiece(perspective, field, used, x, y, z, shape, 1, 0);
        if (isDefinitelyViable) {
            return true;
        }
        return false;
    }

    public static boolean solve(int[][][] field, boolean[] used) {
        int x = -1, y = -1, z = -1;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j + i < field.length; j++) {
                for (int k = 0; k + i < field.length; k++) {
                    if (field[i][j][k] == 0) {
                        x = j;
                        y = k;
                        z = i;
                        k = field.length;
                        j = field.length;
                        i = field.length;
                    }
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
            boolean isDefinitelyViable = pieceConstraintSolve(field, used, shape, x, y, z);
            if (isDefinitelyViable) {
                return true;
            }
        }
        return false;
    }

    private static boolean tryPiece(Perspective perspective, int[][][] field, boolean[] used, int x,
                                    int y, int z, int shape, int xIndex, int yIndex) {
        for (int orient = 0; orient < SHAPES_ON_BOARD[shape].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[shape].getNumBlocks(); center++) {
                if (!isWithinBoundary(perspective, field, orient, center, shape, x, y, z, xIndex, yIndex)) {
                    continue;
                }
                fillShape(perspective, field, orient, center, shape, x, y, z, xIndex, yIndex, shape + 1);
                used[shape] = true;
                if (solve(field, used)) {
                    return true;
                }
                fillShape(perspective, field, orient, center, shape, x, y, z, xIndex, yIndex, NO_SHAPE);
                used[shape] = false;
            }
        }
        return false;
    }

    private static boolean isWithinBoundary(Perspective perspective, int[][][] field, int orient, int center, int shape,
                                         int x, int y, int z, int xIndex, int yIndex) {
        for (int block = 0; block < SHAPES_ON_BOARD[shape].getNumBlocks(); block++) {
            int newX = x + ROTATION[orient][xIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + xIndex);
            int newY = y + ROTATION[orient][yIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + yIndex);
            if (perspective.isOutOfBounds(field, newX, newY, z) || perspective.getNode(field, newX, newY, z) > 0) {
                return false;
            }
        }
        return true;
    }

    private static void fillShape(Perspective perspective, int[][][] field, int orient, int center, int shape,
                           int x, int y, int z, int xIndex, int yIndex, int value) {
        for (int block = 0; block < SHAPES_ON_BOARD[shape].getNumBlocks(); block++) {
            int newX = x + ROTATION[orient][xIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + xIndex);
            int newY = y + ROTATION[orient][yIndex] * SHAPES_ON_BOARD[shape].getBlockOffset(center, block * 2 + yIndex);
            perspective.setNode(field, newX, newY, z, value);
        }
    }
}
