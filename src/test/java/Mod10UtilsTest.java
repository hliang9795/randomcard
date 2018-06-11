package com.cedar.randomcard;

import org.junit.jupiter.api.*;

public class Mod10UtilsTest {
    @Test
    @Tag("Util")
    // to do: parameterize this test case. Need valid card# with all 10 possible check sum digits.
    public void testMod10Append() {
        Assertions.assertEquals(  4500000083808367L, Mod10Utils.appendMod10(450000008380836L), "Mod10Append");
        Assertions.assertEquals(  45000000542025901L, Mod10Utils.appendMod10(450000005420259L), "Mod10Append");
    }
}
