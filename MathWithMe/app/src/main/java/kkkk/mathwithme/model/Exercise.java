package kkkk.mathwithme.model;

/**
 * Created by DAKY on 15/04/2016.
 */
public interface Exercise {

    public Class<? extends Exercise> getType();
    public int[] getParameters();
    public int[] getSolutions();

}
