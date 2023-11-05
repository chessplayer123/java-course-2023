package edu.project2.Renderers;

public class Border {
    // For better view in IDEA: File -> Settings -> Console Font -> Line height = 1.0
    private static final int EMPTY = 0b1111;
    private static final int LEFT = 0b1110;
    private static final int RIGHT = 0b1101;
    private static final int UP = 0b1011;
    private static final int BOTTOM = 0b0111;

    private int mask;

    private Border(int mask) {
        this.mask = mask;
    }

    public static Border empty() {
        return new Border(EMPTY);
    }

    public static Border vert() {
        return new Border(UP & BOTTOM);
    }

    public static Border hor() {
        return new Border(LEFT & RIGHT);
    }

    public Border toLeft() {
        mask &= LEFT;
        return this;
    }

    public Border toLeftOn(boolean condition) {
        if (condition) {
            mask &= LEFT;
        }
        return this;
    }

    public Border toRight() {
        mask &= RIGHT;
        return this;
    }

    public Border toRightOn(boolean condition) {
        if (condition) {
            mask &= RIGHT;
        }
        return this;
    }

    public Border toUp() {
        mask &= UP;
        return this;
    }

    public Border toUpOn(boolean condition) {
        if (condition) {
            mask &= UP;
        }
        return this;
    }

    public Border toBot() {
        mask &= BOTTOM;
        return this;
    }

    public Border toBotOn(boolean condition) {
        if (condition) {
            mask &= BOTTOM;
        }
        return this;
    }

    @Override
    public String toString() {
        return switch (mask) {
            case BOTTOM & LEFT              -> "┐";
            case BOTTOM & RIGHT             -> "┌";
            case UP & LEFT                  -> "┘";
            case UP & RIGHT                 -> "└";
            case UP & RIGHT & LEFT & BOTTOM -> "┼";
            case UP & BOTTOM                -> "│";
            case UP & BOTTOM & LEFT         -> "┤";
            case UP & BOTTOM & RIGHT        -> "├";
            case LEFT & RIGHT               -> "─";
            case LEFT & RIGHT & BOTTOM      -> "┬";
            case LEFT & RIGHT & UP          -> "┴";
            case RIGHT                      -> "╶";
            case LEFT                       -> "╴";
            case UP                         -> "╵";
            case BOTTOM                     -> "╷";
            default                         -> " ";
        };
    }
}
