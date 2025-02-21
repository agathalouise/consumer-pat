package br.com.alelo.consumer.consumerpat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeepCopyUtil {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Method to make a deep copy of an object.
   * This method is thread-safe, and it does not copy the memory address of the object.
   * It creates a new object with the same values as the source object.
   *
   * @param source The object to be copied
   * @param type   The class of the object to be copied
   * @param <T>    The type of the object to be copied
   * @return A new object with the same values as the source object
   */
  public static <T> T deepCopy(T source, Class<T> type) {
    try {
      String json = objectMapper.writeValueAsString(source);
      return objectMapper.readValue(json, type);
    } catch (Exception e) {
      throw new RuntimeException("Error while trying to copy object", e);
    }
  }
}