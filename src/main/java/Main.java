/**
 * Created by lukas on 14.1.2019.
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String args[]) throws
            AWTException, IOException {
        //Main loop
        boolean moving = false;
        while (true) {
            //If there are instructions, follow them. Otherwise, take a screenshot, analyze it and decide where to go.
            if (moving) {
                //Do something here
            } else {
                //Take a screenshot
                BufferedImage screenshot = new Robot().createScreenCapture(
                        new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                //testing
                //int test = screenshot.getRGB(100, 500);
                //System.out.println(test);

                int colour = screenshot.getRGB(100, 500);
                int red = (colour & 0x00ff0000) >> 16;
                int green = (colour & 0x0000ff00) >> 8;
                int blue = colour & 0x000000ff;

                System.out.println("red = " + red);
                System.out.println("green = " + green);
                System.out.println("blue = " + blue);
                // ^ Good, this works.
            }
        }
    }
}
