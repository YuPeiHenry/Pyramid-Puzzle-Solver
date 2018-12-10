package PyramidPuzzleSolver;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.ROTATION;
import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;
import static PyramidPuzzleSolver.ThreeDimUi.TUPLE_SIZE;

import PyramidPuzzleSolver.ThreeDUtils.InverseSlice;
import PyramidPuzzleSolver.ThreeDUtils.Perspective;
import PyramidPuzzleSolver.ThreeDUtils.Slice;
import PyramidPuzzleSolver.ThreeDUtils.BottomUp;

public class ThreeDimSolver {

    //i = 2 ...
    //      ...
    //      ...

    //i = 1 ..
    //      ..

    //i = 0 .

    //k =   012
    //j = 0 ...
    //j = 1 ...

    public static boolean limitedsolve(int[][][] field, boolean[] used, int[] tuple) {
        int x = tuple[2], y = tuple[3], z = tuple[1];
        int slicex = (x + y <= TUPLE_SIZE - z) ? x : x - y;
        int slicey = (x + y <= TUPLE_SIZE - z) ? y : y - x;
        int slicez = x + y + z;
        int Islicex = (x + (TUPLE_SIZE - y) <= TUPLE_SIZE) ? (TUPLE_SIZE - y) - z : (TUPLE_SIZE - y) - z - x;
        int Islicey = (x + (TUPLE_SIZE - y) <= TUPLE_SIZE) ? x : x - (TUPLE_SIZE - y);
        int Islicez = x + (TUPLE_SIZE - y);
        return limitsolveflat(field, used, x, y, z, tuple[0] - 1) ||
                limitsolveslice(field, used, slicex, slicey, slicez, tuple[0] - 1) ||
                limitsolveinverseslice(field, used, Islicex, Islicey, Islicez, tuple[0] - 1);
    }

    private static boolean limitsolveflat(int[][][] field, boolean[] used, int x, int y, int z, int i) {
        return limitSolveGivenPerspective(new BottomUp(), field, used, x, y, z, i);
    }

    private static boolean limitsolveslice(int[][][] field, boolean[] used, int x, int y, int z, int i) {
        return limitSolveGivenPerspective(new Slice(), field, used, x, y, z, i);
    }

    private static boolean limitsolveinverseslice(int[][][] field, boolean[] used, int x, int y, int z, int i) {
        return limitSolveGivenPerspective(new InverseSlice(), field, used, x, y, z, i);
    }

    private static boolean limitSolveGivenPerspective(Perspective perspective, int[][][] field, boolean[] used, int x,
                                                      int y, int z, int i) {
        if (used[i]) {
            return false;
        }
        for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                boolean pass = true;
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    if (perspective.isOutOfBounds(field, newX, newY, z) || perspective.getNode(field, newX, newY, z) > 0) {
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
                    perspective.setNode(field, newX, newY, z, i + 1);
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    perspective.setNode(field, newX, newY, z, 0);
                }
                used[i] = false;
            }
        }
        if (!SHAPES_ON_BOARD[i].getIsXyFlipped()) {
            return false;
        }
        for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                boolean pass = true;
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    if (perspective.isOutOfBounds(field, newX, newY, z) || perspective.getNode(field, newX, newY, z) > 0) {
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
                    perspective.setNode(field, newX, newY, z, i + 1);
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    perspective.setNode(field, newX, newY, z, 0);
                }
                used[i] = false;
            }
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
        int slicex = (x + y <= TUPLE_SIZE - z) ? x : x - y;
        int slicey = (x + y <= TUPLE_SIZE - z) ? y : y - x;
        int slicez = x + y + z;
        int Islicex = (x + (TUPLE_SIZE - y) <= TUPLE_SIZE) ? (TUPLE_SIZE - y) - z : (TUPLE_SIZE - y) - z - x;
        int Islicey = (x + (TUPLE_SIZE - y) <= TUPLE_SIZE) ? x : x - (TUPLE_SIZE - y);
        int Islicez = x + (TUPLE_SIZE - y);
        return solveFlat(field, used, x, y, z) || solveSlice(field, used, slicex, slicey, slicez) ||
                solveInverseSlice(field, used, Islicex, Islicey, Islicez);
    }

    private static boolean solveFlat(int[][][] field, boolean[] used, int x, int y, int z) {
        return solveGivenPerspective(new BottomUp(), field, used, x, y, z);
    }

    private static boolean solveSlice(int[][][] field, boolean[] used, int x, int y, int z) {
        return solveGivenPerspective(new Slice(), field, used, x, y, z);
    }

    private static boolean solveInverseSlice(int[][][] field, boolean[] used, int x, int y, int z) {
        return solveGivenPerspective(new InverseSlice(), field, used, x, y, z);
    }

    private static boolean solveGivenPerspective(Perspective perspective, int[][][] field, boolean[] used, int x, int y,
                                                 int z) {
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
                        if (perspective.isOutOfBounds(field, newX, newY, z) || perspective.getNode(field, newX, newY, z) > 0) {
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
                        perspective.setNode(field, newX, newY, z, i + 1);
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        perspective.setNode(field, newX, newY, z, 0);
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
                        if (perspective.isOutOfBounds(field, newX, newY, z) || perspective.getNode(field, newX, newY, z) > 0) {
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
                        perspective.setNode(field, newX, newY, z, i + 1);
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        perspective.setNode(field, newX, newY, z, 0);
                    }
                    used[i] = false;
                }
            }
        }
        return false;
    }

}
