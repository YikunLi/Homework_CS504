import java.util.*;

/**
 * Created by liyikun on 2017/7/1.
 * 参照：
 * http://blog.csdn.net/sky_zhe/article/details/10051967
 * http://www.keithschwarz.com/darts-dice-coins/
 * 还有一点问题，有空了再研究研究
 */
public class AliasStrategy implements WeightedRandomSamplingAlgorithm {

    @Override
    public Map<String, Integer> result(Map<String, Double> weights) {
        Element[] array = new Element[weights.size()];
        Iterator<Map.Entry<String, Double>> iterator = weights.entrySet().iterator();
        double sum = 0;
        while (iterator.hasNext()) {
            sum += iterator.next().getValue();
        }
        iterator = weights.entrySet().iterator();
        List<Integer> larges = new ArrayList<>();
        List<Integer> smalls = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            Map.Entry<String, Double> entry = iterator.next();
            array[i] = new Element(entry.getKey(), entry.getValue() / sum);
            array[i].prob = array[i].pdf * array.length;
            if (array[i].prob > 1) {
                larges.add(i);
            } else if (array[i].prob < 1) {
                smalls.add(i);
            } else {
                array[i].alia = Element.NULL;
            }
        }
        // 待优化
        for (int i = 0, j = 0; larges.size() != 0 && smalls.size() != 0; ) {
            double prob1 = array[smalls.get(i)].prob;
            double prob2 = array[larges.get(j)].prob;
            double probSum = prob1 + prob2;
            if (probSum - 2 <= 0.000000000001) {
                array[smalls.get(i)].alia = larges.get(larges.size() - 1 - j);
                array[larges.get(larges.size() - 1 - j)].alia = Element.NULL;
                smalls.remove(i);
                larges.remove(larges.size() - 1 - j);
            }
        }

        Map<String, Integer> samplingMap = new HashMap<>();
        for (int i = 0; i < RANDOM_NUMBER; i++) {
            int randomIndex = (int) (Math.random() * array.length);
            double randomNumber = Math.random();
            if (randomNumber < array[randomIndex].pdf) {
                // randomIndex
                this.increase(samplingMap, array[randomIndex].key);
            } else {
                if (array[randomIndex].alia != Element.NULL) {
                    // array[randomIndex].alia
                    this.increase(samplingMap, array[array[randomIndex].alia].key);
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

    private class Element {

        private static final int NULL = -1;

        String key;
        double pdf;
        double prob;
        int alia;

        public Element(String key, double pdf) {
            this.key = key;
            this.pdf = pdf;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "key='" + key + '\'' +
                    ", pdf=" + pdf +
                    ", prob=" + prob +
                    ", alia=" + alia +
                    '}';
        }
    }
}
