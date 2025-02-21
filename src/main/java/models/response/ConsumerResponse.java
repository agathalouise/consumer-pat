package models.response;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumerResponse implements Serializable {

    private int id;
    private String name;
    private String documentNumber;
    private Date birthDate;

    //contacts
    private Long mobilePhoneNumber;
    private Long residencePhoneNumber;
    private Long phoneNumber;
    private String email;

    //Address
    private String street;
    private int number;
    private String city;
    private String country;
    private int portalCode;

    //cards
    private Long foodCardNumber;
    private BigDecimal foodCardBalance;
    private Long fuelCardNumber;
    private BigDecimal fuelCardBalance;
    private Long drugstoreNumber;
    private BigDecimal drugstoreCardBalance;

}
