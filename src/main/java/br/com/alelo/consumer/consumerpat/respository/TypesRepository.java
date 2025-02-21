package br.com.alelo.consumer.consumerpat.respository;

import br.com.alelo.consumer.consumerpat.entities.TypeEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypesRepository extends JpaRepository <TypeEntity, Integer> {

    @Cacheable("types")
    List<TypeEntity> findAll();
}
