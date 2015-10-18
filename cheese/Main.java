/**
 * Binary search solution to the Cheese Slicing problem.
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=4785
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    /** Sphere Coordinates */
    public static ArrayList<Integer> x = new ArrayList<Integer>();
    public static ArrayList<Integer> y = new ArrayList<Integer>();
    public static ArrayList<Integer> z = new ArrayList<Integer>();
    public static ArrayList<Integer> r = new ArrayList<Integer>();

    /** Printline shortcut */
    public static void pl(Object o) {
        System.out.println( o );
    }

    /**
     * Searches through the z axis for the target volume
     * s : start of interval in z axis
     * e : end of interval in z axis
     * t : target volume that we are searching for
     */
    public static double search( double s, double e, double t ) {

        // Base case
        // Interval smaller than precision, then we're done
        if( e - s < 10E-6 ) {
            return s;
        }
        
        // find volume up from 0 to middle of interval
        double middle = s + ( e - s ) / 2.0;
        double v = volume( middle );
        
        // Volume was bigger than the target
        if( v > t )
        {
            // Search to the left
            return search( s, middle, t );
        }
        else
        {
            // search to the right
            return search( middle, e, t );
        }
    }

    /**
     * Computes the volume of the block of cheese from
     * 0.0 up to zi on the z axis.
     */
    public static double volume( double zi ) {

        // Volume if there were no spheres
        double v = 1.0E5 * 1.0E5 * zi;

        // subtract every sphere
        for( int i = 0; i < r.size(); i++ ) {
            double rt = r.get( i );
            // sphere starts before zi
            if( z.get( i ) - rt  <= zi ) {
                double sVol = (4.0/3.0) * Math.PI * rt * rt * rt;
                // sphere ends before zi
                if( z.get( i ) + rt  <= zi ) {
                    v -= sVol;
                }
                // center is after zi
                else if( z.get( i ) >= zi) {
                    double h = ( zi + rt - z.get( i ) );
                    v -= ( 1.0 / 3.0 ) * Math.PI * h * h * ( 3.0 * rt - h ); // sphere cap
                }
                // center is before zi
                else {
                    double h = ( z.get( i ) + rt - zi );
                    sVol -= ( 1.0 / 3.0 ) * Math.PI * h * h * ( 3.0 * rt - h ); // sphere cap
                    v -= sVol;
                }
            }
        }
        return v;
    }

    public static void main( String args[] ) {

        Scanner sc = new Scanner(System.in);

        while( sc.hasNext() ) {

            // Volume without any spheres
            double V = 100000.0 * 100000.0 * 100000.0;
            int n, s;
            n = sc.nextInt();
            s = sc.nextInt();

            // Read and subtract sphere volumes to get the
            // total volume of the cheese block
            for( int i = 0; i < n; i++ ) {
                int rt = sc.nextInt();
                r.add(rt);
                x.add(sc.nextInt());
                y.add(sc.nextInt());
                z.add(sc.nextInt());
                V -= (4.0/3.0) * Math.PI * rt * rt * rt;
            }
            
            // Prev and curr z-coordinate
            double prev = 0.0;
            double curr = 0.0;

            // Search for the position in the z-axis that gives
            // us the required volume for each slice
            for( int i = 0; i < s - 1; i++ )
            {
                // target volume from z = 0.0 to z-value of slice
                double target = V * ( ( i + 1.0 ) / s );
                // search along z axis for the z-value given the volume
                curr = search( 0.0, 1.0E5, target );
                System.out.format( "%.9f\n", ( curr - prev ) / 1000.0 );
                prev = curr;
            }
            System.out.format( "%.9f\n", ( 1.0E5 - prev ) / 1000.0 );

            // Get ready for the next test case
            x.clear();
            y.clear();
            z.clear();
            r.clear();
        }
    }

}
