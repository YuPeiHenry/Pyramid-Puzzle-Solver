import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class threeDim {
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
			Ellipse2D.Double circle = new Ellipse2D.Double(2, 2, getWidth() - 4, getHeight() - 4);
			if (on) {
				g2d.setColor(new Color(R, G, B));
			} else {
				g2d.setColor(new Color(0, 0, 0));
			}
			g2d.fill(circle);
			g2d.setColor(new Color(0, 0, 0));
			g2d.setStroke(new BasicStroke(2));
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
		for (int orient = 0; orient < shapes[i].numOrients; orient++) {
			for (int center = 0; center < shapes[i].numBlocks; center++) {
				boolean pass = true;
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
					field[z][newX][newY] = i + 1;
				}
				used[i] = true;
				if (solve(field, used)) {
					return true;
				}
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					field[z][newX][newY] = 0;
				}
				used[i] = false;
			}
		}
		if (!shapes[i].XYswap) {
			return false;
		}
		for (int orient = 0; orient < shapes[i].numOrients; orient++) {
			for (int center = 0; center < shapes[i].numBlocks; center++) {
				boolean pass = true;
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
					field[z][newX][newY] = i + 1;
				}
				used[i] = true;
				if (solve(field, used)) {
					return true;
				}
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
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
		for (int orient = 0; orient < shapes[i].numOrients; orient++) {
			for (int center = 0; center < shapes[i].numBlocks; center++) {
				boolean pass = true;
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
					setslice(field, z, newX, newY, i + 1);
				}
				used[i] = true;
				if (solve(field, used)) {
					return true;
				}
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					setslice(field, z, newX, newY, 0);
				}
				used[i] = false;
			}
		}
		if (!shapes[i].XYswap) {
			return false;
		}
		for (int orient = 0; orient < shapes[i].numOrients; orient++) {
			for (int center = 0; center < shapes[i].numBlocks; center++) {
				boolean pass = true;
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
					setslice(field, z, newX, newY, i + 1);
				}
				used[i] = true;
				if (solve(field, used)) {
					return true;
				}
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
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
		for (int orient = 0; orient < shapes[i].numOrients; orient++) {
			for (int center = 0; center < shapes[i].numBlocks; center++) {
				boolean pass = true;
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
					setinverseslice(field, z, newX, newY, i + 1);
				}
				used[i] = true;
				if (solve(field, used)) {
					return true;
				}
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					setinverseslice(field, z, newX, newY, 0);
				}
				used[i] = false;
			}
		}
		if (!shapes[i].XYswap) {
			return false;
		}
		for (int orient = 0; orient < shapes[i].numOrients; orient++) {
			for (int center = 0; center < shapes[i].numBlocks; center++) {
				boolean pass = true;
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
					if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
					setinverseslice(field, z, newX, newY, i + 1);
				}
				used[i] = true;
				if (solve(field, used)) {
					return true;
				}
				for (int block = 0; block < shapes[i].numBlocks; block++) {
					int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
					int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
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
						if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
						field[z][newX][newY] = i + 1;
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						field[z][newX][newY] = 0;
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
						if (newX < 0 || newY < 0 || newX + z >= field.length || newY + z >= field.length || field[z][newX][newY] > 0) {
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
						field[z][newX][newY] = i + 1;
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						field[z][newX][newY] = 0;
					}
					used[i] = false;
				}
			}
		}
		return false;
	}
	
	private static boolean solveslice(int[][][] field, boolean[] used, int x, int y, int z) {
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
						if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
						setslice(field, z, newX, newY, i + 1);
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						setslice(field, z, newX, newY, 0);
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
						if (newX < 0 || newY < 0 || newX + newY > z || getslice(field, z, newX, newY) > 0) {
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
						setslice(field, z, newX, newY, i + 1);
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						setslice(field, z, newX, newY, 0);
					}
					used[i] = false;
				}
			}
		}
		return false;
	}
	
	private static boolean solveinverseslice(int[][][] field, boolean[] used, int x, int y, int z) {
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
						if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
						setinverseslice(field, z, newX, newY, i + 1);
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newX = x + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newY = y + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						setinverseslice(field, z, newX, newY, 0);
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
						if (newX < 0 || newY < 0 || newX + newY > z || getinverseslice(field, z, newX, newY) > 0) {
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
						setinverseslice(field, z, newX, newY, i + 1);
					}
					used[i] = true;
					if (solve(field, used)) {
						return true;
					}
					for (int block = 0; block < shapes[i].numBlocks; block++) {
						int newY = y + rotation[orient][0] * shapes[i].layouts[center][block * 2];
						int newX = x + rotation[orient][1] * shapes[i].layouts[center][block * 2 + 1];
						setinverseslice(field, z, newX, newY, 0);
					}
					used[i] = false;
				}
			}
		}
		return false;
	}
	
	private static int getslice(int[][][]field, int i, int j, int k) {
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
	
	private static int getinverseslice(int[][][]field, int i, int j, int k) {
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
			public void actionPerformed(ActionEvent e)
		    {
				colorPanel panel = solution[tuple[1]][tuple[2]][tuple[3]];
				if (panel != result) {
					result.R = panel.R;
					result.G = panel.G;
					result.B = panel.B;
					panel.R = 255;
					panel.G = 255;
					panel.B = 255;
					panel.repaint();
					tuple[1] = s;
					tuple[2] = t;
					tuple[3] = u;
				}
				result.on = !result.on;
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
		result.on = true;
		result.R = shapes[i].R;
		result.G = shapes[i].G;
		result.B = shapes[i].B;
		result.setBounds(x, y, 30, 30);
		JButton button = new JButton();
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setPreferredSize(new Dimension(30, 30));
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
	
	private static colorPanel toggleButton(int x, int y, int[][][] field, int[] tuple, colorPanel[][][] solution) {
		colorPanel result = new colorPanel();
		result.on = false;
		result.R = 255;
		result.G = 255;
		result.B = 255;
		result.setBounds(x, y, 30, 30);
		JButton button = new JButton();
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setPreferredSize(new Dimension(30, 30));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
		    {
				colorPanel panel = solution[tuple[1]][tuple[2]][tuple[3]];
				tuple[0]++;
				if (tuple[0] > shapes.length) {
					tuple[0] = 0;
					result.on = false;
					panel.R = 255;
					panel.G = 255;
					panel.B = 255;
				} else {
					result.on = true;
					result.R = shapes[tuple[0] - 1].R;
					result.G = shapes[tuple[0] - 1].G;
					result.B = shapes[tuple[0] - 1].B;
					panel.R = shapes[tuple[0] - 1].R;
					panel.G = shapes[tuple[0] - 1].G;
					panel.B = shapes[tuple[0] - 1].B;
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
			public void actionPerformed(ActionEvent e)
		    {
				if (limitation.on) {
					field[tuple[1]][tuple[2]][tuple[3]] = 0;
					if (limitedsolve(field, used, tuple)) {
						System.out.println("SOLVED");
						for (int s = 0; s < 5; s++) {
							for (int t = 0; s + t < 5; t++) {
								for (int u = 0; s + u < 5; u++) {
									if (field[s][t][u] < 99 && field[s][t][u] > 0) {
										solution[s][t][u].R = shapes[field[s][t][u] - 1].R;
										solution[s][t][u].G = shapes[field[s][t][u] - 1].G;
										solution[s][t][u].B = shapes[field[s][t][u] - 1].B;
										solution[s][t][u].on = true;
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
										solution[s][t][u].R = shapes[field[s][t][u] - 1].R;
										solution[s][t][u].G = shapes[field[s][t][u] - 1].G;
										solution[s][t][u].B = shapes[field[s][t][u] - 1].B;
										solution[s][t][u].on = true;
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
		boolean[] used = new boolean[shapes.length];
		for (int i = 0; i < shapes.length; i++) {
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
