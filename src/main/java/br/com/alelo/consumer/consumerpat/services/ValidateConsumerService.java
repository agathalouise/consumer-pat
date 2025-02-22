package br.com.alelo.consumer.consumerpat.services;

import lombok.extern.log4j.Log4j2;
import br.com.alelo.consumer.consumerpat.models.request.ConsumerRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class ValidateConsumerService {

  /**
   * Validates a ConsumerRequest object by ensuring it meets specific criteria.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @throws IllegalArgumentException if the consumer object is null,
   *                                  if any required fields are missing,
   *                                  if no phone numbers are provided,
   *                                  or if no card numbers are present.
   */
  public void validate(ConsumerRequest consumer) {
    log.traceEntry("validate(consumer={})", consumer);

    if (Objects.isNull(consumer)) {
      log.warn("ConsumerRequest object is null.");
      throw new IllegalArgumentException("ConsumerRequest object cannot be null.");
    }

    if (Boolean.FALSE.equals(hasAllRequiredFields(consumer))) {
      log.warn("All required fields must be filled.");
      throw new IllegalArgumentException("All required fields must be filled.");
    }

    if (Boolean.FALSE.equals(hasAtLeastOnePhone(consumer))) {
      log.warn("At least one phone number must be provided.");
      throw new IllegalArgumentException("At least one phone number must be provided.");
    }

    if (Boolean.FALSE.equals(hasAtLeastOneCard(consumer))) {
      log.warn("At least one card must be provided.");
      throw new IllegalArgumentException("At least one card must be provided.");
    }

    log.traceExit("validate(consumer): void");
  }

  /**
   * Returns true if all required fields in the given ConsumerRequest object are filled, false otherwise.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @return true if all required fields are filled, false otherwise
   */
  private boolean hasAllRequiredFields(ConsumerRequest consumer) {
    log.traceEntry("hasAllRequiredFields(consumer={})", consumer);

    return log.traceExit("hasAllRequiredFields(consumer): {}", Objects.nonNull(consumer.getName())
        && Objects.nonNull(consumer.getDocumentNumber())
        && Objects.nonNull(consumer.getBirthDate())
        && Objects.nonNull(consumer.getContactInfo().getEmail())
        && Objects.nonNull(consumer.getAddress().getStreet())
        && Objects.nonNull(consumer.getAddress().getNumber())
        && Objects.nonNull(consumer.getAddress().getCity())
        && Objects.nonNull(consumer.getAddress().getCountry())
        && Objects.nonNull(consumer.getAddress().getPostalCode()));
  }

  /**
   * Returns true if at least one phone number is filled in the given ConsumerRequest object,
   * false otherwise.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @return true if at least one phone number is filled, false otherwise
   */
  private boolean hasAtLeastOnePhone(ConsumerRequest consumer) {
    log.traceEntry("hasAtLeastOnePhone(consumer={})", consumer);

    return log.traceExit("hasAtLeastOnePhone(consumer): {}",
        Objects.nonNull(consumer.getContactInfo().getMobilePhoneNumber())
            || Objects.nonNull(consumer.getContactInfo().getResidencePhoneNumber())
            || Objects.nonNull(consumer.getContactInfo().getPhoneNumber()));
  }

  /**
   * Returns true if at least one card number is filled in the given ConsumerRequest object,
   * false otherwise.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @return true if at least one card number is filled, false otherwise
   */
  private boolean hasAtLeastOneCard(ConsumerRequest consumer) {
    log.traceEntry("hasAtLeastOneCard(consumer={})", consumer);

    return log.traceExit("hasAtLeastOneCard(consumer): {}",
        Objects.nonNull(consumer.getFuelCard())
            || Objects.nonNull(consumer.getFoodCard())
            || Objects.nonNull(consumer.getDrugstoreCard()));
  }

}
