package br.com.alelo.consumer.consumerpat.services;

import br.com.alelo.consumer.consumerpat.entities.ConsumerEntity;
import br.com.alelo.consumer.consumerpat.entities.ExtractEntity;
import br.com.alelo.consumer.consumerpat.entities.TypeEntity;
import br.com.alelo.consumer.consumerpat.enums.CardType;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.respository.ExtractRepository;
import br.com.alelo.consumer.consumerpat.respository.TypesRepository;
import lombok.RequiredArgsConstructor;
import models.request.PurchaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseService {

  private final ConsumerRepository repository;
  private final ExtractRepository extractRepository;
  private final TypesRepository typesRepository;

  /**
   * Process a purchase request.
   *
   * <p>This method will locate the consumer by the provided card number.
   * If the consumer is found, the purchase value will be deducted from the
   * consumer's card balance. The {@code establishmentTypeCode} is used to
   * determine if a discount or surcharge should be applied to the purchase.
   *
   * @param request the purchase request containing the product description,
   *                card number, establishment type and purchase value
   */
  public void processPurchase(PurchaseRequest request) {
    List<TypeEntity> listTypeEntities = typesRepository.findAll();
    TypeEntity typeEntity = validateEstablishmentAndReturn(request.getEstablishmentTypeCode(), listTypeEntities);
    var value = request.getValue();

    ConsumerEntity consumerEntity = repository.findByAnyCardNumber(request.getCardNumber())
        .orElseThrow(() -> new IllegalArgumentException("Consumer not found for card number: " + request.getCardNumber()));

    BigDecimal adjustment = switch (typeEntity.getId()) {
      case 1 -> calculatePercentage(value, BigDecimal.valueOf(-10)); //Alimentação - 10% de desconto
      case 3 -> calculatePercentage(value, BigDecimal.valueOf(35)); //Combustível - 35% de acréscimo
      default -> BigDecimal.ZERO;
    };

    value = value.add(adjustment);

    deductBalance(consumerEntity,request.getCardNumber(), value);
    repository.save(consumerEntity);

    saveExtract(request, value);
  }

  /**
   * Deduct a specified purchase value from a consumer's card balance.
   *
   * <p>This method locates the consumer by the provided card number.
   * If the consumer is found, the specified value is deducted from the card balance.
   *
   * @param consumer the consumer entity
   * @param cardNumber the number of the card to be deducted
   * @param value the value to be deducted from the card
   */
  private void deductBalance(ConsumerEntity consumer, Long cardNumber, BigDecimal value) {
    //todo acho que nao preciso disso
    CardType cardType = CardType.fromCardNumber(consumer, cardNumber);

    switch (cardType) {
      case FOOD -> {
        validateBalance(consumer.getFoodCardBalance(), value);
        consumer.setFoodCardBalance(consumer.getFoodCardBalance().subtract(value));;
      }
      case DRUGSTORE -> {
        validateBalance(consumer.getDrugstoreCardBalance(), value);
        consumer.setDrugstoreCardBalance(consumer.getDrugstoreCardBalance().subtract(value));
      }
      case FUEL -> {
        validateBalance(consumer.getFuelCardBalance(), value);
        consumer.setFuelCardBalance(consumer.getFuelCardBalance().subtract(value));
      }
    }
  }

  /**
   * Verifies that the provided balance is sufficient to cover the provided value.
   * If the balance is insufficient, an exception is thrown.
   *
   * @param balance the balance to be checked
   * @param value the value to be compared against the balance
   * @throws IllegalArgumentException if the balance is insufficient
   */
  private void validateBalance(BigDecimal balance, BigDecimal value) {
    if (balance.compareTo(value) < 0) {
      throw new IllegalArgumentException("Insufficient funds.");
    }
  }

  /**
   * Verifies if the establishment type is valid .
   *
   * @param establishmentTypeCode the code of the establishment type to be verified
   * @param listTypeEntities             the list of all types of establishments
   * @return the type if the establishment type is valid for the given card number
   * @throws IllegalArgumentException if the establishment type is not valid for the given card number
   */
  private TypeEntity validateEstablishmentAndReturn(int establishmentTypeCode, List<TypeEntity> listTypeEntities) {
    return listTypeEntities.stream()
        .filter(t -> t.getId() == establishmentTypeCode)
        .findFirst()
        .orElseThrow(() -> {
          throw new IllegalArgumentException("The establishment type is not valid");
        });
  }

  /**
   * Calculates a percentage of the given value.
   *
   * @param value       the value to which the percentage is to be applied
   * @param percentage  the percentage to apply, expressed as a decimal value (e.g. 10 for 10%)
   * @return the result of applying the given percentage to the given value
   */
  private BigDecimal calculatePercentage(BigDecimal value, BigDecimal percentage) {
    return value.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR);
  }

  /**
   * Saves a new Extract entity, which represents a purchase made by a consumer.
   *
   * @param request the purchase request containing the product description and card number
   * @param value   the value of the purchase
   */
  private void saveExtract(PurchaseRequest request, BigDecimal value) {
    extractRepository.save(ExtractEntity.builder()
        .productDescription(request.getProductDescription())
        .dateBuy(new Date())
        .cardNumber(request.getCardNumber())
        .amount(value).build());
  }
}
