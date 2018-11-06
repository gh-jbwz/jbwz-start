package com.jbwz.lemon.server.security.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author yyh
 */
public class RandomUtil {

  private static final String abc = "abcdefghkmnpqrstvxyz_";
  private static final String number = "23567890";
  private static final String ABC = "ABCDEFGHIGLNPQRSTUVXYZ";
  private static final char[] RANDOM_LETTER = ArrayUtils.addAll(ArrayUtils
      .addAll(abc.toCharArray(), ABC.toCharArray()), number.toCharArray());

  public static String nextNumberCaptcha() {
    String s = "";
    s = RandomStringUtils.random(6, false, true);
    return s;
  }

  public static String randomUserName() {
    String s = "";
    s = RandomStringUtils.random(
        13, 0, RANDOM_LETTER.length, true, true, RANDOM_LETTER);
    return s;
  }

  public static String nextBothCaptcha() {
    String s = "";
    s = RandomStringUtils.random(6, true, true);
    return s;
  }
}
