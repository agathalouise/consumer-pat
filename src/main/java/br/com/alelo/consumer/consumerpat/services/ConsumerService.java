package br.com.alelo.consumer.consumerpat.services;


import br.com.alelo.consumer.consumerpat.entities.ConsumerEntity;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import models.request.ConsumerRequest;
import models.response.ConsumerResponse;
import models.response.PaginatedResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsumerService {

  private final ConsumerRepository repository;

  /**
   * List all consumers in the database, paginated.
   *
   * @param page the page number to be returned. Page numbers are 0-indexed.
   * @param size the maximum number of consumers to be returned
   * @return a paginated response containing the list of consumers in the given page
   * and total counts of pages and elements.
   */
  public PaginatedResponse<ConsumerResponse> listAllConsumers(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<ConsumerEntity> consumerPage = repository.findAll(pageable);

    List<ConsumerResponse> consumers = consumerPage.map(consumerEntity -> {
      var consumerResponse = new ConsumerResponse();
      BeanUtils.copyProperties(consumerEntity, consumerResponse);
      return consumerResponse;
    }).getContent();

    return new PaginatedResponse<>(consumers, consumerPage.getTotalPages(), consumerPage.getTotalElements());
  }

  /**
   * Creates a new consumer in the database if not already existing.
   * Validates the ConsumerRequest. If valid, creates and saves a new ConsumerEntity.
   *
   * @param consumerRequest contains consumer details
   * @throws IllegalArgumentException if consumer exists or validation fails
   */
  public void createConsumer(ConsumerRequest consumerRequest) {
    var optionalConsumer = repository.findByDocNumber(consumerRequest.getDocumentNumber());

    if (optionalConsumer.isPresent()) {
      throw new IllegalArgumentException("A consumer with this document number already exists.");
    }

    validateConsumer(consumerRequest);

    ConsumerEntity consumerEntity = new ConsumerEntity();
    BeanUtils.copyProperties(consumerRequest, consumerEntity);
    repository.save(consumerEntity);
  }

  /**
   * Updates an existing consumer in the database.
   *
   * <p>
   * Locates the consumer by the provided document number. If found, updates the
   * consumer's details from the given request. The food, fuel and drugstore
   * balances are not updated.
   *
   * @param updateRequest the consumer request containing the updated details
   * @throws IllegalArgumentException if consumer is not found
   */
  public void updateConsumer(ConsumerRequest updateRequest) {
    ConsumerEntity existingConsumerEntity = repository.findByDocNumber(updateRequest.getDocumentNumber())
        .orElseThrow(() -> new IllegalArgumentException("Consumer not found"));

    // removendo foodBalance, fuelBalance, drugstoreBalance para não sobreescrever o saldo.
    BeanUtils.copyProperties(updateRequest, existingConsumerEntity,
        "foodBalance", "fuelBalance", "drugstoreBalance");

    repository.save(existingConsumerEntity);
  }

  /**
   * Validates a ConsumerRequest object by ensuring it meets specific criteria.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @throws IllegalArgumentException if the consumer object is null,
   *                                  if any required fields are missing,
   *                                  if no phone numbers are provided,
   *                                  or if no card numbers are present.
   */
  private void validateConsumer(ConsumerRequest consumer) {
    if (consumer == null) {
      throw new IllegalArgumentException("ConsumerRequest object cannot be null.");
    }

    if (!hasAllRequiredFields(consumer)) {
      throw new IllegalArgumentException("All required fields must be filled.");
    }

    if (!hasAtLeastOnePhone(consumer)) {
      throw new IllegalArgumentException("At least one phone number must be provided.");
    }

    if (!hasAtLeastOneCard(consumer)) {
      throw new IllegalArgumentException("At least one card must be provided.");
    }
  }

  /**
   * Returns true if all required fields in the given ConsumerRequest object are filled, false otherwise.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @return true if all required fields are filled, false otherwise
   */
  private boolean hasAllRequiredFields(ConsumerRequest consumer) {
    return Stream.of(
        consumer.getName(),
        consumer.getDocumentNumber(),
        consumer.getBirthDate(),
        consumer.getEmail(),
        consumer.getStreet(),
        consumer.getNumber(),
        consumer.getCity(),
        consumer.getCountry(),
        consumer.getPostalCode()
    ).allMatch(Objects::nonNull);
  }

  /**
   * Returns true if at least one phone number is filled in the given ConsumerRequest object,
   * false otherwise.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @return true if at least one phone number is filled, false otherwise
   */
  private boolean hasAtLeastOnePhone(ConsumerRequest consumer) {
    return Stream.of(
        consumer.getMobilePhoneNumber(),
        consumer.getResidencePhoneNumber(),
        consumer.getPhoneNumber()
    ).anyMatch(Objects::nonNull);
  }

  /**
   * Returns true if at least one card number is filled in the given ConsumerRequest object,
   * false otherwise.
   *
   * @param consumer the ConsumerRequest object to be validated
   * @return true if at least one card number is filled, false otherwise
   */
  private boolean hasAtLeastOneCard(ConsumerRequest consumer) {
    return Stream.of(
        consumer.getFoodCardNumber(),
        consumer.getFuelCardNumber(),
        consumer.getDrugstoreCardNumber()
    ).anyMatch(Objects::nonNull);
  }
}
