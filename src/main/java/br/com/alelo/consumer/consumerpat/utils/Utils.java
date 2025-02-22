package br.com.alelo.consumer.consumerpat.utils;

import java.util.UUID;

public class Utils {

  /**
   * Generates a random UUID as a string without dashes.
   *
   * @return a string representation of a random UUID with all dashes removed
   */
  public static String generateUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
