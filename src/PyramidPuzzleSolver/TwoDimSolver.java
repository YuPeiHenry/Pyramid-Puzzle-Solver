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
            for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
                for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                    boolean pass = true;
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        if (newX < 0 || newY < 0 || newX + newY >= field.length || field[newX][newY] > 0) {
                            pass = false;
                            break;
                        }
                    }
                    if (!pass) {
                        continue;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        field[newX][newY] = i + 1;
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        field[newX][newY] = 0;
                    }
                    used[i] = false;
                }
            }
            if (!SHAPES_ON_BOARD[i].getIsXyFlipped()) {
                continue;
            }
            for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
                for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                    boolean pass = true;
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        if (newX < 0 || newY < 0 || newX + newY >= field.length || field[newX][newY] > 0) {
                            pass = false;
                            break;
                        }
                    }
                    if (!pass) {
                        continue;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        field[newX][newY] = i + 1;
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        field[newX][newY] = 0;
                    }
                    used[i] = false;
                }
            }
        }
        return false;
    }
}
