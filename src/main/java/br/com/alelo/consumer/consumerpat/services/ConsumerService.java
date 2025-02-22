package br.com.alelo.consumer.consumerpat.services;


import br.com.alelo.consumer.consumerpat.entities.ConsumerEntity;
import br.com.alelo.consumer.consumerpat.exceptions.DataIntegratyViolationException;
import br.com.alelo.consumer.consumerpat.exceptions.NotFoundException;
import br.com.alelo.consumer.consumerpat.mappers.ConsumerMapper;
import br.com.alelo.consumer.consumerpat.respositories.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import br.com.alelo.consumer.consumerpat.models.request.ConsumerRequest;
import br.com.alelo.consumer.consumerpat.models.response.ConsumerResponse;
import br.com.alelo.consumer.consumerpat.models.response.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsumerService {

  private final ConsumerRepository repository;
  private final ConsumerMapper mapper;
  private final ValidateConsumerService validateConsumer;

  /**
   * List all consumers in the database, paginated.
   *
   * @param page the page number to be returned. Page numbers are 0-indexed.
   * @param size the maximum number of consumers to be returned
   * @return a paginated response containing the list of consumers in the given page
   * and total counts of pages and elements.
   */
  public PaginatedResponse<ConsumerResponse> listAllConsumers(int page, int size) {
    log.traceEntry("listAllConsumers(page={}, size={})", page, size);

    Pageable pageable = PageRequest.of(page, size);
    Page<ConsumerEntity> consumerPage = repository.findAll(pageable);
    log.debug("Found {} consumers in page {}", consumerPage.getTotalElements(), page);

    List<ConsumerResponse> consumers = consumerPage
        .map(consumerEntity -> mapper.toConsumerResponse(consumerEntity)).getContent();

    return log.traceExit("listAllConsumers(page, size): {}",
        new PaginatedResponse<>(consumers, consumerPage.getTotalPages(), consumerPage.getTotalElements()));
  }

  /**
   * Creates a new consumer in the database if not already existing.
   * Validates the ConsumerRequest. If valid, creates and saves a new ConsumerEntity.
   *
   * @param consumerRequest contains consumer details
   * @throws IllegalArgumentException if consumer exists or validation fails
   */
  public void createConsumer(ConsumerRequest consumerRequest) {
    log.traceEntry("createConsumer(consumerRequest={})", consumerRequest);

    var optionalConsumer = repository.findByDocNumber(consumerRequest.getDocumentNumber());

    if (optionalConsumer.isPresent()) {
      log.warn("Consumer with id {} already exists", optionalConsumer.get().getId());
      throw new DataIntegratyViolationException("A consumer with this id already exists.");
    }

    validateConsumer.validate(consumerRequest);

    log.debug("saving consumer in db");
    repository.save(mapper.toConsumerEntity(consumerRequest));
    log.debug("consumer saved sucessfully");

    log.traceExit("createConsumer(consumerRequest): void");
  }

  /**
   * Updates an existing consumer in the database.
   *
   * <p>
   * Locates the consumer by the provided id. If found, updates the
   * consumer's details from the given request. The food, fuel and drugstore
   * balances are not updated.
   *
   * @param updateRequest the consumer request containing the updated details
   * @throws IllegalArgumentException if consumer is not found
   */
  public void updateConsumer(ConsumerRequest updateRequest, String id) {
    log.traceEntry("updateConsumer(updateRequest={}, id={})", updateRequest, id);

    var existingConsumerEntity = repository.findById(UUID.fromString(id))
        .orElseThrow(() -> {
          log.warn("Consumer with id {} not found", id);
          return new NotFoundException("Consumer not found in database");
        });

    updateRequest.getFoodCard().setCardBalance(null);
    updateRequest.getFuelCard().setCardBalance(null);
    updateRequest.getDrugstoreCard().setCardBalance(null);

    existingConsumerEntity = mapper.toConsumerEntity(updateRequest);

    log.debug("updating consumer in db");
    repository.save(existingConsumerEntity);
    log.debug("Consumer with id {} updated successfully", id);

    log.traceExit("updateConsumer(updateRequest, id): void");
  }
}
