package br.com.alelo.consumer.consumerpat.entities;

import br.com.alelo.consumer.consumerpat.enums.CardType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @NonNull
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


    public void addBalance(CardType cardType, BigDecimal value) {
        switch (cardType) {
            case DRUGSTORE -> drugstoreCardBalance = drugstoreCardBalance.add(value);
            case FOOD -> foodCardBalance = foodCardBalance.add(value);
            case FUEL -> fuelCardBalance = fuelCardBalance.add(value);
        }
    }

}
