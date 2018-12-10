package PyramidPuzzleSolver.Shape;

import static PyramidPuzzleSolver.Shape.TypesOfShapes.SHAPES_ON_BOARD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import PyramidPuzzleSolver.ColorPanel;

/**
 * Makes a circular button for users to indicate which pieces have they used.
 */
public class UiUtil {

    public static ColorPanel makeSelection(int buttonSize, int buttonX, int buttonY, boolean[] used, int i) {
        ColorPanel result = new ColorPanel();
        result.setNodeIsOn(true);
        result.setColor(SHAPES_ON_BOARD[i].getColor());
        result.setBounds(buttonX, buttonY, buttonSize, buttonSize);
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
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

    public static void setToggleableButtonProperties(int buttonSize, int buttonX, int buttonY,
                                                     ColorPanel toggleableButton, ActionListener toggleAction) {
        JButton button = new JButton();
        toggleableButton.setBounds(buttonX, buttonY, buttonSize, buttonSize);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
        button.addActionListener(toggleAction);

        toggleableButton.add(button);
    }

    public static void setSolveButtonProperties(int buttonSize, int buttonX, int buttonY, JPanel solveButton,
                                                ActionListener solverAction) {
        solveButton.setBounds(buttonX, buttonY, 120, buttonSize);
        JButton button = new JButton("Solve");
        button.setPreferredSize(new Dimension(120, buttonSize));
        button.addActionListener(solverAction);
        solveButton.add(button);
    }
}
