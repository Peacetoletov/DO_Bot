import java.awt.image.BufferedImage;

/**
 * Created by lukas on 21.1.2019.
 */

public class Core {

    private ImageProcessor ip = new ImageProcessor();

    public Core() {

        //TEST
        try {
            Thread.sleep(2000);
        } catch(Exception e) {

        }

        boolean moving = false;
        while (true) {
            //If there are instructions, follow them. Otherwise, take a screenshot, analyze it and decide where to go.
            if (moving) {
                //Do something here
            } else {
                /**
                 * Info:
                 * Top pixel, který můžu používat: 158
                 * Bottom pixel: 1040
                 * Šířka: 0 - 1919
                 *
                 * Each box is about 20 pixels wide and high.
                 */
                BufferedImage screenshot = ip.takeScreenshot();


                /** Right now, I will start analyzing in the bottom left corner and go through each line, ending
                 * in the bottom right corner. Once this is done, I can optimize it by starting in the center
                 * of the screen and going in a spiral.
                 *
                 */

                int x = 10;     //0 + half a box
                int y = 168;    //158 + half a box
                boolean boxFound = false;
                while (!boxFound) {
                    if (isBox(screenshot, x, y)) {
                        //System.out.println("I found a box at x = " + x + "; y = " + y);
                        boxFound = true;
                    }
                    if (x < 1909) {
                        x += 10;
                    } else if (y < 1030) {
                        y += 10;
                        x = 10;
                    } else {
                        //System.out.println("Didn't find a box");
                        break;
                    }


                }


            }

            break;      //REMOVE THIS
        }
    }

    boolean isBox(BufferedImage screenshot, int x, int y) {
        int[] rgb = ip.getRGB(screenshot, x, y);

        if (rgb[0] > 240 && rgb[1] > 200 && rgb[2] > 150) {
            //Probably a box
            return true;
        }
        return false;

        /* This will sometimes return a false positive. If I'm near a portal, there's a white text which can mess up
        with this algorithm. But that's fine - by clicking on the zone where the text is, the player will move away
        from the portal and the text soon disappears.
        */
    }

}
