package util;

import java.util.ArrayList;
import java.util.List;

public class RoutePlanner {

    public static List<int[]> optimizeRoute(List<int[]> targets) {
        List<int[]> fullRoute = new ArrayList<>();
        int[] current = new int[]{0, 0};
        fullRoute.add(current.clone());

        for (int[] target : targets) {
            current = moveStepByStep(current, target, fullRoute);
        }

        // Връщане до [0,0]
        moveStepByStep(current, new int[]{0, 0}, fullRoute);

        return fullRoute;
    }

    private static int[] moveStepByStep(int[] from, int[] to, List<int[]> route) {
        int x = from[0];
        int y = from[1];

        while (x != to[0] || y != to[1]) {
            if (x < to[0]) x++;
            else if (x > to[0]) x--;

            else if (y < to[1]) y++;
            else if (y > to[1]) y--;

            route.add(new int[]{x, y});
        }

        return new int[]{x, y};
    }

    public static int distance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }
}
