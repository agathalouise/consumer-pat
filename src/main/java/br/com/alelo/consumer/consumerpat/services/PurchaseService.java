package br.com.alelo.consumer.consumerpat.services;

import br.com.alelo.consumer.consumerpat.entities.EstablishmentTypesEntity;
import br.com.alelo.consumer.consumerpat.entities.ExtractEntity;
import br.com.alelo.consumer.consumerpat.models.request.PurchaseRequest;
import br.com.alelo.consumer.consumerpat.respositories.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.respositories.EstablishmentTypesRepository;
import br.com.alelo.consumer.consumerpat.respositories.ExtractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import static br.com.alelo.consumer.consumerpat.enums.TransactionType.BALANCE_DEDUCTED;
import static br.com.alelo.consumer.consumerpat.utils.Utils.generateUUID;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseService {

  private final ConsumerRepository repository;
  private final ExtractRepository extractRepository;
  private final EstablishmentTypesRepository establishmentTypesRepository;


  /**
   * Process a purchase made by a consumer.
   *
   * <p>
   * This method receives a purchase request containing the product description, card number,
   * establishment type code, and value. It then:
   * <ol>
   *   <li>validates the value</li>
   *   <li>finds the consumer by card number</li>
   *   <li>finds the establishment type by code</li>
   *   <li>applies the discount or addition for the establishment type</li>
   *   <li>deducts the balance from the card</li>
   *   <li>saves the new balance in the database</li>
   *   <li>saves an extract of the purchase in the database</li>
   * </ol>
   * <p>
   *
   * @param request the purchase request containing the product description, card number,
   *                establishment type code, and value
   */
  public void processPurchase(PurchaseRequest request) {
    log.traceEntry("processPurchase(request={})", request);

    var value = request.getValue();
    validateValue(value);

    //find consumer by card number
    var consumer = repository.findByAnyCardNumber(request.getCardNumber())
        .orElseThrow(() -> new IllegalArgumentException("Consumer not found for card number"));

    //find type by establishment type code
    var listEstablishment = establishmentTypesRepository.findAll();
    var establishment = validateEstablishmentAndReturn(request.getEstablishmentTypeCode(), listEstablishment);

    //apply discount or additions
    BigDecimal adjustment = getAdjustment(establishment, value);
    value = value.add(adjustment);

    //deduct balance
    var card = consumer.getCardByNumber(request.getCardNumber(), establishment);
    validateBalance(card.getCardBalance(), value);
    card.deductBalance(value);

    log.debug("Updating balance in db");
    repository.save(consumer);
    log.debug("update balance sucessfully");

    saveExtract(request, value);

    log.traceExit("processPurchase(request): void");
  }


  /**
   * Calculates the adjustment to be applied to the purchase value based on the establishment type.
   *
   * <p>
   * For establishment type with ID 1, a 10% discount is applied.
   * For type ID 3, a 35% addition is applied.
   * For other types, no adjustment is made.
   * </p>
   *
   * @param typeEntity the entity representing the type of establishment
   * @param value      the original value of the purchase
   * @return the calculated adjustment to be applied to the value
   */
  private BigDecimal getAdjustment(EstablishmentTypesEntity typeEntity, BigDecimal value) {
    log.traceEntry("getAdjustment(typeEntity={}, value={})", typeEntity, value);

    return log.traceExit("getAdjustment(typeEntity, value ): {}",
        switch (typeEntity.getId()) {
          case 1 -> calculatePercentage(value, BigDecimal.valueOf(-10)); //Alimentação - 10% de desconto
          case 3 -> calculatePercentage(value, BigDecimal.valueOf(35)); //Combustível - 35% de acréscimo
          default -> BigDecimal.ZERO;
        });
  }

  /**
   * Calculates a percentage of the given value.
   *
   * @param value      the value to which the percentage is to be applied
   * @param percentage the percentage to apply, expressed as a decimal value (e.g. 10 for 10%)
   * @return the result of applying the given percentage to the given value
   */
  private BigDecimal calculatePercentage(BigDecimal value, BigDecimal percentage) {
    log.traceEntry("calculatePercentage(value={}, percentage={})", value, percentage);

    return log.traceExit("calculatePercentage(value, percentage ): {}",
        value.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR));
  }

  /**
   * Verifies that the provided balance is sufficient to cover the provided value.
   * If the balance is insufficient, an exception is thrown.
   *
   * @param balance the balance to be checked
   * @param value   the value to be compared against the balance
   * @throws IllegalArgumentException if the balance is insufficient
   */
  private void validateBalance(BigDecimal balance, BigDecimal value) {
    log.traceEntry("validateBalance(balance={}, value={})", balance, value);

    if (balance.compareTo(value) < 0) {
      log.warn("Insufficient funds.");
      throw new IllegalArgumentException("Insufficient funds.");
    }

    log.traceExit("processPurchase(balance, value): void");
  }

  /**
   * Verifies if the establishment type is valid .
   *
   * @param establishmentTypeCode the code of the establishment type to be verified
   * @param listTypeEntities      the list of all types of establishments
   * @return the type if the establishment type is valid for the given card number
   * @throws IllegalArgumentException if the establishment type is not valid for the given card number
   */
  private EstablishmentTypesEntity validateEstablishmentAndReturn(int establishmentTypeCode, List<EstablishmentTypesEntity> listTypeEntities) {
    log.traceEntry("validateEstablishmentAndReturn(establishmentTypeCode={}, listTypeEntities={})",
        establishmentTypeCode, listTypeEntities);

    return log.traceExit("validateEstablishmentAndReturn(establishmentTypeCode, listTypeEntities ): {}",
        listTypeEntities.stream()
            .filter(t -> t.getId() == establishmentTypeCode)
            .findFirst()
            .orElseThrow(() -> {
              log.warn("invalid establishment type: {}", establishmentTypeCode);
              return new IllegalArgumentException("The establishment type is not valid");
            }));
  }


  /**
   * Saves a new Extract entity, which represents a purchase made by a consumer.
   *
   * @param request the purchase request containing the product description and card number
   * @param value   the value of the purchase
   */
  private void saveExtract(PurchaseRequest request, BigDecimal value) {
    log.traceEntry("saveExtract(request={}, value={})", request, value);

    log.debug("Updating extract in db");
    extractRepository.save(ExtractEntity.builder()
        .id(generateUUID())
        .description(request.getProductDescription())
        .dateOfEvent(new Date())
        .cardNumber(request.getCardNumber())
        .amount(value)
        .establishmentId(request.getEstablishmentTypeCode())
        .establishmentName(request.getEstablishmentName())
        .transactionType(BALANCE_DEDUCTED)
        .build());

    log.debug("update extract successfully");

    log.traceExit("saveExtract(request): void");
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
