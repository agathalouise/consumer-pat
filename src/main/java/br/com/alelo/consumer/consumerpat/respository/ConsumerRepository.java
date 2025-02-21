package br.com.alelo.consumer.consumerpat.respository;

import br.com.alelo.consumer.consumerpat.entities.ConsumerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<ConsumerEntity, Integer> {
    Page<ConsumerEntity> findAll(Pageable pageable);

    Optional<ConsumerEntity> findByDocNumber(String docNumber);

    @Query("""
                SELECT c FROM Consumer c 
                WHERE c.drugstoreNumber = :cardNumber 
                   OR c.foodCardNumber = :cardNumber 
                   OR c.fuelCardNumber = :cardNumber
            """)
    Optional<ConsumerEntity> findByAnyCardNumber(@Param("cardNumber") Long cardNumber);

}
