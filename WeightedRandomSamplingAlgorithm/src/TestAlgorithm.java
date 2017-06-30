import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liyikun on 2017/7/1.
 */
public class TestAlgorithm {

    public static void main(String args[]) {
        Map<String, Double> weights = new LinkedHashMap<>();
        weights.put("yellow", 0.1);
        weights.put("white", 0.2);
        weights.put("blue", 0.4);
        weights.put("red", 0.5);
        System.out.println(new ExhaustionStrategy().result(weights));
        System.out.println(new DiscreteStrategy().result(weights));
        System.out.println(new AliasStrategy().result(weights));
    }
}
