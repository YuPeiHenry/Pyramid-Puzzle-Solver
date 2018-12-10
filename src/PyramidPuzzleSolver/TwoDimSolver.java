package PyramidPuzzleSolver;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.ROTATION;
import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;

public class TwoDimSolver {
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
        for (int i = 0; i < SHAPES_ON_BOARD.length; i++) {
            if (used[i]) {
                continue;
            }
            boolean isDefinitelyViable = tryPiece(field, used, i, x, y, 0, 1);
            if (isDefinitelyViable) {
                return true;
            }
            if (!SHAPES_ON_BOARD[i].getIsXyFlippable()) {
                continue;
            }
            isDefinitelyViable = tryPiece(field, used, i, x, y, 1, 0);
            if (isDefinitelyViable) {
                return true;
            }
        }
        return false;
    }

    private static boolean tryPiece(int[][] field, boolean[] used, int i, int x, int y, int xIndex, int yIndex) {
        for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                boolean pass = true;
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][xIndex] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + xIndex);
                    int newY = y + ROTATION[orient][yIndex] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + yIndex);
                    if (newX < 0 || newY < 0 || newX + newY >= field.length || field[newX][newY] > 0) {
                        pass = false;
                        break;
                    }
                }
                if (!pass) {
                    continue;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][xIndex] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + xIndex);
                    int newY = y + ROTATION[orient][yIndex] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + yIndex);
                    field[newX][newY] = i + 1;
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][xIndex] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + xIndex);
                    int newY = y + ROTATION[orient][yIndex] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + yIndex);
                    field[newX][newY] = 0;
                }
                used[i] = false;
            }
        }
        return false;
    }
}
