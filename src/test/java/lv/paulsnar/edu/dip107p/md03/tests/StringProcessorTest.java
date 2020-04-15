package lv.paulsnar.edu.dip107p.md03.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lv.paulsnar.edu.dip107p.md03.StringProcessor;

class StringProcessorTest {
  @Test
  void testBasicStringProcessing() {
    assertEquals("kMīatna p", StringProcessor.process("Man patīk"));
    assertEquals("ešdoāklo", StringProcessor.process("šokolāde"));
    assertEquals("cab", StringProcessor.process("abc"));
    assertEquals("dacb", StringProcessor.process("abcd"));
    assertEquals("a", StringProcessor.process("a"));
    assertEquals("", StringProcessor.process(""));
  }

  @Test
  void testUnicodeAwareStringProcessing() {
    assertEquals("🐝🦋🐺", StringProcessor.process("🦋🐺🐝"));
    assertEquals("🐌🦋🐝🐺", StringProcessor.process("🦋🐺🐝🐌"));
  }
}
