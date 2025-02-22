package br.com.alelo.consumer.consumerpat.entities;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long mobilePhoneNumber;
  private Long residencePhoneNumber;
  private Long phoneNumber;
  @NotNull
  private String email;
}
