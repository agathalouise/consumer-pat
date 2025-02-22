package br.com.alelo.consumer.consumerpat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
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
  @NotEmpty private String email;

}
