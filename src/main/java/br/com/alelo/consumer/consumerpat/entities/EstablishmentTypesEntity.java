package br.com.alelo.consumer.consumerpat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ESTABLISHMENT_TYPE")
public class EstablishmentTypesEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  private Integer id;
  private String name;

}
