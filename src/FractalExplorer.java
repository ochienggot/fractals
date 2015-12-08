/**
 * Created by ngot on 16/10/2015.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class FractalExplorer {

    // display size height = width of the display in pixels (square display)
    private int displaySize;

    private JImageDisplay imageDisplay;

    private FractalGenerator fractalGenerator;

    private Rectangle2D.Double range;

    private JComboBox fractalChooser;

    JFrame frame;

    public FractalExplorer(int displaySize) {

        this.displaySize = displaySize;
        range = new Rectangle2D.Double();
        fractalGenerator = new Mandelbrot();
    }

    /**
     * Initialize Swing GUI: a JFrame containing a JImageDisplay object and
     * a button for resetting the display.
     */
    void createAndShowGUI() {

        frame = new JFrame("Fractal Explorer");
        frame.setLayout(new BorderLayout());

        imageDisplay = new JImageDisplay(displaySize, displaySize);
        imageDisplay.addMouseListener(new ActionHandlerMouseEvents());
        frame.add(imageDisplay, BorderLayout.CENTER);

        JButton button = new JButton("Reset Display");
        ActionHandler actionHandler = new ActionHandler();
        button.setActionCommand("reset");
        button.addActionListener(actionHandler);
        //frame.add(button, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Save Image");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(actionHandler);

        // Save Image button -> panel?
        JPanel southPanel = new JPanel();
        southPanel.add(button);
        southPanel.add(saveButton);
        frame.add(southPanel, BorderLayout.SOUTH);

        fractalChooser = new JComboBox();
        fractalChooser.addItem(new Mandelbrot());
        fractalChooser.addItem(new Tricorn());
        fractalChooser.addItem(new BurningShip());
        fractalChooser.addActionListener(actionHandler);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Fractal:"));
        panel.add(fractalChooser);
        frame.add(panel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Private helper method to display the fractal
     * Loops through every pixel in the display (x: 0 to displaySize, y: 0 to displaySize)
     */
    private void drawFractal() {
        //fractalGenerator.getInitialRange(range);

        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {

                double xCoord = fractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

                int iterations = fractalGenerator.numIterations(xCoord, yCoord);
                if (iterations == -1) {
                    imageDisplay.drawPixel(x, y, 0);
                } else {

                    float hue = 0.7f + (float) iterations / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    imageDisplay.drawPixel(x, y, rgbColor);
                }
            }
        }

        imageDisplay.repaint();
    }

    private class ActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();

            if (e.getSource() == fractalChooser) {
                // Get fractal user selected and display it
                fractalGenerator = (FractalGenerator)fractalChooser.getSelectedItem();
                fractalGenerator.getInitialRange(range);
                drawFractal();

            }
            else if (cmd.equals("reset")) {
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
            else if (cmd.equals("save")){

                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int ret = fileChooser.showDialog(frame, "Save file");
                try {
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        ImageIO.write(imageDisplay.getImage(), "png", file);

                    }
                } catch (IOException io) {
                    JOptionPane.showMessageDialog(frame, io.getMessage(), "Cannot save image", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ActionHandlerMouseEvents extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = e.getLocationOnScreen();
            // map the click pixel coordinates into the area of the fractal being displayed
            double xCoord = fractalGenerator.getCoord(range.x, range.x + range.width, displaySize, (int)point.getX());
            double yCoord = fractalGenerator.getCoord(range.y, range.y + range.width, displaySize, (int)point.getY());
            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            drawFractal();
        }
    }

    public static void main(String[] args) {

        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}
