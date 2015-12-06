import java.awt.*;

/**
 * Created by ngot on 14/10/2015.
 */

import java.awt.geom.*;

public class Mandelbrot extends FractalGenerator {

    private static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }


    public int numIterations(double x, double y) {
        // zo = 0;
        // zn = zn-1 **2 + c ;
        // terminating condition: iterations == 2000 or |z| > 2
        double re = 0;
        double im = 0;
        int iterations = 0;

        while (iterations < MAX_ITERATIONS) {
            // |z|^2 > 2^2
            double nextRe = re * re - im * im + x;
            double nextIm = 2 * re * im + y;

            re = nextRe;
            im = nextIm;
            if ((re*re + im*im) > 4) {
                // The point is not in the Mandelbrot set
                return iterations;
            }
            iterations++;
        }

        // point didn't escape outside the boundary
        return -1;
    }
}
