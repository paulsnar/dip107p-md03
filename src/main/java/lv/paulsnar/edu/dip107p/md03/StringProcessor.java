package lv.paulsnar.edu.dip107p.md03;

public final class StringProcessor {
  private StringProcessor() {
  }

  public static String process(String string) {
    StringBuilder result = new StringBuilder(string.length());
    int[] codePoints = CodePoints.extractFrom(string);

    int left = 0, right = codePoints.length - 1;
    while (left <= right) {
      result.append(CodePoints.codePointToChars(codePoints[right]));
      if (left != right) {
        result.append(CodePoints.codePointToChars(codePoints[left]));
      }

      right -= 1;
      left += 1;
    }

    return result.toString();
  }
}
