package kkkk.mathwithme.model;

/**
 * Created by DAKY on 15/04/2016.
 */
public class ExerciseGenerator {

    protected static Exercise generatorExercise(int seed, int equationType, int level) {
        switch(equationType) {
            case 1:
                return new LinearEquation(seed, level);
            case 2:
                return new QuadraticEquation(seed, level);
        }
        return null;
    }

}
