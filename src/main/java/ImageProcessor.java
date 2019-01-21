import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by lukas on 21.1.2019.
 */

public class ImageProcessor {

    private Robot robot;

    public ImageProcessor() {
        try {
            robot = new Robot();
        } catch (java.awt.AWTException e) {
            System.out.println("Error occured while creating Robot in ImageProcessor! Error message: " + e);
        }

    }

    public int[] getRGB(BufferedImage screenshot, int x, int y) {
        int[] rgb = new int[3];
        int colour = screenshot.getRGB(x, y);
        rgb[0] = (colour & 0x00ff0000) >> 16;
        rgb[1] = (colour & 0x0000ff00) >> 8;
        rgb[2] = colour & 0x000000ff;
        return rgb;
    }

    public BufferedImage takeScreenshot() {
        return robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }
}
