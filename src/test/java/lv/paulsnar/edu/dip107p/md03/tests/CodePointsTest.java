package lv.paulsnar.edu.dip107p.md03.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import lv.paulsnar.edu.dip107p.md03.CodePoints;

class CodePointsTest {
  private static int[] toCodeUnits(String string) {
    char[] codeUnits = string.toCharArray();
    int[] codeUnitsInt = new int[codeUnits.length];
    for (int i = 0; i < codeUnits.length; i += 1) {
      codeUnitsInt[i] = codeUnits[i];
    }
    return codeUnitsInt;
  }

  private static char[] charArray(char... chars) {
    return chars;
  }

  private static int[] intArray(int... ints) {
    return ints;
  }

  @Test
  void testExtractionNonSurrogatePassthrough() {
    assertArrayEquals(
      toCodeUnits("hello"), CodePoints.extractFrom("hello"));
    assertArrayEquals(
      toCodeUnits(""), CodePoints.extractFrom(""));
    assertArrayEquals(
      toCodeUnits("glāžšķūņu rūķīši"),
      CodePoints.extractFrom("glāžšķūņu rūķīši"));
  }

  @Test
  void testExtractionSurrogateDecomposition() {
    assertArrayEquals(intArray(0x1F41D), CodePoints.extractFrom("🐝"));
    assertArrayEquals(intArray('h', 'e', 'l', 'l', 'o', ' ', 0x1F60A),
      CodePoints.extractFrom("hello 😊"));
    assertArrayEquals(intArray(0x1F5B2, ' ', '!'),
      CodePoints.extractFrom("🖲 !"));
    assertArrayEquals(intArray(0x1F41D, 0x1F60A),
      CodePoints.extractFrom("🐝😊"));
  }

  @Test
  void testComposition() {
    assertArrayEquals(charArray('h'), CodePoints.codePointToChars((int) 'h'));
    assertArrayEquals(charArray((char) 55357, (char) 56349),
      CodePoints.codePointToChars(0x1F41D));
  }
}
