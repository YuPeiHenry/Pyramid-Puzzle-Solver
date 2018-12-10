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

public class TwoDim {
    private static final int BUTTON_SIZE = 40;
    private static final int TRIANGLE_BOARD_WIDTH = 10;

    //i = 0 ...
    //i = 1 ..
    //i = 2 .
    private static boolean solve(int[][] field, boolean[] used) {
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

    private static ActionListener getToggleAction(ColorPanel toggleableButton, int[][] field, int s, int t) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleableButton.toggleNode();
                toggleableButton.repaint();
                if (field[s][t] == 0) {
                    field[s][t] = 99;
                } else {
                    field[s][t] = 0;
                }
            }
        };
    }

    private static ColorPanel makeToggleableButton(int buttonX, int buttonY, int[][] field, int s, int t) {
        ColorPanel toggleableButton = new ColorPanel();
        ActionListener toggleAction = getToggleAction(toggleableButton, field, s, t);
        UiUtil.setToggleableButtonProperties(BUTTON_SIZE, buttonX, buttonY, toggleableButton, toggleAction);
        return toggleableButton;
    }

    private static ActionListener getSolverAction(ColorPanel[][] solution, int[][] field, boolean[] used) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solve(field, used)) {
                    System.out.println("SOLVED");
                    for (int s = 0; s < solution.length; s++) {
                        for (int t = 0; s + t < solution.length; t++) {
                            if (field[s][t] < 99 && field[s][t] > 0) {
                                solution[s][t].setColor(SHAPES_ON_BOARD[field[s][t] - 1].getColor());
                                solution[s][t].setNodeIsOn(true);
                                solution[s][t].repaint();
                            }
                        }
                    }
                } else {
                    System.out.println("FAIL");
                }
            }
        };
    }

    private static JPanel makeSolveButton(int buttonX, int buttonY, int[][] field, boolean[] used, ColorPanel[][] solution) {
        JPanel solveButton = new JPanel();
        ActionListener solverAction = getSolverAction(solution, field, used);
        UiUtil.setSolveButtonProperties(BUTTON_SIZE, buttonX, buttonY, solveButton, solverAction);
        return solveButton;
    }

    public static void main(String[] args) {
        //1. Create the frame.
        JFrame frame = new JFrame("Pyramid 2D");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        frame.setBackground(ColorPanel.WHITE);
        frame.setLayout(null);

        int[][] field = new int[TRIANGLE_BOARD_WIDTH][];
        ColorPanel[][] solution = new ColorPanel[TRIANGLE_BOARD_WIDTH][];
        for (int s = 0; s < TRIANGLE_BOARD_WIDTH; s++) {
            field[s] = new int[TRIANGLE_BOARD_WIDTH - s];
            solution[s] = new ColorPanel[TRIANGLE_BOARD_WIDTH - s];
            for (int t = 0; s + t < TRIANGLE_BOARD_WIDTH; t++) {
                solution[s][t] = makeToggleableButton(420 + (t - s) * BUTTON_SIZE, 70 + (t + s) * BUTTON_SIZE, field, s, t);
                frame.add(solution[s][t]);
            }
        }
        boolean[] used = new boolean[SHAPES_ON_BOARD.length];
        for (int i = 0; i < SHAPES_ON_BOARD.length; i++) {
            frame.add(UiUtil.makeSelection(BUTTON_SIZE,30 + i * 60, 490, used, i));
        }

        frame.add(makeSolveButton(760, 490, field, used, solution));

        //4. Size the frame.
        frame.pack();
        frame.setSize(900, 600);

        //5. Show it.
        frame.setVisible(true);
    }
}
