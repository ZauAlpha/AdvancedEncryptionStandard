import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public
class

ByteTest {

    @Test
    public void
    testConstructor() {
        Byte aByte = new Byte(32);

        assertEquals(false, aByte.binary[0]);
        assertEquals(false, aByte.binary[1]);
        assertEquals(false, aByte.binary[2]);
        assertEquals(false, aByte.binary[3]);
        assertEquals(false, aByte.binary[4]);
        assertEquals(true, aByte.binary[5]);
        assertEquals(false, aByte.binary[6]);
        assertEquals(false, aByte.binary[7]);
    }

    @Test
    public void testGetBinary() {
        Byte aByte = new Byte(32);
        // 32 = 0010 0000
        boolean[] expectedBinary = {false, false, false, false, false, true, false, false};
        assertArrayEquals(expectedBinary, aByte.binary);
    }

    @Test
    public void testSetBinary() {
        Byte aByte = new Byte(10);

        boolean[] newBinary = {true, false, false, false, true, false, false, false};
        aByte.binary = newBinary;

        assertArrayEquals(newBinary, aByte.binary);
    }
}