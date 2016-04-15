package kkkk.mathwithme.model.server;

/**
 * Created by Gilad Kinory on 15/04/2016.
 * giladkinory2000@gmail.com
 */
public interface CallableWithParameter<ParameterType, ReturnType> {

    ReturnType call(ParameterType parameter);
}
