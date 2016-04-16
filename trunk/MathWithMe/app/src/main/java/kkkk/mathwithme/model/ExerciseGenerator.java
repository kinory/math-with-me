package kkkk.mathwithme.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DAKY on 15/04/2016.
 */
public class ExerciseGenerator {

    private final int seed;
    private final int equationType;
    private final int level;

    private Map<Integer, Exercise> exercisesDict;

    public ExerciseGenerator(int seed, int equationType, int level) {
        this.seed = seed;
        this.equationType = equationType;
        this.level = level;
        exercisesDict = new HashMap<>();
    }

    public Exercise generateExercise() {
        if (exercisesDict.containsKey(seed))
            return exercisesDict.get(seed);
        Exercise exercise = null;
        switch(equationType) {
            case 1:
                exercise = new LinearEquation(level);
                exercisesDict.put(seed, exercise);
                break;
            case 2:
                exercise = new QuadraticEquation(level);
                exercisesDict.put(seed, exercise);
                break;
        }
        return exercise;
    }

}
