package br.com.alelo.consumer.consumerpat.services;

import br.com.alelo.consumer.consumerpat.enums.CardType;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BalanceService {

  private final ConsumerRepository repository;

  /**
   * Credits a specified value to a consumer's card.
   * <p>
   * This method locates the consumer by the provided card number.
   * If the consumer is found, the specified value is credited to the card balance.
   * If the card number is not found, an exception is thrown.
   *
   * @param cardNumber the number of the card to be credited
   * @param value      the value to be credited to the card
   * @throws IllegalArgumentException if the card number is not associated with any consumer
   */
  public void addBalance(Long cardNumber, BigDecimal value) {
    validateValue(value);

    repository.findByAnyCardNumber(cardNumber)
        .ifPresentOrElse(
            consumer -> {
              CardType cardType = CardType.fromCardNumber(consumer, cardNumber);

              consumer.addBalance(cardType, value);
              repository.save(consumer);
            },
            // Handle the case where the card number is not found
            () -> {
              throw new IllegalArgumentException("Card not found: " + cardNumber);
            }
        );
  }


  /**
   * Verifies if the value is valid to be added to a card balance.
   *
   * @param value the value to be validated
   * @throws IllegalArgumentException if the value is not valid
   */
  private void validateValue(BigDecimal value) {
    if (value.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("The value must be equal or greater than $0.01: " + value);
    }
  }

}
