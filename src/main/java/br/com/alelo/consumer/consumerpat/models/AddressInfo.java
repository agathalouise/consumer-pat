package br.com.alelo.consumer.consumerpat.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AddressInfo implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Schema(description = "Rua")
  private String street;

  @Schema(description = "Número da residência")
  private Integer number;

  @Schema(description = "Cidade")
  private String city;

  @Schema(description = "País")
  private String country;

  @Schema(description = "Código postal")
  private Integer postalCode;

}
