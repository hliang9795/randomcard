package com.cedar.number.fun;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RandomCardTest {
    private Runtime rt = Runtime.getRuntime();
    private static final Logger logger = LogManager.getLogger("RandomCardTest");
    private long m1, m2;
    private int size = 100000;

    @BeforeEach
    void collectMemoryBefore () {
        rt.gc();
        m1 = rt.freeMemory()/1024;
    }

    @AfterEach
    void collectMemoryAfter() {
        rt.gc();
        m2 = rt.freeMemory()/1024;
        System.out.format("m1=%dk, m2=%dk, diff=%dk\n", m1, m2, (m1-m2));
    }

    @Test
    public void Test_randomCardGen_M1 () {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M1(450000000000000L, size, "Numbers_M1.txt");
//        rt.traceMethodCalls(true );
     }

    @Test
    public void Test_randomCardGen_M2 () {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M2(450000000000000L, size, "Numbers_M2.txt");
    }
    @Test
    public void Test_randomCardGen_M3() {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M3(450000000000000L, size, "Numbers_M3.txt");
    }
    @Test
    public void Test_randomCardGen_M4() {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M4(450000000000000L, size, "Numbers_M4.txt");
    }
}