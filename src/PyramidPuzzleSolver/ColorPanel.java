package PyramidPuzzleSolver;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

/**
 * Acts as a single circle that can be switched isOn or off isOn click, to create the game board.
 */
public class ColorPanel extends JPanel {
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);

    private boolean isOn = false;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean isNodeOn() {
        return isOn;
    }

    public void setNodeIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void toggleNode() {
        isOn = !isOn;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        Ellipse2D.Double circle = new Ellipse2D.Double(3, 3, getWidth() - 6, getHeight() - 6);
        if (isOn) {
            g2d.setColor(color);
        } else {
            g2d.setColor(BLACK);
        }
        g2d.fill(circle);
        g2d.setColor(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(circle);
    }

    public void reset() {
        isOn = false;
        color = WHITE;
    }
}