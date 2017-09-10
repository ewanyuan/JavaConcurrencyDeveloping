import forkJoin.AreaCalculator;
import org.junit.Test;

import java.util.*;

/**
 * Created by ewan on 06/09/2017.
 */
public class CollectionsTest {
    @Test
    public void Hash() {
        Map<String, AreaCalculator> map = new HashMap<>();
        for (int i = 0; i <1000 ; i++) {
            map.put(i+"_", new AreaCalculator());
        }
        Iterator<Map.Entry<String, AreaCalculator>> iterator = map.entrySet().iterator();
        if (iterator != null) {
            while (iterator.hasNext()) {
                Map.Entry<String, AreaCalculator> entry = iterator.next();
                int hashCode = entry.getKey().hashCode();
                System.out.println("hashCode:"+hashCode);
                System.out.println("i:"+arrayIndex(hashCode));
            }
        }
    }

    private static final int arrayIndex(int hashCode) {
        int h;
        return  (h = hashCode) ^ (h >>> 16);
    }
}
