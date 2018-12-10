package PyramidPuzzleSolver;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;
import static PyramidPuzzleSolver.Shape.TypesOfShapes.ROTATION;

import PyramidPuzzleSolver.Shape.UiUtil;

public class ThreeDim {
    private static final int BUTTON_SIZE = 30;
    private static final int PYRAMID_BASE_SIZE = 5;
    private static final int TUPLE_SIZE = 4;

    private static ColorPanel[][][] solution;

    //i = 2 ...
    //      ...
    //      ...

    //i = 1 ..
    //      ..

    //i = 0 .

    //k =   012
    //j = 0 ...
    //j = 1 ...

    private static boolean limitedsolve(int[][][] field, boolean[] used, int[] tuple) {
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

    private static boolean solve(int[][][] field, boolean[] used) {
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

    private static ActionListener getToggleAction(ColorPanel toggleableButton, int[][][] field, int[] tuple,
                                                  int s, int t, int u) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColorPanel panel = solution[tuple[1]][tuple[2]][tuple[3]];
                if (panel != toggleableButton) {
                    toggleableButton.setColor(panel.getColor());
                    panel.setColor(ColorPanel.WHITE);
                    panel.repaint();
                    tuple[1] = s;
                    tuple[2] = t;
                    tuple[3] = u;
                }
                toggleableButton.toggleNode();
                toggleableButton.repaint();
                if (field[s][t][u] == 0) {
                    field[s][t][u] = 99;
                } else {
                    field[s][t][u] = 0;
                }
            }
        };
    }
    private static ColorPanel makeToggleableButton(int buttonX, int buttonY, int[][][] field, int s, int t, int u, int[] tuple) {
        ColorPanel toggleableButton = new ColorPanel();
        ActionListener toggleAction = getToggleAction(toggleableButton, field, tuple, s, t, u);
        UiUtil.setToggleableButtonProperties(BUTTON_SIZE, buttonX, buttonY, toggleableButton, toggleAction);
        return toggleableButton;
    }

    private static ColorPanel toggleButton(int x, int y, int[][][] field, int[] tuple, ColorPanel[][][] solution) {
        ColorPanel result = new ColorPanel();
        result.setNodeIsOn(false);
        result.setColor(ColorPanel.WHITE);
        result.setBounds(x, y, BUTTON_SIZE, BUTTON_SIZE);
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColorPanel panel = solution[tuple[1]][tuple[2]][tuple[3]];
                tuple[0]++;
                if (tuple[0] > SHAPES_ON_BOARD.length) {
                    tuple[0] = 0;
                    result.setNodeIsOn(false);
                    panel.setColor(ColorPanel.WHITE);
                } else {
                    result.setNodeIsOn(true);
                    result.setColor(SHAPES_ON_BOARD[tuple[0] - 1].getColor());
                    panel.setColor(SHAPES_ON_BOARD[tuple[0] - 1].getColor());
                }
                result.repaint();
                panel.repaint();
            }
        });
        result.add(button);

        return result;
    }

    private static ActionListener getSolverAction(ColorPanel limitation, int[][][] field, int[] tuple, boolean[] used) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (limitation.isNodeOn()) {
                    field[tuple[1]][tuple[2]][tuple[3]] = 0;
                    if (limitedsolve(field, used, tuple)) {
                        System.out.println("SOLVED");
                        for (int s = 0; s < PYRAMID_BASE_SIZE; s++) {
                            for (int t = 0; s + t < PYRAMID_BASE_SIZE; t++) {
                                for (int u = 0; s + u < PYRAMID_BASE_SIZE; u++) {
                                    if (field[s][t][u] < 99 && field[s][t][u] > 0) {
                                        solution[s][t][u].setColor(SHAPES_ON_BOARD[field[s][t][u] - 1].getColor());
                                        solution[s][t][u].setNodeIsOn(true);
                                        solution[s][t][u].repaint();
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("FAIL");
                    }
                } else {
                    if (solve(field, used)) {
                        System.out.println("SOLVED");
                        for (int s = 0; s < PYRAMID_BASE_SIZE; s++) {
                            for (int t = 0; s + t < PYRAMID_BASE_SIZE; t++) {
                                for (int u = 0; s + u < PYRAMID_BASE_SIZE; u++) {
                                    if (field[s][t][u] < 99 && field[s][t][u] > 0) {
                                        solution[s][t][u].setColor(SHAPES_ON_BOARD[field[s][t][u] - 1].getColor());
                                        solution[s][t][u].setNodeIsOn(true);
                                        solution[s][t][u].repaint();
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("FAIL");
                    }
                }
            }
        };
    }

    private static JPanel makeSolveButton(int buttonX, int buttonY, int[][][] field, boolean[] used, ColorPanel[][][] solution,
                                          ColorPanel limitation, int[] tuple) {
        JPanel solveButton = new JPanel();
        ActionListener solverAction = getSolverAction(limitation, field, tuple, used);
        UiUtil.setSolveButtonProperties(BUTTON_SIZE, buttonX, buttonY, solveButton, solverAction);
        return solveButton;
    }

    private static void drawPyramid(int[][][] field, int[] tuple, JFrame frame) {
        for (int s = 0; s < PYRAMID_BASE_SIZE; s++) {
            field[s] = new int[PYRAMID_BASE_SIZE - s][PYRAMID_BASE_SIZE - s];
            solution[s] = new ColorPanel[PYRAMID_BASE_SIZE - s][PYRAMID_BASE_SIZE - s];
            for (int t = 0; s + t < PYRAMID_BASE_SIZE; t++) {
                for (int u = 0; s + u < PYRAMID_BASE_SIZE; u++) {
                    solution[s][t][u] = makeToggleableButton(70 + (s + 2 * u) * 15,
                            40 + ((TUPLE_SIZE - s) * (7 - s) / 2 + t) * BUTTON_SIZE,
                            field, s, t, u, tuple);
                    frame.add(solution[s][t][u]);
                }
            }
        }
    }

    public static void main(String[] args) {
        //1. Create the frame.
        JFrame frame = new JFrame("Pyramid 3D");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        frame.setBackground(ColorPanel.WHITE);
        frame.setLayout(null);

        int[][][] field = new int[PYRAMID_BASE_SIZE][][];
        solution = new ColorPanel[PYRAMID_BASE_SIZE][][];
        int[] tuple = new int[TUPLE_SIZE];
        drawPyramid(field, tuple, frame);

        boolean[] used = new boolean[SHAPES_ON_BOARD.length];
        for (int i = 0; i < SHAPES_ON_BOARD.length; i++) {
            frame.add(UiUtil.makeSelection(BUTTON_SIZE,350, BUTTON_SIZE + i * 40, used, i));
        }

        ColorPanel limitation = toggleButton(275, 550, field, tuple, solution);
        frame.add(limitation);
        frame.add(makeSolveButton(305, 550, field, used, solution, limitation, tuple));

        //4. Size the frame.
        frame.pack();
        frame.setSize(500, 700);

        //5. Show it.
        frame.setVisible(true);
    }
}
