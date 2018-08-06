import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class twoDim {
	private static class colorPanel extends JPanel {
		private boolean on = false;
		private int R = 255, G = 255, B = 255;
		public void reset() {
			on = false;
			R = 255;
			G = 255;
			B = 255;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			Ellipse2D.Double circle = new Ellipse2D.Double(3, 3, getWidth() - 6, getHeight() - 6);
			if (on) {
				g2d.setColor(new Color(R, G, B));
			} else {
				g2d.setColor(new Color(0, 0, 0));
			}
			g2d.fill(circle);
			g2d.setColor(new Color(0, 0, 0));
			g2d.setStroke(new BasicStroke(3));
			g2d.draw(circle);
		}
	}
	
	private static class Shape {
		private int R, G, B;
		private int numOrients;
		private int numBlocks;
		private boolean XYswap;
		//[numBlocks][numBlocks * 2]
		private int[][] layouts = null;
		public Shape(int R, int G, int B, int numOrients, int numBlocks, boolean XYswap, int[][] layouts) {
			this.R = R;
			this.G = G;
			this.B = B;
			this.numOrients = numOrients;
			this.numBlocks = numBlocks;
			this.XYswap = XYswap;
			this.layouts = layouts;
		}
	}
	//..
	//..
	private static final int[][] layouts1 = {
			{0, 0, 1, 0, 0, 1, 1, 1},
			{-1, 0, 0, 0, -1, 1, 0, 1},
			{0, -1, 1, -1, 0, 0, 1, 0},
			{-1, -1, 0, -1, -1, 0, 0, 0}};
	private static final Shape Square = new Shape(120, 240, 120, 1, 4, false, layouts1);
	//...
	//.
	//.
	private static final int[][] layouts2 = {
			{0, 0, 1, 0, 2, 0, 0, 1, 0, 2},
			{-1, 0, 0, 0, 1, 0, -1, 1, -1, 2},
			{-2, 0, -1, 0, 0, 0, -2, 1, -2, 2},
			{0, -1, 1, -1, 2, -1, 0, 0, 0, 1},
			{0, -2, 1, -2, 2, -2, 0, -1, 0, 0}};
	private static final Shape V = new Shape(120, 120, 240, 4, 5, false, layouts2);
	//....
	//.
	private static final int[][] layouts3 = {
			{0, 0, 1, 0, 2, 0, 3, 0, 0, 1},
			{-1, 0, 0, 0, 1, 0, 2, 0, -1, 1},
			{-2, 0, -1, 0, 0, 0, 1, 0, -2, 1},
			{-3, 0, -2, 0, -1, 0, 0, 0, -3, 1},
			{0, -1, 1, -1, 2, -1, 3, -1, 0, 0}};
	private static final Shape LongL = new Shape(40, 40, 240, 4, 5, true, layouts3);
	//...
	//.
	private static final int[][] layouts4 = {
			{0, 0, 1, 0, 2, 0, 0, 1},
			{-1, 0, 0, 0, 1, 0, -1, 1},
			{-2, 0, -1, 0, 0, 0, -2, 1},
			{0, -1, 1, -1, 2, -1, 0, 0}};
	private static final Shape L = new Shape(240, 120, 40, 4, 4, true, layouts4);
	//....
	private static final int[][] layouts5 = {
			{0, 0, 1, 0, 2, 0, 3, 0},
			{-1, 0, 0, 0, 1, 0, 2, 0},
			{-2, 0, -1, 0, 0, 0, 1, 0},
			{-3, 0, -2, 0, -1, 0, 0, 0}};
	private static final Shape I = new Shape(120, 40, 120, 1, 4, true, layouts5);
	//...
	//.x.
	private static final int[][] layouts6 = {
			{0, 0, 1, 0, 2, 0, 0, 1, 2, 1},
			{-1, 0, 0, 0, 1, 0, -1, 1, 1, 1},
			{-2, 0, -1, 0, 0, 0, -2, 1, 0, 1},
			{0, -1, 1, -1, 2, -1, 0, 0, 2, 0},
			{-2, -1, -1, -1, 0, -1, -2, 0, 0, 0}};
	private static final Shape YellowHat = new Shape(200, 200, 40, 4, 5, true, layouts6);
	//x.x
	//...
	//x.x
	private static final int[][] layouts7 = {
			{0, 0, -1, 1, 0, 1, 1, 1, 0, 2},
			{1, -1, 0, 0, 1, 0, 2, 0, 1, 1},
			{0, -1, -1, 0, 0, 0, 1, 0, 0, 1},
			{-1, -1, -2, 0, -1, 0, 0, 0, -1, 1},
			{0, -2, -1, -1, 0, -1, 1, -1, 0, 0}};
	private static final Shape X = new Shape(120, 120, 120, 1, 5, false, layouts7);
	//..
	//.
	private static final int[][] layouts8 = {
			{0, 0, 1, 0, 0, 1},
			{-1, 0, 0, 0, -1, 1},
			{0, -1, 1, -1, 0, 0}};
	private static final Shape Small = new Shape(220, 220, 220, 4, 3, false, layouts8);
	//..
	//x..
	//xx.
	private static final int[][] layouts9 = {
			{0, 0, 1, 0, 1, 1, 2, 1, 2, 2},
			{-1, 0, 0, 0, 0, 1, 1, 1, 1, 2},
			{-1, -1, 0, -1, 0, 0, 0, 1, 1, 1},
			{-2, -1, -1, -1, -1, 0, 0, 0, 0, 1},
			{-2, -2, -1, -2, -1, -1, 0, -1, 0, 0}};
	private static final Shape Wave = new Shape(240, 40, 240, 4, 5, false, layouts9);
	//...
	//..
	private static final int[][] layouts10 = {
			{0, 0, 1, 0, 2, 0, 0, 1, 1, 1},
			{-1, 0, 0, 0, 1, 0, -1, 1, 0, 1},
			{-2, 0, -1, 0, 0, 0, -2, 1, -1, 1},
			{0, -1, 1, -1, 2, -1, 0, 0, 1, 0},
			{-1, -1, 0, -1, 1, -1, -1, 0, 0, 0}};
	private static final Shape Thumb = new Shape(240, 40, 40, 4, 5, true, layouts10);
	//....
	//x.xx
	private static final int[][] layouts11 = {
			{0, 0, 1, 0, 2, 0, 3, 0, 1, 1},
			{-1, 0, 0, 0, 1, 0, 2, 0, 0, 1},
			{-2, 0, -1, 0, 0, 0, 1, 0, -1, 1},
			{-3, 0, -2, 0, -1, 0, 0, 0, -2, 1},
			{-1, -1, 0, -1, 1, -1, 2, -1, 0, 0}};
	private static final Shape Fork = new Shape(240, 200, 120, 4, 5, true, layouts11);
	//..
	//x...
	private static final int[][] layouts12 = {
			{0, 0, 1, 0, 1, 1, 2, 1, 3, 1},
			{-1, 0, 0, 0, 0, 1, 1, 1, 2, 1},
			{-1, -1, 0, -1, 0, 0, 1, 0, 2, 0},
			{-2, -1, -1, -1, -1, 0, 0, 0, 1, 0},
			{-3, -1, -2, -1, -2, 0, -1, 0, 0, 0}};
	private static final Shape Snake = new Shape(40, 120, 40, 4, 5, true, layouts12);

	

	private static final int[][] rotation = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
	private static final Shape[] shapes = {Square, V, LongL, L, I,
			YellowHat, X, Small, Wave, Thumb, Fork, Snake};
	
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
		for (int i = 0; i < shapes.length; i++) {
			if (used[i]) {
				continue;
			}
			for (int orient = 0; orient < shapes[i].numOrients; orient++) {
				for (int center = 0; center < shapes[i].numBlocks; center++) {
					boolean pass = true;
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						if (newX < 0 || newY < 0 || newX + newY >= field.length || field[newX][newY] > 0) {
							pass = false;
							break;
						}
					}
					if (!pass) {
						continue;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						field[newX][newY] = i + 1;
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						field[newX][newY] = 0;
					}
					used[i] = false;
				}
			}
			if (!shapes[i].XYswap) {
				continue;
			}
			for (int orient = 0; orient < shapes[i].numOrients; orient++) {
				for (int center = 0; center < shapes[i].numBlocks; center++) {
					boolean pass = true;
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						if (newX < 0 || newY < 0 || newX + newY >= field.length || field[newX][newY] > 0) {
							pass = false;
							break;
						}
					}
					if (!pass) {
						continue;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						field[newX][newY] = i + 1;
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						field[newX][newY] = 0;
					}
					used[i] = false;
				}
			}
		}
		return false;
	}
	
	private static colorPanel makeButton(int x, int y, int[][] field, int s, int t) {
		colorPanel result = new colorPanel();
		result.setBounds(x, y, 40, 40);
		JButton button = new JButton();
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setPreferredSize(new Dimension(40, 40));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
		    {
				result.on = !result.on;
				result.repaint();
				if (field[s][t] == 0) {
					field[s][t] = 99;
				} else {
					field[s][t] = 0;
				}
		    }
		});
		result.add(button);
		
		return result;
	}
	
	private static colorPanel makeSelection(int x, int y, boolean[] used, int i) {
		colorPanel result = new colorPanel();
		result.on = true;
		result.R = shapes[i].R;
		result.G = shapes[i].G;
		result.B = shapes[i].B;
		result.setBounds(x, y, 40, 40);
		JButton button = new JButton();
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setPreferredSize(new Dimension(40, 40));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
		    {
				result.on = !result.on;
				result.repaint();
				used[i] = !used[i];
		    }
		});
		result.add(button);

		return result;
	}
	
	private static JPanel solveButton(int x, int y, int[][] field, boolean[] used, colorPanel[][] solution) {
		JPanel result = new JPanel();
		result.setBounds(x, y, 120, 40);
		JButton button = new JButton("Solve");
		button.setPreferredSize(new Dimension(120, 40));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
		    {
				if (solve(field, used)) {
					System.out.println("SOLVED");
					for (int s = 0; s < solution.length; s++) {
						for (int t = 0; s + t < solution.length; t++) {
							if (field[s][t] < 99 && field[s][t] > 0) {
								solution[s][t].R = shapes[field[s][t] - 1].R;
								solution[s][t].G = shapes[field[s][t] - 1].G;
								solution[s][t].B = shapes[field[s][t] - 1].B;
								solution[s][t].on = true;
								solution[s][t].repaint();
							}
						}
					}
				} else {
					System.out.println("FAIL");
				}
		    }
		});
		result.add(button);

		return result;
	}

	public static void main(String[] args) {
		//1. Create the frame.
		JFrame frame = new JFrame("Pyramid 2D");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//3. Create components and put them in the frame.
		frame.setBackground(new Color(255, 255, 255));
		frame.setLayout(null);
		
		int[][] field = new int[10][];
		colorPanel[][] solution = new colorPanel[10][];
		for (int s = 0; s < 10; s++) {
			field[s] = new int[10 - s];
			solution[s] = new colorPanel[10 - s];
			for (int t = 0; s + t < 10; t++) {
				solution[s][t] = makeButton(420 + (t - s) * 40, 70 + (t + s) * 40, field, s, t);
				frame.add(solution[s][t]);
			}
		}
		boolean[] used = new boolean[shapes.length];
		for (int i = 0; i < shapes.length; i++) {
			frame.add(makeSelection(30 + i * 60, 490, used, i));
		}
		
		frame.add(solveButton(760, 490, field, used, solution));
		
		//4. Size the frame.
		frame.pack();
		frame.setSize(900, 600);

		//5. Show it.
		frame.setVisible(true);
	}

	
}
