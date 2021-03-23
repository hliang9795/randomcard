package com.cedar.number.fun;

import com.cedar.number.fun.MaxNumSameDigits;

import org.junit.jupiter.api.*;

public class MaxNumSameDigitsTest {
    @Test
    @Disabled
    public void test1() {
        for (int i = 0; i < 10; i++) {
            System.out.format("n=%d, mnsd=%d\n", i, MaxNumSameDigits.getMaxNSameDigits(i, 2));
        }
    }

    @Test
    public void test2() {
        MaxNumSameDigits mnsd = new MaxNumSameDigits();
        mnsd.statRandom(1000,3);
    }

    @Test
    public void test3() {
        MaxNumSameDigits mnsd = new MaxNumSameDigits();
        mnsd.statSeq(1000000,6);
        //mnsd.statSeq(10000000,7);
        //mnsd.statSeq(100000000,8);
        //mnsd.statSeq(1000000000,9);
    }
}
