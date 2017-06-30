import java.util.Map;

/**
 * Created by liyikun on 2017/6/30.
 */
public interface WeightedRandomSamplingAlgorithm {

    /**
     * Random number of times
     */
    int RANDOM_NUMBER = 10000;

    /**
     * Given n item, each one has weight weights[i], these weights form the unnormalized probability,
     * distribution we want to sample from, each item should have probability w[i]/sum(w) of being chosen.
     *
     * @param weights array of item weight
     * @return Position of item selected
     */
    Map<String, Integer> result(Map<String, Double> weights);
}
