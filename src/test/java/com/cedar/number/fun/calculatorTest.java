package com.cedar.number.fun;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class calculatorTest {
private calculator c = new calculator();
    @Test
    @DisplayName("test add")
    public void Test_add() {
        assertEquals(2, c.add(1,1));
    }
}