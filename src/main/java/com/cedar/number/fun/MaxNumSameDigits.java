package com.cedar.number.fun;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class MaxNumSameDigits {
    private static final Logger logger = LogManager.getLogger("MaxNumSameDigits");

    public static int getMaxNSameDigits(int x, int n) {
        int y[] = {0,0,0,0,0,0,0,0,0,0};
        for  ( ; n > 0; n--) {
            y[x % 10]++;
            x = x/10;
        }
        int m = 0;
        for (int i=0; i<10; i++) { if (y[i] > m) m = y[i]; }
        return m;
    }

    public void statRandom(int max, int d) {
        Random rt = new Random();
        Map<Integer, List<Integer>> m = new Random().ints(0,max).
                limit(max).
                boxed().
                collect(groupingBy(x -> MaxNumSameDigits.getMaxNSameDigits(x,d)));
        printCount(m);
    }

    public void statSeq(int max, int d) {
        Map<Integer, List<Integer>> m = IntStream.range(0,max).
                boxed().
                collect(groupingBy(x -> MaxNumSameDigits.getMaxNSameDigits(x,d)));
        printCount(m);
    }

    private static void  printCount(Map<Integer, List<Integer>> m) {
        System.out.println();
        System.out.println();
        for (Integer i : m.keySet()) {
            System.out.format("key=%d, size=%d\n", i, m.get(i).size());
        }
    }
}
