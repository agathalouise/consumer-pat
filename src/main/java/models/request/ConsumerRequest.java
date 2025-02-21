package models.request;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static br.com.alelo.consumer.consumerpat.constants.ConsumerConstants.DEVE_TER_16_DÍGITOS;

@Data
public class ConsumerRequest {

    private String documentNumber;
    private String name;
    private Date birthDate;

    // Contatos
    private Long mobilePhoneNumber;
    private Long residencePhoneNumber;
    private Long phoneNumber;
    @Email private String email;

    // Endereço
    private String street;
    private int number;
    private String city;
    private String country;
    private int postalCode;

    @Min(value = 1000000000000000L, message = DEVE_TER_16_DÍGITOS)
    @Max(value = 9999999999999999L, message = DEVE_TER_16_DÍGITOS)
    private Long foodCardNumber;
    private String foodCardBalance;

    @Min(value = 1000000000000000L, message = DEVE_TER_16_DÍGITOS)
    @Max(value = 9999999999999999L, message = DEVE_TER_16_DÍGITOS)
    private Long fuelCardNumber;
    private String fuelCardBalance;

    @Min(value = 1000000000000000L, message = DEVE_TER_16_DÍGITOS)
    @Max(value = 9999999999999999L, message = DEVE_TER_16_DÍGITOS)
    private Long drugstoreCardNumber;
    private String drugstoreCardBalance;

}
