package logic;

import java.util.ArrayList;
import java.util.List;

public class Common {
    /**
     * pattern for autumn: 1, 1, 1...
     * pattern for spring: 1, 0, 1, 0...
     */
    public static List<String[]> generateTestSample() {
        List<String[]> res = new ArrayList<>();
        long curTime = System.currentTimeMillis() / 1000L;
        long gap = 40L * 60L;
        for (int i = 0; i < 36; i++) {
            res.add(new String[2]);
            if (i % 3 > 0) {
                res.get(i)[0] = "#autumn";
            } else {
                res.get(i)[0] = "#spring";
            }
            res.get(i)[1] = Long.toString(curTime);
            curTime -= gap;
        }
        return res;
    }
}
