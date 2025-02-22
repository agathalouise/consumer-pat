package br.com.alelo.consumer.consumerpat.models.response;

import lombok.*;
import br.com.alelo.consumer.consumerpat.models.AddressInfo;
import br.com.alelo.consumer.consumerpat.models.CardInfo;
import br.com.alelo.consumer.consumerpat.models.ContactInfo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumerResponse implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String documentNumber;
    private Date birthDate;

    private ContactInfo contactInfo;

    private AddressInfo address;

    private CardInfo foodCard;
    private CardInfo fuelCard;
    private CardInfo drugstoreCard;

}
