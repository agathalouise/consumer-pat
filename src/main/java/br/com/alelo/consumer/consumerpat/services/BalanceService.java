package br.com.alelo.consumer.consumerpat.services;

import br.com.alelo.consumer.consumerpat.entities.ExtractEntity;
import br.com.alelo.consumer.consumerpat.exceptions.NotFoundException;
import br.com.alelo.consumer.consumerpat.respositories.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.respositories.ExtractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static br.com.alelo.consumer.consumerpat.enums.TransactionType.BALANCE_ADDED;
import static br.com.alelo.consumer.consumerpat.utils.Utils.generateUUID;


@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BalanceService {

  private final ConsumerRepository repository;
  private final ExtractRepository extractRepository;

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
    log.traceEntry("addBalance(cardNumber, value={})", value);

    validateValue(value);

    var consumer = repository.findByAnyCardNumber(cardNumber)
        .orElseThrow(() -> {
          log.warn("Card not found");
          return new NotFoundException("Card not found");
        });

    var card = consumer.getCardByNumber(cardNumber);
    card.addBalance(value);

    log.debug("Updating balance in db");
    repository.save(consumer);
    log.debug("update balance sucessfully");

    //salvando a operação de recarga no extrato
    saveExtract(cardNumber, value);

    log.traceExit("addBalance(cardNumber, value): void");
  }

  /**
   * Saves a new Extract entity, which represents a recharge operation made by a consumer.
   *
   * @param cardNumber the number of the card to which the recharge is to be applied
   * @param value      the value of the recharge
   */
  private void saveExtract(Long cardNumber, BigDecimal value) {
    log.traceEntry("saveExtract(cardNumber, value={})", value);

    log.debug("Updating extract in db");
    extractRepository.save(ExtractEntity.builder()
        .id(generateUUID())
        .description("Recharge of balance")
        .amount(value)
        .cardNumber(cardNumber)
        .dateOfEvent(new Date())
        .transactionType(BALANCE_ADDED)
        .build());

    log.traceExit("saveExtract(cardNumber, value): void");
  }

  /**
   * Verifies if the value is valid to be added to a card balance.
   *
   * @param value the value to be validated
   * @throws IllegalArgumentException if the value is not valid
   */
  private void validateValue(BigDecimal value) {
    log.traceEntry("validateValue(value={})", value);

    if (value.compareTo(BigDecimal.ZERO) <= 0) {
      log.warn("invalid value: {}", value);
      throw new IllegalArgumentException("The value must be equal or greater than $0.01: " + value);
    }

    log.traceExit("validateValue(value): void");
  }

}
