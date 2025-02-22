package br.com.alelo.consumer.consumerpat.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CONSUMER_DETAILS")
public class ConsumerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull private String name;
    @NotNull private String documentNumber;
    @NotNull private Date birthDate;

    @Embedded
    private Contact contact;

    @Embedded
    private Address address;

    @Embedded
    private Card foodCard;

    @Embedded
    private Card fuelCard;

    @Embedded
    private Card drugstoreCard;

    public Card getCardByNumber(Long cardNumber) {
        if (drugstoreCard != null && Objects.equals(drugstoreCard.getCardNumber(), cardNumber)) return drugstoreCard;
        if (foodCard != null && Objects.equals(foodCard.getCardNumber(), cardNumber)) return foodCard;
        if (fuelCard != null && Objects.equals(fuelCard.getCardNumber(), cardNumber)) return fuelCard;
        return null;
    }
}

