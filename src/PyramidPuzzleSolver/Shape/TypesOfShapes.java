package PyramidPuzzleSolver.Shape;

/**
 * Stores common shape information. Each shape is preceeded with a comment that shows how it looks.
 * . Represents filled
 * x Represents Empty
 */
public class TypesOfShapes {
    //..
    //..
    private static final int[][] LAYOUTS_1 = {
            {0, 0, 1, 0, 0, 1, 1, 1},
            {-1, 0, 0, 0, -1, 1, 0, 1},
            {0, -1, 1, -1, 0, 0, 1, 0},
            {-1, -1, 0, -1, -1, 0, 0, 0}};
    private static final ShapeOnBoard SHAPE_SQUARE = new ShapeOnBoard(120, 240, 120, 1, 4, false, LAYOUTS_1);
    //...
    //.
    //.
    private static final int[][] LAYOUTS_2 = {
            {0, 0, 1, 0, 2, 0, 0, 1, 0, 2},
            {-1, 0, 0, 0, 1, 0, -1, 1, -1, 2},
            {-2, 0, -1, 0, 0, 0, -2, 1, -2, 2},
            {0, -1, 1, -1, 2, -1, 0, 0, 0, 1},
            {0, -2, 1, -2, 2, -2, 0, -1, 0, 0}};
    private static final ShapeOnBoard SHAPE_V = new ShapeOnBoard(120, 120, 240, 4, 5, false, LAYOUTS_2);
    //....
    //.
    private static final int[][] LAYOUTS_3 = {
            {0, 0, 1, 0, 2, 0, 3, 0, 0, 1},
            {-1, 0, 0, 0, 1, 0, 2, 0, -1, 1},
            {-2, 0, -1, 0, 0, 0, 1, 0, -2, 1},
            {-3, 0, -2, 0, -1, 0, 0, 0, -3, 1},
            {0, -1, 1, -1, 2, -1, 3, -1, 0, 0}};
    private static final ShapeOnBoard SHAPE_LONG_L = new ShapeOnBoard(40, 40, 240, 4, 5, true, LAYOUTS_3);
    //...
    //.
    private static final int[][] LAYOUTS_4 = {
            {0, 0, 1, 0, 2, 0, 0, 1},
            {-1, 0, 0, 0, 1, 0, -1, 1},
            {-2, 0, -1, 0, 0, 0, -2, 1},
            {0, -1, 1, -1, 2, -1, 0, 0}};
    private static final ShapeOnBoard SHAPE_L = new ShapeOnBoard(240, 120, 40, 4, 4, true, LAYOUTS_4);
    //....
    private static final int[][] LAYOUTS_5 = {
            {0, 0, 1, 0, 2, 0, 3, 0},
            {-1, 0, 0, 0, 1, 0, 2, 0},
            {-2, 0, -1, 0, 0, 0, 1, 0},
            {-3, 0, -2, 0, -1, 0, 0, 0}};
    private static final ShapeOnBoard SHAPE_I = new ShapeOnBoard(120, 40, 120, 1, 4, true, LAYOUTS_5);
    //...
    //.x.
    private static final int[][] LAYOUTS_6 = {
            {0, 0, 1, 0, 2, 0, 0, 1, 2, 1},
            {-1, 0, 0, 0, 1, 0, -1, 1, 1, 1},
            {-2, 0, -1, 0, 0, 0, -2, 1, 0, 1},
            {0, -1, 1, -1, 2, -1, 0, 0, 2, 0},
            {-2, -1, -1, -1, 0, -1, -2, 0, 0, 0}};
    private static final ShapeOnBoard SHAPE_YELLOW_HAT = new ShapeOnBoard(200, 200, 40, 4, 5, true, LAYOUTS_6);
    //x.x
    //...
    //x.x
    private static final int[][] LAYOUTS_7 = {
            {0, 0, -1, 1, 0, 1, 1, 1, 0, 2},
            {1, -1, 0, 0, 1, 0, 2, 0, 1, 1},
            {0, -1, -1, 0, 0, 0, 1, 0, 0, 1},
            {-1, -1, -2, 0, -1, 0, 0, 0, -1, 1},
            {0, -2, -1, -1, 0, -1, 1, -1, 0, 0}};
    private static final ShapeOnBoard SHAPE_X = new ShapeOnBoard(120, 120, 120, 1, 5, false, LAYOUTS_7);
    //..
    //.
    private static final int[][] LAYOUTS_8 = {
            {0, 0, 1, 0, 0, 1},
            {-1, 0, 0, 0, -1, 1},
            {0, -1, 1, -1, 0, 0}};
    private static final ShapeOnBoard SHAPE_SMALL = new ShapeOnBoard(220, 220, 220, 4, 3, false, LAYOUTS_8);
    //..
    //x..
    //xx.
    private static final int[][] LAYOUTS_9 = {
            {0, 0, 1, 0, 1, 1, 2, 1, 2, 2},
            {-1, 0, 0, 0, 0, 1, 1, 1, 1, 2},
            {-1, -1, 0, -1, 0, 0, 0, 1, 1, 1},
            {-2, -1, -1, -1, -1, 0, 0, 0, 0, 1},
            {-2, -2, -1, -2, -1, -1, 0, -1, 0, 0}};
    private static final ShapeOnBoard SHAPE_WAVE = new ShapeOnBoard(240, 40, 240, 4, 5, false, LAYOUTS_9);
    //...
    //..
    private static final int[][] LAYOUTS_10 = {
            {0, 0, 1, 0, 2, 0, 0, 1, 1, 1},
            {-1, 0, 0, 0, 1, 0, -1, 1, 0, 1},
            {-2, 0, -1, 0, 0, 0, -2, 1, -1, 1},
            {0, -1, 1, -1, 2, -1, 0, 0, 1, 0},
            {-1, -1, 0, -1, 1, -1, -1, 0, 0, 0}};
    private static final ShapeOnBoard SHAPE_THUMB = new ShapeOnBoard(240, 40, 40, 4, 5, true, LAYOUTS_10);
    //....
    //x.xx
    private static final int[][] LAYOUTS_11 = {
            {0, 0, 1, 0, 2, 0, 3, 0, 1, 1},
            {-1, 0, 0, 0, 1, 0, 2, 0, 0, 1},
            {-2, 0, -1, 0, 0, 0, 1, 0, -1, 1},
            {-3, 0, -2, 0, -1, 0, 0, 0, -2, 1},
            {-1, -1, 0, -1, 1, -1, 2, -1, 0, 0}};
    private static final ShapeOnBoard SHAPE_FORK = new ShapeOnBoard(240, 200, 120, 4, 5, true, LAYOUTS_11);
    //..
    //x...
    private static final int[][] LAYOUTS_12 = {
            {0, 0, 1, 0, 1, 1, 2, 1, 3, 1},
            {-1, 0, 0, 0, 0, 1, 1, 1, 2, 1},
            {-1, -1, 0, -1, 0, 0, 1, 0, 2, 0},
            {-2, -1, -1, -1, -1, 0, 0, 0, 1, 0},
            {-3, -1, -2, -1, -2, 0, -1, 0, 0, 0}};
    private static final ShapeOnBoard SHAPE_SNAKE = new ShapeOnBoard(40, 120, 40, 4, 5, true, LAYOUTS_12);

    public static final int[][] ROTATION = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    public static final ShapeOnBoard[] SHAPES_ON_BOARD = {SHAPE_SQUARE, SHAPE_V, SHAPE_LONG_L, SHAPE_L, SHAPE_I,
            SHAPE_YELLOW_HAT, SHAPE_X, SHAPE_SMALL, SHAPE_WAVE, SHAPE_THUMB, SHAPE_FORK, SHAPE_SNAKE};
}
