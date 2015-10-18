import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Integer> x = new ArrayList<Integer>();
    public static ArrayList<Integer> y = new ArrayList<Integer>();
    public static ArrayList<Integer> z = new ArrayList<Integer>();
    public static ArrayList<Integer> r = new ArrayList<Integer>();

    public static void pl(Object o) {
        System.out.println( o );
    }
    public static double search( double s, double e, double t ) {
        // Interval smaller than precision
        if( e - s < 10E-6 ) {
            return s;
        }
        
        // check middle of interval
        double middle = s + ( e - s ) / 2.0;
        double v = volume( middle );
        
        if( v > t )
        {
            return search( s, middle, t );
        }
        else
        {
            return search( middle, e, t );
        }
    }
    public static double volume( double zi ) {
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
            double V = 100000.0 * 100000.0 * 100000.0;
            int n, s;
            n = sc.nextInt();
            s = sc.nextInt();
            for( int i = 0; i < n; i++ ) {
                int rt = sc.nextInt();
                r.add(rt);
                x.add(sc.nextInt());
                y.add(sc.nextInt());
                z.add(sc.nextInt());
                V -= (4.0/3.0) * Math.PI * rt * rt * rt;
            }

            double prev = 0.0;
            double curr = 0.0;
            for( int i = 0; i < s - 1; i++ )
            {
                double target = V * ( ( i + 1.0 ) / s );
                // search along z axis
                curr = search( 0.0, 1.0E5, target );
                System.out.format( "%.9f\n", ( curr - prev ) / 1000.0 );
                prev = curr;
            }
            System.out.format( "%.9f\n", ( 1.0E5 - prev ) / 1000.0 );
            x.clear();
            y.clear();
            z.clear();
            r.clear();
        }
    }

}
