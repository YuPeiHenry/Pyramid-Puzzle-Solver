package PyramidPuzzleSolver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;
import static PyramidPuzzleSolver.Shape.TypesOfShapes.ROTATION;

public class threeDim {
    private static colorPanel[][][] solution;

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
        int slicex = (x + y <= 4 - z) ? x : x - y;
        int slicey = (x + y <= 4 - z) ? y : y - x;
        int slicez = x + y + z;
        int Islicex = (x + (4 - y) <= 4) ? (4 - y) - z : (4 - y) - z - x;
        int Islicey = (x + (4 - y) <= 4) ? x : x - (4 - y);
        int Islicez = x + (4 - y);
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
        int slicex = (x + y <= 4 - z) ? x : x - y;
        int slicey = (x + y <= 4 - z) ? y : y - x;
        int slicez = x + y + z;
        int Islicex = (x + (4 - y) <= 4) ? (4 - y) - z : (4 - y) - z - x;
        int Islicey = (x + (4 - y) <= 4) ? x : x - (4 - y);
        int Islicez = x + (4 - y);
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
            return field[8 - i - j - k][i - 4 + j][i - 4 + k];
        }
    }

    private static void setslice(int[][][] field, int i, int j, int k, int x) {
        if (i < field.length) {
            field[i - j - k][j][k] = x;
        } else {
            field[8 - i - j - k][i - 4 + j][i - 4 + k] = x;
        }
    }

    private static int getinverseslice(int[][][] field, int i, int j, int k) {
        if (i < field.length) {
            int z = i - j - k;
            return field[z][k][4 - z - j];
        } else if (i + j + k > 8) {
            return 99;
        } else {
            int z = 8 - i - j - k;
            return field[z][i - 4 + k][k];
        }
    }

    private static void setinverseslice(int[][][] field, int i, int j, int k, int x) {
        if (i < field.length) {
            int z = i - j - k;
            field[z][k][4 - z - j] = x;
        } else {
            int z = 8 - i - j - k;
            field[z][i - 4 + k][k] = x;
        }
    }

    private static colorPanel makeButton(int x, int y, int[][][] field, int s, int t, int u, int[] tuple) {
        colorPanel result = new colorPanel();
        result.setBounds(x, y, 30, 30);
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(30, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorPanel panel = solution[tuple[1]][tuple[2]][tuple[3]];
                if (panel != result) {
                    result.setColor(panel.getColor());
                    panel.setColor(colorPanel.WHITE);
                    panel.repaint();
                    tuple[1] = s;
                    tuple[2] = t;
                    tuple[3] = u;
                }
                result.toggleNode();
                result.repaint();
                if (field[s][t][u] == 0) {
                    field[s][t][u] = 99;
                } else {
                    field[s][t][u] = 0;
                }
            }
        });
        result.add(button);

        return result;
    }

    private static colorPanel makeSelection(int x, int y, boolean[] used, int i) {
        colorPanel result = new colorPanel();
        result.setNodeIsOn(true);
        result.setColor(SHAPES_ON_BOARD[i].getColor());
        result.setBounds(x, y, 30, 30);
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(30, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.toggleNode();
                result.repaint();
                used[i] = !used[i];
            }
        });
        result.add(button);

        return result;
    }

    private static colorPanel toggleButton(int x, int y, int[][][] field, int[] tuple, colorPanel[][][] solution) {
        colorPanel result = new colorPanel();
        result.setNodeIsOn(false);
        result.setColor(colorPanel.WHITE);
        result.setBounds(x, y, 30, 30);
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(30, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorPanel panel = solution[tuple[1]][tuple[2]][tuple[3]];
                tuple[0]++;
                if (tuple[0] > SHAPES_ON_BOARD.length) {
                    tuple[0] = 0;
                    result.setNodeIsOn(false);
                    panel.setColor(colorPanel.WHITE);
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

    private static JPanel solveButton(int x, int y, int[][][] field, boolean[] used, colorPanel[][][] solution,
                                      colorPanel limitation, int[] tuple) {
        JPanel result = new JPanel();
        result.setBounds(x, y, 120, 30);
        JButton button = new JButton("Solve");
        button.setPreferredSize(new Dimension(120, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (limitation.isNodeOn()) {
                    field[tuple[1]][tuple[2]][tuple[3]] = 0;
                    if (limitedsolve(field, used, tuple)) {
                        System.out.println("SOLVED");
                        for (int s = 0; s < 5; s++) {
                            for (int t = 0; s + t < 5; t++) {
                                for (int u = 0; s + u < 5; u++) {
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
                        for (int s = 0; s < 5; s++) {
                            for (int t = 0; s + t < 5; t++) {
                                for (int u = 0; s + u < 5; u++) {
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
        });
        result.add(button);

        return result;
    }

    public static void main(String[] args) {
        //1. Create the frame.
        JFrame frame = new JFrame("Pyramid 3D");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        frame.setBackground(new Color(255, 255, 255));
        frame.setLayout(null);

        int[][][] field = new int[5][][];
        solution = new colorPanel[5][][];
        int[] tuple = new int[4];
        for (int s = 0; s < 5; s++) {
            field[s] = new int[5 - s][5 - s];
            solution[s] = new colorPanel[5 - s][5 - s];
            for (int t = 0; s + t < 5; t++) {
                for (int u = 0; s + u < 5; u++) {
                    solution[s][t][u] = makeButton(70 + (s + 2 * u) * 15,
                            40 + ((4 - s) * (7 - s) / 2 + t) * 30,
                            field, s, t, u, tuple);
                    frame.add(solution[s][t][u]);
                }
            }
        }
        boolean[] used = new boolean[SHAPES_ON_BOARD.length];
        for (int i = 0; i < SHAPES_ON_BOARD.length; i++) {
            frame.add(makeSelection(350, 30 + i * 40, used, i));
        }

        colorPanel limitation = toggleButton(275, 550, field, tuple, solution);
        frame.add(limitation);
        frame.add(solveButton(305, 550, field, used, solution, limitation, tuple));

        //4. Size the frame.
        frame.pack();
        frame.setSize(500, 700);

        //5. Show it.
        frame.setVisible(true);
    }
}
