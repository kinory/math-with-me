package kkkk.mathwithme.model;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by DAKY on 15/04/2016.
 *
 * Represents
 * a*(b*x+c) + d*(x+i) = e*(f*x+g) + h
 */
public class LinearEquation implements Exercise {

    public static void main(String[] args) {
        Exercise le = new LinearEquation(3, 1);
        System.out.println(Arrays.toString(le.getParameters()));
        System.out.println(Arrays.toString(le.getSolutions()));
        Exercise le2 = new LinearEquation(3, 1);
        System.out.println(Arrays.toString(le2.getParameters()));
        System.out.println(Arrays.toString(le2.getSolutions()));
        Exercise le3 = new LinearEquation(3, 1);
        System.out.println(Arrays.toString(le3.getParameters()));
        System.out.println(Arrays.toString(le3.getSolutions()));
    }

    private Random parametersGenerator;

    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;

    private double[] solutions;

    protected LinearEquation(int seed, int level) {
        parametersGenerator = new Random(seed);

        a = sign() * parametersGenerator.nextInt(31);
        b = sign() * parametersGenerator.nextInt(31);
        c = sign() * parametersGenerator.nextInt(31);
        switch (level) {
            case 1:
                d = 0;
                e = 0;
                break;
            case 2:
                d = sign() * parametersGenerator.nextInt(31);
                e = 0;
                break;
            case 3:
                d = sign() * parametersGenerator.nextInt(31);
                e = sign() * parametersGenerator.nextInt(31);
                break;
        }
        f = sign() * parametersGenerator.nextInt(31);
        while (a * b + d - e * f == 0) {
            f = sign() * parametersGenerator.nextInt(31);
        }
        g = sign() * parametersGenerator.nextInt(31);
        h = sign() * parametersGenerator.nextInt(31);
        i = sign() * parametersGenerator.nextInt(31);

        solutions = new double[]{(e * g + h - a * c - d * i) / (a * b + d - e * f)};
    }

    private static int sign() {
        return new Random().nextInt(2) - 1;
    }

    public int[] getParameters() {
        return new int[]{a, b, c, d, e, f, g, h};
    }

    public double[] getSolutions() {
        return solutions;
    }
}
