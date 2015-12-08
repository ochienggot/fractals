/**
 * Created by ngot on 07/12/2015.
 */
import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {

    private static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }


    public int numIterations(double x, double y) {
        /* take the absolute value of each component */
        double re = 0;
        double im = 0;
        int iterations = 0;

        while (iterations < MAX_ITERATIONS) {
            double nextRe = re * re - im * im + x;
            double nextIm = 2 * Math.abs(re) * Math.abs(im) + y;

            re = nextRe;
            im = nextIm;
            if ((re*re + im*im) > 4) {
                return iterations;
            }
            iterations++;
        }

        return -1;
    }

    @Override
    public String toString() {
        return "Burning Ship";
    }
}