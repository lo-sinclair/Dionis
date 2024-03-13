package locb.odi.dionis.tools;

import java.util.concurrent.ThreadLocalRandom;

public class Tools {

    public static boolean isChans(double percent) {
        //return ThreadLocalRandom.current().nextInt(10) > percent/10-1 ? true : false;
        return  (Math.random() > 1-percent/100) ? true : false;
    }
}
