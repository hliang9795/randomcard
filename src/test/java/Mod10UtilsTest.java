
import com.cedar.number.fun.Mod10Utils;
import org.junit.jupiter.api.*;

public class Mod10UtilsTest {
    @Test
    @Tag("Util")
    // to do: parameterize this test case. Need valid card# with all 10 possible check sum digits.
    private void testMod10Append() {
        Assertions.assertEquals(  4012888888881881L, Mod10Utils.appendMod10(401288888888188L), "Mod10Append");
        Assertions.assertEquals(  4500000071781360L, Mod10Utils.appendMod10(450000007178136L), "Mod10Append");
    }
}
