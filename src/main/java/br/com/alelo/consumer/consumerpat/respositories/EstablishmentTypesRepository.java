package br.com.alelo.consumer.consumerpat.respositories;

import br.com.alelo.consumer.consumerpat.entities.EstablishmentTypesEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstablishmentTypesRepository extends JpaRepository <EstablishmentTypesEntity, Integer> {

    @Cacheable("establishment_type")
    List<EstablishmentTypesEntity> findAll();

}
