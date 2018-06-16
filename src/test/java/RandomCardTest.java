import com.cedar.number.fun.RandomCard;
import org.junit.jupiter.api.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class RandomCardTest {
    private Runtime rt = Runtime.getRuntime();
    private static final Logger logger = LogManager.getLogger("RandomCardTest");
    private long m1, m2;
    private int size = 10000000;

    @BeforeEach
    void collectMemoryBefore () {
        rt.gc();
        m1 = rt.freeMemory()/1024;
    }

    @AfterEach
    void collectMemoryAfter() {
        rt.gc();
        m2 = rt.freeMemory()/1024;
        System.out.format("m1=%d, m2=%d, diff=%d\n", m1, m2, (m1-m2));
    }

    @Test
    @Tag("RandomCard")
    void Test_randomCardGen_M1 () {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M1(450000000000000L, size, "Numbers_M1.txt");
        rt.traceMethodCalls(true );
     }

    @Test
    @Tag("RandomCard")
    void Test_randomCardGen_M2 () {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M2(450000000000000L, size, "Numbers_M2.txt");
    }
    @Test
    @Tag("RandomCard")
    void Test_randomCardGen_M3() {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M3(450000000000000L, size, "Numbers_M3.txt");
    }
    @Test
    @Tag("RandomCard")
    void Test_randomCardGen_M4() {
        RandomCard rc = new RandomCard();
        rc.randomCardGen_M4(450000000000000L, size, "Numbers_M4.txt");
    }
}

