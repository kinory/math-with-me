package kkkk.mathwithme.model;

import java.util.Random;

/**
 * Created by DAKY on 15/04/2016.
 *
 * Represents:
 * getParameters()[0]*x^2 + getParameters()[1]*x + getParameters()[2] = 0
 * and provides:
 * getSolutions() -> int[]
 */
public class QuadraticEquation implements Exercise {

    private Random parametersGenerator;

    private int a;
    private int b;
    private int c;

    private double[] solutions;

    protected QuadraticEquation(int seed, int level) {
        parametersGenerator = new Random(seed);

        int A, B, C, D;
        B = sign() * parametersGenerator.nextInt(31);
        D = sign() * parametersGenerator.nextInt(31);

        switch (level) {
            case 1:
                A = 0;
                C = 1;
                break;
            case 2:
                A = divisor(B);
                C = 1;
                break;
            case 3:
                A = divisor(B);
                C = divisor(D);
                break;
            default:
                A = 1;
                C = 1;
        }


        a = A*C;
        b = D*A + B*C;
        c = B * D;

        solutions = new double[]{-B/A, -D/C};
    }

    private static int divisor(int num) {
        for (int i = 2; i < Math.sqrt(num); i++) {
            if (num % i == 0)
                return i;
        }
        return 1;
    }

    private static int sign() {
        return new Random().nextInt(2) - 1;
    }

    public int[] getParameters() {
        return new int[]{a, b, c};
    }

    public double[] getSolutions() {
        return solutions;
    }
}
