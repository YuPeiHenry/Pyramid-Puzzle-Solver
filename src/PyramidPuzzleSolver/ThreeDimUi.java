package PyramidPuzzleSolver;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;

import PyramidPuzzleSolver.Shape.UiUtil;

/**
 * Draws the UI for the 3D puzzle.
 */
public class ThreeDimUi {
    public static final int PYRAMID_BASE_SIZE = 5;
    private static final int BUTTON_SIZE = 30;
    private static final int TUPLE_SIZE = 4;
    private static final int INDEX_COLOR = 0;
    private static final int INDEX_X = 1;
    private static final int INDEX_Y = 2;
    private static final int INDEX_Z = 3;

    private static ColorPanel[][][] solution;


    private static ActionListener getToggleAction(ColorPanel toggleableButton, int[][][] field, int[] tuple,
                                                  int x, int y, int z) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColorPanel panel = solution[tuple[INDEX_X]][tuple[INDEX_Y]][tuple[INDEX_Z]];
                if (panel != toggleableButton) {
                    toggleableButton.setColor(panel.getColor());
                    panel.setColor(ColorPanel.WHITE);
                    panel.repaint();
                    tuple[INDEX_X] = x;
                    tuple[INDEX_Y] = y;
                    tuple[INDEX_Z] = z;
                }
                toggleableButton.toggleNode();
                toggleableButton.repaint();
                if (field[x][y][z] == 0) {
                    field[x][y][z] = 99;
                } else {
                    field[x][y][z] = 0;
                }
            }
        };
    }
    private static ColorPanel makeToggleableButton(int buttonX, int buttonY, int[][][] field, int x, int y, int z, int[] tuple) {
        ColorPanel toggleableButton = new ColorPanel();
        ActionListener toggleAction = getToggleAction(toggleableButton, field, tuple, x, y, z);
        UiUtil.setToggleableButtonProperties(BUTTON_SIZE, buttonX, buttonY, toggleableButton, toggleAction);
        return toggleableButton;
    }

    private static ColorPanel makeConstraintColorChoosingButton(int buttonX, int buttonY, int[] tuple,
                                                                ColorPanel[][][] solution) {
        ColorPanel result = new ColorPanel();
        result.setNodeIsOn(false);
        result.setColor(ColorPanel.WHITE);
        result.setBounds(buttonX, buttonY, BUTTON_SIZE, BUTTON_SIZE);
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColorPanel panel = solution[tuple[INDEX_X]][tuple[INDEX_Y]][tuple[INDEX_Z]];
                tuple[INDEX_COLOR]++;
                if (tuple[INDEX_COLOR] > SHAPES_ON_BOARD.length) {
                    tuple[INDEX_COLOR] = 0;
                    result.setNodeIsOn(false);
                    panel.setColor(ColorPanel.WHITE);
                } else {
                    result.setNodeIsOn(true);
                    result.setColor(SHAPES_ON_BOARD[tuple[INDEX_COLOR] - 1].getColor());
                    panel.setColor(SHAPES_ON_BOARD[tuple[INDEX_COLOR] - 1].getColor());
                }
                result.repaint();
                panel.repaint();
            }
        });
        result.add(button);

        return result;
    }

    private static void tryToSolveWithConstraint(int[][][] field, int[] tuple, boolean[] used) {
        field[tuple[INDEX_X]][tuple[INDEX_Y]][tuple[INDEX_Z]] = 0;
        if (ThreeDimSolver.pieceConstraintSolve(field, used, tuple[INDEX_COLOR], tuple[INDEX_X], tuple[INDEX_Y], tuple[INDEX_Z])) {
            showSolveSuccess(field);
        } else {
            showSolveFailure();
        }
    }

    private static void tryToSolveWithoutConstraint(int[][][] field, boolean[] used) {
        if (ThreeDimSolver.solve(field, used)) {
            showSolveSuccess(field);
        } else {
            showSolveFailure();
        }
    }

    private static void showSolveSuccess(int[][][] field) {
        System.out.println("SOLVED");
        for (int x = 0; x < PYRAMID_BASE_SIZE; x++) {
            for (int y = 0; x + y < PYRAMID_BASE_SIZE; y++) {
                for (int z = 0; x + z < PYRAMID_BASE_SIZE; z++) {
                    if (field[x][y][z] < 99 && field[x][y][z] > 0) {
                        solution[x][y][z].setColor(SHAPES_ON_BOARD[field[x][y][z] - 1].getColor());
                        solution[x][y][z].setNodeIsOn(true);
                        solution[x][y][z].repaint();
                    }
                }
            }
        }
    }

    private static void showSolveFailure() {
        System.out.println("FAIL");
    }

    private static ActionListener getSolverAction(ColorPanel limitation, int[][][] field, int[] tuple, boolean[] used) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (limitation.isNodeOn()) {
                    tryToSolveWithConstraint(field, tuple, used);
                } else {
                    tryToSolveWithoutConstraint(field, used);
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
        for (int x = 0; x < PYRAMID_BASE_SIZE; x++) {
            field[x] = new int[PYRAMID_BASE_SIZE - x][PYRAMID_BASE_SIZE - x];
            solution[x] = new ColorPanel[PYRAMID_BASE_SIZE - x][PYRAMID_BASE_SIZE - x];
            for (int y = 0; x + y < PYRAMID_BASE_SIZE; y++) {
                for (int z = 0; x + z < PYRAMID_BASE_SIZE; z++) {
                    solution[x][y][z] = makeToggleableButton(70 + (x + 2 * z) * 15,
                            40 + ((TUPLE_SIZE - x) * (7 - x) / 2 + y) * BUTTON_SIZE,
                            field, x, y, z, tuple);
                    frame.add(solution[x][y][z]);
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
            frame.add(UiUtil.makeSelectionButton(BUTTON_SIZE,350, BUTTON_SIZE + i * 40, used, i));
        }

        ColorPanel limitation = makeConstraintColorChoosingButton(275, 550, tuple, solution);
        frame.add(limitation);
        frame.add(makeSolveButton(305, 550, field, used, solution, limitation, tuple));

        //4. Size the frame.
        frame.pack();
        frame.setSize(500, 700);

        //5. Show it.
        frame.setVisible(true);
    }
}
