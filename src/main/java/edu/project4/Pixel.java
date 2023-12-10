package edu.project4;

public class Pixel {
    private static final int BYTE_SIZE = 8;

    private int red;
    private int green;
    private int blue;
    private int hitCount;

    public Pixel(int red, int green, int blue, int hitCount) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.hitCount = hitCount;
    }

    public static Pixel create(int red, int green, int blue) {
        return new Pixel(red, green, blue, 0);
    }

    public synchronized void mix(int mixRed, int mixGreen, int mixBlue) {
        if (hitCount == 0) {
            setRGB(mixRed, mixGreen, mixBlue);
        } else {
            setRGB((red + mixRed) / 2, (green + mixGreen) / 2, (blue + mixBlue) / 2);
        }
        ++hitCount;
    }

    public synchronized void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public synchronized int getHitCount() {
        return hitCount;
    }

    public synchronized int toRGB() {
        return ((red << (2 * BYTE_SIZE)) | (green << BYTE_SIZE) | blue);
    }

    public int red() {
        return red;
    }

    public int green() {
        return green;
    }

    public int blue() {
        return blue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o.getClass() != Pixel.class) {
            return false;
        }

        Pixel other = (Pixel) o;
        return red == other.red && blue == other.blue && green == other.green && other.hitCount == hitCount;
    }

    @Override
    public int hashCode() {
        return toRGB() + hitCount;
    }
}
