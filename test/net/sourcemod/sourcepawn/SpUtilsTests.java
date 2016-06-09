package net.sourcemod.sourcepawn;

import net.sourcemod.sourcepawn.lexer.SpUtils;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;

public class SpUtilsTests {

  @Test
  public void testParseBoolean() {
    assertTrue(SpUtils.parseBoolean("true").equals(BigInteger.ONE));
    assertTrue(SpUtils.parseBoolean("false").equals(BigInteger.ZERO));
  }

  @Test
  public void testParseBinary() {
    assertTrue(SpUtils.parseBinary("0b").equals(BigInteger.ZERO));
    assertTrue(SpUtils.parseBinary("0b0").equals(BigInteger.ZERO));
    assertTrue(SpUtils.parseBinary("0b1").equals(BigInteger.ONE));
    assertTrue(SpUtils.parseBinary("0b1111").equals(BigInteger.valueOf(0b1111)));
    assertTrue(SpUtils.parseBinary("0b0101").equals(BigInteger.valueOf(0b0101)));
    assertTrue(SpUtils.parseBinary("0b1010").equals(BigInteger.valueOf(0b1010)));
    assertTrue(SpUtils.parseBinary("0b1010").equals(BigInteger.valueOf(0b1010)));
    StringBuilder builder = new StringBuilder("0b00000000");
    for (int i = 2; i <= 18; i+=2) {
      builder.insert(i, '_');
      assertTrue(SpUtils.parseBinary(builder).equals(BigInteger.ZERO));
    }
  }

  @Test
  public void testParseOctal() {
    assertTrue(SpUtils.parseOctal("0o").equals(BigInteger.ZERO));
    assertTrue(SpUtils.parseOctal("0o0").equals(BigInteger.ZERO));
    assertTrue(SpUtils.parseOctal("0o1").equals(BigInteger.ONE));
    assertTrue(SpUtils.parseOctal("0o1111").equals(BigInteger.valueOf(01111)));
    assertTrue(SpUtils.parseOctal("0o0101").equals(BigInteger.valueOf(00101)));
    assertTrue(SpUtils.parseOctal("0o1010").equals(BigInteger.valueOf(01010)));
    assertTrue(SpUtils.parseOctal("0o1010").equals(BigInteger.valueOf(01010)));
    StringBuilder builder = new StringBuilder("0o00000000");
    for (int i = 2; i <= 18; i += 2) {
      builder.insert(i, '_');
      assertTrue(SpUtils.parseOctal(builder).equals(BigInteger.ZERO));
    }
  }

  @Test
  public void testParseDecimal() {
    assertTrue(SpUtils.parseDecimal("0").equals(BigInteger.ZERO));
    assertTrue(SpUtils.parseDecimal("1").equals(BigInteger.ONE));
    assertTrue(SpUtils.parseDecimal("1111").equals(BigInteger.valueOf(1111)));
    assertTrue(SpUtils.parseDecimal("0101").equals(BigInteger.valueOf(101)));
    assertTrue(SpUtils.parseDecimal("1010").equals(BigInteger.valueOf(1010)));
    assertTrue(SpUtils.parseDecimal("1010").equals(BigInteger.valueOf(1010)));
    StringBuilder builder = new StringBuilder("00000000");
    for (int i = 1; i < 16; i += 2) {
      builder.insert(i, '_');
      assertTrue(SpUtils.parseDecimal(builder).equals(BigInteger.ZERO));
    }
  }

  @Test
  public void testParseHexadecimal() {
    assertTrue(SpUtils.parseHexadecimal("0x").equals(BigInteger.ZERO));
    assertTrue(SpUtils.parseHexadecimal("0x0").equals(BigInteger.ZERO));
    assertTrue(SpUtils.parseHexadecimal("0x1").equals(BigInteger.ONE));
    assertTrue(SpUtils.parseHexadecimal("0x1111").equals(BigInteger.valueOf(0x1111)));
    assertTrue(SpUtils.parseHexadecimal("0x0101").equals(BigInteger.valueOf(0x0101)));
    assertTrue(SpUtils.parseHexadecimal("0x1010").equals(BigInteger.valueOf(0x1010)));
    assertTrue(SpUtils.parseHexadecimal("0x1010").equals(BigInteger.valueOf(0x1010)));
    StringBuilder builder = new StringBuilder("0x00000000");
    for (int i = 2; i <= 18; i += 2) {
      builder.insert(i, '_');
      assertTrue(SpUtils.parseHexadecimal(builder).equals(BigInteger.ZERO));
    }
  }

  public SpUtilsTests() {
  }

}
