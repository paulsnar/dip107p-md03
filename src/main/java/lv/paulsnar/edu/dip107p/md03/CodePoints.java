package lv.paulsnar.edu.dip107p.md03;

public final class CodePoints {
  private CodePoints() {
  }

  public static int[] extractFrom(String string) {
    char[] codeUnits = string.toCharArray();
    int[] codePoints = new int[codeUnits.length];
    int i = 0;
    int surrogate = 0;
    for (char codeUnit : codeUnits) {
      if (surrogate != 0) {
        surrogate |= codeUnit & 0x3FF;
        codePoints[i] = 0x10000 + surrogate;
        i += 1;
        surrogate = 0;
      } else if ((codeUnit & 0xFC00) == 0xD800) {
        surrogate = codeUnit & 0x3FF;
        surrogate <<= 10;
      } else {
        codePoints[i] = codeUnit;
        i += 1;
      }
    }

    if (i != codePoints.length) {
      int[] actualCodePoints = new int[i];
      for (int ii = 0; ii < i; ii += 1) {
        actualCodePoints[ii] = codePoints[ii];
      }
      codePoints = actualCodePoints;
    }
    return codePoints;
  }

  public static char[] codePointToChars(int codePoint) {
    if (codePoint >= 0x10000) {
      codePoint -= 0x10000;
      int lower = codePoint & 0x3FF;
      int upper = (codePoint >> 10) & 0x3FF;
      char leftSurrogate = (char) (0xD800 | upper);
      char rightSurrogate = (char) (0xDC00 | lower);
      char[] array = {leftSurrogate, rightSurrogate};
      return array;
    } else {
      char[] array = {(char) codePoint};
      return array;
    }
  }
}
