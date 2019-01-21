import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

/**
 * Created by lukas on 21.1.2019.
 */

public class Core {

    private Robot robot;
    private ImageProcessor ip = new ImageProcessor();

    public Core() {
        //TEST
        try {
            Thread.sleep(2000);
            robot = new Robot();
        } catch(Exception e) {

        }

        int prevX = 0;
        int prevY = 0;
        int sameClicks = 0;
        int farDistanceNumber = 0;

        while (true) {
            /**
             * Info:
             * Top pixel, který můžu používat: 158
             * Bottom pixel: 1040
             * Šířka: 0 - 1919
             *
             * Each box is about 20 pixels wide and high.
             */
            BufferedImage screenshot = ip.takeScreenshot();

            int x = 10;     //0 + half a box

            int y = 168;    //158 + half a box

            boolean boxFound = false;
            while (!boxFound) {
                if (isBox(screenshot, x, y)) {
                    //System.out.println("I found a box at x = " + x + "; y = " + y);
                    boxFound = true;
                    break;
                }

                if (x < 1909) {
                    x += 5;
                } else if (y < 1030) {
                    y += 5;
                    x = 10;

                } else {
                    //System.out.println("Didn't find a box");

                    break;
                }
            }


            if (boxFound) {
                robot.mouseMove(x, y);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.delay((int) (Math.random() * 500));
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                if (x == prevX && y == prevY) {
                    sameClicks++;
                } else {
                    sameClicks = 0;
                }
                prevX = x;
                prevY = y;

            } else {
                //Move somewhere
                switch (farDistanceNumber) {
                    //Top left
                    case 0:
                        robot.mouseMove(1670, 890);
                        break;
                    //Bottom left
                    case 1:
                        robot.mouseMove(1670, 980);
                        break;
                    //Top right
                    case 2:
                        robot.mouseMove(1870, 890);
                        break;
                    //Bottom right
                    case 3:
                        robot.mouseMove(1870, 980);
                        break;
                }
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.delay((int) (Math.random() * 500));
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                if (x == prevX && y == prevY) {
                    sameClicks++;
                } else {
                    sameClicks = 0;
                }
                prevX = x;
                prevY = y;
            }

            //Check if it's stuck
            if (sameClicks == 10) {
                //Unstuck
                sameClicks = 0;
                if (farDistanceNumber < 3)
                    farDistanceNumber++;
                else
                    farDistanceNumber = 0;
            }


            try {
                Thread.sleep(1000);
            } catch(Exception e) {

            }
        }
    }

    private boolean isBox(BufferedImage screenshot, int x, int y) {
        int[] rgb = ip.getRGB(screenshot, x, y);

        if (rgb[0] > 230 && rgb[1] > 200 && rgb[2] > 150 && rgb[2] < 250) {
            //Probably a box

            /* Each box has a blue aura around itself. I can use that to dismiss false positives.
            If in the next 50 pixel to the left and 50 to the right there aren't at least 5 pixels
            with B higher than the sum of R and G, it's a false positive.
             */

            int leftBluePixels = 0;
            int rightBluePixels = 0;

            try {
                //Check left
                for (int i = 1; i <= 50; i++) {
                    int[] rgbLeft = ip.getRGB(screenshot, x - i, y);
                    if (rgbLeft[0] + rgbLeft[1] < rgbLeft[2]) {
                        leftBluePixels++;
                    }
                    if (leftBluePixels == 5) {
                        break;
                    }
                }
                //Check right
                for (int i = 1; i <= 50; i++) {
                    int[] rgbRight = ip.getRGB(screenshot, x + i, y);
                    if (rgbRight[0] + rgbRight[1] < rgbRight[2]) {
                        rightBluePixels++;
                    }
                    if (rightBluePixels == 5) {
                        break;
                    }
                }

            } catch (Exception e) {
                //Out of bounds - can happen
                return false;
            }

            if (leftBluePixels == 5 && rightBluePixels == 5) {
                return true;
            }
        }
        return false;
    }
}
