package PyramidPuzzleSolver;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.ROTATION;
import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;
import static PyramidPuzzleSolver.ThreeDimUi.TUPLE_SIZE;

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
        if (used[i]) {
            return false;
        }
        for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                boolean pass = true;
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center,block * 2 + 1);
                    if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
                    field[z][newX][newY] = i + 1;
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    field[z][newX][newY] = 0;
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
                    if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
                    field[z][newX][newY] = i + 1;
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    field[z][newX][newY] = 0;
                }
                used[i] = false;
            }
        }
        return false;
    }

    private static boolean limitsolveslice(int[][][] field, boolean[] used, int x, int y, int z, int i) {
        if (used[i]) {
            return false;
        }
        for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                boolean pass = true;
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
                    setslice(field, z, newX, newY, i + 1);
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    setslice(field, z, newX, newY, 0);
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
                    if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
                    setslice(field, z, newX, newY, i + 1);
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    setslice(field, z, newX, newY, 0);
                }
                used[i] = false;
            }
        }
        return false;
    }

    private static boolean limitsolveinverseslice(int[][][] field, boolean[] used, int x, int y, int z, int i) {
        if (used[i]) {
            return false;
        }
        for (int orient = 0; orient < SHAPES_ON_BOARD[i].getNumOrients(); orient++) {
            for (int center = 0; center < SHAPES_ON_BOARD[i].getNumBlocks(); center++) {
                boolean pass = true;
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
                    setinverseslice(field, z, newX, newY, i + 1);
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    setinverseslice(field, z, newX, newY, 0);
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
                    if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
                    setinverseslice(field, z, newX, newY, i + 1);
                }
                used[i] = true;
                if (solve(field, used)) {
                    return true;
                }
                for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                    int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                    int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                    setinverseslice(field, z, newX, newY, 0);
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
        return solveflat(field, used, x, y, z) || solveslice(field, used, slicex, slicey, slicez) ||
                solveinverseslice(field, used, Islicex, Islicey, Islicez);
    }

    private static boolean solveflat(int[][][] field, boolean[] used, int x, int y, int z) {
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
                        if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
                        field[z][newX][newY] = i + 1;
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        field[z][newX][newY] = 0;
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
                        if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
                        field[z][newX][newY] = i + 1;
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        field[z][newX][newY] = 0;
                    }
                    used[i] = false;
                }
            }
        }
        return false;
    }

    private static boolean solveslice(int[][][] field, boolean[] used, int x, int y, int z) {
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
                        if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
                        setslice(field, z, newX, newY, i + 1);
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        setslice(field, z, newX, newY, 0);
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
                        if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
                        setslice(field, z, newX, newY, i + 1);
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        setslice(field, z, newX, newY, 0);
                    }
                    used[i] = false;
                }
            }
        }
        return false;
    }

    private static boolean solveinverseslice(int[][][] field, boolean[] used, int x, int y, int z) {
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
                        if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
                        setinverseslice(field, z, newX, newY, i + 1);
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newX = x + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newY = y + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        setinverseslice(field, z, newX, newY, 0);
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
                        if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
                        setinverseslice(field, z, newX, newY, i + 1);
                    }
                    used[i] = true;
                    if (solve(field, used)) {
                        return true;
                    }
                    for (int block = 0; block < SHAPES_ON_BOARD[i].getNumBlocks(); block++) {
                        int newY = y + ROTATION[orient][0] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2);
                        int newX = x + ROTATION[orient][1] * SHAPES_ON_BOARD[i].getBlockOffset(center, block * 2 + 1);
                        setinverseslice(field, z, newX, newY, 0);
                    }
                    used[i] = false;
                }
            }
        }
        return false;
    }

    private static int getslice(int[][][] field, int i, int j, int k) {
        if (i < field.length) {
            return field[i - j - k][j][k];
        } else if (i + j + k > 8) {
            return 99;
        } else {
            return field[8 - i - j - k][i - TUPLE_SIZE + j][i - TUPLE_SIZE + k];
        }
    }

    private static void setslice(int[][][] field, int i, int j, int k, int x) {
        if (i < field.length) {
            field[i - j - k][j][k] = x;
        } else {
            field[8 - i - j - k][i - TUPLE_SIZE + j][i - TUPLE_SIZE + k] = x;
        }
    }

    private static int getinverseslice(int[][][] field, int i, int j, int k) {
        if (i < field.length) {
            int z = i - j - k;
            return field[z][k][TUPLE_SIZE - z - j];
        } else if (i + j + k > 8) {
            return 99;
        } else {
            int z = 8 - i - j - k;
            return field[z][i - TUPLE_SIZE + k][k];
        }
    }

    private static void setinverseslice(int[][][] field, int i, int j, int k, int x) {
        if (i < field.length) {
            int z = i - j - k;
            field[z][k][TUPLE_SIZE - z - j] = x;
        } else {
            int z = 8 - i - j - k;
            field[z][i - TUPLE_SIZE + k][k] = x;
        }
    }
}
