/**
 * Created by ngot on 13/10/2015.
 */

import javax.swing.*;
import java.awt.image.*;
import java.awt.Dimension;
import java.awt.*;

public class JImageDisplay extends JComponent {

    private BufferedImage bufImage;

    public JImageDisplay(int width, int height) {

        bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width, height));
    }

    public BufferedImage getImage() {
        return bufImage;
    }

    /**
     * override method in superclass. Calls the superclass method and then
     * draws the image
     * @param g: the graphics object
     */
    @Override protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bufImage, 0, 0, bufImage.getWidth(), bufImage.getHeight(), null);
    }

    /**
     * Sets all pixels in the image data to black (RGB value 0)
     */
    public void clearImage() {
        for (int i = 0; i < bufImage.getWidth(); i++) {
            for (int j = 0; j < bufImage.getHeight(); j++) {
                bufImage.setRGB(i, j, 0);
            }
        }
    }

    /**
     * sets a pixel to have a specific color
     * @param x: x coordinate
     * @param y: y coordinate
     * @param rgbColor: rgb color to set
     */
    public void drawPixel(int x, int y, int rgbColor) {
        bufImage.setRGB(x, y, rgbColor);
    }
}
