import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by liyikun on 2017/7/1.
 */
public class DiscreteStrategy implements WeightedRandomSamplingAlgorithm {

    @Override
    public Map<String, Integer> result(Map<String, Double> weights) {
        Point[] points = new Point[weights.size()];
        Iterator<Map.Entry<String, Double>> iterator = weights.entrySet().iterator();
        double sum = 0;
        for (int i = 0; iterator.hasNext(); i++) {
            Map.Entry<String, Double> entry = iterator.next();
            sum += entry.getValue();
            points[i] = new Point(sum, entry.getKey());
        }

        Map<String, Integer> samplingMap = new HashMap<>();
        for (int i = 0; i < RANDOM_NUMBER; i++) {
            double random = Math.random() * sum;
            // binary search
            for (int low = 0, high = points.length - 1; low < high; ) {
                int mid = (low + high) >>> 1;
                if (random <= points[mid].weight) {
                    if (mid == 0) {
                        this.increase(samplingMap, points[0].key);
                        break;
                    } else if (random >= points[mid - 1].weight) {
                        this.increase(samplingMap, points[mid].key);
                        break;
                    } else {
                        high = mid;
                    }
                } else {
                    if (mid == points.length - 1) {
                        this.increase(samplingMap, points[points.length - 1].key);
                        break;
                    } else if (random <= points[mid + 1].weight) {
                        this.increase(samplingMap, points[mid + 1].key);
                        break;
                    } else {
                        low = mid;
                    }
                }
            }
        }
        return samplingMap;
    }

    private void increase(Map<String, Integer> map, String key) {
        Integer integer = map.get(key);
        if (integer == null) {
            integer = 0;
        }
        map.put(key, ++integer);
    }

    private class Point {

        double weight;
        String key;

        public Point(double weight, String key) {
            this.weight = weight;
            this.key = key;
        }
    }
}
