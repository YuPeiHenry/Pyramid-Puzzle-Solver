package PyramidPuzzleSolver;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;

import PyramidPuzzleSolver.Shape.UiUtil;

public class ThreeDimUi {
    private static final int BUTTON_SIZE = 30;
    private static final int PYRAMID_BASE_SIZE = 5;
    public static final int TUPLE_SIZE = 4;

    private static ColorPanel[][][] solution;


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

    private static void tryToSolveWithConstraint(int[][][] field, int[] tuple, boolean[] used) {
        field[tuple[1]][tuple[2]][tuple[3]] = 0;
        if (ThreeDimSolver.limitedsolve(field, used, tuple)) {
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
