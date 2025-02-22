package br.com.alelo.consumer.consumerpat.entities;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotEmpty private String street;
  private int number;
  @NotEmpty private String city;
  @NotEmpty private String country;
  @NotNull private int postalCode;
}
