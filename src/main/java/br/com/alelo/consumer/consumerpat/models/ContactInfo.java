package br.com.alelo.consumer.consumerpat.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serial;
import java.io.Serializable;

@Data
public class ContactInfo implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @Schema(description = "Número de telefone celular")
  private Long mobilePhoneNumber;

  @Schema(description = "Número de telefone residencial")
  private Long residencePhoneNumber;

  @Schema(description = "Número de telefone adicional")
  private Long phoneNumber;

  @Email
  @Schema(description = "Email")
  private String email;
}
