package br.com.alelo.consumer.consumerpat.respository;

import br.com.alelo.consumer.consumerpat.entities.ExtractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractRepository extends JpaRepository<ExtractEntity, Integer> {
}
