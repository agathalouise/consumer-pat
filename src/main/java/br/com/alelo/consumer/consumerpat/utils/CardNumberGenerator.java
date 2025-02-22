package br.com.alelo.consumer.consumerpat.utils;

import br.com.alelo.consumer.consumerpat.models.CardInfo;
import br.com.alelo.consumer.consumerpat.models.request.CardInfoRequest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CardNumberGenerator {

  private static Set<Long> generatedCardNumbers = new HashSet<>();


  /**
   * Generates a unique 16-digit card number.
   * <p>
   * This method repeatedly generates a random 16-digit number until a unique number,
   * not already present in the 'generatedCardNumbers' set, is found. The generated
   * number is then added to the set to ensure future uniqueness.
   *
   * @return a unique 16-digit card number
   */
  private static Long generateUniqueCardNumber() {
    Long cardNumber;
    do {
      cardNumber = ThreadLocalRandom.current().nextLong(1000000000000000L, 10000000000000000L);
    } while (generatedCardNumbers.contains(cardNumber)); // Garante que o número não foi gerado antes

    generatedCardNumbers.add(cardNumber);
    return cardNumber;
  }

  /**
   * Generates a new {@link CardInfo} with a unique 16-digit card number and the given initial balance.
   *
   * @param initialBalance the initial balance of the card
   * @return a new {@link CardInfo} with a unique card number and the given initial balance
   */
  public static CardInfoRequest createCard(BigDecimal initialBalance) {
    Long cardNumber = generateUniqueCardNumber();
    return CardInfoRequest.builder().cardNumber(cardNumber).cardBalance(initialBalance).build();
  }
}