package br.com.alelo.consumer.consumerpat.respositories;

import br.com.alelo.consumer.consumerpat.entities.ConsumerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<ConsumerEntity, String> {
  Page<ConsumerEntity> findAll(Pageable pageable);

  Optional<ConsumerEntity> findByDocumentNumber(String docNumber);

  @Query("""
       SELECT c FROM ConsumerEntity c 
       WHERE c.drugstoreCard.cardNumber = :cardNumber 
          OR c.foodCard.cardNumber = :cardNumber 
          OR c.fuelCard.cardNumber = :cardNumber
      """)
  Optional<ConsumerEntity> findByAnyCardNumber(@Param("cardNumber") Long cardNumber);

}
