import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by liyikun on 2017/7/1.
 */
public class ExhaustionStrategy implements WeightedRandomSamplingAlgorithm {

    private static final int PRECISION = 100;

    /**
     * 默认精度以100作为计算
     *
     * @param weights array of item weight
     * @return
     */
    @Override
    public Map<String, Integer> result(Map<String, Double> weights) {
        String[] samplings = new String[PRECISION];
        double sumWeights = 0;
        Iterator<Map.Entry<String, Double>> iterator = weights.entrySet().iterator();
        while (iterator.hasNext()) {
            sumWeights += iterator.next().getValue();
        }
        iterator = weights.entrySet().iterator();
        Map.Entry<String, Double> entry = null;
        for (int i = 0, count = 0; i < samplings.length; i++, count--) {
            if (count == 0 && iterator.hasNext()) {
                entry = iterator.next();
                count = (int) (entry.getValue() / sumWeights * PRECISION);
            }
            samplings[i] = entry.getKey();
        }

        Map<String, Integer> samplingMap = new HashMap<>();
        for (int i = 0; i < RANDOM_NUMBER; i++) {
            int number = (int) (Math.random() * PRECISION);
            String key = samplings[number];
            Integer times = samplingMap.get(key);
            if (times == null) {
                times = 1;
            } else {
                times++;
            }
            samplingMap.put(key, times);
        }
        return samplingMap;
    }
}
