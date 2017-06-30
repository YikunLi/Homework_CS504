## 一共实现了三种算法，穷举法、离散法、Alias算法。第三种算法还有一点小问题。  
```
Input:
        Map<String, Double> weights = new LinkedHashMap<>();
        weights.put("yellow", 0.1);
        weights.put("white", 0.2);
        weights.put("blue", 0.4);
        weights.put("red", 0.5);
        System.out.println(new ExhaustionStrategy().result(weights));
        System.out.println(new DiscreteStrategy().result(weights));
        System.out.println(new AliasStrategy().result(weights));
Output:
        {red=4346, blue=3296, white=1520, yellow=838}
        {red=4168, blue=3351, white=1623, yellow=858}
        {red=3362, blue=2936, white=453, yellow=203}
``` 
