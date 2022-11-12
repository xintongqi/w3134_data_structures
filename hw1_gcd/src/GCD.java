/**
 * This class consists methods that calculates the Greatest Common Divisor(GCD)
 * of two integers which are not 0 at the same time using the Euclidean Algorithm. <br>
 * The Euclidean Algorithm states the following.
 * <ul>
 *     <li> The GCD between 0 and A (A!=0) is A </li>
 *     <li> If A=B*Q+R, then GCD(A,B)=GCD(B,R) </li>
 * </ul>
 *
 * @author Xintong Qi
 * @version 1.0
 */

public class GCD {
    /**
     * This method finds the Greatest Common Divisor(GCD) of
     * two integers using the iterative method.
     * @param m the first integer
     * @param n the second integer
     * @return the GCD of the two integers
     */
    public static int iterativeGcd(int m, int n){
        int r; // reminder
        while(n!=0){
            r=m%n;
            m=n;
            n=r;
        }
        return m;
    }

    /**
     * This method finds the Greatest Common Divisor(GCD) of
     * two integers using the recursive method.
     * @param m the first integer
     * @param n the second integer
     * @return the GCD of the two integers
     */
    public static int recursiveGcd(int m, int n){
        if(n==0){
            return m;
        }else{
            return recursiveGcd(n, m%n);
        }
        // TODO: can do it in a one-liner
    }

    /**
     * This is the entry point of the program. The method calls
     * {@link #iterativeGcd(int, int)} and {@link #recursiveGcd(int, int)} after validating
     * the input numbers are two integers that cannot be 0 at the same time
     */
    public static void main(String[] args){

        // not enough arguments or too many arguments
        if (args.length!=2){
            System.err.println("Usage: java GCD <integer m> <integer n>");
            System.exit(1);
        }

        int num1=0,num2=0;
        // first number is not an int
        try {
            num1 = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            System.err.println("Error: The first argument is not a valid integer.");
            System.exit(1);
        }

        // second number is not an int
        try {
            num2 = Integer.parseInt(args[1]);
        } catch (NumberFormatException e){
            System.err.println("Error: The second argument is not a valid integer.");
            System.exit(1);
        }

        // m and n are both 0
        if (num1==0 && num2==0){
            System.out.println("gcd(0, 0) = undefined");
            System.exit(0);
        }

        // change negative value into positive, if any
        int m=num1,n=num2;
        if (num1<0){
            m=Math.abs(num1);
        }
        if (num2<0){
            n=Math.abs(num2);
        }
        // TODO: remove if statement, can directly cast abs

        // ensure m is the larger number
        int tmp=m;
        if(m<n){
            m=n;
            n=tmp;
        }
        // TODO: doesn't matter which one comes first, the two funcs can handle it

        // calculate
        System.out.println(String.format("Iterative: gcd(%d, %d) = %d", num1,num2,iterativeGcd(m,n)));
        System.out.println(String.format("Recursive: gcd(%d, %d) = %d", num1,num2,recursiveGcd(m,n)));
        System.exit(0);
    }
}
