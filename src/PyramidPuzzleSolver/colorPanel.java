package PyramidPuzzleSolver;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

/**
 * Acts as a single circle that can be switched on or off on click, to create the game board.
 */
public class colorPanel extends JPanel {
    public boolean on = false;
    public int red = 255, green = 255, blue = 255;
    public void reset() {
        on = false;
        red = 255;
        green = 255;
        blue = 255;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        Ellipse2D.Double circle = new Ellipse2D.Double(3, 3, getWidth() - 6, getHeight() - 6);
        if (on) {
            g2d.setColor(new Color(red, green, blue));
        } else {
            g2d.setColor(new Color(0, 0, 0));
        }
        g2d.fill(circle);
        g2d.setColor(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(circle);
    }
}